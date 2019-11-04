package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.follow.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关注
 *
 * @author luoyanjie
 * @date 2019-07-29 12:16
 * Coding happily every day!
 */
@Api(tags = "关注")
@Slf4j
@RestController
@RequestMapping("follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @ApiOperation(value = "关注")
    @PostMapping("toFollow.action")
    public Result<Boolean> toFollow(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "被关注着ID", required = true) @RequestParam(value = "objectId") Integer objectId
    ) {
        return ResultUtil.success(followService.toFollow(userId, objectId));
    }

    @ApiOperation(value = "取消关注")
    @PostMapping("unFollow.action")
    public Result<Boolean> unFollow(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "被关注着ID", required = true) @RequestParam(value = "objectId") Integer objectId
    ) {
        return ResultUtil.success(followService.unFollow(userId, objectId));
    }
}
