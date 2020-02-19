package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.dto.admin.UserLookRecordDTO;
import com.lym.mechanical.bean.entity.CarUserCollectionDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Select("select * from car_user_collection where car_user_id = #{userId} order by create_time desc")
    @ResultMap("BaseResultMap")
    List<CarUserCollectionDO> search(@Param("userId") Integer userId);

    @Delete("delete from car_user_collection where car_user_id = #{userId} and publish_id = #{publishId}")
    void deleteByUserIdAndPublishId(@Param("userId") Integer userId, @Param("publishId") Integer publishId);

    List<CarUserCollectionDO> selectBatchByPublishId(@Param("publishIds") List<Integer> publishIds);

    List<UserLookRecordDTO> selectUserCollectByPublishId(@Param("publishId") Integer publishId);
}