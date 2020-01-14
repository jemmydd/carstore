package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.VipOrderDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VipOrderDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipOrderDO record);

    int insertSelective(VipOrderDO record);

    VipOrderDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipOrderDO record);

    int updateByPrimaryKey(VipOrderDO record);

    @Select("select * from vip_order where status = #{status} order by update_time desc")
    @ResultMap("BaseResultMap")
    List<VipOrderDO> selectByStatus(@Param("status") String status);
}