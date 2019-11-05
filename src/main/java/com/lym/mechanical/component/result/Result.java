package com.lym.mechanical.component.result;

import lombok.Data;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Data
public class Result<T> {
    //错误码
    private String code;

    //错误描述
    private String msg;

    //数据
    private T data;
}
