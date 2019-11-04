package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.banner.FirstCategoryDTO;
import com.luoyanjie.mechanical.bean.dto.banner.SecondCategoryDTO;
import com.luoyanjie.mechanical.bean.param.category.AddFirstCategoryParam;
import com.luoyanjie.mechanical.bean.param.category.ModifyFirstCategoryParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.category.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-08-07 18:12
 * Coding happily every day!
 */
@Api(tags = "ADMIN-类目")
@Slf4j
@RestController
@RequestMapping("adminCategory")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取一级分类列表")
    @GetMapping("firstCategoryPageData.admin")
    public Result<PageData<FirstCategoryDTO>> getFirstCategoryPageData(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return ResultUtil.success(categoryService.getFirstCategoryPageData(pageNum, pageSize));
    }

    @ApiOperation(value = "获取一个一级分类")
    @GetMapping("firstCategory.admin")
    public Result<FirstCategoryDTO> getOne(@ApiParam(value = "一级分类ID") @RequestParam(value = "firstId") Integer firstId) {
        return ResultUtil.success(categoryService.getOne(firstId));
    }

    @ApiOperation(value = "获取某个一级分类的二级分类")
    @GetMapping("secondCategory.admin")
    public Result<List<SecondCategoryDTO>> getSecondCategory(@ApiParam(value = "一级分类ID") @RequestParam(value = "firstId") Integer firstId) {
        return ResultUtil.success(categoryService.getSecondCategory(firstId));
    }

    @ApiOperation(value = "添加一级类目")
    @PostMapping("firstCategory.admin")
    public Result<Boolean> addFirstCategory(@RequestBody AddFirstCategoryParam param) {
        return ResultUtil.success(categoryService.addFirstCategory(param));
    }

    @ApiOperation(value = "修改一级类目")
    @PostMapping("modifyFirstCategory.admin")
    public Result<Boolean> modifyFirstCategory(@RequestBody ModifyFirstCategoryParam param) {
        return ResultUtil.success(categoryService.modifyFirstCategory(param));
    }

    @ApiOperation(value = "在首页展示一级分类")
    @PostMapping("showIndex.admin")
    public Result<Boolean> showIndex(@ApiParam(value = "一级分类ID") @RequestParam(value = "firstId") Integer firstId) {
        return ResultUtil.success(categoryService.showIndex(firstId));
    }

    @ApiOperation(value = "取消在首页展示一级分类")
    @PostMapping("unShowIndex.admin")
    public Result<Boolean> unShowIndex(@ApiParam(value = "一级分类ID") @RequestParam(value = "firstId") Integer firstId) {
        return ResultUtil.success(categoryService.unShowIndex(firstId));
    }

    @ApiOperation(value = "删除一个一级分类")
    @PostMapping("deleteFirstCategory.admin")
    public Result<Boolean> deleteFirstCategory(@ApiParam(value = "一级分类ID") @RequestParam(value = "firstId") Integer firstId) {
        return ResultUtil.success(categoryService.deleteFirstCategory(firstId));
    }
}
