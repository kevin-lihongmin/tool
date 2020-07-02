package com.kevin.tool.order.code.check;

/**
 *  编码工具类
 * @author lihongmin
 * @date 2020/7/1 11:43
 * @since 1.0.0
 */
public class CodeUtil {

    /**
     *  不进行检查的标志
     */
    private static final String UN_CHECK = "00";

    /**
     *  获取检查链
     * @param code 子订单码
     * @return 检查开关数组
     * @throws IllegalAccessException 编码长度异常
     */
    public static Boolean[] checkChain(String code) throws IllegalAccessException {
        Segment.STATUS status = CheckRequestContext.getInstance().getStatus();
        int length = status.getStart() - status.getEnd();
        if (code == null || code.length() != length) {
            throw new IllegalAccessException("编码长度异常!");
        }

        Boolean[] result = new Boolean[length / 2];
        String substring;
        for (int i = 0; i <= length / 2; i++) {
            substring = code.substring(i * 2, i * 2 + 1);
            result[i] = !UN_CHECK.equals(substring);
        }
        return result;
    }


}
