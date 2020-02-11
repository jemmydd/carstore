package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.dto.my.CommonDTO;
import com.lym.mechanical.bean.entity.MessageDO;
import org.apache.ibatis.annotations.*;
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

    @Select("select distinct user_group from (select * from message where from_car_user_id = #{userId} or to_car_user_id = #{userId} order by create_time desc, id desc) a limit 2")
    @ResultType(String.class)
    List<String> selectRecentlyUsers(@Param("userId") Integer userId);

    @Select("select distinct user_group from message where from_car_user_id = #{userId} or to_car_user_id = #{userId} order by create_time desc, id desc")
    @ResultType(String.class)
    List<String> selectRecentlyUsersNoLimit(@Param("userId") Integer userId);

    List<MessageDO> selectBatchByUserGroup(@Param("userGroups") List<String> userGroups);

    List<MessageDO> selectByUserId(@Param("userId") Integer userId, @Param("name") String name);

    @Select("SELECT * from message where user_group = #{userGroup} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<MessageDO> selectByUserGroup(@Param("userGroup") String userGroup);

    @Select("SELECT * from message where to_car_user_id = #{toUserId} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<MessageDO> selectByToUserId(@Param("toUserId") Integer toUserId);

    void updateReadByToUserIdAndFromUserId(@Param("toUserId") Integer toUserId, @Param("fromUserId") Integer fromUserId);

    List<CommonDTO> selectByUserIdAndOtherUserIds(@Param("userId") Integer userId, @Param("userIds") List<Integer> userIds);
}