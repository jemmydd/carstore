package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.IntentionCustomDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IntentionCustomDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IntentionCustomDO record);

    int insertSelective(IntentionCustomDO record);

    IntentionCustomDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntentionCustomDO record);

    int updateByPrimaryKey(IntentionCustomDO record);

    @Select("select * from intention_custom where user_id = #{userId} order by create_time desc")
    @ResultMap("BaseResultMap")
    List<IntentionCustomDO> selectByUserId(@Param("userId") Integer userId);

    @Select("select * from intention_custom where user_id = #{userId} and intention_custom_user_id = #{otherUserId}")
    @ResultMap("BaseResultMap")
    IntentionCustomDO selectByUserIdAndOtherId(@Param("userId") Integer userId, @Param("otherUserId") Integer otherUserId);
}