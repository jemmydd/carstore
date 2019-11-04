package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.dto.invite.InviteDTO;
import com.luoyanjie.mechanical.bean.dto.my.FansDTO;
import com.luoyanjie.mechanical.bean.dto.my.PersonalHomePageDTO;
import com.luoyanjie.mechanical.bean.dto.my.PublishInHpDTO;
import com.luoyanjie.mechanical.bean.dto.my.v2.PersonalHomePageV2DTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.param.my.ModifyPersonalHomePageBkParam;
import com.luoyanjie.mechanical.bean.param.my.PersonalDataParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.component.result.RollIdPageData;
import com.luoyanjie.mechanical.service.my.MyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-07-30 09:05
 * Coding happily every day!
 */
@Api(tags = "我的")
@Slf4j
@RestController
@RequestMapping("my")
public class MyController {
    @Autowired
    private MyService myService;

    @ApiOperation(value = "获取用户的概览数据")
    @GetMapping("overview.action")
    public Result<UserDTO> getOverview(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(myService.getOverview(userId));
    }

    @ApiOperation(value = "获取用户的个人资料")
    @GetMapping("personalData.action")
    public Result<UserDTO> getPersonalData(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(myService.getPersonalData(userId));
    }

    @ApiOperation(value = "修改用户的个人资料")
    @PostMapping("personalData.action")
    public Result<Boolean> modifyPersonalData(PersonalDataParam param) {
        return ResultUtil.success(myService.modifyPersonalData(param));
    }

    @ApiOperation(value = "获取某个人的个人主页")
    @GetMapping("personalHomePage.action")
    public Result<PersonalHomePageDTO> getPersonalHomePage(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "主页者ID", required = true) @RequestParam(value = "whoId") Integer whoId,
            @ApiParam(value = "发布数的页大小", required = true) @RequestParam(value = "baseSize") Integer baseSize) {
        return ResultUtil.success(myService.getPersonalHomePage(userId, whoId, baseSize));
    }

    @ApiOperation(value = "获取某个人的个人主页下面的发布")
    @GetMapping("personalHomePagePublish.action")
    public Result<RollIdPageData<PublishInHpDTO>> getPersonalHomePagePublish(
            @ApiParam(value = "登录用户ID，公共参数里面有") @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "主页者ID") @RequestParam(value = "whoId") Integer whoId,
            @ApiParam(value = "发布数的页大小", required = true) @RequestParam(value = "baseSize") Integer baseSize,
            @ApiParam(value = "你之前拿到的最小ID") @RequestParam(value = "currentMinId") Integer currentMinId) {
        return ResultUtil.success(myService.getPersonalHomePagePublish(userId, whoId, baseSize, currentMinId));
    }

    @ApiOperation(value = "修改个人主页的背景图")
    @PostMapping("personalHomePageBk.action")
    public Result<Boolean> modifyPersonalHomePageBk(ModifyPersonalHomePageBkParam param) {
        return ResultUtil.success(myService.modifyPersonalHomePageBk(param));
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

    @ApiOperation(value = "获取我的收藏分页列表")
    @GetMapping("myCollectionPageData.action")
    public Result<PageData<PublishDTO>> getMyCollectionPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有") @RequestParam(value = "userId") Integer userId) {
        return ResultUtil.success(myService.getMyCollectionPageData(pageNum, pageSize, userId));
    }

    @ApiOperation(value = "获取我的动态分页列表")
    @GetMapping("myDiscussPageData.action")
    public Result<PageData<DiscussDTO>> getMyDiscussPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有") @RequestParam(value = "userId") Integer userId) {
        return ResultUtil.success(myService.getMyDiscussPageData(pageNum, pageSize, userId));
    }

    @ApiOperation(value = "获取我的粉丝列表")
    @GetMapping("myFansPageData.action")
    public Result<PageData<FansDTO>> getMyFansPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有") @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(myService.getMyFansPageData(pageNum, pageSize, userId));
    }

    @ApiOperation(value = "获取我的关注列表")
    @GetMapping("myFollowPageData.action")
    public Result<PageData<FansDTO>> getMyFollowPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有") @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(myService.getMyFollowPageData(pageNum, pageSize, userId));
    }

    @ApiOperation(value = "获取我的求购信息")
    @GetMapping("myPurchaseInformationPageData.action")
    public Result<PageData<PurchaseInformationDTO>> myPurchaseInformationPageData(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "是否下架：1上架中、0已下架") @RequestParam(value = "shelfStatus", required = false) Byte shelfStatus
    ) {
        return ResultUtil.success(myService.myPurchaseInformationPageData(pageNum, pageSize, userId, shelfStatus));
    }

    @ApiOperation(value = "获取我邀请的的好友列表")
    @GetMapping("myInvitePageData.action")
    public Result<PageData<InviteDTO>> myInvitePageData(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "登录用户ID，公共参数里面有", required = true) @RequestParam(value = "userId") Integer userId
    ) {
        return ResultUtil.success(myService.myInvitePageData(pageNum, pageSize, userId));
    }

    //****** START 个人主页V2
    @ApiOperation(value = "获取某个人的个人主页V2，只返回第一个分页的发布数据")
    @GetMapping("/v2/personalHomePage.action")
    public Result<PersonalHomePageV2DTO> getPersonalHomePageV2(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "主页者ID", required = true) @RequestParam(value = "whoId") Integer whoId,
            @ApiParam(value = "发布数的页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize) {
        return ResultUtil.success(myService.getPersonalHomePageV2(userId, whoId, pageSize));
    }

    @ApiOperation(value = "获取某个人的个人主页下面的发布V2")
    @GetMapping("/v2/personalHomePagePublish.action")
    public Result<PageData<PublishDTO>> getPersonalHomePagePublishV2(
            @ApiParam(value = "登录用户ID，公共参数里面有", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "主页者ID", required = true) @RequestParam(value = "whoId") Integer whoId,
            @ApiParam(value = "发布数的页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "页码，应该从2开始", required = true) @RequestParam(value = "pageNum") Integer pageNum) {
        return ResultUtil.success(myService.getPersonalHomePagePublishV2(userId, whoId, pageSize, pageNum));
    }
}
