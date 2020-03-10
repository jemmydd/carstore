package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.NameCardDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NameCardDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameCardDO record);

    int insertSelective(NameCardDO record);

    NameCardDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameCardDO record);

    int updateByPrimaryKey(NameCardDO record);

    @Select("select * from name_card where user_id = #{userId} limit 1")
    @ResultMap("BaseResultMap")
    NameCardDO selectByUserId(@Param("userId") Integer userId);

    @Select("select * from name_card order by create_time desc,id desc limit 10")
    @ResultMap("BaseResultMap")
    List<NameCardDO> selectRefereeCards();

    List<NameCardDO> selectBatchByPrimaryKey(@Param("ids") List<Integer> ids);

    List<NameCardDO> selectByCardNo(@Param("cardNo") String cardNo);

    List<NameCardDO> selectBatchByUserId(@Param("userIds") List<Integer> userIds);
}