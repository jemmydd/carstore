package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.FollowDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FollowDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FollowDO record);

    int insertSelective(FollowDO record);

    FollowDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FollowDO record);

    int updateByPrimaryKey(FollowDO record);

    @Select("select to_id from follow where from_id = #{fromId}")
    @ResultType(Integer.class)
    List<Integer> selectToByFrom(@Param("fromId") Integer fromId);

    List<FollowDO> selectByFrom(@Param("fromId") Integer fromId);

    List<FollowDO> selectByTo(@Param("toId") Integer toId);

    @Delete("delete from follow where from_id = #{fromId} and to_id = #{toId}")
    void deleteByFromTo(@Param("fromId") Integer fromId, @Param("toId") Integer toId);

    @Select("select * from follow where from_id = #{userId} or to_id = #{userId}")
    @ResultMap("BaseResultMap")
    List<FollowDO> selectAbout(@Param("userId") Integer userId);
}