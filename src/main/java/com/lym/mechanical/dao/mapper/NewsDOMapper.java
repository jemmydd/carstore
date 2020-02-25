package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.NewsDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewsDO record);

    int insertSelective(NewsDO record);

    NewsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewsDO record);

    int updateByPrimaryKey(NewsDO record);

    List<NewsDO> selectForWeb(@Param("typeId") Integer typeId, @Param("isValid") String isValid);
}