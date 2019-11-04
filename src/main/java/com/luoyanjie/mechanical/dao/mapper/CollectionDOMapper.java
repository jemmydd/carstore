package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.CollectionDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CollectionDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectionDO record);

    int insertSelective(CollectionDO record);

    CollectionDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionDO record);

    int updateByPrimaryKey(CollectionDO record);

    List<CollectionDO> search(@Param("userId") Integer userId);

    @Select("select count(*) from collection where user_id = #{userId}")
    @ResultType(Long.class)
    Long selectCountByUser(@Param("userId") Integer userId);

    @Select("select * from collection where user_id = #{userId} and publish_id = #{publishId}")
    @ResultMap("BaseResultMap")
    CollectionDO selectByUserAndPublish(@Param("userId") Integer userId, @Param("publishId") Integer publishId);

    @Delete("delete from collection where user_id = #{userId} and publish_id = #{publishId}")
    void deleteByUserPublish(@Param("userId") Integer userId, @Param("publishId") Integer publishId);

    @Select("select publish_id from collection where user_id = #{userId}")
    @ResultType(Integer.class)
    List<Integer> selectPublishByUser(@Param("userId") Integer userId);
}