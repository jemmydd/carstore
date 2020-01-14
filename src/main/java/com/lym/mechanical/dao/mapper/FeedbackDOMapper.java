package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.FeedbackDO;
import org.springframework.stereotype.Component;

@Component
public interface FeedbackDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeedbackDO record);

    int insertSelective(FeedbackDO record);

    FeedbackDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeedbackDO record);

    int updateByPrimaryKey(FeedbackDO record);
}