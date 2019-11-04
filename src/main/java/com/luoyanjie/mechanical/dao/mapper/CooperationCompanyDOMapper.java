package com.luoyanjie.mechanical.dao.mapper;

import com.luoyanjie.mechanical.bean.entity.CooperationCompanyDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CooperationCompanyDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CooperationCompanyDO record);

    int insertSelective(CooperationCompanyDO record);

    CooperationCompanyDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CooperationCompanyDO record);

    int updateByPrimaryKey(CooperationCompanyDO record);

    @Select("select * from cooperation_company order by id desc")
    @ResultMap("BaseResultMap")
    List<CooperationCompanyDO> selectAll();

    List<CooperationCompanyDO> searchForAdmin(@Param("name") String name, @Param("address") String address, @Param("contactPhone") String contactPhone);
}