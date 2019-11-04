package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.BannerDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BannerDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerDO record);

    int insertSelective(BannerDO record);

    BannerDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerDO record);

    int updateByPrimaryKey(BannerDO record);

    List<BannerDO> selectLimit(@Param("count") Integer count);

    @Select("select * from banner order by is_valid desc, id desc")
    @ResultMap("BaseResultMap")
    List<BannerDO> select();

    @Update("update banner set bind_id = #{bindId}, image = #{image}, is_valid = #{isValid}, url = #{url} where id = #{id}")
    void update(@Param("id") Integer id, @Param("isValid") Byte isValid, @Param("bindId") Integer bindId, @Param("image") String image, @Param("url") String url);
}