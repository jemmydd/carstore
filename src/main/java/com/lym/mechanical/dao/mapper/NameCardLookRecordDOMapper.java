package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.dto.admin.UserLookRecordDTO;
import com.lym.mechanical.bean.dto.message.LookUserDTO;
import com.lym.mechanical.bean.dto.my.CommonDTO;
import com.lym.mechanical.bean.entity.NameCardLookRecordDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NameCardLookRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NameCardLookRecordDO record);

    int insertSelective(NameCardLookRecordDO record);

    NameCardLookRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NameCardLookRecordDO record);

    int updateByPrimaryKey(NameCardLookRecordDO record);

    @Select("select * from name_card_look_record where user_id = #{userId} order by create_time desc, id desc")
    @ResultMap("BaseResultMap")
    List<NameCardLookRecordDO> selectByUserId(@Param("userId") Integer userId);

    List<LookUserDTO> selectLookRecordByUserId(@Param("userId") Integer userId);

    @Update("update name_card_look_record set has_dial = #{type} where user_id = #{userId} and card_id = #{cardId}")
    void updateDialByCardIdAndUserId(@Param("userId") Integer userId, @Param("cardId") Integer cardId, @Param("type") String type);

    List<CommonDTO> selectByCardIdAndDate(@Param("cardId") Integer cardId, @Param("date") String date,
                                          @Param("hasManyLook") String hasManyLook, @Param("hasDial") String hasDial,
                                          @Param("hasMobile") String hasMobile);

    List<CommonDTO> selectGuestByUserId(@Param("userId") Integer cardId, @Param("date") String date,
                                        @Param("hasManyLook") String hasManyLook, @Param("hasDial") String hasDial,
                                        @Param("hasMobile") String hasMobile);

    List<NameCardLookRecordDO> selectBatchByUserId(@Param("userIds") List<Integer> userIds);

    List<UserLookRecordDTO> selectLastByUserId(@Param("userId") Integer userId);

    List<NameCardLookRecordDO> selectBatchByNameCardId(@Param("cardIds") List<Integer> cardIds);

    @Select("select * from name_card_look_record where card_id = #{cardId} order by create_time desc")
    @ResultMap("BaseResultMap")
    List<NameCardLookRecordDO> selectByCardId(@Param("cardId") Integer cardId);

    @Select("select * from name_card_look_record where card_id = #{cardId} and user_id = #{userId}")
    @ResultMap("BaseResultMap")
    List<NameCardLookRecordDO> selectByUserIdAndCardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);
}