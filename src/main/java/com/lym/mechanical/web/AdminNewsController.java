package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.admin.AdminNewsDTO;
import com.lym.mechanical.bean.dto.admin.AdminNewsTypeDTO;
import com.lym.mechanical.bean.entity.NewsTypeDO;
import com.lym.mechanical.bean.param.admin.AdminNewsParam;
import com.lym.mechanical.bean.param.admin.AdminNewsSearchParam;
import com.lym.mechanical.bean.param.admin.AdminNewsTypeParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.admin.AdminNewsService;
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

import java.util.List;

/**
 * @author liyimin
 * @create 2020-02-25 11:58
 * Good good code, day day up!
 **/
@RestController
@RequestMapping("adminNews")
@Api(tags = "海报管理")
public class AdminNewsController {

    @Autowired
    private AdminNewsService adminNewsService;

    @PostMapping("newsList.admin")
    @ApiOperation(value = "海报列表")
    public Result<PageData<AdminNewsDTO>> newsList(@RequestBody AdminNewsSearchParam param) {
        return ResultUtil.success(adminNewsService.newsList(param));
    }

    @PostMapping("addNews.admin")
    @ApiOperation(value = "新增海报")
    public Result<Boolean> addNews(@RequestBody AdminNewsParam param) {
        return ResultUtil.success(adminNewsService.addNews(param));
    }

    @PostMapping("modifyNews.admin")
    @ApiOperation(value = "编辑海报")
    public Result<Boolean> modifyNews(@RequestBody AdminNewsParam param) {
        return ResultUtil.success(adminNewsService.modifyNews(param));
    }

    @GetMapping("deleteNews.admin")
    @ApiOperation(value = "删除海报")
    public Result<Boolean> deleteNews(@RequestParam("id") @ApiParam(value = "海报id") Integer id) {
        return ResultUtil.success(adminNewsService.deleteNews(id));
    }

    @GetMapping("typeList.admin")
    @ApiOperation(value = "主题列表")
    public Result<PageData<AdminNewsTypeDTO>> typeList(@RequestParam("pageNum") @ApiParam(value = "当前页，从1开始") Integer pageNum,
                                                       @RequestParam("pageSize") @ApiParam(value = "每页显示数量") Integer pageSize) {
        return ResultUtil.success(adminNewsService.typeList(pageNum, pageSize));
    }

    @PostMapping("addType.admin")
    @ApiOperation(value = "新增主题")
    public Result<Boolean> addType(@RequestBody AdminNewsTypeParam param) {
        return ResultUtil.success(adminNewsService.addType(param));
    }

    @PostMapping("modifyType.admin")
    @ApiOperation(value = "编辑主题")
    public Result<Boolean> modifyType(@RequestBody AdminNewsTypeParam param) {
        return ResultUtil.success(adminNewsService.modifyType(param));
    }

    @GetMapping("deleteType.admin")
    @ApiOperation(value = "删除主题")
    public Result<Boolean> deleteType(@RequestParam("id") @ApiParam(value = "主题id") Integer id) {
        return ResultUtil.success(adminNewsService.deleteType(id));
    }

    @GetMapping("selectTypeList")
    @ApiOperation(value = "主题下拉框列表")
    public Result<List<NewsTypeDO>> selectTypeList() {
        return ResultUtil.success(adminNewsService.selectTypeList());
    }
}
