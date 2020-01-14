package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.card.RecentlyUserDTO;
import com.lym.mechanical.bean.dto.message.LookRecordDTO;
import com.lym.mechanical.bean.dto.message.MessageDTO;
import com.lym.mechanical.bean.dto.message.MessageDetailDTO;
import com.lym.mechanical.bean.param.message.MessageSendParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<List<MessageDTO>> messageList(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                @RequestParam(value = "name", required = false) @ApiParam("搜索联系人") String name) {
        return ResultUtil.success(messageService.list(userId, name));
    }

    @GetMapping("allRead.action")
    @ApiOperation(value = "一键已读")
    public Result<Boolean> allRead(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(messageService.allRead(userId));
    }

    @GetMapping("detail.action")
    @ApiOperation(value = "消息详情")
    public Result<MessageDetailDTO> messageDetail(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                  @RequestParam("otherUserId") @ApiParam(value = "对方的用户id") Integer otherUserId,
                                                  @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                  @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(messageService.messageDetail(userId, otherUserId, pageNum, pageSize));
    }

    @GetMapping("makeTag.action")
    @ApiOperation(value = "标记为意向客户/取消标记为意向客户")
    public Result<Boolean> makeTag(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                   @RequestParam("otherUserId") @ApiParam(value = "对方的用户id") Integer otherUserId) {
        return ResultUtil.success(messageService.makeTag(userId, otherUserId));
    }

    @PostMapping("send.action")
    @ApiOperation(value = "发送消息")
    public Result<Boolean> sendMessage(@RequestBody MessageSendParam param) {
        return ResultUtil.success(messageService.sendMessage(param));
    }

    @GetMapping("lookRecord.action")
    @ApiOperation(value = "谁看过我")
    public Result<PageData<LookRecordDTO>> lookRecord(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                      @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                      @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(messageService.lookRecord(userId, pageNum, pageSize));
    }

    @GetMapping("askStatistic.action")
    @ApiOperation(value = "访问统计")
    public Result<PageData<RecentlyUserDTO>> askStatistic(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                          @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                          @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(messageService.askStatistic(userId, pageNum, pageSize));
    }
}
