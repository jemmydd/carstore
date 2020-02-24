package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminCarUserDTO;
import com.lym.mechanical.bean.dto.admin.AdminFriendDTO;
import com.lym.mechanical.bean.param.admin.AdminCarStoreSearchParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminCarStoreService;
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
 * @create 2020-02-21 15:57
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminCarStore")
@Api(tags = "车商管理")
public class AdminCarStoreController {

    @Autowired
    private AdminCarStoreService adminCarStoreService;

    @PostMapping("list.admin")
    @ApiOperation(value = "车商列表")
    public Result<PageData<AdminCarUserDTO>> list(@RequestBody AdminCarStoreSearchParam param) {
        return ResultUtil.success(adminCarStoreService.list(param));
    }

    @GetMapping("friendList.admin")
    @ApiOperation(value = "友情合作列表")
    public Result<PageData<AdminFriendDTO>> friendList(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                                       @RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                       @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminCarStoreService.friendList(userId, pageNum, pageSize));
    }
}
