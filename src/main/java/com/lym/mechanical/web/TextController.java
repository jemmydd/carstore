package com.lym.mechanical.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TextController
 * @Description
 * @Date 2019/11/5 9:10
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("test")
public class TextController {

    @GetMapping("test")
    @ApiOperation(value = "测试")
    public String helloworld() {
        return "hello world!";
    }
}
