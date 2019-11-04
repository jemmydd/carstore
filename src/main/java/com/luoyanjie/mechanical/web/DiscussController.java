package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.param.discuss.SendDiscussParam;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.discuss.DiscussService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-08-07 20:04
 * Coding happily every day!
 */
@Api(tags = "动态")
@Slf4j
@RestController
@RequestMapping("discuss")
public class DiscussController {
    @Autowired
    private DiscussService discussService;

    @ApiOperation(value = "读取一个动态")
    @PostMapping("read.action")
    public Result<Boolean> toRead(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId,
            @ApiParam(value = "动态ID", required = true) @RequestParam(value = "discussId") Integer discussId
    ) {
        return ResultUtil.success(discussService.toRead(userId, publishId, discussId));
    }

    @ApiOperation(value = "发布一个动态")
    @PostMapping("sendDiscuss.action")
    public Result<Boolean> sendDiscuss(@RequestBody SendDiscussParam param) {
        return ResultUtil.success(discussService.sendDiscuss(param));
    }
}
