package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminStatisticDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyimin
 * @create 2020-02-14 16:38
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminStatistic")
@Api(tags = "统计")
public class AdminStatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping("statistic.admin")
    @ApiOperation(value = "统计")
    public Result<AdminStatisticDTO> statistic() {
        return ResultUtil.success(statisticService.statistic());
    }
}
