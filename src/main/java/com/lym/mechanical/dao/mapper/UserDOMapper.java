package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    @Select("select * from user where phone = #{phone} limit 1")
    @ResultMap("BaseResultMap")
    UserDO selectByPhone(@Param("phone") String phone);

    @Update("update user set publish_count = publish_count + 1 where id = #{id}")
    void addPublishCount(@Param("id") Integer id);

    List<UserDO> selectBatchByPrimaryKey(@Param("ids") List<Integer> ids);
}