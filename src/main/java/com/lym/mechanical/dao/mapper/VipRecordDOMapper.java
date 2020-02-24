package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.VipRecordDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VipRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRecordDO record);

    int insertSelective(VipRecordDO record);

    VipRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipRecordDO record);

    int updateByPrimaryKey(VipRecordDO record);

    List<VipRecordDO> selectForWeb(@Param("startTime") String startTime, @Param("endTime") String endTime);
}