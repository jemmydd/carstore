package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.VipConfigDO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VipConfigDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipConfigDO record);

    int insertSelective(VipConfigDO record);

    VipConfigDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipConfigDO record);

    int updateByPrimaryKey(VipConfigDO record);

    @Select("select * from vip_config")
    @ResultMap("BaseResultMap")
    List<VipConfigDO> selectAll();
}