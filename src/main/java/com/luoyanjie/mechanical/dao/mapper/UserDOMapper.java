package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.UserDO;
import org.apache.ibatis.annotations.*;
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

    List<UserDO> selectBatchByPrimaryKey(@Param("ids") List<Integer> ids);

    List<Integer> selectIdBatch(@Param("ids") List<Integer> ids);

    @Select("select id from user where id = #{id}")
    @ResultType(Integer.class)
    Integer selectId(@Param("id") Integer id);

    @Update("update user set publish_count = publish_count + 1 where id = #{id}")
    void addPublishCount(@Param("id") Integer id);

    @Update("update user set view_phone_count = view_phone_count + 1 where id = #{id}")
    void addViewPhoneCount(@Param("id") Integer id);

    @Update("update user set fans_count = fans_count + 1 where id = #{id}")
    void addFansCount(@Param("id") Integer id);

    @Update("update user set follow_count = follow_count + 1 where id = #{id}")
    void addFollowCount(@Param("id") Integer id);

    @Update("update user set collection_count = collection_count + 1 where id = #{id}")
    void addCollectionCount(@Param("id") Integer id);

    @Update("update user set purchase_information_count = purchase_information_count + 1 where id = #{id}")
    void addPurchaseInformationCount(@Param("id") Integer id);

    @Update("update user set invite_count = invite_count + 1 where id = #{id}")
    void addInviteCount(@Param("id") Integer id);

    @Update("update user set fans_count = if(fans_count - 1 < 0, 0, fans_count - 1) where id = #{id}")
    void subtractFansCount(Integer objectId);

    @Update("update user set follow_count = if(follow_count - 1 < 0, 0, follow_count - 1) where id = #{id}")
    void subtractFollowCount(@Param("id") Integer id);

    @Update("update user set collection_count = if(collection_count - 1 < 0, 0, collection_count - 1) where id = #{id}")
    void subtractCollectionCount(@Param("id") Integer id);

    List<UserDO> searchForAdmin(
            @Param("provinceCode") String provinceCode,
            @Param("cityCode") String cityCode,
            @Param("areaCode") String areaCode,
            @Param("phone") String phone
    );

    @Select("select * from user where is_deleted = 0")
    @ResultMap("BaseResultMap")
    List<UserDO> selectAllNotDelete();

    @Select("select count(*) from user")
    @ResultType(Integer.class)
    Integer selectCount();

    @Select("select * from user where create_time like concat(#{year}, '%')")
    @ResultMap("BaseResultMap")
    List<UserDO> selectByYear(@Param("year") String year);

    @Select("select * from user where create_time like concat(#{yearMonth}, '%')")
    @ResultMap("BaseResultMap")
    List<UserDO> selectByYearMonth(@Param("yearMonth") String yearMonth);

    @Select("select * from user where account = #{account} limit 1")
    @ResultMap("BaseResultMap")
    UserDO selectByAccount(@Param("account") String account);

    @Select("select * from user where openid = #{openid} limit 1")
    @ResultMap("BaseResultMap")
    UserDO selectByWxPgInfo(@Param("openid") String openid);

    @Update("update user set phone = null where id = #{id}")
    void unBindPhone(@Param("id") Integer id);
}