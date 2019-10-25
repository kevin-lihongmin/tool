package com.kevin.tool.timeconsume;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Stack;

/**
 */
@Aspect
@Slf4j
public class TimeConsumeAction {

    /**
     *  日志分隔符
     */
    private static final String SEPARATOR = " ;  ";

    /**
     *  方法调用计时器
     */
    private static final ThreadLocal<Stack<PosStopWatch>> MONITOR_THREAD_LOCAL = new ThreadLocal(){
        @Override
        public Stack<PosStopWatch> initialValue() {
            return new Stack<>();
        }
    };

    /**
     *  结果日志记录器
     */
    private static final ThreadLocal<StringBuilder> LOG_THREAD_LOCAL = new ThreadLocal(){
        @Override
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };

    /**
     * 只切面 TimeConsume 注解标注的方法
     */
    @Pointcut("@annotation(com.kevin.tool.timeconsume.TimeConsume)")
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
            PosStopWatch stopWatch = new PosStopWatch(timeConsume.taskName());
            stopWatch.start(timeConsume.taskName());
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
            PosStopWatch stopWatch = MONITOR_THREAD_LOCAL.get().pop();
            stopWatch.stop();
            LOG_THREAD_LOCAL.get().append(stopWatch.shortSummary()).append(SEPARATOR);

            // 最外层（可能多个）出时，调用remove防止内存溢出
            if (MONITOR_THREAD_LOCAL.get().empty()) {
                log.info(LOG_THREAD_LOCAL.get().toString());
                MONITOR_THREAD_LOCAL.remove();
                LOG_THREAD_LOCAL.remove();
            }
        }
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
