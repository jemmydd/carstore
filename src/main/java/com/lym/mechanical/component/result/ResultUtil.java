package com.lym.mechanical.component.result;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
public class ResultUtil {
    private static <D> Result<D> result(D data, String code, String message) {
        Result<D> result = new Result<>();

        result.setCode(code);
        result.setData(data);
        result.setMsg(message);

        return result;
    }

    private static <D> Result<D> result(D data, ResultCode resultCode) {
        return result(data, resultCode.getCode(), resultCode.getMsg());
    }

    public static <D> Result<D> success() {
        return result(null, ResultCode.DEFAULT_SUCCESS);
    }

    public static <D> Result<D> success(D data) {
        return result(data, ResultCode.DEFAULT_SUCCESS);
    }

    /*public static <D> Result<D> success(String msg) {
        return result(null, ResultCode.DEFAULT_SUCCESS.getCode(), msg);
    }*/

    public static <D> Result<D> success(ResultCode resultCode) {
        return result(null, resultCode);
    }

    public static <D> Result<D> success(D data, String message) {
        return result(data, ResultCode.DEFAULT_SUCCESS.getCode(), message);
    }

    public static <D> Result<D> success(D data, ResultCode resultCode) {
        return result(data, resultCode);
    }

    public static <D> Result<D> error() {
        return result(null, ResultCode.DEFAULT_ERROR);
    }


    public static <D> Result<D> error(D data) {
        return result(data, ResultCode.DEFAULT_ERROR);
    }

    /*public static <D> Result<D> error(String msg) {
        return result(null, ResultCode.DEFAULT_ERROR.getCode(), msg);
    }*/

    public static <D> Result<D> error(ResultCode resultCode) {
        return result(null, resultCode);
    }

    public static <D> Result<D> error(D data, String message) {
        return result(data, ResultCode.DEFAULT_ERROR.getCode(), message);
    }

    public static <D> Result<D> error(D data, ResultCode resultCode) {
        return result(data, resultCode);
    }

}
