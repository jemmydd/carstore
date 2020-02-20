package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminLookRecordDTO;
import com.lym.mechanical.bean.dto.admin.AdminPublishRecordDTO;
import com.lym.mechanical.bean.dto.admin.AdminUserDTO;
import com.lym.mechanical.bean.param.admin.AdminUserSearchParam;
import com.lym.mechanical.bean.param.admin.AdminVipParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminUserService;
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
 * @create 2020-02-20 13:23
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminUser")
@Api(tags = "访客管理")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("list")
    @ApiOperation(value = "访客列表")
    public Result<PageData<AdminUserDTO>> list(@RequestBody AdminUserSearchParam param) {
        return ResultUtil.success(adminUserService.list(param));
    }

    @PostMapping("vip")
    @ApiOperation(value = "开通VIP")
    public Result<Boolean> vip(@RequestBody AdminVipParam param) {
        return ResultUtil.success(adminUserService.vip(param));
    }

    @GetMapping("collectRecord")
    @ApiOperation(value = "收藏的设备列表")
    public Result<PageData<AdminPublishRecordDTO>> collectRecord(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                                 @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                                 @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminUserService.collectRecord(userId, pageNum, pageSize));
    }

    @GetMapping("nameCardRecord")
    @ApiOperation(value = "浏览的名片列表")
    public Result<PageData<AdminLookRecordDTO>> nameCardRecord(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                               @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                               @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminUserService.nameCardRecord(userId, pageNum, pageSize));
    }
}
