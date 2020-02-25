package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.NewsTypeDO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsTypeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewsTypeDO record);

    int insertSelective(NewsTypeDO record);

    NewsTypeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewsTypeDO record);

    int updateByPrimaryKey(NewsTypeDO record);

    @Select("select * from news_type order by id desc")
    @ResultMap("BaseResultMap")
    List<NewsTypeDO> selectAll();
}