package com.kevin.tool.order.sendpay.check;

import org.springframework.context.ApplicationContext;

/**
 *  编码工具类
 * @author lihongmin
 * @date 2020/7/1 11:43
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class CodeUtil {

    /**
     *  标识{@code true} 订单码标位
     */
    private static final String TRUE_CODE = "01";

    /**
     *  标位长度
     */
    private static final int MARKER_LENGTH = 2;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        CodeUtil.applicationContext = applicationContext;
    }

    /**
     *  获取检查链
     * @param code 子订单码
     * @return 检查开关数组
     * @throws IllegalAccessException 编码长度异常
     */
    public static Boolean[] checkChain(String code) throws IllegalAccessException {
        RequestContextParam param = CheckRequestContext.getInstance().get();
        int length = param.entry.end - param.entry.start;
        if (code == null || code.length() != length) {
            throw new IllegalAccessException("编码长度异常!");
        }

        Boolean[] result = new Boolean[length / MARKER_LENGTH];
        String substring;
        for (int i = 0; i <= length / MARKER_LENGTH; i++) {
            substring = code.substring(i * 2, i * 2 + 1);
            result[i] = !TRUE_CODE.equals(substring);
        }
        return result;
    }

    /**
     *  标位码 --> 值解析
     * @param code 标位码，当前为两位数， 后续超过[01, 99] 可能会使用字母
     */
    public static Boolean isTrue(String code) {
        return TRUE_CODE.equals(code);
    }


}
