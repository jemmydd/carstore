package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.card.*;
import com.lym.mechanical.bean.param.card.ApplyCarStoreParam;
import com.lym.mechanical.bean.param.card.NameCardParam;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.card.NameCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname NameCardController
 * @Description 名片控制类
 * @Date 2019/11/6 17:34
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("card")
@Api(tags = "名片")
public class NameCardController {

    @Autowired
    private NameCardService nameCardService;

    @GetMapping("myCard.action")
    @ApiOperation(value = "查看自己名片")
    public Result<MyNameCardDTO> myNameCard(@RequestParam("userId") @ApiParam(value = "用户id", required = true) Integer userId) {
        return ResultUtil.success(nameCardService.myNameCard(userId));
    }

    @GetMapping("otherCard.action")
    @ApiOperation(value = "查看他人名片")
    public Result<OtherNameCardDTO> OtherNameCard(@RequestParam("cardId") @ApiParam(value = "需要查看的名片id", required = true) Integer cardId,
                                                  @RequestParam(value = "userId", required = false) @ApiParam(value = "当前登录用户id", required = false) Integer userId) {
        return ResultUtil.success(nameCardService.otherNameCard(cardId, userId));
    }

    @PostMapping("apply.action")
    @ApiOperation(value = "申请成为车商")
    public Result<Boolean> applyCarStore(@RequestBody ApplyCarStoreParam param) {
        return ResultUtil.success(nameCardService.applyCarStore(param));
    }

    @GetMapping("changeNameCard.action")
    @ApiOperation(value = "切换名片列表")
    public Result<ChangeNameCardDTO> changeNameCard(@RequestParam("userId") @ApiParam(value = "当前登录用户id", required = true) Integer userId) {
        return ResultUtil.success(nameCardService.changeNameCard(userId));
    }

    @PostMapping("createNameCard.action")
    @ApiOperation(value = "创建名片")
    public Result<Integer> createNameCard(@RequestBody NameCardParam param) {
        return ResultUtil.success(nameCardService.createNameCard(param));
    }

    @PostMapping("modifyNameCard.action")
    @ApiOperation(value = "编辑名片")
    public Result<Boolean> modifyNameCard(@RequestBody NameCardParam param) {
        return ResultUtil.success(nameCardService.modifyNameCard(param));
    }

    @GetMapping("searchFriendCards.action")
    @ApiOperation(value = "添加友情合作搜索名片")
    public Result<List<NameCardSimpleDTO>> searchFriendCards(@RequestParam(value = "cardId", required = false) @ApiParam(value = "名片编号") String cardId,
                                                             @RequestParam("userId") @ApiParam(value = "当前登录用户id", required = true) Integer userId) {
        return ResultUtil.success(nameCardService.searchFriendCards(cardId, userId));
    }

    @GetMapping("historyFriendCards.action")
    @ApiOperation(value = "添加友情合作历史添加")
    public Result<List<NameCardSimpleDTO>> historyFriendCards(@RequestParam("userId") @ApiParam(value = "当前登录用户id", required = true) Integer userId) {
        return ResultUtil.success(nameCardService.historyFriendCards(userId));
    }

    @PostMapping("addFriend.action")
    @ApiOperation(value = "添加友情合作")
    public Result<Boolean> addFriend(@RequestParam(value = "cardId", required = false) @ApiParam(value = "名片id") Integer cardId,
                                     @RequestParam("userId") @ApiParam(value = "当前登录用户id", required = true) Integer userId) {
        return ResultUtil.success(nameCardService.addFriend(cardId, userId));
    }

    @GetMapping("takeMobile.action")
    @ApiOperation(value = "拨号")
    public Result<Boolean> takeMobile(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                      @RequestParam("cardId") @ApiParam(value = "名片id") Integer cardId) {
        return ResultUtil.success(nameCardService.takeMobile(userId, cardId));
    }
}
