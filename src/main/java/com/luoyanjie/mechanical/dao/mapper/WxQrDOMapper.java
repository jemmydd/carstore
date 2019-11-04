package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.WxQrDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface WxQrDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxQrDO record);

    int insertSelective(WxQrDO record);

    WxQrDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxQrDO record);

    int updateByPrimaryKey(WxQrDO record);

    @Select("Select * from wx_qr where md5_param = #{md5Param} and type = #{type}")
    @ResultMap("BaseResultMap")
    WxQrDO selectByMd5Param(@Param("md5Param") String md5Param, @Param("type") String type);
}