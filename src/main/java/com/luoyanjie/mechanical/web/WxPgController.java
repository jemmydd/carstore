package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.dto.wxPg.WxUserPhoneDTO;
import com.luoyanjie.mechanical.bean.param.wxPg.QrParam;
import com.luoyanjie.mechanical.bean.param.wxPg.TemplateSendParam;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.wxPg.WxPgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author luoyanjie
 * @date 2019-08-31 23:26
 * Coding happily every day!
 */
@Api(tags = "微信小程序交互")
@Slf4j
@RestController
@RequestMapping("wxPg")
public class WxPgController {
    @Autowired
    private WxPgService wxPgService;

    @ApiOperation(value = "授权，失败报异常或返回-1的用户、成功返回正常用户")
    @GetMapping("auth.action")
    public Result<UserDTO> auth(
            @ApiParam(value = "code", required = true) @RequestParam(value = "code") String code
    ) {
        return ResultUtil.success(wxPgService.auth(code));
    }

    @ApiOperation(value = "获取用户的手机号")
    @GetMapping("wxUserPhone.action")
    public Result<WxUserPhoneDTO> getWxUserPhone(
            @ApiParam(value = "code", required = true) @RequestParam(value = "code") String code,
            @ApiParam(value = "encryptedData", required = true) @RequestParam(value = "encryptedData") String encryptedData,
            @ApiParam(value = "iv", required = true) @RequestParam(value = "iv") String iv
    ) {
        return ResultUtil.success(wxPgService.getWxUserPhone(code, encryptedData, iv));
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

    @PostMapping("templateSend.action")
    @ApiOperation(value = "模板消息发送")
    public Result<String> templateSend(@RequestBody TemplateSendParam param) throws IOException {
        return ResultUtil.success(wxPgService.templateSend(param));
    }
}
