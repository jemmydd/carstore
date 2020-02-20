package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.VipRecordDO;
import org.springframework.stereotype.Component;

@Component
public interface VipRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRecordDO record);

    int insertSelective(VipRecordDO record);

    VipRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipRecordDO record);

    int updateByPrimaryKey(VipRecordDO record);
}