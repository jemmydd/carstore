package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.QuestionAnswerDO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionAnswerDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionAnswerDO record);

    int insertSelective(QuestionAnswerDO record);

    QuestionAnswerDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionAnswerDO record);

    int updateByPrimaryKey(QuestionAnswerDO record);

    @Select("select * from question_answer")
    @ResultMap("BaseResultMap")
    List<QuestionAnswerDO> selectAll();
}