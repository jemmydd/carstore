package com.luoyanjie.mechanical.service.brand;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.brand.BrandAdminDTO;
import com.luoyanjie.mechanical.bean.entity.BrandDO;
import com.luoyanjie.mechanical.bean.param.brand.AddBrandParam;
import com.luoyanjie.mechanical.bean.param.brand.ModifyBrandParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.BrandDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-09-30 22:00
 * Coding happily every day!
 */
@Slf4j
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDOMapper brandDOMapper;

    @Override
    public SubmitDTO add(AddBrandParam param) {
        BrandDO brandDO = BrandDO.builder()
                .capital(param.getCapital())
                .isHot(param.getIsHot())
                .name(param.getName())
                .build();

        brandDOMapper.insertSelective(brandDO);

        return SubmitDTO.success(brandDO.getId());
    }

    @Override
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

    @Override
    public Boolean delete(Integer id) {
        if (id != null) brandDOMapper.deleteByPrimaryKey(id);

        return true;
    }

    @Override
    public PageData<BrandAdminDTO> search(Integer pageNum, Integer pageSize, String name, String capital, Byte isHot) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<BrandDO> pd = (Page<BrandDO>) brandDOMapper.search(name, capital, isHot);

        List<BrandDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(pd, data.stream().map(this::cov).collect(Collectors.toList()));
    }

    @Override
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
