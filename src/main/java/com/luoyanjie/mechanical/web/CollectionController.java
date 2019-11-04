package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.collection.CollectionService;
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
 * @date 2019-08-07 16:54
 * Coding happily every day!
 */
@Api(tags = "收藏")
@Slf4j
@RestController
@RequestMapping("collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @ApiOperation(value = "收藏一个发布")
    @PostMapping("toCollect.action")
    public Result<Boolean> toCollect(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId
    ) {
        return ResultUtil.success(collectionService.toCollect(userId, publishId));
    }

    @ApiOperation(value = "取消收藏一个发布")
    @PostMapping("unCollect.action")
    public Result<Boolean> unCollect(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId
    ) {
        return ResultUtil.success(collectionService.unCollect(userId, publishId));
    }
}
