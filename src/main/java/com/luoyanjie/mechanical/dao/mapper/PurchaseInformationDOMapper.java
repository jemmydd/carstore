package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.PurchaseInformationDO;
import com.luoyanjie.mechanical.bean.entitySelf.PurchaseInformationSelfDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PurchaseInformationDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseInformationDO record);

    int insertSelective(PurchaseInformationDO record);

    PurchaseInformationDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseInformationDO record);

    int updateByPrimaryKey(PurchaseInformationDO record);

    List<PurchaseInformationDO> search(
            @Param("categoryFirstId") Integer categoryFirstId,
            @Param("categorySecondId") Integer categorySecondId,
            @Param("provinceCode") String provinceCode,
            @Param("cityCode") String cityCode,
            @Param("areaCode") String areaCode,
            @Param("keyword") String keyword,
            @Param("contactMobile") String contactMobile,
            @Param("shelfStatus") Byte shelfStatus,
            @Param("creator") Integer creator,
            @Param("brandId") Integer brandId
    );

    PurchaseInformationDO searchById(@Param("id") Integer id);

    @Update("update purchase_information set view_count = view_count + 1 where id = #{id}")
    void addViewCountCount(@Param("id") Integer id);
}