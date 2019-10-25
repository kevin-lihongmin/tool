package com.kevin.tool.timeconsume;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
@Aspect
@Slf4j
public class TimeConsumeAction {

    private static final ThreadLocal<Stack<StopWatch>> Thread_Local = new ThreadLocal(){
        @Override
        public Stack<StopWatch> initialValue() {
            return new Stack<>();
        }
    };

    private static final AtomicLong start = new AtomicLong();

    /**
     * 只切面 TimeConsume 注解标注的方法
     */
    @Pointcut("@annotation(com.kevin.jpa.timeconsume.TimeConsume)")
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
            StopWatch stopWatch = new StopWatch(timeConsume.taskName());
            stopWatch.start(timeConsume.taskName());
            Thread_Local.get().push(stopWatch);
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
            StopWatch stopWatch = Thread_Local.get().pop();
            stopWatch.stop();
            log.info(stopWatch.shortSummary());
            Thread_Local.remove();
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
