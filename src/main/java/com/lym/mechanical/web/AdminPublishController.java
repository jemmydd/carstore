package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminPublishDTO;
import com.lym.mechanical.bean.dto.admin.AdminPublishRecordDTO;
import com.lym.mechanical.bean.param.admin.AdminPublishSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-19 16:12
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminPublish")
@Api(tags = "设备管理")
public class AdminPublishController {

    @Autowired
    private AdminPublishService adminPublishService;

    @PostMapping("list")
    @ApiOperation(value = "设备列表")
    public Result<PageData<AdminPublishDTO>> list(@RequestBody AdminPublishSearchParam param) {
        return ResultUtil.success(adminPublishService.list(param));
    }

    @GetMapping("recordList")
    @ApiOperation(value = "浏览的访客/收藏的访客")
    public Result<PageData<AdminPublishRecordDTO>> recordList(@RequestParam("publishId") @ApiParam(value = "设备id") Integer publishId,
                                                              @RequestParam("type") @ApiParam(value = "0-浏览的访客，1-收藏的访客") String type,
                                                            @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                            @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminPublishService.recordList(publishId, type, pageNum, pageSize));
    }
}
