package com.lym.mechanical.service.category;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lym.mechanical.bean.common.ImageConstant;
import com.lym.mechanical.bean.dto.category.CategoryDTO;
import com.lym.mechanical.bean.dto.category.FirstCategoryDTO;
import com.lym.mechanical.bean.dto.category.SecondCategoryDTO;
import com.lym.mechanical.bean.entity.CategoryDO;
import com.lym.mechanical.bean.param.category.AddFirstCategoryParam;
import com.lym.mechanical.bean.param.category.AddSecondCategoryParam;
import com.lym.mechanical.bean.param.category.ModifyFirstCategoryParam;
import com.lym.mechanical.bean.param.category.ModifySecondCategoryParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.CategoryDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryDOMapper categoryDOMapper;

    private static final Integer LIMIT = 7;

    private static final Integer DEFAULT_SORTED_NUM = -1;
    private static final Integer SELECT_ALL_ID = -1;
    private static final String SELECT_ALL_TEXT = "全部";
    private static final Integer FIRST_LEVEL = 1;
    private static final Integer SECOND_LEVEL = 2;
    private static final CategoryDTO FIRST_SELECT_ALL = CategoryDTO.builder()
            .children(Lists.newArrayList())
            .icon(ImageConstant.FIRST_SELECT_ALL)
            .id(SELECT_ALL_ID)
            .categoryLevel(FIRST_LEVEL)
            .name(SELECT_ALL_TEXT)
            .build();
    private static final CategoryDTO SECOND_SELECT_ALL = CategoryDTO.builder()
            .children(Lists.newArrayList())
            .icon(ImageConstant.SECOND_SELECT_ALL)
            .id(SELECT_ALL_ID)
            .name(SELECT_ALL_TEXT)
            .categoryLevel(SECOND_LEVEL)
            .build();

    public List<CategoryDTO> getIndexHot() {
        List<CategoryDTO> categoryDTOS = Lists.newArrayList();

        List<CategoryDO> categoryFirstDOS = categoryDOMapper.selectAllFirst();

        if (!ObjectUtils.isEmpty(categoryFirstDOS)) {
            /*List<CategoryDO> temp = categoryFirstDOS.stream()
                    .sorted(Comparator.comparing(CategoryDO::getSortedNum))
                    .collect(Collectors.toList())
                    .subList(0, endForIndexHot(categoryFirstDOS.size()));

            categoryDTOS.addAll(temp.stream().map(row -> cov(row, Lists.newArrayList())).collect(Collectors.toList()));*/

            categoryDTOS.addAll(
                    categoryFirstDOS.stream().sorted(Comparator.comparing(CategoryDO::getSortedNum)).map(row -> cov(row, Lists.newArrayList())).collect(Collectors.toList())
            );

            //categoryDTOS.add(FIRST_SELECT_ALL);
        }

        return categoryDTOS;
    }

    public List<CategoryDTO> getCategoryForList() {
        List<CategoryDTO> categoryDTOS = Lists.newArrayList();

        List<CategoryDO> categoryFirstDOS = categoryDOMapper.selectAllUnDeleted();

        if (!ObjectUtils.isEmpty(categoryFirstDOS)) {
            Map<Integer, List<CategoryDO>> cm = categoryFirstDOS.stream().collect(Collectors.groupingBy(CategoryDO::getCategoryLevel));

            List<CategoryDO> seconds = cm.get(SECOND_LEVEL);
            Map<Integer, List<CategoryDO>> cms = seconds.stream().collect(Collectors.groupingBy(CategoryDO::getParentId));

            List<CategoryDO> sortedFirst = cm.get(FIRST_LEVEL).stream().sorted(Comparator.comparing(CategoryDO::getSortedNum)).collect(Collectors.toList());

            for (CategoryDO first : sortedFirst) {
                List<CategoryDTO> temp = Lists.newArrayList();

                List<CategoryDO> secondForThis = cms.get(first.getId());
                if (!ObjectUtils.isEmpty(secondForThis)) {
                    temp.addAll(
                            secondForThis.stream().map(c -> cov(c, Lists.newArrayList())).collect(Collectors.toList())
                    );
                }

                //temp.add(SECOND_SELECT_ALL);

                categoryDTOS.add(cov(first, temp));
            }
        }

        return categoryDTOS;
    }

    public PageData<FirstCategoryDTO> getFirstCategoryPageData(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<CategoryDO> pd = (Page<CategoryDO>) categoryDOMapper.select();

        List<CategoryDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        List<CategoryDO> second = categoryDOMapper.selectBatchSecondByFirst(data.stream().map(CategoryDO::getId).distinct().collect(Collectors.toList()));
        Map<Integer, List<CategoryDO>> secondMap = second.stream().collect(Collectors.groupingBy(CategoryDO::getParentId));

        return PageData.data(pd, data.stream().map(row -> cavForAdmin(row, secondMap.get(row.getId()))).collect(Collectors.toList()));
    }

    public FirstCategoryDTO getOne(Integer firstId) {
        if (firstId == null) throw new RuntimeException("一级分类ID不能为空");

        return cavForAdmin(categoryDOMapper.selectByPrimaryKey(firstId), categoryDOMapper.selectSecondByFirst(firstId));
    }

    public List<SecondCategoryDTO> getSecondCategory(Integer firstId) {
        if (firstId == null) throw new RuntimeException("一级分类ID不能为空");

        List<SecondCategoryDTO> data = Lists.newArrayList();

        List<CategoryDO> categoryDOS = categoryDOMapper.selectSecondByFirst(firstId);
        if (!ObjectUtils.isEmpty(categoryDOS)) {
            data.addAll(categoryDOS.stream().map(this::cavSecondForAdmin).collect(Collectors.toList()));
        }

        return data;
    }

    @Transactional
    public Boolean addFirstCategory(AddFirstCategoryParam param) {
        if (StringUtils.isEmpty(param.getIcon())) throw new RuntimeException("icon不能为空");
        if (StringUtils.isEmpty(param.getName())) throw new RuntimeException("名称不能为空");

        try {
            CategoryDO categoryDO = CategoryDO.builder()
                    .categoryLevel(FIRST_LEVEL)
                    .icon(param.getIcon())
                    .isDeleted((byte) 0)
                    .isShowIndex(param.getIsShowIndex() == null ? (byte) 0 : (param.getIsShowIndex() ? (byte) 1 : (byte) 0))
                    .name(param.getName())
                    .parentId(0)
                    .sortedNum(param.getSortedNum() == null ? DEFAULT_SORTED_NUM : param.getSortedNum())
                    .build();

            categoryDOMapper.insertSelective(categoryDO);

            if (!ObjectUtils.isEmpty(param.getSecondCategories())) {
                Integer pId = categoryDO.getId();

                int index = 0;
                for (AddSecondCategoryParam row : param.getSecondCategories()) {
                    categoryDOMapper.insertSelective(
                            CategoryDO.builder()
                                    .categoryLevel(SECOND_LEVEL)
                                    .icon(row.getIcon())
                                    .isDeleted((byte) 0)
                                    .isShowIndex((byte) 0)
                                    .name(row.getName())
                                    .parentId(pId)
                                    .sortedNum(index)
                                    .build()
                    );

                    index++;
                }
            }
        } catch (DuplicateKeyException ex) {
            ex.printStackTrace();
            throw new RuntimeException("一级分类创建失败，名称重复");
        }

        return true;
    }

    public Boolean modifyFirstCategory(ModifyFirstCategoryParam param) {
        if (param.getFirstId() == null) throw new RuntimeException("一级分类名称不能为空");
        if (StringUtils.isEmpty(param.getIcon())) throw new RuntimeException("icon不能为空");
        if (StringUtils.isEmpty(param.getName())) throw new RuntimeException("名称不能为空");

        CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(param.getFirstId());
        if (categoryDO == null) throw new RuntimeException("一级分类不存在");

        if (!FIRST_LEVEL.equals(categoryDO.getCategoryLevel())) throw new RuntimeException("这个不是一级分类");

        List<CategoryDO> secondsDb = categoryDOMapper.selectSecondByFirst(param.getFirstId());

        try {
            categoryDOMapper.updateByPrimaryKeySelective(
                    CategoryDO.builder()
                            .icon(param.getIcon())
                            .id(param.getFirstId())
                            .isShowIndex(param.getIsShowIndex() == null ? (byte) 0 : (param.getIsShowIndex() ? (byte) 1 : (byte) 0))
                            .name(param.getName())
                            .sortedNum(param.getSortedNum() == null ? DEFAULT_SORTED_NUM : param.getSortedNum())
                            .build()
            );

            if (param.getSecondCategories() == null) param.setSecondCategories(Lists.newArrayList());
            List<Integer> secondIds = param.getSecondCategories().stream().filter(s -> s.getSecondId() != null).map(ModifySecondCategoryParam::getSecondId).distinct().collect(Collectors.toList());

            //遍历数据库里面的二级
            for (CategoryDO second : secondsDb) {
                if (secondIds.contains(second.getId())) {
                    categoryDOMapper.updateByPrimaryKeySelective(
                            CategoryDO.builder()
                                    .id(second.getId())
                                    .icon(second.getIcon())
                                    .name(second.getName())
                                    .build()
                    );
                } else {
                    categoryDOMapper.softDelete(second.getId());
                }
            }

            //遍历传进来的二级
            for (ModifySecondCategoryParam second : param.getSecondCategories()) {
                if (second.getSecondId() == null) {
                    categoryDOMapper.insertSelective(
                            CategoryDO.builder()
                                    .categoryLevel(SECOND_LEVEL)
                                    .icon(second.getIcon())
                                    .isDeleted((byte) 0)
                                    .isShowIndex((byte) 0)
                                    .name(second.getName())
                                    .parentId(param.getFirstId())
                                    .sortedNum(DEFAULT_SORTED_NUM)
                                    .build()
                    );
                }
            }
        } catch (DuplicateKeyException ex) {
            ex.printStackTrace();
            throw new RuntimeException("一级分类创建失败，名称重复");
        }

        return true;
    }

    public Boolean showIndex(Integer firstId) {
        if (firstId == null) throw new RuntimeException("一级分类名称不能为空");

        categoryDOMapper.updateByPrimaryKeySelective(
                CategoryDO.builder()
                        .id(firstId)
                        .isShowIndex((byte) 1)
                        .build()
        );

        return true;
    }

    public Boolean unShowIndex(Integer firstId) {
        if (firstId == null) throw new RuntimeException("一级分类名称不能为空");

        categoryDOMapper.updateByPrimaryKeySelective(
                CategoryDO.builder()
                        .id(firstId)
                        .isShowIndex((byte) 0)
                        .build()
        );

        return true;
    }

    public Boolean deleteFirstCategory(Integer firstId) {
        if (firstId == null) throw new RuntimeException("一级分类名称不能为空");

        categoryDOMapper.updateByPrimaryKeySelective(
                CategoryDO.builder()
                        .id(firstId)
                        .isDeleted((byte) 1)
                        .build()
        );

        return true;
    }

    public Map<Integer, String> allMap() {
        return categoryDOMapper.selectAll().stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));
    }

    private CategoryDTO cov(CategoryDO categoryDO, List<CategoryDTO> children) {
        return CategoryDTO.builder()
                .children(children)
                .icon(categoryDO.getIcon())
                .id(categoryDO.getId())
                .categoryLevel(categoryDO.getCategoryLevel())
                .name(StringUtils.isEmpty(categoryDO.getName()) ? "" : categoryDO.getName())
                .build();
    }

    private FirstCategoryDTO cavForAdmin(CategoryDO row, List<CategoryDO> secondSource) {
        List<SecondCategoryDTO> second = Lists.newArrayList();
        List<String> s = Lists.newArrayList();

        if (!ObjectUtils.isEmpty(secondSource)) {
            s.addAll(secondSource.stream().map(CategoryDO::getName).collect(Collectors.toList()));
            second.addAll(secondSource.stream()
                    .map(x -> SecondCategoryDTO.builder().icon(x.getIcon()).id(x.getId()).name(StringUtils.isEmpty(x.getName()) ? "" : x.getName()).build())
                    .collect(Collectors.toList()));
        }

        return FirstCategoryDTO.builder()
                .icon(row.getIcon())
                .id(row.getId())
                .IsShowIndex(Objects.equals(row.getIsShowIndex(), (byte) 1))
                .name(StringUtils.isEmpty(row.getName()) ? "" : row.getName())
                .secondCategories(second)
                .secondName(s)
                .secondNameStr(StringUtils.join(s, ","))
                .build();
    }

    private SecondCategoryDTO cavSecondForAdmin(CategoryDO row) {
        return SecondCategoryDTO.builder()
                .icon(row.getIcon())
                .id(row.getId())
                .name(StringUtils.isEmpty(row.getName()) ? "" : row.getName())
                .build();
    }

    private Integer endForIndexHot(Integer size) {
        return size >= LIMIT ? LIMIT : size;
    }
}
