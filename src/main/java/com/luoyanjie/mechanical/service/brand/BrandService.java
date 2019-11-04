package com.luoyanjie.mechanical.service.brand;

import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.brand.BrandAdminDTO;
import com.luoyanjie.mechanical.bean.param.brand.AddBrandParam;
import com.luoyanjie.mechanical.bean.param.brand.ModifyBrandParam;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-09-30 22:00
 * Coding happily every day!
 */
public interface BrandService {
    SubmitDTO add(AddBrandParam param);

    SubmitDTO modify(ModifyBrandParam param);

    Boolean delete(Integer id);

    PageData<BrandAdminDTO> search(Integer pageNum, Integer pageSize, String name, String capital, Byte isHot);

    List<BrandAdminDTO> getAll(String name, String capital, Byte isHot);
}
