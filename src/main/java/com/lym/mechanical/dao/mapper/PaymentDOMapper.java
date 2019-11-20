package com.lym.mechanical.dao.mapper;

import com.lym.mechanical.bean.entity.PaymentDO;
import org.springframework.stereotype.Component;

@Component
public interface PaymentDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentDO record);

    int insertSelective(PaymentDO record);

    PaymentDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaymentDO record);

    int updateByPrimaryKey(PaymentDO record);
}