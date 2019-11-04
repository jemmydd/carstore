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

/**
 * @author luoyanjie
 * @date 2019-08-24 21:48
 * Coding happily every day!
 */
@Api(tags = "求购信息")
@Slf4j
@RestController
@RequestMapping("purchaseInformation")
public class PurchaseInformationController {
    @Autowired
    private PurchaseInformationService purchaseInformationService;

    @ApiOperation(value = "发布求购需求")
    @PostMapping("add.action")
    public Result<SubmitDTO> add(@RequestBody AddPurchaseInformationParam param) {
        return ResultUtil.success(purchaseInformationService.add(param));
    }

    @ApiOperation(value = "获取未下架的求购信息")
    @GetMapping("getUnSoldOutPageData.action")
    public Result<PageData<PurchaseInformationDTO>> getUnSoldOutPageData(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "省份code") @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @ApiParam(value = "城市code") @RequestParam(value = "cityCode", required = false) String cityCode,
            @ApiParam(value = "区code") @RequestParam(value = "areaCode", required = false) String areaCode,
            @ApiParam(value = "关键字") @RequestParam(value = "keyword", required = false) String keyword,
            @ApiParam(value = "联系电话") @RequestParam(value = "contactMobile", required = false) String contactMobile,
            @ApiParam(value = "1级分类") @RequestParam(value = "categoryFirstId", required = false) Integer categoryFirstId,
            @ApiParam(value = "2级分类") @RequestParam(value = "categorySecondId", required = false) Integer categorySecondId,
            @ApiParam(value = "品牌ID") @RequestParam(value = "brandId", required = false) Integer brandId
    ) {
        return ResultUtil.success(
                purchaseInformationService.search(
                        pageNum, pageSize, categoryFirstId, categorySecondId, provinceCode, cityCode, areaCode, keyword, contactMobile, (byte) 1, null, brandId
                )
        );
    }

    @ApiOperation(value = "编辑求购信息")
    @PostMapping("modify.action")
    public Result<SubmitDTO> modify(@RequestBody ModifyPurchaseInformationParam param) {
        return ResultUtil.success(purchaseInformationService.modify(param));
    }

    @ApiOperation(value = "下架求购信息")
    @PostMapping("down.action")
    public Result<Boolean> down(
            @ApiParam(value = "用户ID，公共参数", required = true) @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "求购信息ID", required = true) @RequestParam(value = "purchaseInformationId") Integer purchaseInformationId
    ) {
        return ResultUtil.success(purchaseInformationService.down(userId, purchaseInformationId));
    }

    @ApiOperation(value = "获取一个求购信息的详情")
    @GetMapping("detail.action")
    public Result<PurchaseInformationDTO> getOne(@ApiParam(value = "purchaseInformationId", required = true) @RequestParam(value = "purchaseInformationId") Integer purchaseInformationId) {
        return ResultUtil.success(purchaseInformationService.getOne(purchaseInformationId));
    }
}
