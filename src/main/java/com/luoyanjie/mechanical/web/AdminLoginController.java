package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.login.AdminLoginDTO;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-08-19 19:31
 * Coding happily every day!
 */
@Api(tags = "ADMIN-登录")
@Slf4j
@RestController
@RequestMapping("adminLogin")
public class AdminLoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "后台登录")
    @GetMapping("login.admin")
    public Result<AdminLoginDTO> login(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "account") String account,
            @ApiParam(value = "密码，必须是md5过", required = true) @RequestParam(value = "pwdByMd5") String pwdByMd5
    ) {
        return ResultUtil.success(loginService.login(account, pwdByMd5));
    }

    @ApiOperation(value = "修改密码-通过账号")
    @PostMapping("modifyPwdByAccount.admin")
    public Result<Boolean> modifyPwdByAccount(
            @ApiParam(value = "原始密码") @RequestParam(value = "oldPwd") String oldPwd,
            @ApiParam(value = "新的密码") @RequestParam(value = "newPwd") String newPwd,
            @ApiParam(value = "账户") @RequestParam(value = "account") String account
    ) {
        return ResultUtil.success(loginService.modifyPwdByAccount(oldPwd, newPwd, account));
    }

    @ApiOperation(value = "修改密码-通过用户ID")
    @PostMapping("modifyPwdById.admin")
    public Result<Boolean> modifyPwdById(
            @ApiParam(value = "原始密码") @RequestParam(value = "oldPwd") String oldPwd,
            @ApiParam(value = "新的密码") @RequestParam(value = "newPwd") String newPwd,
            @ApiParam(value = "ID") @RequestParam(value = "id") Integer id
    ) {
        return ResultUtil.success(loginService.modifyPwdById(oldPwd, newPwd, id));
    }
}
