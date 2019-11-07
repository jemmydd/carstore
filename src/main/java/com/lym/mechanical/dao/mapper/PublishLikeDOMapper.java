package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.PublishLikeDO;
import org.springframework.stereotype.Component;

@Component
public interface PublishLikeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishLikeDO record);

    int insertSelective(PublishLikeDO record);

    PublishLikeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishLikeDO record);

    int updateByPrimaryKey(PublishLikeDO record);
}