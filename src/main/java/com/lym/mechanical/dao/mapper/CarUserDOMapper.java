package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.CarUserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface CarUserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarUserDO record);

    int insertSelective(CarUserDO record);

    CarUserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarUserDO record);

    int updateByPrimaryKey(CarUserDO record);

    @Select("select * from car_user where openid = #{openId} and is_delete = 0")
    @ResultMap("BaseResultMap")
    CarUserDO selectByOpenId(@Param("openId") String openId);
}