package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.CollectionDO;
import org.springframework.stereotype.Component;

@Component
public interface CollectionDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectionDO record);

    int insertSelective(CollectionDO record);

    CollectionDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionDO record);

    int updateByPrimaryKey(CollectionDO record);
}