package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.CarUserActiveDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CarUserActiveDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarUserActiveDO record);

    int insertSelective(CarUserActiveDO record);

    CarUserActiveDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarUserActiveDO record);

    int updateByPrimaryKey(CarUserActiveDO record);

    @Select("select * from car_user_active where user_id = #{userId} and active_date = #{date}")
    @ResultMap("BaseResultMap")
    CarUserActiveDO selectByUserIdAndDate(@Param("userId") Integer userId, @Param("date") String date);

    @Select("select * from car_user_active where user_id = #{userId} and active_date <= #{endDate} and active_date >= #{startDate}")
    @ResultMap("BaseResultMap")
    List<CarUserActiveDO> selectByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("startDate") String startDate,
                                                       @Param("endDate") String endDate);
}