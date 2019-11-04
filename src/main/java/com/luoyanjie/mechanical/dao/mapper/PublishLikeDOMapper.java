package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.PublishLikeDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface PublishLikeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishLikeDO record);

    int insertSelective(PublishLikeDO record);

    PublishLikeDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishLikeDO record);

    int updateByPrimaryKey(PublishLikeDO record);

    @Select("select * from publish_like where user_id = #{userId} and publish_id = #{publishId} limit 1")
    @ResultMap("BaseResultMap")
    PublishLikeDO selectByUserAndPublish(@Param("userId") Integer userId, @Param("publishId") Integer publishId);
}