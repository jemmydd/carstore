package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.user.CarUserDTO;
import com.lym.mechanical.bean.dto.wxPg.WxUserPhoneDTO;
import com.lym.mechanical.bean.entity.CarUserDO;
import com.lym.mechanical.bean.param.wxPg.QrParam;
import com.lym.mechanical.bean.param.wxPg.WxLoginInfo;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.wxPg.WxPgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author liyimin
 * @date 2019-11-05 15:57:12
 * good good code, day day up!
 */
@Api(tags = "微信小程序交互")
@Slf4j
@RestController
@RequestMapping("wxPg")
public class WxPgController {

    @Autowired
    private WxPgService wxPgService;

    @ApiOperation(value = "授权，失败报异常或返回-1的用户、成功返回正常用户")
    @PostMapping("auth.action")
    public Result<CarUserDO> auth(@RequestBody WxLoginInfo info) {
        return ResultUtil.success(wxPgService.auth(info));
    }

    @ApiOperation(value = "获取用户的手机号")
    @GetMapping("wxUserPhone.action")
    public Result<WxUserPhoneDTO> getWxUserPhone(
            @ApiParam(value = "userId", required = true) @RequestParam(value = "用户id") Integer userId,
            @ApiParam(value = "encryptedData", required = true) @RequestParam(value = "encryptedData") String encryptedData,
            @ApiParam(value = "iv", required = true) @RequestParam(value = "iv") String iv
    ) {
        return ResultUtil.success(wxPgService.getWxUserPhone(userId, encryptedData, iv));
    }

    @GetMapping("accessToken.action")
    @ApiOperation(value = "获取微信小程序的accessToken")
    public Result<String> getAccessToken() throws IOException {
        return ResultUtil.success(wxPgService.getAccessToken());
    }

    @GetMapping("qr.action")
    @ApiOperation(value = "获取微信小程序的二维码")
    public String getQr(QrParam param) {
        return wxPgService.getQr(param);
    }
}
