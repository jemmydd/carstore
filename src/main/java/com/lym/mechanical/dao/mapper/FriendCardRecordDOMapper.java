package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.FriendCardRecordDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendCardRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendCardRecordDO record);

    int insertSelective(FriendCardRecordDO record);

    FriendCardRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendCardRecordDO record);

    int updateByPrimaryKey(FriendCardRecordDO record);

    void insertBatchSelective(@Param("data") List<FriendCardRecordDO> data);

    @Select("select * from friend_card_record where user_id = #{userId} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<FriendCardRecordDO> selectByUserId(@Param("userId") Integer userId);

    @Select("select * from friend_card_record where user_id = #{userId} and card_id = #{cardId}")
    @ResultMap("BaseResultMap")
    FriendCardRecordDO selectByUserIdAndCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}