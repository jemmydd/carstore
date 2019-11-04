package com.luoyanjie.mechanical.service.publish;

import com.luoyanjie.mechanical.bean.dto.my.PublishInHpDTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDetailDTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishSubmitDTO;
import com.luoyanjie.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.luoyanjie.mechanical.bean.param.publish.*;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.RollIdPageData;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-07-30 21:02
 * Coding happily every day!
 */
public interface PublishService {
    PageData<PublishDTO> getPageData(PublishParam publishParam);

    PageData<PublishDTO> getPageDataComplex(PublishComplexParam param);

    RollIdPageData<PublishInHpDTO> getForHomePage(Integer whoId, Integer baseSize, Integer currentMinId);

    List<PublishDTO> getPublishByIds(List<Integer> ids, Integer requestUserId, PublishCallSceneEnum publishCallSceneEnum);

    PublishSubmitDTO publish(PublishSubmitParam param, Boolean needCheckUser);

    Boolean modifyPublish(PublishSubmitParam param, Boolean needCheckUser);

    Boolean downOrUpPublish(Integer userId, Integer publishId, Byte type);

    PublishDetailDTO getDetail(Integer userId, Integer publishId);

    Boolean like(Integer userId, Integer publishId);

    String getPublishPhone(Integer userId, Integer publishId);

    PageData<PublishDTO> searchForAdmin(Integer pageNum, Integer pageSize, String provinceCode, String cityCode, String areaCode, String phone, String title);

    PublishSubmitDTO publishForAdmin(AdminPublishSubmitParam param);

    Boolean deletePublish(Integer publishId);

    Boolean modifyPublishForAdmin(AdminPublishModifyParam param);
}
