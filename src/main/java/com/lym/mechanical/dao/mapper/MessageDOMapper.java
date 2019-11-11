package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.MessageDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MessageDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageDO record);

    int insertSelective(MessageDO record);

    MessageDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageDO record);

    int updateByPrimaryKey(MessageDO record);

    @Select("select distinct user_group from message where from_car_user_id = #{userId} or to_car_user_id = #{userId} order by create_time desc, id desc limit 2")
    @ResultType(String.class)
    List<String> selectRecentlyUsers(@Param("userId") Integer userId);

    @Select("select distinct user_group from message where from_car_user_id = #{userId} or to_car_user_id = #{userId} order by create_time desc, id desc")
    @ResultType(String.class)
    List<String> selectRecentlyUsersNoLimit(@Param("userId") Integer userId);

    List<MessageDO> selectBatchByUserGroup(@Param("userGroups") List<String> userGroups);

    @Select("SELECT * from message JOIN (SELECT MAX(create_time) AS `time`,user_group AS user_group FROM message WHERE from_car_user_id = #{userId} OR to_car_user_id = #{userId}" +
            " GROUP BY user_group ) a ON message.create_time = a.`time` AND message.user_group = a.user_group")
    @ResultMap("BaseResultMap")
    List<MessageDO> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT * from message where user_group = #{userGroup} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<MessageDO> selectByUserGroup(@Param("userGroup") String userGroup);
}