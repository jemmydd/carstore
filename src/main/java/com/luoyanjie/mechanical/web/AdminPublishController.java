package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishSubmitDTO;
import com.luoyanjie.mechanical.bean.param.publish.AdminPublishModifyParam;
import com.luoyanjie.mechanical.bean.param.publish.AdminPublishSubmitParam;
import com.luoyanjie.mechanical.bean.param.publish.PublishSubmitParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.publish.PublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-08-14 11:38
 * Coding happily every day!
 */
@Api(tags = "ADMIN-发布")
@Slf4j
@RestController
@RequestMapping("adminPublish")
public class AdminPublishController {
    @Autowired
    private PublishService publishService;

    @ApiOperation(value = "查询发布")
    @GetMapping("searchForAdmin.admin")
    public Result<PageData<PublishDTO>> searchForAdmin(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "省份code") @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @ApiParam(value = "城市code") @RequestParam(value = "cityCode", required = false) String cityCode,
            @ApiParam(value = "区code") @RequestParam(value = "areaCode", required = false) String areaCode,
            @ApiParam(value = "发布者手机号") @RequestParam(value = "phone", required = false) String phone,
            @ApiParam(value = "title") @RequestParam(value = "title", required = false) String title
    ) {
        return ResultUtil.success(publishService.searchForAdmin(pageNum, pageSize, provinceCode, cityCode, areaCode, phone, title));
    }

    @ApiOperation(value = "提交一个发布")
    @PostMapping("publish.admin")
    public Result<PublishSubmitDTO> publish(@RequestBody AdminPublishSubmitParam param) {
        return ResultUtil.success(publishService.publishForAdmin(param));
    }

    @ApiOperation(value = "删除一个发布")
    @PostMapping("deletePublish.admin")
    public Result<Boolean> deletePublish(@ApiParam(value = "发布ID", required = true) @RequestParam(value = "publishId") Integer publishId) {
        return ResultUtil.success(publishService.deletePublish(publishId));
    }

    @ApiOperation(value = "修改一个发布")
    @PostMapping("modifyPublish.admin")
    public Result<Boolean> modifyPublish(@RequestBody AdminPublishModifyParam param) {
        return ResultUtil.success(publishService.modifyPublishForAdmin(param));
    }
}
