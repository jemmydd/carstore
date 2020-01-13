package com.lym.mechanical.web;

import com.lym.mechanical.bean.dto.brand.BrandAdminDTO;
import com.lym.mechanical.component.result.Result;
import com.lym.mechanical.component.result.ResultUtil;
import com.lym.mechanical.service.brand.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-10-02 18:38
 * Coding happily every day!
 */
@Api(tags = "品牌")
@Slf4j
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "查询所有品牌")
    @GetMapping("search.action")
    public Result<List<BrandAdminDTO>> getAll(
            @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "首字符") @RequestParam(value = "capital", required = false) String capital,
            @ApiParam(value = "是否热门") @RequestParam(value = "isHot", required = false) Byte isHot
    ) {
        return ResultUtil.success(brandService.getAll(name, capital, isHot));
    }
}
