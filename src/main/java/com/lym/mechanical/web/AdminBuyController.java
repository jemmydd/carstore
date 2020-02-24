package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminBuyDTO;
import com.lym.mechanical.bean.param.admin.AdminBuySearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminBuyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-24 14:58
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminBuy")
@Api(tags = "购买管理")
public class AdminBuyController {

    @Autowired
    private AdminBuyService adminBuyService;

    @PostMapping("list.admin")
    @ApiOperation(value = "购买记录列表")
    public Result<PageData<AdminBuyDTO>> list(@RequestBody AdminBuySearchParam param) {
        return ResultUtil.success(adminBuyService.list(param));
    }
}
