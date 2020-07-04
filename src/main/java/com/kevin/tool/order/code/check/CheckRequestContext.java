package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.generate.DefaultCodeFactory;
import com.kevin.tool.order.code.generate.param.CodeParam;

/**
 *  检查请求切面
 * @author lihongmin
 * @date 2020/7/1 13:11
 * @since 1.0.0
 * @see DefaultCodeFactory#generateCode(CodeParam, DefaultCodeFactory.OrderType)
 * @see org.springframework.context.annotation.EnableAspectJAutoProxy
 */
public class CheckRequestContext {

    private static class Singleton {
        private static final CheckRequestContext INSTANCE = new CheckRequestContext();
    }
    private CheckRequestContext (){}
    public static final CheckRequestContext getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     *  存放请求参数
     */
    private static final ThreadLocal<RequestContextParam> THREAD_LOCAL = new InheritableThreadLocal<RequestContextParam>(){
        @Override
        public RequestContextParam initialValue() {
            // 显示设置为null
            return null;
        }
    };

    /**
     *  设置总请求参数
     * @param requestContextParam
     */
    public void set(RequestContextParam requestContextParam) {
        THREAD_LOCAL.set(requestContextParam);
    }

    public RequestContextParam get() {
        return THREAD_LOCAL.get();
    }

    public CodeParam getCodeParam() {
        return THREAD_LOCAL.get().codeParam;
    }

    public Segment.STATUS getStatus() {
        return THREAD_LOCAL.get().status;
    }

    public void remove() {
        THREAD_LOCAL.remove();
    }

}
