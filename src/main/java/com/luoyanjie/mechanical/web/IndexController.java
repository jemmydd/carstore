package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoyanjie
 * @date 2019-08-31 11:13
 * Coding happily every day!
 */
@CrossOrigin
@Api(tags = "首页")
@Slf4j
@RestController
@RequestMapping(value = {"", "index"})
public class IndexController {
    @ApiOperation(value = "默认接口")
    @GetMapping(value = {"", "index.html", "index.htm"})
    public Result<String> index() {
        return ResultUtil.success("欢迎光临");
    }
}
