package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminLoginDTO;
import com.lym.mechanical.bean.param.admin.AdminLoginParam;
import com.lym.mechanical.bean.param.admin.AdminPasswordParam;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-14 16:17
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminLogin")
@Api(tags = "后台管理登陆")
public class AdminLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login.admin")
    @ApiOperation(value = "后台管理登陆")
    public Result<AdminLoginDTO> login(@RequestBody AdminLoginParam param) {
        return ResultUtil.success(loginService.login(param));
    }

    @PostMapping("modifyPwd.admin")
    @ApiOperation(value = "修改密码")
    public Result<AdminLoginDTO> modifyPwd(@RequestBody AdminPasswordParam param) {
        return ResultUtil.success(loginService.modifyPwd(param));
    }
}
