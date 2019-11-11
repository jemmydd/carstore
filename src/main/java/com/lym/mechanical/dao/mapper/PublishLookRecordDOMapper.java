package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.PublishLookRecordDO;
import org.springframework.stereotype.Component;

@Component
public interface PublishLookRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishLookRecordDO record);

    int insertSelective(PublishLookRecordDO record);

    PublishLookRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishLookRecordDO record);

    int updateByPrimaryKey(PublishLookRecordDO record);
}