package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminUserApplyDTO;
import com.lym.mechanical.bean.param.admin.AdminApplyOperateParam;
import com.lym.mechanical.bean.param.admin.AdminApplySearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminUserApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-21 17:35
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminApply")
@Api(tags = "车商审核管理")
public class AdminUserApplyController {

    @Autowired
    private AdminUserApplyService adminUserApplyService;

    @PostMapping("list")
    @ApiOperation(value = "审核列表")
    public Result<PageData<AdminUserApplyDTO>> list(@RequestBody AdminApplySearchParam param) {
        return ResultUtil.success(adminUserApplyService.list(param));
    }

    @PostMapping("operate")
    @ApiOperation(value = "审核操作")
    public Result<Boolean> operate(@RequestBody AdminApplyOperateParam param) {
        return ResultUtil.success(adminUserApplyService.operate(param));
    }
}
