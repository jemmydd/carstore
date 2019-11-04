package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.AddPurchaseInformationParam;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.ModifyPurchaseInformationParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.purchaseInformation.PurchaseInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-24 20:23
 * Coding happily every day!
 */
@Api(tags = "ADMIN-求购信息")
@Slf4j
@RestController
@RequestMapping("adminPurchaseInformation")
public class AdminPurchaseInformationController {
    @Autowired
    private PurchaseInformationService purchaseInformationService;

    @ApiOperation(value = "新增")
    @PostMapping("add.admin")
    public Result<SubmitDTO> add(@RequestBody AddPurchaseInformationParam param) {
        return ResultUtil.success(purchaseInformationService.add(param));
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete.admin")
    public Result<Boolean> delete(@ApiParam(value = "purchaseInformationId", required = true) @RequestParam(value = "purchaseInformationId") Integer purchaseInformationId) {
        return ResultUtil.success(purchaseInformationService.delete(purchaseInformationId));
    }

    @ApiOperation(value = "批量删除")
    @PostMapping("deleteBatch.admin")
    public Result<Boolean> deleteBatchBanner(
            @ApiParam(value = "需要删除的purchaseInformation的ID列表", required = true) @RequestParam(value = "purchaseInformationIds") List<Integer> purchaseInformationIds
    ) {
        return ResultUtil.success(purchaseInformationService.deleteBatch(purchaseInformationIds));
    }

    @ApiOperation(value = "修改")
    @PostMapping("modify.admin")
    public Result<SubmitDTO> modify(@RequestBody ModifyPurchaseInformationParam param) {
        return ResultUtil.success(purchaseInformationService.modify(param));
    }

    @ApiOperation(value = "获取分页列表")
    @GetMapping("search.admin")
    public Result<PageData<PurchaseInformationDTO>> search(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "1级分类") @RequestParam(value = "categoryFirstId", required = false) Integer categoryFirstId,
            @ApiParam(value = "2级分类") @RequestParam(value = "categorySecondId", required = false) Integer categorySecondId,
            @ApiParam(value = "省份code") @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @ApiParam(value = "城市code") @RequestParam(value = "cityCode", required = false) String cityCode,
            @ApiParam(value = "区code") @RequestParam(value = "areaCode", required = false) String areaCode,
            @ApiParam(value = "关键字") @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "手机号") @RequestParam(value = "contactMobile", required = false) String contactMobile,
            @ApiParam(value = "品牌ID") @RequestParam(value = "brandId", required = false) Integer brandId
    ) {
        return ResultUtil.success(purchaseInformationService.search(pageNum, pageSize,
                categoryFirstId, categorySecondId,
                provinceCode, cityCode, areaCode, keyword, contactMobile, null, null, brandId
        ));
    }

    @ApiOperation(value = "获取一个求购信息")
    @GetMapping("one.admin")
    public Result<PurchaseInformationDTO> getOne(@ApiParam(value = "purchaseInformationId", required = true) @RequestParam(value = "purchaseInformationId") Integer purchaseInformationId) {
        return ResultUtil.success(purchaseInformationService.getOne(purchaseInformationId));
    }
}
