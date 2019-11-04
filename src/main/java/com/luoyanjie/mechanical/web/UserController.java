package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 *
 * @author luoyanjie
 * @date 2019-07-29 12:15
 * Coding happily every day!
 */
@Api(tags = "用户")
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取某个用户的信息")
    @GetMapping("user.action")
    public Result<UserDTO> getUser(@ApiParam(value = "需要获取的用户ID", required = true) @RequestParam(value = "whoId") Integer whoId) {
        return ResultUtil.success(userService.getUser(whoId));
    }

    @ApiOperation(value = "用户绑定手机号")
    @PostMapping("bindPhone.action")
    public Result<Boolean> bindPhone(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone
    ) {
        return ResultUtil.success(userService.bindPhone(userId, phone));
    }

    @ApiOperation(value = "用户解绑手机号")
    @PostMapping("unBindPhone.action")
    public Result<Boolean> downPublish(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(userService.unBindPhone(userId));
    }
}
