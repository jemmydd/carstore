package com.luoyanjie.mechanical.service.category;

import com.luoyanjie.mechanical.bean.dto.banner.FirstCategoryDTO;
import com.luoyanjie.mechanical.bean.dto.banner.SecondCategoryDTO;
import com.luoyanjie.mechanical.bean.dto.category.CategoryDTO;
import com.luoyanjie.mechanical.bean.param.category.AddFirstCategoryParam;
import com.luoyanjie.mechanical.bean.param.category.ModifyFirstCategoryParam;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryDTO> getIndexHot();

    List<CategoryDTO> getCategoryForList();

    PageData<FirstCategoryDTO> getFirstCategoryPageData(Integer pageNum, Integer pageSize);

    FirstCategoryDTO getOne(Integer firstId);

    List<SecondCategoryDTO> getSecondCategory(Integer firstId);

    Boolean addFirstCategory(AddFirstCategoryParam param);

    Boolean modifyFirstCategory(ModifyFirstCategoryParam param);

    Boolean showIndex(Integer firstId);

    Boolean unShowIndex(Integer firstId);

    Boolean deleteFirstCategory(Integer firstId);

    Map<Integer, String> allMap();
}
