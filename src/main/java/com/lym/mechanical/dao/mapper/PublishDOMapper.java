package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.PublishDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PublishDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishDO record);

    int insertSelective(PublishDO record);

    PublishDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishDO record);

    int updateByPrimaryKey(PublishDO record);

    @Select("select * from publish where car_user_id = #{carUserId} and is_deleted = 0 and shelf_status = 1 order by create_time desc,id desc")
    @ResultMap("BaseResultMap")
    List<PublishDO> selectByCarUserId(@Param("carUserId") Integer carUserId);

    List<PublishDO> search(
            @Param("searchText") String searchText,
            @Param("provinceCode") String provinceCode,
            @Param("cityCode") String cityCode,
            @Param("areaCode") String areaCode,
            @Param("categoryFirstId") Integer categoryFirstId,
            @Param("categorySecondId") Integer categorySecondId,
            @Param("publishId") Integer publishId,
            @Param("sortedType") String sortedType,
            @Param("shelfStatus") Byte shelfStatus,
            @Param("brandId") Integer brandId
    );

    List<PublishDO> searchForAdmin(
            @Param("provinceCode") String provinceCode,
            @Param("cityCode") String cityCode,
            @Param("areaCode") String areaCode,
            @Param("phone") String phone,
            @Param("title") String title);

    List<PublishDO> searchByIds(@Param("ids") List<Integer> ids);

    PublishDO searchById(@Param("id") Integer id);

    List<PublishDO> selectForHomePage(
            @Param("publishId") Integer publishId,
            @Param("baseSize") Integer baseSize,
            @Param("currentMinId") Integer currentMinId
    );

    @Select("select count(*) from publish where user_id = #{userId} and is_deleted = 0")
    @ResultType(Long.class)
    Long selectByPublish(@Param("userId") Integer userId);

    @Select("select id from publish where id = #{id}")
    @ResultType(Integer.class)
    Integer selectId(@Param("id") Integer id);

    @Update("update publish set like_count = like_count + 1 where id = #{id}")
    void addLikeCount(@Param("id") Integer id);

    @Update("update publish set view_count = view_count + 1 where id = #{id}")
    void addViewCount(@Param("id") Integer id);

    @Update("update publish set discuss_count = discuss_count + 1 where id = #{id}")
    void addDiscussCount(@Param("id") Integer id);

    @Update("update publish set discuss_count = if(discuss_count - 1 < 0, 0, discuss_count - 1) where id = #{id}")
    void subtractDiscussCount(@Param("id") Integer id);

    @Update("update publish set collection_count = collection_count + 1 where id = #{id}")
    void addCollectionCount(@Param("id") Integer id);

    @Update("update publish set collection_count = if(collection_count - 1 < 0, 0, collection_count - 1) where id = #{id}")
    void subtractCollectionCount(@Param("id") Integer id);

    @Update("update publish set view_phone_count = view_phone_count + 1 where id = #{id}")
    void addViewPhoneCount();

    @Update("update publish set is_deleted = 1 where id = #{id}")
    void softDelete(@Param("id") Integer id);

    @Select("select count(*) from publish")
    @ResultType(Integer.class)
    Integer selectCount();

    @Select("select * from publish where create_time like concat(#{year}, '%')")
    @ResultMap("BaseResultMap")
    List<PublishDO> selectByYear(@Param("year") String year);

    @Select("select * from publish where create_time like concat(#{yearMonth}, '%')")
    @ResultMap("BaseResultMap")
    List<PublishDO> selectByYearMonth(@Param("yearMonth") String yearMonth);

    List<PublishDO> searchComplexA(
            @Param("searchText") String searchText,
            @Param("categoryFirstId") Integer categoryFirstId,
            @Param("categorySecondId") Integer categorySecondId,
            @Param("shelfStatus") Byte shelfStatus,
            @Param("locationProvinceCode") String locationProvinceCode,
            @Param("brandId") Integer brandId);

    List<PublishDO> searchComplexB(
            @Param("searchText") String searchText,
            @Param("provinceCode") String provinceCode,
            @Param("cityCode") String cityCode,
            @Param("areaCode") String areaCode,
            @Param("categoryFirstId") Integer categoryFirstId,
            @Param("categorySecondId") Integer categorySecondId,
            @Param("shelfStatus") Byte shelfStatus,
            @Param("brandId") Integer brandId
    );

    List<PublishDO> selectBatchByPrimaryKey(@Param("ids") List<Integer> ids);

    @Select("select * from publish where is_deleted = 0 and car_user_id is not null")
    @ResultMap("BaseResultMap")
    List<PublishDO> selectCarUserPublish();

    List<PublishDO> selectForWeb(@Param("nickName") String nickName, @Param("mobile") String mobile, @Param("userId") String userId);

    List<PublishDO> selectBatchByCarUserId(@Param("userIds") List<Integer> userIds);

    @Select("select * from publish where car_user_id = #{userId} and create_time <= #{endDate} and create_time >= #{startDate}")
    @ResultMap("BaseResultMap")
    List<PublishDO> selectByCarUserIdAndDateBetween(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}