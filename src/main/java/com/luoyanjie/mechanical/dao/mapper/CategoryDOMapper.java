package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.CategoryDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryDO record);

    int insertSelective(CategoryDO record);

    CategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryDO record);

    int updateByPrimaryKey(CategoryDO record);

    @Select("select * from category")
    @ResultMap("BaseResultMap")
    List<CategoryDO> selectAll();

    @Select("select * from category where is_deleted = 0")
    @ResultMap("BaseResultMap")
    List<CategoryDO> selectAllUnDeleted();

    @Select("select * from category where is_deleted = 0 and category_level = 1 and is_show_index = 1")
    @ResultMap("BaseResultMap")
    List<CategoryDO> selectAllFirst();

    void secondGoToFirst(@Param("firstId") Integer firstId, @Param("secondCategoryIds") List<Integer> secondCategoryIds);

    @Select("select * from category where is_deleted = 0 and category_level = 1 order by id desc")
    @ResultMap("BaseResultMap")
    List<CategoryDO> select();

    List<CategoryDO> selectBatchSecondByFirst(@Param("firstIds") List<Integer> firstIds);

    List<CategoryDO> selectSecondByFirst(@Param("firstId") Integer firstId);

    @Update("update category set is_deleted = 1 where id = #{id}")
    void softDelete(@Param("id") Integer id);
}