package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.PublishImageVideoDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PublishImageVideoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PublishImageVideoDO record);

    int insertSelective(PublishImageVideoDO record);

    PublishImageVideoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PublishImageVideoDO record);

    int updateByPrimaryKey(PublishImageVideoDO record);

    @Select("select * from publish_image_video where publish_id = #{publishId} order by id")
    @ResultMap("BaseResultMap")
    List<PublishImageVideoDO> selectByPublishId(@Param("publishId") Integer publishId);

    List<PublishImageVideoDO> selectBatchByPublish(@Param("publishIds") List<Integer> publishIds);

    List<PublishImageVideoDO> selectByPublish(@Param("publishId") Integer publishId);

    void insertBatchSelective(@Param("data") List<PublishImageVideoDO> data);

    @Delete("delete from publish_image_video where publish_id = #{publishId}")
    void deleteByPublish(@Param("publishId") Integer publishId);
}