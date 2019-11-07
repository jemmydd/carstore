package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.publish.PublishDTO;
import com.lym.mechanical.bean.dto.publish.PublishDetailDTO;
import com.lym.mechanical.bean.dto.publish.PublishSubmitDTO;
import com.lym.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.lym.mechanical.bean.param.publish.PublishComplexParam;
import com.lym.mechanical.bean.param.publish.PublishParam;
import com.lym.mechanical.bean.param.publish.PublishSubmitParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.publish.PublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "发布")
@Slf4j
@RestController
@RequestMapping("publish")
public class PublishController {
    @Autowired
    private PublishService publishService;

    @ApiOperation(value = "获取发布列表、搜索您感兴趣的发布项、获取首页的发布数据")
    @GetMapping("pageData.action")
    public Result<PageData<PublishDTO>> getPageData(PublishParam param) {
        param.setPublishCallSceneEnum(PublishCallSceneEnum.SQUARE);
        return ResultUtil.success(publishService.getPageData(param));
    }

    @ApiOperation(value = "提交一个发布")
    @PostMapping("publish.action")
    public Result<PublishSubmitDTO> publish(@RequestBody PublishSubmitParam param) {
        return ResultUtil.success(publishService.publish(param, false));
    }

    @ApiOperation(value = "修改一个发布")
    @PostMapping("modifyPublish.action")
    public Result<Boolean> modifyPublish(@RequestBody PublishSubmitParam param) {
        return ResultUtil.success(publishService.modifyPublish(param, false));
    }

    @ApiOperation(value = "下架一个发布")
    @PostMapping("downPublish.action")
    public Result<Boolean> downPublish(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId
    ) {
        return ResultUtil.success(publishService.downOrUpPublish(userId, publishId, (byte) 0));
    }

    @ApiOperation(value = "重新上架一个之前下架了的发布")
    @PostMapping("upPublish.action")
    public Result<Boolean> upPublish(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId
    ) {
        return ResultUtil.success(publishService.downOrUpPublish(userId, publishId, (byte) 1));
    }

    @ApiOperation(value = "获取发布的详情")
    @GetMapping("detail.action")
    public Result<PublishDetailDTO> getDetail(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId) {
        return ResultUtil.success(publishService.getDetail(userId, publishId));
    }

//    @ApiOperation(value = "点赞")
//    @PostMapping("like.action")
//    public Result<Boolean> like(Integer userId, Integer publishId) {
//        return ResultUtil.success(publishService.like(userId, publishId));
//    }

    @ApiOperation(value = "获取发布者的手机号")
    @GetMapping("publishPhone.action")
    public Result<String> getPublishPhone(@ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
                                          @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId) {
        return ResultUtil.success(publishService.getPublishPhone(userId, publishId));
    }

    @ApiOperation(value = "获取发布列表、搜索您感兴趣的发布项、获取首页的发布数据复杂逻辑版本")
    @GetMapping("pageDataComplex.action")
    public Result<PageData<PublishDTO>> getPageDataComplex(PublishComplexParam param) {
        return ResultUtil.success(publishService.getPageDataComplex(param));
    }
}
