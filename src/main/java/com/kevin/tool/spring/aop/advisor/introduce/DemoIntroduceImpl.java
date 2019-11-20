package com.kevin.tool.spring.aop.advisor.introduce;

import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;

public class DemoIntroduceImpl implements IntroductionAdvisor {

    @Override
    public ClassFilter getClassFilter() {
        return null;
    }

    @Override
    public void validateInterfaces() throws IllegalArgumentException {

    }

    @Override
    public Advice getAdvice() {
        return null;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }

    @Override
    public Class<?>[] getInterfaces() {
        return new Class[0];
    }
}
