package com.kevin.tool.test;

public class EnumTest {

    public static void main(String[] args) {
       /* TsetEnum a = TsetEnum.valueOf("A");
        System.out.println(a.message);*/
        TsetEnum a = TsetEnum.A;
        TsetEnum a1 = TsetEnum.A;
        System.out.println(a.equals(a1));
    }

    public enum TsetEnum {
        A(1, "1"),
        B(2, "2");

        TsetEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        int code;

        String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
