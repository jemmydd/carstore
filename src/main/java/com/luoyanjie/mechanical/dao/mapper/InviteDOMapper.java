package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.InviteDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InviteDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InviteDO record);

    int insertSelective(InviteDO record);

    InviteDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InviteDO record);

    int updateByPrimaryKey(InviteDO record);

    @Select("select * from invite where from_id = #{fromId}")
    @ResultMap("BaseResultMap")
    List<InviteDO> selectByFrom(@Param("fromId") Integer fromId);
}