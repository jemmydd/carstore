package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.BrandDO;
import org.springframework.stereotype.Component;

@Component
public interface BrandDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandDO record);

    int insertSelective(BrandDO record);

    BrandDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandDO record);

    int updateByPrimaryKey(BrandDO record);
}