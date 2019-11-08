package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.message.MessageDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname MessageController
 * @Description
 * @Date 2019/11/8 13:48
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("message")
@Api(tags = "消息")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("list.action")
    @ApiOperation(value = "消息列表")
    public Result<List<MessageDTO>> messageList(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(messageService.list(userId));
    }
}
