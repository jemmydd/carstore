package com.luoyanjie.mechanical.service.cooperationCompany;

import com.luoyanjie.mechanical.bean.dto.cooperationCompany.CooperationCompanyDTO;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.AddCooperationCompanyParam;
import com.luoyanjie.mechanical.bean.param.cooperationCompany.ModifyCooperationCompanyParam;
import com.luoyanjie.mechanical.component.result.PageData;

public interface CooperationCompanyService {
    PageData<CooperationCompanyDTO> getPageData(Integer pageNum, Integer pageSize);

    Boolean add(AddCooperationCompanyParam param);

    Boolean modify(ModifyCooperationCompanyParam param);

    Boolean delete(Integer cooperationCompanyId);

    PageData<CooperationCompanyDTO> searchForAdmin(Integer pageNum, Integer pageSize, String name, String address, String contactPhone);
}
