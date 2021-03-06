package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.dto.admin.UserLookRecordDTO;
import com.lym.mechanical.bean.dto.my.MyGuestDO;
import com.lym.mechanical.bean.dto.statistic.PublishLookDTO;
import com.lym.mechanical.bean.entity.PublishLookRecordDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PublishLookRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishLookRecordDO record);

    int insertSelective(PublishLookRecordDO record);

    PublishLookRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishLookRecordDO record);

    int updateByPrimaryKey(PublishLookRecordDO record);

    List<Integer> selectTodayGuest(@Param("userId") Integer userId);

    List<Integer> selectTotalGuest(@Param("userId") Integer userId);

    List<MyGuestDO> selectMyGuestList(@Param("userId") Integer userId,
                                      @Param("type") String type,
                                      @Param("hasLook") String hasLook,
                                      @Param("hasDial") String hasDial,
                                      @Param("hasCollect") String hasCollect);

    @Select("select * from publish_look_record where publish_id in (select id from publish where car_user_id = #{userId}) order by create_time desc")
    @ResultMap("BaseResultMap")
    List<PublishLookRecordDO> selectByUserId(@Param("userId") Integer userId);

    @Select("SELECT user_id FROM (SELECT user_id,COUNT(*) c FROM publish_look_record WHERE publish_id = #{publishId} GROUP BY user_id" +
            " ORDER BY c DESC) a LIMIT 1")
    @ResultType(Integer.class)
    Integer selectLookTimeTopByPublishId(@Param("publishId") Integer publishId);

    List<PublishLookRecordDO> selectByUserIdAndPublishIdOrderByLookTime(@Param("latentUserId") Integer latentUserId, @Param("publishId") Integer publishId, @Param("userId") Integer userId);

    List<PublishLookRecordDO> selectHistory(@Param("publishId") Integer publishId,
                                            @Param("hasDial") String hasDial, @Param("hasCollect") String hasCollect);

    @Select("SELECT publish_id FROM (SELECT publish_id,COUNT(*) c FROM publish_look_record WHERE user_id = #{userId} GROUP BY publish_id" +
            " ORDER BY c DESC) a LIMIT 1")
    @ResultType(Integer.class)
    Integer selectLookTimeTopByUserId(@Param("userId") Integer userId);

    List<PublishLookRecordDO> selectHistoryByUserId(@Param("latentUserId") Integer latentUserId,
                                                    @Param("hasDial") String hasDial, @Param("hasCollect") String hasCollect,
                                                    @Param("userId") Integer userId);

    @Select("select * from publish_look_record where user_id = #{userId} and publish_id = #{publishId} order by create_time desc, id desc limit 1")
    @ResultMap("BaseResultMap")
    PublishLookRecordDO selectRecentRecord(@Param("userId") Integer userId, @Param("publishId") Integer publishId);

    @Update("update publish_look_record set has_collect = #{type} where user_id = #{userId} and publish_id = #{publishId}")
    void updateCollectByPublishIdAndUserId(@Param("userId") Integer userId, @Param("publishId") Integer publishId, @Param("type") String type);

    @Update("update publish_look_record set has_dial = #{type} where user_id = #{userId} and publish_id = #{publishId}")
    void updateDialByPublishIdAndUserId(@Param("userId") Integer userId, @Param("publishId") Integer publishId, @Param("type") String type);

    @Select("select * from publish_look_record where publish_id in (select id from publish where car_user_id = #{userId}) order by create_time desc")
    @ResultMap("BaseResultMap")
    List<PublishLookRecordDO> selectByPublishUserId(@Param("userId") Integer userId);

    List<PublishLookRecordDO> selectBatchByPublishId(@Param("publishIds") List<Integer> publishIds);

    List<UserLookRecordDTO> selectUserLookByPublishId(@Param("publishId") Integer publishId);

    @Select("select * from publish_look_record where publish_id = #{publishId} and user_id = #{userId}")
    @ResultMap("BaseResultMap")
    List<PublishLookRecordDO> selectByUserIdAndPublishId(@Param("userId") Integer userId, @Param("publishId") Integer publishId);

    List<PublishLookRecordDO> selectBatchByCarUserId(@Param("carUserIds") List<Integer> carUserIds);

    List<PublishLookDTO> selectLookCountByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}