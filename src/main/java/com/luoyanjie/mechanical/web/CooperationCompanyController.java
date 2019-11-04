package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.cooperationCompany.CooperationCompanyDTO;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.cooperationCompany.CooperationCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luoyanjie
 * @date 2019-08-17 19:18
 * Coding happily every day!
 */
@Api(tags = "合作公司")
@Slf4j
@RestController
@RequestMapping("cooperationCompany")
public class CooperationCompanyController {
    @Autowired
    private CooperationCompanyService cooperationCompanyService;

    @ApiOperation(value = "获取合作公司的分页列表")
    @GetMapping("pageData.action")
    public Result<PageData<CooperationCompanyDTO>> getPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize) {
        return ResultUtil.success(cooperationCompanyService.getPageData(pageNum, pageSize));
    }
}
