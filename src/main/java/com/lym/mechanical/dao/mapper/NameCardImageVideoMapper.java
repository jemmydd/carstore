package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.NameCardImageVideo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NameCardImageVideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameCardImageVideo record);

    int insertSelective(NameCardImageVideo record);

    NameCardImageVideo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameCardImageVideo record);

    int updateByPrimaryKey(NameCardImageVideo record);

    @Select("select * from name_card_image_video where card_id = #{cardId} order by id")
    @ResultMap("BaseResultMap")
    List<NameCardImageVideo> selectByCardId(@Param("cardId") Integer cardId);

    void insertBatchSelective(@Param("data") List<NameCardImageVideo> data);

    @Delete("delete from name_card_image_video where card_id = #{cardId}")
    void deleteByCardId(@Param("cardId") Integer cardId);
}