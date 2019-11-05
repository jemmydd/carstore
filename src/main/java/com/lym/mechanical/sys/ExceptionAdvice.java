package com.lym.mechanical.sys;

import com.google.common.base.VerifyException;
import com.lym.mechanical.component.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author liyimin
 * @date 2019-11-05 09:06:21
 * good good code, day day up!
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    public static final String NOT_EMPTY = "[%s]不能为空";
    public static final String NOT_SUPPORT = "[%s]不支持";
    public static final String NOT_EXIST = "[%s]不存在";
    public static final String NOT_OVER = "[%s]不能超过[%s]个";
    public static final String MUST_POS_NUM = "[%s]只能为正整数";

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> throwable(Throwable e) {
        e.printStackTrace();

        String msg = e.getMessage();

        log.error("未处理异常 {}", msg);

        return new ResponseEntity<>(ResultUtil.error(null, msg), HttpStatus.OK);
    }

    @ExceptionHandler({VerifyException.class})
    public ResponseEntity<Object> verifyException(VerifyException e) {
        e.printStackTrace();

        String msg = e.getMessage();

        log.error("校验失败 {}", msg);

        return new ResponseEntity<>(ResultUtil.error(null, msg), HttpStatus.OK);
    }
}
