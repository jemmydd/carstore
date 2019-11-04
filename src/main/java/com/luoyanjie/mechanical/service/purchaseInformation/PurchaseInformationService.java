package com.luoyanjie.mechanical.service.purchaseInformation;

import com.luoyanjie.mechanical.bean.dto.SubmitDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.AddPurchaseInformationParam;
import com.luoyanjie.mechanical.bean.param.purchaseInformation.ModifyPurchaseInformationParam;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.List;

public interface PurchaseInformationService {
    SubmitDTO add(AddPurchaseInformationParam param);

    Boolean delete(Integer purchaseInformationId);

    Boolean deleteBatch(List<Integer> purchaseInformationIds);

    SubmitDTO modify(ModifyPurchaseInformationParam param);

    PageData<PurchaseInformationDTO> search(
            Integer pageNum, Integer pageSize,
            Integer categoryFirstId, Integer categorySecondId,
            String provinceCode, String cityCode, String areaCode,
            String keyword, String contactMobile, Byte shelfStatus, Integer creator,
            Integer brandId
    );

    PurchaseInformationDTO getOne(Integer purchaseInformationId);

    Boolean down(Integer userId, Integer purchaseInformationId);
}
