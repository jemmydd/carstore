package com.luoyanjie.mechanical.service.my;

import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.dto.invite.InviteDTO;
import com.luoyanjie.mechanical.bean.dto.my.FansDTO;
import com.luoyanjie.mechanical.bean.dto.my.PersonalHomePageDTO;
import com.luoyanjie.mechanical.bean.dto.my.PublishInHpDTO;
import com.luoyanjie.mechanical.bean.dto.my.v2.PersonalHomePageV2DTO;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.dto.purchaseInformation.PurchaseInformationDTO;
import com.luoyanjie.mechanical.bean.dto.user.UserDTO;
import com.luoyanjie.mechanical.bean.param.my.ModifyPersonalHomePageBkParam;
import com.luoyanjie.mechanical.bean.param.my.PersonalDataParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.component.result.RollIdPageData;

public interface MyService {
    UserDTO getOverview(Integer userId);

    UserDTO getPersonalData(Integer userId);

    Boolean modifyPersonalData(PersonalDataParam param);

    PersonalHomePageDTO getPersonalHomePage(Integer userId, Integer whoId, Integer baseSize);

    RollIdPageData<PublishInHpDTO> getPersonalHomePagePublish(Integer userId, Integer whoId, Integer baseSize, Integer currentMinId);

    Boolean modifyPersonalHomePageBk(ModifyPersonalHomePageBkParam param);

    PageData<PublishDTO> getMyPublishPageData(Integer pageNum, Integer pageSize, Integer userId, Byte downShelf);

    PageData<PublishDTO> getMyCollectionPageData(Integer pageNum, Integer pageSize, Integer userId);

    PageData<DiscussDTO> getMyDiscussPageData(Integer pageNum, Integer pageSize, Integer userId);

    PageData<FansDTO> getMyFansPageData(Integer pageNum, Integer pageSize, Integer userId);

    PageData<FansDTO> getMyFollowPageData(Integer pageNum, Integer pageSize, Integer userId);

    PageData<PurchaseInformationDTO> myPurchaseInformationPageData(Integer pageNum, Integer pageSize, Integer userId, Byte shelfStatus);

    PageData<InviteDTO> myInvitePageData(Integer pageNum, Integer pageSize, Integer userId);

    PersonalHomePageV2DTO getPersonalHomePageV2(Integer userId, Integer whoId, Integer pageSize);

    PageData<PublishDTO> getPersonalHomePagePublishV2(Integer userId, Integer whoId, Integer pageSize, Integer pageNum);
}
