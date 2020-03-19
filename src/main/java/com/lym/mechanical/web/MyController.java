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
    public Result<PageData<MyGuestDTO>> myGuest(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                            @RequestParam("type") @ApiParam(value = "0-今日访客，1-累计访客，2-沟通过，3-意向客户") String type,
                                            @RequestParam("hasManyLook") @ApiParam(value = "是否多次浏览") String hasManyLook,
                                            @RequestParam("hasDial") @ApiParam(value = "是否拨号，0-未拨号，1-拨号") String  hasDial,
                                            @RequestParam("hasMobile") @ApiParam(value = "是否有联系方式") String hasMobile,
                                                @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(myService.myGuest(userId, type, hasManyLook, hasDial, hasMobile, pageNum, pageSize));
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
    @ApiOperation(value = "设备数据分析")
    public Result<PublishStatisticDTO> publishStatistic(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId) {
        return ResultUtil.success(myService.publishStatistic(userId));
    }

//    @GetMapping("latentUsersPublish.action")
//    @ApiOperation(value = "商品潜在用户-上面的设备信息")
//    public Result<LatentUserPublishDTO> latentUserPublishInfo(@RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId) {
//        return ResultUtil.success(myService.latentUserPublishInfo(publishId));
//    }

//    @GetMapping("latentUserTop.action")
//    @ApiOperation(value = "商品潜在用户-中间访问最多或浏览时间最长的用户")
//    public Result<LookUserDTO> latentUserTop(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
//                                             @RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId,
//                                             @RequestParam("type") @ApiParam(value = "类型，0-访问最多，1-浏览时间最长") String type) {
//        return ResultUtil.success(myService.latentUserTop(userId, publishId, type));
//    }

    @GetMapping("latent.action")
    @ApiOperation(value = "访客数据分析")
    public Result<LatentDTO> latent(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                       @RequestParam("publishId") @ApiParam(value = "发布id") Integer publishId,
                                                       @RequestParam(value = "hasDial", required = false) @ApiParam(value = "是否有拨号") String hasDial,
                                                       @RequestParam(value = "hasCollect", required = false) @ApiParam(value = "是否有收藏") String hasCollect,
                                                       @RequestParam(value = "hasManyLook", required = false) @ApiParam(value = "是否多次浏览") String hasManyLook,
                                                       @RequestParam(value = "hasMobile", required = false) @ApiParam(value = "是否有联系方式") String hasMobile,
                                                       @RequestParam("sortBy") @ApiParam(value = "排序方式,0-综合评分，1-访问次数，2-浏览时间") String sortBy) {
        return ResultUtil.success(myService.latent(userId, publishId, hasDial, hasCollect, hasManyLook, hasMobile, sortBy));
    }

//    @GetMapping("latentUser.action")
//    @ApiOperation(value = "潜在客户分析-上面用户信息")
//    public Result<LatentUserDTO> latentUser(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
//                                            @RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId) {
//        return ResultUtil.success(myService.latentUser(userId, latentUserId));
//    }

//    @GetMapping("latentPublishTop.action")
//    @ApiOperation(value = "潜在客户分析-中间部分")
//    public Result<LatentPublishStatisticDTO> latentPublishTop(@RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId,
//                                                              @RequestParam("type") @ApiParam(value = "类型，0-访问最多的设备，1-浏览时间最长的设备") String type) {
//        return ResultUtil.success(myService.latentPublishTop(latentUserId, type));
//    }

    @GetMapping("latentUser.action")
    @ApiOperation(value = "潜在客户分析")
    public Result<UserLatentDTO> latentPublishList(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                                    @RequestParam("latentUserId") @ApiParam(value = "潜在用户id") Integer latentUserId,
                                                                     @RequestParam(value = "hasDial", required = false) @ApiParam(value = "是否拨号") String hasDial,
                                                                     @RequestParam(value = "hasCollect", required = false) @ApiParam(value = "是否收藏") String hasCollect,
                                                                     @RequestParam(value = "hasManyLook", required = false) @ApiParam(value = "是否多次浏览") String hasManyLook,
                                                                     @RequestParam("sortBy") @ApiParam(value = "排序方式,0-综合评分，1-访问次数，2-浏览时间") String sortBy) {
        return ResultUtil.success(myService.latentPublishList(userId, latentUserId, hasDial, hasCollect, hasManyLook, sortBy));
    }
}
