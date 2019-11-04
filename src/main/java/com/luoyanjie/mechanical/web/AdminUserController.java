package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserSimpleDTO;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.AddPurchaseInformationParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-14 22:06
 * Coding happily every day!
 */
@Api(tags = "ADMIN-用户")
@Slf4j
@RestController
@RequestMapping("adminUser")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询用户")
    @GetMapping("searchForAdmin.admin")
    public Result<PageData<UserDTO>> search(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "省份code") @RequestParam(value = "provinceCode", required = false) String provinceCode,
            @ApiParam(value = "城市code") @RequestParam(value = "cityCode", required = false) String cityCode,
            @ApiParam(value = "区code") @RequestParam(value = "areaCode", required = false) String areaCode,
            @ApiParam(value = "联系电话") @RequestParam(value = "phone", required = false) String phone
    ) {
        return ResultUtil.success(userService.search(pageNum, pageSize, provinceCode, cityCode, areaCode, phone));
    }

    @ApiOperation(value = "所有未删除的用户")
    @GetMapping("allNotDelete.admin")
    public Result<List<UserDTO>> allNotDelete() {
        return ResultUtil.success(userService.allNotDelete());
    }

    @ApiOperation(value = "所有未删除的用户-简单版")
    @GetMapping("allNotDeleteSimple.admin")
    public Result<List<UserSimpleDTO>> allNotDeleteSimple() {
        return ResultUtil.success(userService.allNotDeleteSimple());
    }

    @ApiOperation(value = "拉黑/删除用户")
    @PostMapping("delete.admin")
    public Result<Boolean> delete(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId") Integer userId) {
        return ResultUtil.success(userService.delete(userId));
    }

    @ApiOperation(value = "恢复用户")
    @PostMapping("recover.admin")
    public Result<Boolean> recover(@ApiParam(value = "用户ID", required = true) @RequestParam(value = "userId") Integer userId) {
        return ResultUtil.success(userService.recover(userId));
    }
}
