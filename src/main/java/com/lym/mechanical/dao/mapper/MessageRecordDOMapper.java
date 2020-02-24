package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.MessageRecordDO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MessageRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageRecordDO record);

    int insertSelective(MessageRecordDO record);

    MessageRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageRecordDO record);

    int updateByPrimaryKey(MessageRecordDO record);

    @Select("select * from message_record order by id desc")
    @ResultMap("BaseResultMap")
    List<MessageRecordDO> selectAll();
}