package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.cooperationCompany.CooperationCompanyDTO;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.AddCooperationCompanyParam;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.ModifyCooperationCompanyParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.cooperationCompany.CooperationCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-08-17 19:35
 * Coding happily every day!
 */
@Api(tags = "ADMIN-合作公司")
@Slf4j
@RestController
@RequestMapping("adminCooperationCompany")
public class AdminCooperationCompanyController {
    @Autowired
    private CooperationCompanyService cooperationCompanyService;

    @ApiOperation(value = "增加合作公司")
    @PostMapping("addCooperationCompany.admin")
    public Result<Boolean> modify(@RequestBody AddCooperationCompanyParam param) {
        return ResultUtil.success(cooperationCompanyService.add(param));
    }

    @ApiOperation(value = "删除合作公司")
    @PostMapping("deleteCooperationCompany.admin")
    public Result<Boolean> delete(@ApiParam(value = "合作公司ID") @RequestParam(value = "cooperationCompanyId") Integer cooperationCompanyId) {
        return ResultUtil.success(cooperationCompanyService.delete(cooperationCompanyId));
    }

    @ApiOperation(value = "修改合作公司")
    @PostMapping("modifyCooperationCompany.admin")
    public Result<Boolean> modify(@RequestBody ModifyCooperationCompanyParam param) {
        return ResultUtil.success(cooperationCompanyService.modify(param));
    }

    @ApiOperation(value = "查询合作公司")
    @GetMapping("searchForAdmin.admin")
    public Result<PageData<CooperationCompanyDTO>> searchForAdmin(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小", required = true) @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "公司名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "地址") @RequestParam(value = "address", required = false) String address,
            @ApiParam(value = "联系电话") @RequestParam(value = "contactPhone", required = false) String contactPhone
    ) {
        return ResultUtil.success(cooperationCompanyService.searchForAdmin(pageNum, pageSize, name, address, contactPhone));
    }
}
