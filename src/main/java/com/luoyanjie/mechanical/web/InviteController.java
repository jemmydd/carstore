package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.invite.InviteService;
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
 * @author luoyanjie
 * @date 2019-08-25 16:21
 * Coding happily every day!
 */
@Api(tags = "邀请")
@Slf4j
@RestController
@RequestMapping("invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @ApiOperation(value = "被邀请者绑定到邀请者")
    @PostMapping("inviteBind.action")
    public Result<Boolean> inviteBind(
            @ApiParam(value = "邀请者ID", required = true) @RequestParam(value = "fromId") Integer fromId,
            @ApiParam(value = "被邀请者ID", required = true) @RequestParam(value = "toId") Integer toId
    ) {
        return ResultUtil.success(inviteService.inviteBind(fromId, toId));
    }
}
