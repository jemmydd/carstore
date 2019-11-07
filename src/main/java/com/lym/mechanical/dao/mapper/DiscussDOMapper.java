package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.DiscussDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiscussDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiscussDO record);

    int insertSelective(DiscussDO record);

    DiscussDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscussDO record);

    int updateByPrimaryKey(DiscussDO record);

    @Select("select count(*) from discuss where publish_user_id = #{publishUserId} and user_id != #{publishUserId} and publish_user_read_status = 0")
    @ResultType(Integer.class)
    Integer selectUnreadByPublishUser(@Param("publishUserId") Integer publishUserId);

    @Select("select * from discuss where publish_id = #{publishId}")
    @ResultMap("BaseResultMap")
    List<DiscussDO> selectByPublish(@Param("publishId") Integer publishId);
}