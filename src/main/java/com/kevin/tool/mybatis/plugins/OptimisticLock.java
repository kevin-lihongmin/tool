package com.kevin.tool.mybatis.plugins;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;


/**
 *  Mybatis 自定义插件，{@link OptimisticLock} 就是一个{@link Plugin}
 *
 *  每一个拦截器可以拦截下面的四种，当前只拦截了三种
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 *
 * @author kevin
 * @date 2020/8/10 22:14
 * @since 1.0.0
 * @see org.apache.ibatis.session.Configuration#interceptorChain
 * @see Plugin#signatureMap 其中：Map<Class<?>, Set<Method>> Class 为所有需要实现乐观锁的Mapper，Set<Method>集合为需要乐观锁的方法集合【原则上是所有写的方法】
 * @see Invocation#proceed() 直接执行被代理对象的方法
 * @see MappedStatement 对应每个mapper中的<select>、<delete> 等封装对象
 */
@SuppressWarnings("ALL")
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    // 两种类型都拦截
    @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class OptimisticLock implements Interceptor {

    /**
     *  乐观锁对应的数据库字段
     */
    private final String VERSION_COLUMN = "version";

    /** {@link Executor} 拦截的方法 */
    private static final String EXECUTOR_MATHOD = "update";

    /** @link ParameterHandler} 拦截的方法 */
    private static final String PARAMETER_HANDLER_METHOD = "setParameters";

    /** {@link StatementHandler} 拦截的方法 */
    private static final String STATEMENT_HANDLER_METHOD = "prepare";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();

        if (EXECUTOR_MATHOD.equals(methodName)) {
            Object[] args = invocation.getArgs();
            // 查看乐观锁执行结果是否正确
            if (args != null && args[0] instanceof MappedStatement) {
                MappedStatement mappedStatement = (MappedStatement)args[0];
                if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {
                    int result = (Integer)invocation.proceed();
                    Object param = invocation.getArgs()[1];
                    BoundSql boundSql = mappedStatement.getBoundSql(param);
                    String sql = boundSql.getSql();
                    String paramJson = JSON.toJSONString(param);
                    if (result == 0) {
                        throw new RuntimeException("[触发乐观锁，更新失败], 失败SQL: " + sql + ", 参数: " + paramJson);
                    }

                    return result;
                }
            }
        } else if (PARAMETER_HANDLER_METHOD.equals(methodName)) {
            // 设置乐观锁入参
            setParamters(invocation);
        } else if (STATEMENT_HANDLER_METHOD.equals(methodName)) {
            // 为sql拼乐观锁字段
            updateSqlAppendVersion(invocation);
        }

        // 最后执行jdk被代理方法的调用
        return invocation.proceed();
    }

    /**
     *  拼接 乐观锁的sql
     * @param invocation 代理信息
     */
    private void updateSqlAppendVersion(Invocation invocation) {
        MetaObject metaObject;StatementHandler routingHandler = (StatementHandler)processTarget(invocation.getTarget());
        metaObject = SystemMetaObject.forObject(routingHandler);
        MetaObject hm = metaObject.metaObjectForProperty("delegate");
        String originalSql = (String)hm.getValue("boundSql.sql");
        boolean locker = resolve(hm, this.VERSION_COLUMN);
        if (!locker) {
            return;
        }

        String builder = new StringBuilder().append(originalSql).append(" AND ").append(this.VERSION_COLUMN).append(" = ?").toString();
        hm.setValue("boundSql.sql", builder);
    }

    /**
     *  设置乐观锁入参值
     *
     * @param invocation 代理信息
     */
    private void setParamters(Invocation invocation) {
        MetaObject metaObject;
        ParameterHandler handler = (ParameterHandler)processTarget(invocation.getTarget());
        metaObject = SystemMetaObject.forObject(handler);
        boolean locker = resolve(metaObject, this.VERSION_COLUMN);
        if (!locker) {
            return;
        }

        BoundSql boundSql = (BoundSql)metaObject.getValue("boundSql");
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap)parameterObject;
            if (!paramMap.containsKey(this.VERSION_COLUMN)) {
                throw new TypeException("All the primitive type parameters must add MyBatis's @Param Annotaion");
            }
        }

        Configuration configuration = ((MappedStatement)metaObject.getValue("mappedStatement")).getConfiguration();
        MetaObject pm = configuration.newMetaObject(parameterObject);
        Object value = pm.getValue(this.VERSION_COLUMN);
        ParameterMapping versionMapping = (new ParameterMapping.Builder(configuration, this.VERSION_COLUMN, Object.class)).build();
        TypeHandler typeHandler = versionMapping.getTypeHandler();
        JdbcType jdbcType = versionMapping.getJdbcType();
        if (value == null && jdbcType == null) {
            jdbcType = configuration.getJdbcTypeForNull();
        }

        int versionLocation = boundSql.getParameterMappings().size() + 1;

        try {
            PreparedStatement ps = (PreparedStatement)invocation.getArgs()[0];
            typeHandler.setParameter(ps, versionLocation, value, jdbcType);
        } catch (SQLException | TypeException var16) {
            throw new TypeException("set parameter 'version' faild, Cause: " + var16, var16);
        }

        if (!Objects.equals(value.getClass(), Long.class) && Objects.equals(value.getClass(), Long.TYPE) && log.isDebugEnabled()) {
            log.error("[[[[[[[[[[[-> [Optimistic Loker] <-]]]]]]]]]]]property type error, the type of version property must be Long or long.");
        }

        pm.setValue(this.VERSION_COLUMN, (Integer)value + 1);
    }

    /**
     *  在 {@link SqlSessionFactory#openSession(boolean)} 时, 对 {@link Executor} 的包装【模式】后的包装类型，
     *  再使用责任链{@link InterceptorChain} 进行层层动态代理，返回代理对象
     * @param obj 当前obj为{@link Executor} 或者 动态代理过的对象
     * @return 当前代理后的对象
     */
    @Override
    public Object plugin(Object obj) {
        if (obj instanceof Executor || obj instanceof StatementHandler || obj instanceof ParameterHandler) {
            // 执行jdk动态代理
            return Plugin.wrap(obj, this);
        }
        return obj;
    }

    /**
     *  允许传入 Properties初始化参数
     * @param properties 默认不需要传入
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     *  递归： 获取真正被代理的对象
     * @param target 代理对象
     * @return 最底层的被代理对象
     */
    public static Object processTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject mo = SystemMetaObject.forObject(target);
            return processTarget(mo.getValue("h.target"));
        } else if (!(target instanceof StatementHandler) && !(target instanceof ParameterHandler)) {
            if (log.isDebugEnabled()) {
                log.error("Optimistic Lock plugin plugin init faild.");
            }
            throw new RuntimeException("Optimistic Lock plugin init faild.");
        } else {
            return target;
        }
    }

    /**
     *  我们的数据库字段都有乐观锁字段，不用验证
     *
     *  判断当前是否存现乐观锁字段
     * @param mo 调用前的对象
     * @param versionColumn 乐观锁列
     * @return 是否包含乐观锁字段
     */
    private static boolean resolve(MetaObject mo, String versionColumn) {
        String originalSql = (String)mo.getValue("boundSql.sql");
        MappedStatement ms = (MappedStatement)mo.getValue("mappedStatement");
        return Objects.equals(ms.getSqlCommandType(), SqlCommandType.UPDATE) ? Pattern.matches("[\\s\\S]*?" + versionColumn + "[\\s\\S]*?=[\\s\\S]*?\\?[\\s\\S]*?", originalSql.toLowerCase()) : false;
    }
}
