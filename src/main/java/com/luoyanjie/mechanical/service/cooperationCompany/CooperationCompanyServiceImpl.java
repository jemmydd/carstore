package com.luoyanjie.mechanical.service.cooperationCompany;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luoyanjie.mechanical.bean.common.Constant;
import com.luoyanjie.mechanical.bean.dto.cooperationCompany.CooperationCompanyDTO;
import com.luoyanjie.mechanical.bean.entity.CooperationCompanyDO;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.AddCooperationCompanyParam;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.ModifyCooperationCompanyParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.CooperationCompanyDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-08-17 19:19
 * Coding happily every day!
 */
@Slf4j
@Service
public class CooperationCompanyServiceImpl implements CooperationCompanyService {
    @Autowired
    private CooperationCompanyDOMapper cooperationCompanyDOMapper;

    @Override
    public PageData<CooperationCompanyDTO> getPageData(Integer pageNum, Integer pageSize) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<CooperationCompanyDO> pd = (Page<CooperationCompanyDO>) cooperationCompanyDOMapper.selectAll();

        List<CooperationCompanyDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(pd, data.stream().map(this::cov).collect(Collectors.toList()));
    }

    @Override
    public Boolean add(AddCooperationCompanyParam param) {
        if (param.getUserId() == null) throw new RuntimeException("登录ID不能为空");
        if (StringUtils.isEmpty(param.getName())) throw new RuntimeException("名称不能为空");

        cooperationCompanyDOMapper.insertSelective(
                CooperationCompanyDO.builder()
                        .address(param.getAddress())
                        .bizIntroduce(param.getBizIntroduce())
                        .contactPhone(param.getContactPhone())
                        .creator(param.getUserId())
                        .image(param.getImage())
                        .name(param.getName())
                        .build()
        );

        return true;
    }

    @Override
    public Boolean modify(ModifyCooperationCompanyParam param) {
        if (param.getUserId() == null) throw new RuntimeException("登录ID不能为空");
        if (StringUtils.isEmpty(param.getName())) throw new RuntimeException("名称不能为空");

        cooperationCompanyDOMapper.updateByPrimaryKeySelective(
                CooperationCompanyDO.builder()
                        .id(param.getId())
                        .address(param.getAddress())
                        .bizIntroduce(param.getBizIntroduce())
                        .contactPhone(param.getContactPhone())
                        .creator(param.getUserId())
                        .image(param.getImage())
                        .name(param.getName())
                        .build()
        );

        return true;
    }

    @Override
    public Boolean delete(Integer cooperationCompanyId) {
        if (cooperationCompanyId == null) throw new RuntimeException("合作公司的ID");

        cooperationCompanyDOMapper.deleteByPrimaryKey(cooperationCompanyId);

        return true;
    }

    @Override
    public PageData<CooperationCompanyDTO> searchForAdmin(Integer pageNum, Integer pageSize, String name, String address, String contactPhone) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<CooperationCompanyDO> pd = (Page<CooperationCompanyDO>) cooperationCompanyDOMapper.searchForAdmin(name, address, contactPhone);

        List<CooperationCompanyDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(pd, data.stream().map(this::cov).collect(Collectors.toList()));
    }

    private CooperationCompanyDTO cov(CooperationCompanyDO row) {
        return CooperationCompanyDTO.builder()
                .address(StringUtils.isEmpty(row.getAddress()) ? "" : row.getAddress())
                .bizIntroduce(StringUtils.isEmpty(row.getBizIntroduce()) ? "" : row.getBizIntroduce())
                .contactPhone(StringUtils.isEmpty(row.getContactPhone()) ? "" : row.getContactPhone())
                .createTime(DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                .id(row.getId())
                .image(StringUtils.isEmpty(row.getImage()) ? "" : row.getImage())
                .name(StringUtils.isEmpty(row.getName()) ? "" : row.getName())
                .build();
    }
}
