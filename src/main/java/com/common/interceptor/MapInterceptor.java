package com.common.interceptor;

import com.quanyou.qup.core.util.StringUtil;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Mybatis Mapper返回Map数据类型拦截器,只会拦截方法上有对应注解的
 *
 * @author: zhouwei6
 * @since: 2020/09/25
 */
public class MapInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        DefaultResultSetHandler defaultResultSetHandler = (DefaultResultSetHandler) target;
        //这里通过反射，根据类名称来获取内部类，如果出现变更，则需要修改
        MappedStatement mappedStatement = ReflectUtil.getFieldValue(defaultResultSetHandler, "mappedStatement");
        String className = StringUtil.subBefore(mappedStatement.getId(), ".", false);
        String methodName = StringUtil.subAfter(mappedStatement.getId(), ".", false);
        Method[] methods = Class.forName(className).getDeclaredMethods();
        if (methods == null) {
            return invocation.proceed();
        }

        //找到需要执行的method 注意这里是根据方法名称来查找，如果出现方法重载，需要认真考虑
        for (Method method : methods) {
            if (methodName.equalsIgnoreCase(method.getName())) {
                //如果添加了注解标识，就将结果转换成map
                MapResult map = method.getAnnotation(MapResult.class);
                if (map == null) {
                    return invocation.proceed();
                }
                //进行map的转换
                Statement statement = (Statement) invocation.getArgs()[0];
                return result2Map(statement);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private Object result2Map(Statement statement) throws Throwable {
        ResultSet resultSet = statement.getResultSet();
        if (resultSet == null) {
            return null;
        }
        List<Object> resultList = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        while (resultSet.next()) {
            map.put(resultSet.getObject(1), resultSet.getObject(2));
        }
        resultList.add(map);
        return resultList;
    }
}
