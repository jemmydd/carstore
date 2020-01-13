package com.lym.mechanical.service.brand;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lym.mechanical.bean.dto.brand.BrandAdminDTO;
import com.lym.mechanical.bean.dto.wxPg.SubmitDTO;
import com.lym.mechanical.bean.entity.BrandDO;
import com.lym.mechanical.bean.param.brand.AddBrandParam;
import com.lym.mechanical.bean.param.brand.ModifyBrandParam;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.dao.mapper.BrandDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyimin
 * @date 2020-01-13 09:16:24
 * Coding happily every day!
 */
@Service
public class BrandService {
    @Autowired
    private BrandDOMapper brandDOMapper;

    public SubmitDTO add(AddBrandParam param) {
        BrandDO brandDO = BrandDO.builder()
                .capital(param.getCapital())
                .isHot(param.getIsHot())
                .name(param.getName())
                .build();

        brandDOMapper.insertSelective(brandDO);

        return SubmitDTO.success(brandDO.getId());
    }

    public SubmitDTO modify(ModifyBrandParam param) {
        if (param.getId() == null) throw new RuntimeException("ID不能为空");

        BrandDO old = brandDOMapper.selectByPrimaryKey(param.getId());

        if (old != null) {
            old.setCapital(StringUtils.isEmpty(param.getCapital()) ? null : param.getCapital());
            old.setIsHot(param.getIsHot());
            old.setName(StringUtils.isEmpty(param.getName()) ? null : param.getName());
            brandDOMapper.updateByPrimaryKey(old);
        }

        return SubmitDTO.success(param.getId());
    }

    public Boolean delete(Integer id) {
        if (id != null) brandDOMapper.deleteByPrimaryKey(id);

        return true;
    }

    public PageData<BrandAdminDTO> search(Integer pageNum, Integer pageSize, String name, String capital, Byte isHot) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<BrandDO> pd = (Page<BrandDO>) brandDOMapper.search(name, capital, isHot);

        List<BrandDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(pd, data.stream().map(this::cov).collect(Collectors.toList()));
    }

    public List<BrandAdminDTO> getAll(String name, String capital, Byte isHot) {
        List<BrandDO> data = brandDOMapper.search(name, capital, isHot);

        if (ObjectUtils.isEmpty(data)) return Lists.newArrayList();

        return data.stream().map(this::cov).collect(Collectors.toList());
    }

    private BrandAdminDTO cov(BrandDO row) {
        return BrandAdminDTO.builder()
                .capital(row == null ? "" : row.getCapital())
                .id(row == null ? 0 : row.getId())
                .isHot(row == null ? (byte) 0 : row.getIsHot())
                .name(row == null ? "" : row.getName())
                .build();
    }
}
