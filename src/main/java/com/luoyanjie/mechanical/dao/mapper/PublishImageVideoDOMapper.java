package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.PublishImageVideoDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
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

    List<PublishImageVideoDO> selectBatchByPublish(@Param("publishIds") List<Integer> publishIds);

    List<PublishImageVideoDO> selectByPublish(@Param("publishId") Integer publishId);

    void insertBatchSelective(@Param("data") List<PublishImageVideoDO> data);

    @Delete("delete from publish_image_video where publish_id = #{publishId}")
    void deleteByPublish(@Param("publishId") Integer publishId);
}