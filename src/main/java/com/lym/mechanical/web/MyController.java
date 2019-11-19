package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.card.NameCardDTO;
import com.lym.mechanical.bean.dto.my.*;
import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.my.MyService;
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
 * @Classname MyController
 * @Description
 * @Date 2019/11/11 13:49
 * @Created by jimy
 * good good code, day day up!
 */
@RestController
@RequestMapping("my")
@Api(tags = "我的")
public class MyController {

    @Autowired
    private MyService myService;

    @ApiOperation(value = "我的主页")
    @GetMapping("myIndex.action")
    public Result<MyIndexDTO> myIndex(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(myService.myIndex(userId));
    }

    @ApiOperation(value = "访客列表")
    @GetMapping("myGuest.action")
    public Result<List<MyGuestDTO>> myGuest(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                            @RequestParam("type") @ApiParam(value = "0-今日访客，1-累计访客") String type,
                                            @RequestParam("hasLook") @ApiParam(value = "是否看了设备，0-没看，1-看了") String hasLook,
                                            @RequestParam("hasDial") @ApiParam(value = "是否拨号，0-未拨号，1-拨号") String  hasDial,
                                            @RequestParam("hasCollect") @ApiParam(value = "是否收藏，0-未收藏，1-收藏") String hasCollect) {
        return ResultUtil.success(myService.myGuest(userId, type, hasLook, hasDial, hasCollect));
    }

    @GetMapping("myFriendCards.action")
    @ApiOperation(value = "友情合作列表")
    public Result<List<NameCardDTO>> myFriendCards(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(myService.myFriendCards(userId));
    }

    @GetMapping("removeFriendCards.action")
    @ApiOperation(value = "移除友情合作")
    public Result<Boolean> removeFriendCards(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                         @RequestParam("cardId") @ApiParam(value = "名片id") Integer cardId) {
        return ResultUtil.success(myService.removeFriendCards(userId, cardId));
    }

    @ApiOperation(value = "获取我的收藏分页列表")
    @GetMapping("myCollectionPageData.action")
    public Result<PageData<PublishDTO>> getMyCollectionPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID") @RequestParam(value = "userId") Integer userId) {
        return ResultUtil.success(myService.getMyCollectionPageData(pageNum, pageSize, userId));
    }

    @ApiOperation(value = "获取我的发布分页列表")
    @GetMapping("myPublishPageData.action")
    public Result<PageData<PublishDTO>> getMyPublishPageData(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "是否已下架：上架状态：1出售中、0已下架") @RequestParam(value = "downShelf", required = false) Byte downShelf
    ) {
        return ResultUtil.success(myService.getMyPublishPageData(pageNum, pageSize, userId, downShelf));
    }

    @GetMapping("removeCollect.action")
    @ApiOperation(value = "取消收藏")
    public Result<Boolean> removeCollect(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                         @RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId) {
        return ResultUtil.success(myService.removeCollect(userId, publishId));
    }

    @GetMapping("publishStatistic.action")
    @ApiOperation(value = "商品数据分析")
    public Result<PublishStatisticDTO> publishStatistic(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(myService.publishStatistic(userId));
    }

    @GetMapping("latentUsersPublish.action")
    @ApiOperation(value = "商品潜在用户-上面的设备信息")
    public Result<LatentUserPublishDTO> latentUserPublishInfo(@RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId) {
        return ResultUtil.success(myService.latentUserPublishInfo(publishId));
    }

    @GetMapping("latentUserTop.action")
    @ApiOperation(value = "商品潜在用户-中间访问最多或浏览时间最长的用户")
    public Result<LookUserDTO> latentUserTop(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                             @RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId,
                                             @RequestParam("type") @ApiParam(value = "类型，0-访问最多，1-浏览时间最长") String type) {
        return ResultUtil.success(myService.latentUserTop(userId, publishId, type));
    }

    @GetMapping("latentUserHistory.action")
    @ApiOperation(value = "商品潜在用户-下面历史访问的用户")
    public Result<List<LookUserDTO>> latentUserHistory(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                       @RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId,
                                                       @RequestParam("hasDial") @ApiParam(value = "是否有拨号") String hasDial,
                                                       @RequestParam("hasCollect") @ApiParam(value = "是否有收藏") String hasCollect) {
        return ResultUtil.success(myService.latentUserHistory(userId, publishId, hasDial, hasCollect));
    }

    @GetMapping("latentUser.action")
    @ApiOperation(value = "潜在客户分析-上面用户信息")
    public Result<LatentUserDTO> latentUser(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                            @RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId) {
        return ResultUtil.success(myService.latentUser(userId, latentUserId));
    }

    @GetMapping("latentPublishTop.action")
    @ApiOperation(value = "潜在客户分析-中间部分")
    public Result<LatentPublishStatisticDTO> latentPublishTop(@RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId,
                                                              @RequestParam("type") @ApiParam(value = "类型，0-访问最多的设备，1-浏览时间最长的设备") String type) {
        return ResultUtil.success(myService.latentPublishTop(latentUserId, type));
    }

    @GetMapping("latentPublishList.action")
    @ApiOperation(value = "潜在客户分析-下面部分")
    public Result<List<LatentPublishStatisticDTO>> latentPublishList(@RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId,
                                                                     @RequestParam("hasDial") @ApiParam(value = "是否拨号") String hasDial,
                                                                     @RequestParam("hasCollect") @ApiParam(value = "是否收藏") String hasCollect) {
        return ResultUtil.success(myService.latentPublishList(latentUserId, hasDial, hasCollect));
    }
}