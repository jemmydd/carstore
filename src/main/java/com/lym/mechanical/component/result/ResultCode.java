package com.lym.mechanical.component.result;

import java.util.Objects;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
public enum ResultCode {
    DEFAULT_SUCCESS("111111", "成功"),
    DEFAULT_ERROR("000000", "失败"),

    AUTH_FAIL("100001", "没有权限"),
    PARAM_ERROR("100002", "参数错误"),
    TOKEN_EXCEPTION("100003", "TOKEN异常"),
    ;

    private String code;

    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static boolean isSucceed(String code) {
        return Objects.equals(code, DEFAULT_SUCCESS.code);
    }

    @Override
    public String toString() {
        return this.code + " " + this.msg;
    }
}
