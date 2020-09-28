package com.common.performance;

import com.quanyou.qup.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Stack;


/**
 *  统计方法执行时间，可以支持注解的方法嵌套，自己保证会被动态代理切中
 * @author kevin
 * @date 2020/8/23 10:25
 * @since 1.0.0
 * @see org.springframework.context.annotation.EnableAspectJAutoProxy
 */
@Aspect
@Slf4j
public class TimeConsumeAction {

    /**
     * 名称分割符
     */
    private static final String SEPARATOR_NAME = "#";

    /**
     *  方法调用计时器
     */
    private static final ThreadLocal<Stack<TimeConsumeStopWatch>> MONITOR_THREAD_LOCAL = ThreadLocal.withInitial(Stack::new);

    /**
     * 只切面 TimeConsume 注解标注的方法
     */
    @Pointcut("@annotation(com.quanyou.qup.middle.common.performance.TimeConsume)")
    private void timeConsumeAspect() {
    }

    /**
     *  Aop环绕
     * @param pjp 切入点信息
     * @return 代理对象
     * @throws Throwable 执行异常
     */
    @Around("timeConsumeAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        TimeConsume timeConsume = getTimeConsume(pjp);
        if (timeConsume.print()) {
            String taskName = StringUtil.isBlank(timeConsume.taskName()) ? getDefaultName(pjp) : timeConsume.taskName();
            TimeConsumeStopWatch stopWatch = new TimeConsumeStopWatch(taskName);
            stopWatch.start(taskName);
            MONITOR_THREAD_LOCAL.get().push(stopWatch);
        }
        return pjp.proceed();
    }

    /**
     *  Aop后置方法
     * @param pjp 切入点信息
     * @throws Throwable
     */
    @After("timeConsumeAspect()")
    public void after(JoinPoint pjp) {
        TimeConsume timeConsume = getTimeConsume(pjp);
        if (timeConsume.print()) {
            TimeConsumeStopWatch stopWatch = MONITOR_THREAD_LOCAL.get().pop();
            stopWatch.stop();
//            LOG_THREAD_LOCAL.get().append(stopWatch.shortSummary()).append(SEPARATOR);

            // 最外层（可能多个）出时，调用remove方法进行gc防止内存溢出
            if (MONITOR_THREAD_LOCAL.get().empty()) {
                log.info(stopWatch.shortSummary());
                MONITOR_THREAD_LOCAL.remove();
//                LOG_THREAD_LOCAL.remove();
            }
        }
    }

    /**
     *  获取默认task名称
     * @param pjp 切入点信息
     * @return 默认task名称
     */
    private String getDefaultName(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getDeclaringType().getSimpleName() + SEPARATOR_NAME + methodSignature.getMethod().getName();
    }

    /**
     *  获取注解的参数信息
     * @param pjp 切入点信息
     * @return 注解信息
     */
    private TimeConsume getTimeConsume(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(TimeConsume.class);
    }

}
