package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.NameCardFriendDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NameCardFriendDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameCardFriendDO record);

    int insertSelective(NameCardFriendDO record);

    NameCardFriendDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameCardFriendDO record);

    int updateByPrimaryKey(NameCardFriendDO record);

    @Select("select * from name_card_friend where user_id = #{userId} order by create_time desc,id desc")
    @ResultMap("BaseResultMap")
    List<NameCardFriendDO> selectByUserId(@Param("userId") Integer userId);

    void insertBatchSelective(@Param("data") List<NameCardFriendDO> data);

    @Delete("delete from name_card_friend where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Integer userId);

    @Select("select * from name_card_friend where user_id = #{userId} and card_id = #{cardId}")
    @ResultMap("BaseResultMap")
    NameCardFriendDO selectByUserIdAndCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}