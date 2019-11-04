package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.banner.BannerAdminDTO;
import com.luoyanjie.mechanical.bean.param.banner.AddBannerParam;
import com.luoyanjie.mechanical.bean.param.banner.ModifyBannerParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.banner.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-10 19:39
 * Coding happily every day!
 */
@Api(tags = "ADMIN-banner")
@Slf4j
@RestController
@RequestMapping("adminBanner")
public class AdminBannerController {
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "新增一个banner")
    @PostMapping("addBanner.admin")
    public Result<Boolean> addBanner(AddBannerParam param) {
        return ResultUtil.success(bannerService.addBanner(param));
    }

    @ApiOperation(value = "修改一个banner")
    @PostMapping("modifyBanner.admin")
    public Result<Boolean> modifyBanner(ModifyBannerParam param) {
        return ResultUtil.success(bannerService.modifyBanner(param));
    }

    @ApiOperation(value = "使一个banner有效")
    @PostMapping("validBanner.admin")
    public Result<Boolean> validBanner(@ApiParam(value = "bannerId") @RequestParam(value = "bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.validBanner(bannerId));
    }

    @ApiOperation(value = "使一个banner失效")
    @PostMapping("unValidBanner.admin")
    public Result<Boolean> unValidBanner(@ApiParam(value = "bannerId") @RequestParam(value = "bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.unValidBanner(bannerId));
    }

    @ApiOperation(value = "删除一个banner")
    @PostMapping("deleteBanner.admin")
    public Result<Boolean> deleteBanner(@ApiParam(value = "bannerId") @RequestParam(value = "bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.deleteBanner(bannerId));
    }

    @ApiOperation(value = "批量删除banner")
    @PostMapping("deleteBatchBanner.admin")
    public Result<Boolean> deleteBatchBanner(@ApiParam(value = "需要删除的banner的ID列表", required = true) @RequestParam(value = "bannerIds") List<Integer> bannerIds) {
        return ResultUtil.success(bannerService.deleteBatchBanner(bannerIds));
    }

    @ApiOperation(value = "获取一个banner")
    @GetMapping("banner.admin")
    public Result<BannerAdminDTO> getOne(@ApiParam(value = "发布ID") @RequestParam(value = "bannerId") Integer bannerId) {
        return ResultUtil.success(bannerService.getOne(bannerId));
    }

    @ApiOperation(value = "获取banner分页列表")
    @GetMapping("pageData.admin")
    public Result<PageData<BannerAdminDTO>> getPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize) {
        return ResultUtil.success(bannerService.getPageData(pageNum, pageSize));
    }
}
