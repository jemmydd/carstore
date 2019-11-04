package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.category.CategoryDTO;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.category.CategoryService;
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
 * @date 2019-08-03 17:03
 * Coding happily every day!
 */
@Api(tags = "类目")
@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页曝光的类目")
    @GetMapping("indexHot.action")
    public Result<List<CategoryDTO>> getIndexHot() {
        return ResultUtil.success(categoryService.getIndexHot());
    }

    @ApiOperation(value = "获取所有类目列表")
    @GetMapping("categoryForList.action")
    public Result<List<CategoryDTO>> getCategoryForList() {
        return ResultUtil.success(categoryService.getCategoryForList());
    }
}
