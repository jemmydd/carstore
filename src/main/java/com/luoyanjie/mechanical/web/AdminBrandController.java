package com.luoyanjie.mechanical.web;

import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.brand.BrandAdminDTO;
import com.luoyanjie.mechanical.bean.param.brand.AddBrandParam;
import com.luoyanjie.mechanical.bean.param.brand.ModifyBrandParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.Result;
import com.luoyanjie.mechanical.component.result.ResultUtil;
import com.luoyanjie.mechanical.service.brand.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author luoyanjie
 * @date 2019-09-30 21:59
 * Coding happily every day!
 */
@Api(tags = "ADMIN-品牌")
@Slf4j
@RestController
@RequestMapping("adminBrand")
public class AdminBrandController {
    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "新增")
    @PostMapping("add.admin")
    public Result<SubmitDTO> add(AddBrandParam param) {
        return ResultUtil.success(brandService.add(param));
    }

    @ApiOperation(value = "修改")
    @PostMapping("modify.admin")
    public Result<SubmitDTO> modify(ModifyBrandParam param) {
        return ResultUtil.success(brandService.modify(param));
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete.admin")
    public Result<Boolean> delete(@ApiParam(value = "id") @RequestParam(value = "id") Integer id) {
        return ResultUtil.success(brandService.delete(id));
    }

    @ApiOperation(value = "查询分页列表")
    @GetMapping("search.admin")
    public Result<PageData<BrandAdminDTO>> search(
            @ApiParam(value = "页码") @RequestParam(value = "pageNum") Integer pageNum,
            @ApiParam(value = "页大小") @RequestParam(value = "pageSize") Integer pageSize,
            @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "首字符") @RequestParam(value = "capital", required = false) String capital,
            @ApiParam(value = "是否热门") @RequestParam(value = "isHot", required = false) Byte isHot
    ) {
        return ResultUtil.success(brandService.search(pageNum, pageSize, name, capital, isHot));
    }
}
