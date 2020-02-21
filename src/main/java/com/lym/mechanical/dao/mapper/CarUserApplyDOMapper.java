package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.CarUserApplyDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CarUserApplyDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarUserApplyDO record);

    int insertSelective(CarUserApplyDO record);

    CarUserApplyDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarUserApplyDO record);

    int updateByPrimaryKey(CarUserApplyDO record);

    @Select("select * from car_user_apply where user_id = #{userId} order by create_time desc")
    @ResultMap("BaseResultMap")
    List<CarUserApplyDO> selectByUserId(@Param("userId") Integer userId);

    List<CarUserApplyDO> selectBatchByUserId(@Param("userIds") List<Integer> userIds);

    List<CarUserApplyDO> selectForWeb(@Param("startTime") String startTime, @Param("endTime") String endTime);
}