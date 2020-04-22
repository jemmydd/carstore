package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.statistic.StatisticDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.statistic.WeekStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StatisticController
 * @Description
 * @Date 2020/4/22 15:06
 * @Created by jimy
 * good good code, day day up!
 */
@RequestMapping("statistic")
@RestController
@Api(tags = "小程序端周统计")
public class StatisticController {

    @Autowired
    private WeekStatisticService weekStatisticService;

    @GetMapping("statisticWeek.action")
    @ApiOperation(value = "周数据分析")
    public Result<StatisticDTO> statisticWeek(@RequestParam("userId") @ApiParam(value = "用户id") Integer userId,
                                              @RequestParam("startDate") @ApiParam(value = "统计开始日期，yyyy-MM-dd") String startDate,
                                              @RequestParam("endDate") @ApiParam(value = "统计结束日期，yyyy-MM-dd") String endDate) {
        return ResultUtil.success(weekStatisticService.statisticWeek(userId, startDate, endDate));
    }
}
