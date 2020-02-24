package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminMessageDTO;
import com.lym.mechanical.bean.dto.admin.AdminVipDTO;
import com.lym.mechanical.bean.param.admin.AdminMessageParam;
import com.lym.mechanical.bean.param.admin.AdminMessageSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminSettingService;
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
 * @create 2020-02-24 16:27
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminSetting")
@Api(tags = "设置")
public class AdminSettingController {

    @Autowired
    private AdminSettingService adminSettingService;

    @GetMapping("vipList.admin")
    @ApiOperation(value = "vip套餐列表")
    public Result<PageData<AdminVipDTO>> vipList(@RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                 @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminSettingService.vipList(pageNum, pageSize));
    }

    @PostMapping("addVip.admin")
    @ApiOperation(value = "新增vip套餐")
    public Result<Boolean> addVip(@RequestBody AdminVipDTO param) {
        return ResultUtil.success(adminSettingService.addVip(param));
    }

    @PostMapping("modifyVip.admin")
    @ApiOperation(value = "编辑vip套餐")
    public Result<Boolean> modifyVip(@RequestBody AdminVipDTO param) {
        return ResultUtil.success(adminSettingService.modifyVip(param));
    }

    @PostMapping("deleteVip.admin")
    @ApiOperation(value = "删除vip套餐")
    public Result<Boolean> deleteVip(@RequestParam("id") @ApiParam(value = "id") Integer id) {
        return ResultUtil.success(adminSettingService.deleteVip(id));
    }

    @PostMapping("messageList.admin")
    @ApiOperation(value = "系统站内信列表")
    public Result<PageData<AdminMessageDTO>> messageList(@RequestBody AdminMessageSearchParam param) {
        return ResultUtil.success(adminSettingService.messageList(param));
    }

    @PostMapping("send.admin")
    @ApiOperation(value = "发送系统站内信")
    public Result<Boolean> send(@RequestBody AdminMessageParam param) {
        return ResultUtil.success(adminSettingService.send(param));
    }
}
