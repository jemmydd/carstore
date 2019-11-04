package com.luoyanjie.mechanical.component.result;

import lombok.Data;

/**
 * @author luoyanjie
 * @date 2019-07-29 17:13
 * Coding happily every day!
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
