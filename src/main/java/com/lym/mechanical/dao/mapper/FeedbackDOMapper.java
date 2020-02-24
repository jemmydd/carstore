package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.FeedbackDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FeedbackDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackDO record);

    int insertSelective(FeedbackDO record);

    FeedbackDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackDO record);

    int updateByPrimaryKey(FeedbackDO record);

    @Select("select * from feedback")
    @ResultMap("BaseResultMap")
    List<FeedbackDO> selectAll();

    List<FeedbackDO> selectForWeb(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                  @Param("isDeal") String isDeal);
}