package com.lym.mechanical.web;

import com.lym.mechanical.bean.entity.NewsTypeDO;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.news.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liyimin
 * @create 2020-02-25 12:35
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("news")
@Api(tags = "宣传海报")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("typeList.action")
    @ApiOperation(value = "主题列表")
    public Result<List<NewsTypeDO>> typeList() {
        return ResultUtil.success(newsService.typeList());
    }

    @GetMapping("newsList.action")
    @ApiOperation(value = "海报列表")
    public Result<PageData<String>> newsList(@RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                             @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize,
                                             @RequestParam("typeId") @ApiParam(value = "主题id") Integer typeId) {
        return ResultUtil.success(newsService.newsList(pageNum, pageSize, typeId));
    }
}
