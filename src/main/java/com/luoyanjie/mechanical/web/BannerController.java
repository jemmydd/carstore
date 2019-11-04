package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.banner.BannerDTO;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-10 18:57
 * Coding happily every day!
 */
@Api(tags = "小程序banner")
@Slf4j
@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "获取banner列表")
    @GetMapping("list.action")
    public Result<List<BannerDTO>> getList(
            @ApiParam(value = "获取几个", required = true) @RequestParam(value = "count") Integer count
    ) {
        return ResultUtil.success(bannerService.list(count));
    }
}
