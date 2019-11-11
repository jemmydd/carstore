package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.dto.message.LookUserDTO;
import com.lym.mechanical.bean.entity.NameCardLookRecordDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NameCardLookRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameCardLookRecordDO record);

    int insertSelective(NameCardLookRecordDO record);

    NameCardLookRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameCardLookRecordDO record);

    int updateByPrimaryKey(NameCardLookRecordDO record);

    @Select("select * from name_card_look_record where user_id = #{userId} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<NameCardLookRecordDO> selectByUserId(@Param("userId") Integer userId);

    List<LookUserDTO> selectLookRecordByUserId(@Param("userId") Integer userId);
}