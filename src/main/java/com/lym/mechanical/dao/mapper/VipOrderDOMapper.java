package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.VipOrderDO;
import org.springframework.stereotype.Component;

@Component
public interface VipOrderDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipOrderDO record);

    int insertSelective(VipOrderDO record);

    VipOrderDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipOrderDO record);

    int updateByPrimaryKey(VipOrderDO record);
}