package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.index.AdminIndexDataDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisMonthDataIncreaseDTO;
import com.luoyanjie.mechanical.bean.dto.index.ThisYearDataDistributionDTO;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.index.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-15 10:39
 * Coding happily every day!
 */
@Api(tags = "ADMIN-首页")
@Slf4j
@RestController
@RequestMapping("adminIndex")
public class AdminIndexController {
    @Autowired
    private IndexService indexService;

    @ApiOperation(value = "获取统计数据")
    @GetMapping("indexData.admin")
    public Result<AdminIndexDataDTO> getIndexData() {
        return ResultUtil.success(indexService.getIndexData());
    }

    @ApiOperation(value = "获取本年度数据分布")
    @GetMapping("thisYearDataDistribution.admin")
    public Result<List<ThisYearDataDistributionDTO>> getThisYearDataDistribution() {
        return ResultUtil.success(indexService.getThisYearDataDistribution());
    }

    @ApiOperation(value = "获取本月数据增长曲线")
    @GetMapping("thisMonthDataIncrease.admin")
    public Result<List<ThisMonthDataIncreaseDTO>> getThisMonthDataIncrease() {
        return ResultUtil.success(indexService.getThisMonthDataIncrease());
    }
}
