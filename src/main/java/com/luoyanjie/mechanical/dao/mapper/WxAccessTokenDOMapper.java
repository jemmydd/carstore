package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.WxAccessTokenDO;
import org.springframework.stereotype.Component;

@Component
public interface WxAccessTokenDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxAccessTokenDO record);

    int insertSelective(WxAccessTokenDO record);

    WxAccessTokenDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxAccessTokenDO record);

    int updateByPrimaryKey(WxAccessTokenDO record);
}