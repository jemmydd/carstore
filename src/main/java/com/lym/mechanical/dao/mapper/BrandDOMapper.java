package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.BrandDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BrandDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrandDO record);

    int insertSelective(BrandDO record);

    BrandDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrandDO record);

    int updateByPrimaryKey(BrandDO record);

    List<BrandDO> search(@Param("name") String name, @Param("capital") String capital, @Param("isHot") Byte isHot);
}