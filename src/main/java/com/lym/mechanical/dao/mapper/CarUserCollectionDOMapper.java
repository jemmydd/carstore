package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.CarUserCollectionDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface CarUserCollectionDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarUserCollectionDO record);

    int insertSelective(CarUserCollectionDO record);

    CarUserCollectionDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarUserCollectionDO record);

    int updateByPrimaryKey(CarUserCollectionDO record);

    @Select("select * from car_user_collection where car_user_id = #{userId} and publish_id = #{publishId}")
    @ResultMap("BaseResultMap")
    CarUserCollectionDO selectByUserAndPublish(@Param("userId") Integer userId, @Param("publishId") Integer publishId);
}