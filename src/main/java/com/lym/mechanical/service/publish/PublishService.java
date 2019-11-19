package com.lym.mechanical.service.publish;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lym.mechanical.bean.common.Constant;
import com.lym.mechanical.bean.common.DefaultHandleConstant;
import com.lym.mechanical.bean.dto.location.LocationDetailDTO;
import com.lym.mechanical.bean.dto.publish.*;
import com.lym.mechanical.bean.dto.user.UserDTO;
import com.lym.mechanical.bean.dto.wxPg.OperateDTO;
import com.lym.mechanical.bean.entity.*;
import com.lym.mechanical.bean.enumBean.*;
import com.lym.mechanical.bean.param.publish.*;
import com.lym.mechanical.component.result.PageData;
import com.lym.mechanical.component.result.RollIdPageData;
import com.lym.mechanical.dao.mapper.*;
import com.lym.mechanical.service.category.CategoryService;
import com.lym.mechanical.service.user.UserService;
import com.lym.mechanical.sys.ExceptionAdvice;
import com.lym.mechanical.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublishService {
    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private PublishImageVideoDOMapper publishImageVideoDOMapper;

    @Autowired
    private PublishLikeDOMapper publishLikeDOMapper;

    @Autowired
    private DiscussDOMapper discussDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private CollectionDOMapper collectionDOMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandDOMapper brandDOMapper;

    @Autowired
    private CarUserCollectionDOMapper carUserCollectionDOMapper;

    private static final Integer OVER_SIZE = 8;
    private static final String CONTENT_PREFIX = "回复%s：";

    public PageData<PublishDTO> getPageData(PublishParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());

        SortedTypeEnum sortedTypeEnum = StringUtils.isEmpty(param.getSortedType()) ? SortedTypeEnum.NEWEST : SortedTypeEnum.valueOf(param.getSortedType());

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Page<PublishDO> pd = (Page<PublishDO>) publishDOMapper.search(
                param.getSearchText(),
                param.getProvinceCode(), param.getCityCode(), param.getAreaCode(), param.getCategoryFirstId(), param.getCategorySecondId(),
                param.getPublishId(),
                sortedTypeEnum.name(),
                param.getIsDownShelf(),
                param.getBrandId()
        );

        List<PublishDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(param.getPageSize());

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(PublishDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, List<PublishImageVideoDO>> pimMap = getImageVideoMap(data.stream().map(PublishDO::getId).distinct().collect(Collectors.toList()));

        Map<Integer, String> cm = categoryService.allMap();

        return PageData.data(pd,
                data.stream()
                        .map(row -> cov(row, param.getPublishId(), userDOMap.get(row.getUserId()), param.getPublishCallSceneEnum(), pimMap.get(row.getId()), cm))
                        .collect(Collectors.toList())
        );
    }

    public PageData<PublishDTO> getPageDataComplex(PublishComplexParam param) {
        PageData.checkPageParam(param.getPageNum(), param.getPageSize());

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Page<PublishDO> pd;
        if (!StringUtils.isEmpty(param.getLocationProvinceCode()) && StringUtils.isEmpty(param.getProvinceCode()) && StringUtils.isEmpty(param.getCityCode())) {
            pd = (Page<PublishDO>) publishDOMapper.searchComplexA(
                    param.getSearchText(), param.getCategoryFirstId(), param.getCategorySecondId(),
                    param.getIsDownShelf(),
                    param.getLocationProvinceCode(),
                    param.getBrandId()
            );

        } else {
            pd = (Page<PublishDO>) publishDOMapper.searchComplexB(
                    param.getSearchText(),
                    param.getProvinceCode(), param.getCityCode(), param.getAreaCode(), param.getCategoryFirstId(), param.getCategorySecondId(),
                    param.getIsDownShelf(),
                    param.getBrandId()
            );
        }

        List<PublishDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(param.getPageSize());

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(PublishDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, List<PublishImageVideoDO>> pimMap = getImageVideoMap(data.stream().map(PublishDO::getId).distinct().collect(Collectors.toList()));
        Map<Integer, String> cm = categoryService.allMap();

        return PageData.data(pd,
                data.stream()
                        .map(row -> cov(row, param.getPublishId(), userDOMap.get(row.getUserId()), PublishCallSceneEnum.SQUARE, pimMap.get(row.getId()), cm))
                        .collect(Collectors.toList())
        );
    }

    public RollIdPageData<PublishInHpDTO> getForHomePage(Integer whoId, Integer baseSize, Integer currentMinId) {
        if (whoId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "个人主页者ID"));

        int getSize = baseSize + 1;
        List<PublishDO> data = publishDOMapper.selectForHomePage(whoId, getSize, currentMinId);
        if (ObjectUtils.isEmpty(data)) return RollIdPageData.noData(false, baseSize);

        List<PublishImageVideoDO> publishImageVideoDOS = publishImageVideoDOMapper.selectBatchByPublish(
                data.stream().map(PublishDO::getId).distinct().collect(Collectors.toList())
        );
        Map<Integer, List<PublishImageVideoDO>> pvmMap = publishImageVideoDOS.stream().collect(Collectors.groupingBy(PublishImageVideoDO::getId));

        Map<Integer, PublishDO> psMap = data.stream().collect(Collectors.toMap(PublishDO::getId, p -> p));
        boolean hasMore = data.size() == getSize;
        List<PublishDO> thisData = hasMore ? data.subList(0, data.size() - 1) : data.subList(0, data.size());
        PublishDO last = thisData.get(thisData.size() - 1);

        Date now = new Date();
        String today = DateFormatUtils.format(now, Constant.INT_DATE_FORMAT);
        String yesterday = DateFormatUtils.format(DateUtils.addDays(now, -1), Constant.INT_DATE_FORMAT);

        Map<Integer, List<PublishDO>> dateMap = thisData.stream().collect(Collectors.groupingBy(p -> Integer.parseInt(DateFormatUtils.format(p.getCreateTime(), Constant.INT_DATE_FORMAT))));
        List<Integer> datesTemp = Lists.newArrayList(dateMap.keySet());
        List<Integer> dates = datesTemp.stream().sorted((o1, o2) -> -o1.compareTo(o2)).collect(Collectors.toList());

        List<PublishInHpDTO> result = Lists.newArrayList();
        for (Integer date : dates) {
            String dayText;
            String monthText = "";
            String yearText = "";

            String baseDate = String.valueOf(date);

            if (Objects.equals(baseDate, today)) {
                dayText = "今天";
            } else {
                if (Objects.equals(baseDate, yesterday)) {
                    dayText = "昨天";
                } else {
                    String[] b = baseDate.split("\\-");
                    String[] t = today.split("\\-");

                    if (Objects.equals(b[0], t[0])) {
                        if (Objects.equals(b[1], t[1])) {
                            dayText = b[2];
                        } else {
                            dayText = b[2];
                            monthText = b[1];
                        }
                    } else {
                        dayText = b[2];
                        monthText = b[1];
                        yearText = b[0];
                    }
                }
            }

            result.add(
                    PublishInHpDTO.builder()
                            .date(date)
                            .dayText(dayText)
                            .items(dateMap.get(date).stream()
                                    .map(row -> {
                                                List<PublishImageVideoDO> pvmTemp = pvmMap.get(row.getId());

                                                return PublishInHpItemDTO.builder()
                                                        .id(row.getId())
                                                        .imageVideos(ObjectUtils.isEmpty(pvmTemp) ? Lists.newArrayList() : pvmTemp.stream().map(PublishImageVideoDO::getFile).collect(Collectors.toList()))
                                                        .imageVideosNum(ObjectUtils.isEmpty(pvmTemp) ? 0 : pvmTemp.size())
                                                        .title(psMap.get(row.getId()) == null ? "" : psMap.get(row.getId()).getTitle())
                                                        .build();
                                            }
                                    )
                                    .collect(Collectors.toList())
                            )
                            .monthText(monthText)
                            .yearText(yearText)
                            .build()
            );
        }

        return RollIdPageData.data(false, last.getId(), baseSize, hasMore, result);
    }

    public List<PublishDTO> getPublishByIds(List<Integer> ids, Integer requestUserId, PublishCallSceneEnum publishCallSceneEnum) {
        if (StringUtils.isEmpty(ids)) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID列表"));

        List<PublishDO> data = publishDOMapper.searchByIds(ids);

        if (ObjectUtils.isEmpty(data)) return Lists.newArrayList();

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(PublishDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, List<PublishImageVideoDO>> pimMap = getImageVideoMap(data.stream().map(PublishDO::getId).distinct().collect(Collectors.toList()));
        Map<Integer, String> cm = categoryService.allMap();

        return data.stream().map(row -> cov(row, requestUserId, userDOMap.get(row.getUserId()), publishCallSceneEnum, pimMap.get(row.getId()), cm)).collect(Collectors.toList());
    }

    @Transactional
    public PublishSubmitDTO publish(PublishSubmitParam param, Boolean needCheckUser) {
        //checkForAddOrModify(param);

        UserDO userDO = userDOMapper.selectByPhone("13328202442");
//        if (needCheckUser) checkUserForAddOrModify(userDO, "发布");

        String mainMedia = getMainMedia(param.getImageVideos());

        userDOMapper.addPublishCount(userDO.getId());

        PublishDO publishDO = PublishDO.builder()
                .areaCode(param.getAreaCode())
                .areaName(param.getAreaName())
                .categoryFirstId(param.getFirstCategoryId())
                .categorySecondId(param.getSecondCategoryId())
                .cityCode(param.getCityCode())
                .cityName(param.getCityName())
                .collectionCount(0)
                .contactPhone(param.getContactPhone())
                .discussCount(0)
                .inPrice(param.getInPrice())
                .isDeleted(DeletedStatusEnum.EXIST.getCode())
                .likeCount(0)
                .mainMedia(mainMedia)
                .outPrice(param.getOutPrice())
                .phone(param.getContactPhone())
                .productiveYear(param.getProductiveYear())
                .provinceCode(param.getProvinceCode())
                .provinceName(param.getProvinceName())
                .shelfStatus((byte) 1)
                .textIntroduce(param.getTextIntroduce())
                .title(param.getTitle())
                .userId(userDO.getId())
                .viewCount(0)
                .voiceIntroduce(param.getVoiceIntroduce())
                .voiceIntroduceTime(param.getVoiceIntroduceTime())

                .brandId(param.getBrandId())
                .usageHours(param.getUsageHours())
                .hasInvoice(param.getHasInvoice())
                .hasCertificate(param.getHasCertificate())
                .contact(param.getContact())
                .carUserId(param.getUserId())
                .build();

        publishDOMapper.insertSelective(publishDO);

        Integer id = publishDO.getId();

        publishImageVideoDOMapper.insertBatchSelective(
                param.getImageVideos().stream()
                        .map(row -> PublishImageVideoDO.builder()
                                .file(row.getFileUrl())
                                .firstPic(row.getFileUrl())
                                .publishId(id)
                                .type(row.getType())
                                .build())
                        .collect(Collectors.toList())
        );

        return PublishSubmitDTO.builder()
                .id(id)
                .succeed(true)
                .build();
    }

    public Boolean modifyPublish(PublishSubmitParam param, Boolean needCheckUser) {
        //checkForAddOrModify(param);

        UserDO userDO = userDOMapper.selectByPhone("13328202442");
//        if (needCheckUser) checkUserForAddOrModify(userDO, "发布");

        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(param.getId());
        if (publishDO == null) throw new RuntimeException("发布不存在");

        publishImageVideoDOMapper.deleteByPublish(param.getId());

        String mainMedia = getMainMedia(param.getImageVideos());

        PublishDO publishDOModify = PublishDO.builder()
                .id(param.getId())
                .areaCode(param.getAreaCode())
                .areaName(param.getAreaName())
                .categoryFirstId(param.getFirstCategoryId())
                .categorySecondId(param.getSecondCategoryId())
                .cityCode(param.getCityCode())
                .cityName(param.getCityName())
                .contactPhone(param.getContactPhone())
                .inPrice(param.getInPrice())
                .mainMedia(mainMedia)
                .outPrice(param.getOutPrice())
                .phone(param.getContactPhone())
                .productiveYear(param.getProductiveYear())
                .provinceCode(param.getProvinceCode())
                .provinceName(param.getProvinceName())
                .textIntroduce(param.getTextIntroduce())
                .title(param.getTitle())
                .userId(userDO.getId())
                .voiceIntroduce(param.getVoiceIntroduce())
                .voiceIntroduceTime(param.getVoiceIntroduceTime())
                .shelfStatus((byte) 1)//自动变成出售中

                .brandId(param.getBrandId())
                .usageHours(param.getUsageHours())
                .hasInvoice(param.getHasInvoice())
                .hasCertificate(param.getHasCertificate())
                .contact(param.getContact())
                .carUserId(param.getUserId())
                .build();

        publishDOMapper.updateByPrimaryKeySelective(publishDOModify);

        publishImageVideoDOMapper.insertBatchSelective(
                param.getImageVideos().stream()
                        .map(row -> PublishImageVideoDO.builder()
                                .file(row.getFileUrl())
                                .firstPic(row.getFileUrl())
                                .publishId(publishDO.getId())
                                .type(row.getType())
                                .build())
                        .collect(Collectors.toList())
        );

        return true;
    }

    public Boolean downOrUpPublish(Integer userId, Integer publishId, Byte type) {
//        if (userId == null) throw new RuntimeException("用户ID不能为空");
        if (publishId == null) throw new RuntimeException("发布ID不能为空");

//        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
//        if (userDO == null) throw new RuntimeException("用户不存在");
//        if (DeletedStatusEnum.isDeleted(userDO.getIsDeleted())) throw new RuntimeException("用户已被删除");

        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
        if (publishDO == null) throw new RuntimeException("发布不存在");

        publishDOMapper.updateByPrimaryKeySelective(
                PublishDO.builder()
                        .id(publishId)
                        .shelfStatus(type)
                        .build()
        );

        return true;
    }

    @Transactional
    public PublishDetailDTO getDetail(Integer userId, Integer publishId) {
        if (publishId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));

        PublishDO PublishDO = publishDOMapper.searchById(publishId);
        if (PublishDO == null) throw new RuntimeException("发布不存在");

        publishDOMapper.addViewCount(publishId);

        List<PublishImageVideoDO> publishImageVideoDOS = publishImageVideoDOMapper.selectByPublish(PublishDO.getId());

        List<PublishDiscussDTO> publishDiscussDTOS = Lists.newArrayList();
        List<DiscussDO> discussDOS = discussDOMapper.selectByPublish(publishId);
        if (!ObjectUtils.isEmpty(discussDOS)) {
            List<DiscussDO> first = discussDOS.stream().filter(d -> d.getBeReplyDiscussId() == null).sorted((o1, o2) -> -o1.getId().compareTo(o2.getId())).collect(Collectors.toList());
            List<DiscussDO> other = discussDOS.stream().filter(d -> d.getBeReplyDiscussId() != null).sorted((o1, o2) -> -o1.getId().compareTo(o2.getId())).collect(Collectors.toList());
            Map<Integer, List<DiscussDO>> otherMap = other.stream().collect(Collectors.groupingBy(DiscussDO::getFloorDiscussId));

            for (DiscussDO discussDO : first) {
                List<PublishDiscussDTO> secondLevel = Lists.newArrayList();
                List<DiscussDO> down = otherMap.get(discussDO.getId());
                if (!ObjectUtils.isEmpty(down)) {
                    List<DiscussDO> sortedFloors = down.stream().sorted((o1, o2) -> -o1.getId().compareTo(o2.getId())).collect(Collectors.toList());

                    for (DiscussDO sortedFloor : sortedFloors) {
                        UserDTO br = userService.getUser(sortedFloor.getBeReplyReplyDiscussUserId());

                        secondLevel.add(
                                PublishDiscussDTO.builder()
                                        .beReplyDiscussId(sortedFloor.getBeReplyDiscussId())
                                        .beReplyReplyDiscussUserId(sortedFloor.getBeReplyReplyDiscussUserId())
                                        .createTime(DateFormatUtils.format(sortedFloor.getCreateTime(), Constant.DATE_FORMAT))
                                        .createTimeFriendly(DateUtil.formatDate(sortedFloor.getCreateTime()))
                                        .content(sortedFloor.getContent())
                                        .contentPrefix(br == null ? "" : String.format(CONTENT_PREFIX, br.getNickName()))
                                        .floorDiscussId(sortedFloor.getFloorDiscussId())
                                        .floorDiscussUserId(sortedFloor.getFloorDiscussUserId())
                                        .id(sortedFloor.getId())
                                        .publishId(sortedFloor.getPublishId())
                                        .publishUserId(sortedFloor.getPublishUserId())
                                        .publishUserReadStatus(sortedFloor.getPublishUserReadStatus())
                                        .userId(sortedFloor.getUserId())
                                        .userInfo(userService.getUser(sortedFloor.getUserId()))
                                        .build()
                        );
                    }
                }


                publishDiscussDTOS.add(
                        PublishDiscussDTO.builder()
                                .beReplyDiscussId(discussDO.getBeReplyDiscussId())
                                .beReplyReplyDiscussUserId(discussDO.getBeReplyReplyDiscussUserId())
                                .createTime(DateFormatUtils.format(discussDO.getCreateTime(), Constant.DATE_FORMAT))
                                .createTimeFriendly(DateUtil.formatDate(discussDO.getCreateTime()))
                                .content(discussDO.getContent())
                                .id(discussDO.getId())
                                .publishId(discussDO.getPublishId())
                                .publishUserId(discussDO.getPublishUserId())
                                .publishUserReadStatus(discussDO.getPublishUserReadStatus())
                                .userId(discussDO.getUserId())
                                .userInfo(userService.getUser(discussDO.getUserId()))
                                .secondLevel(secondLevel)
                                .build()
                );
            }
        }

        UserDO publishUser = userDOMapper.selectByPrimaryKey(PublishDO.getUserId());
        CarUserCollectionDO collectionDO = carUserCollectionDOMapper.selectByUserAndPublish(userId, publishId);
        Map<Integer, String> cm = categoryService.allMap();

        return PublishDetailDTO.builder()
                .discuss(publishDiscussDTOS)
                .publish(cov(PublishDO, userId, publishUser, PublishCallSceneEnum.SQUARE, publishImageVideoDOS, cm))
                .collectionDown(collectionDO != null)
                .build();
    }

//    @Transactional
//    public Boolean like(Integer userId, Integer publishId) {
//        checkForHandle(userId, publishId);
//
//        PublishLikeDO publishLikeDO = publishLikeDOMapper.selectByUserAndPublish(userId, publishId);
//        if (publishLikeDO == null) {
//            publishDOMapper.addLikeCount(publishId);
//
//            publishLikeDOMapper.insertSelective(PublishLikeDO.builder().publishId(publishId).userId(userId).build());
//        }
//
//        return true;
//    }

    @Transactional
    public String getPublishPhone(Integer userId, Integer publishId) {
        checkForView(userId, publishId);

        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
        if (publishDO == null) throw new RuntimeException("发布不存在");

        return StringUtils.isEmpty(publishDO.getPhone()) ? "" : publishDO.getPhone();

        /*UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) throw new RuntimeException("用户不存在");

        if (StringUtils.isEmpty(userDO.getPhone())) throw new RuntimeException("请先绑定手机号");

        publishDOMapper.addViewPhoneCount();

        userDOMapper.addViewPhoneCount(userId);

        UserDO publisher = userDOMapper.selectByPrimaryKey(publishDO.getUserId());

        return publisher == null ? "" : publisher.getPhone();*/
    }

    public PageData<PublishDTO> searchForAdmin(Integer pageNum, Integer pageSize, String provinceCode, String cityCode, String areaCode, String phone, String title) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<PublishDO> pd = (Page<PublishDO>) publishDOMapper.searchForAdmin(provinceCode, cityCode, areaCode, phone, title);

        List<PublishDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(PublishDO::getUserId).distinct().collect(Collectors.toList()));
        Map<Integer, List<PublishImageVideoDO>> pimMap = getImageVideoMap(data.stream().map(PublishDO::getId).distinct().collect(Collectors.toList()));
        Map<Integer, String> cm = categoryService.allMap();

        return PageData.data(pd,
                data.stream()
                        .map(row -> cov(row, null, userDOMap.get(row.getUserId()), PublishCallSceneEnum.ADMIN, pimMap.get(row.getId()), cm))
                        .collect(Collectors.toList())
        );
    }

    public PublishSubmitDTO publishForAdmin(AdminPublishSubmitParam param) {
        return publish(
                PublishSubmitParam.builder()
                        .contactPhone(param.getContactPhone())
                        .firstCategoryId(param.getFirstCategoryId())
                        .imageVideos(param.getImageVideos())
                        .inPrice(param.getInPrice())
                        .outPrice(param.getOutPrice())
                        .productiveYear(param.getProductiveYear())
                        .secondCategoryId(param.getSecondCategoryId())
                        .textIntroduce(param.getTextIntroduce())
                        .title(param.getTitle())
                        .userId(param.getPublishUserId())
                        .voiceIntroduce(param.getVoiceIntroduce())

                        .areaCode(param.getAreaCode())
                        .areaName(param.getAreaName())
                        .cityCode(param.getCityCode())
                        .cityName(param.getCityName())
                        .provinceCode(param.getProvinceCode())
                        .provinceName(param.getProvinceName())
                        .build(),
                false
        );
    }

    public Boolean deletePublish(Integer publishId) {
        publishDOMapper.softDelete(publishId);

        return true;
    }

    public Boolean modifyPublishForAdmin(AdminPublishModifyParam param) {
        return modifyPublish(
                PublishSubmitParam.builder()
                        .contactPhone(param.getContactPhone())
                        .firstCategoryId(param.getFirstCategoryId())
                        .id(param.getId())
                        .imageVideos(param.getImageVideos())
                        .inPrice(param.getInPrice())
                        .outPrice(param.getOutPrice())
                        .productiveYear(param.getProductiveYear())
                        .secondCategoryId(param.getSecondCategoryId())
                        .textIntroduce(param.getTextIntroduce())
                        .title(param.getTitle())
                        .userId(param.getPublishUserId())
                        .voiceIntroduce(param.getVoiceIntroduce())

                        .areaCode(param.getAreaCode())
                        .areaName(param.getAreaName())
                        .cityCode(param.getCityCode())
                        .cityName(param.getCityName())
                        .provinceCode(param.getProvinceCode())
                        .provinceName(param.getProvinceName())
                        .build(),
                false
        );
    }

    private void checkForAddOrModify(PublishSubmitParam param) {
        if (param.getUserId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (StringUtils.isEmpty(param.getTitle())) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "标题"));
        if (ObjectUtils.isEmpty(param.getImageVideos())) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "图片或视频"));
        if (param.getImageVideos().size() > OVER_SIZE) throw new RuntimeException(String.format(ExceptionAdvice.NOT_OVER, "图片或视频一共", OVER_SIZE));
        if (StringUtils.isEmpty(param.getContactPhone())) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "联系号码"));
        if (param.getContactPhone().length() > Constant.MAX_PHONE_LENGTH) throw new RuntimeException(String.format(ExceptionAdvice.NOT_OVER, "联系号码字数", Constant.MAX_PHONE_LENGTH));
        if (param.getOutPrice() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "转让价"));
        if (param.getInPrice() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "入手价"));
        if (param.getFirstCategoryId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "一级分类"));
        if (param.getFirstCategoryId() <= 0) throw new RuntimeException(String.format(ExceptionAdvice.MUST_POS_NUM, "一级分类"));
        if (param.getSecondCategoryId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "二级分类"));
        if (param.getSecondCategoryId() <= 0) throw new RuntimeException(String.format(ExceptionAdvice.MUST_POS_NUM, "二级分类"));
        if (param.getProductiveYear() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "生产年份"));
    }

    private void checkUserForAddOrModify(UserDO userDO, String operate) {
        if (userDO == null) throw new RuntimeException("用户不存在");

        if (DeletedStatusEnum.isDeleted(userDO.getIsDeleted())) throw new RuntimeException("用户已被删除");
        if (StringUtils.isEmpty(userDO.getPhone())) throw new RuntimeException(operate + "前需要绑定手机号");
        if (StringUtils.isEmpty(userDO.getProvinceCode())) throw new RuntimeException(operate + "前需要设置省份");
        if (StringUtils.isEmpty(userDO.getCityCode())) throw new RuntimeException(operate + "前需要设置城市");
        if (StringUtils.isEmpty(userDO.getAreaCode())) throw new RuntimeException(operate + "前需要设置区");
    }

    private String getMainMedia(List<ImageVideosParam> imageVideos) {
        String mainMedia = "";

        if (!ObjectUtils.isEmpty(imageVideos)) {
            ImageVideosParam first = imageVideos.get(0);
            if (ImageVideosTypeEnum.isVideo(first.getType())) {
                mainMedia = first.getFileUrl() + "?x-oss-process=video/snapshot,t_1000,f_jpg,w_0,h_0,m_fast";
            } else {
                mainMedia = first.getFileUrl();
            }
        }

        return mainMedia;
    }

    private Map<Integer, List<PublishImageVideoDO>> getImageVideoMap(List<Integer> publishIds) {
        return publishImageVideoDOMapper.selectBatchByPublish(publishIds).stream().collect(Collectors.groupingBy(PublishImageVideoDO::getPublishId));
    }

    private PublishDTO cov(PublishDO row, Integer requestUserId, UserDO publishUser, PublishCallSceneEnum publishCallSceneEnum, List<PublishImageVideoDO> publishImageVideoDOS, Map<Integer, String> cm) {
        publishImageVideoDOS = ObjectUtils.isEmpty(publishImageVideoDOS) ? Lists.newArrayList() : publishImageVideoDOS.stream().sorted(Comparator.comparing(PublishImageVideoDO::getId)).collect(Collectors.toList());

        BrandDO brandDO = row.getBrandId() == null ? null : brandDOMapper.selectByPrimaryKey(row.getBrandId());

        return PublishDTO.builder()
                .categoryFirstId(row.getCategoryFirstId())
                .categoryFirstName(cm.get(row.getCategoryFirstId()))
                .categorySecondId(row.getCategorySecondId())
                .categorySecondName(cm.get(row.getCategorySecondId()))
                .collectionCount(row.getCollectionCount() == null ? 0 : row.getCollectionCount())
                .contactPhone(StringUtils.isEmpty(row.getContactPhone()) ? DefaultHandleConstant.PUBLISH_CONTACT_PHONE : row.getContactPhone())
                .createTime(DateFormatUtils.format(row.getCreateTime(), Constant.DATE_FORMAT))
                .createTimeFriendly(DateUtil.formatDate(row.getCreateTime()))
                .discussCount(row.getDiscussCount())
                .id(row.getId())
                .imageVideos(ObjectUtils.isEmpty(publishImageVideoDOS) ? Lists.newArrayList() : publishImageVideoDOS.stream().map(innerRow -> PublishImageVideoDTO.builder().file(innerRow.getFile()).type(innerRow.getType()).build()).collect(Collectors.toList()))
                .inPrice(row.getInPrice() == null ? DefaultHandleConstant.PUBLISH_IN : String.valueOf(row.getInPrice()))
                .isDeleted(row.getIsDeleted())
                .isDownShelf(getIsDownShelf(row.getShelfStatus()))
                .likeCount(row.getLikeCount() == null ? 0 : row.getLikeCount())
                .location(userService.getUserLocation(row.getProvinceName(), row.getCityName(), row.getAreaName()))
                .locationDetail(getLocationDetail(row))
                .mainMedia(row.getMainMedia())
                .operates(getOperates(row, requestUserId, publishCallSceneEnum))
                .outPrice(row.getOutPrice() == null ? DefaultHandleConstant.PUBLISH_OUT : String.valueOf(row.getOutPrice()))
                .phone(StringUtils.isEmpty(row.getPhone()) ? "" : row.getPhone())
                .productiveYear(row.getProductiveYear())
                .publishUserInfo(userService.getUser(publishUser))
                .textIntroduce(StringUtils.isEmpty(row.getTextIntroduce()) ? DefaultHandleConstant.PUBLISH_TEXT_INTRODUCE : row.getTextIntroduce())
                .title(StringUtils.isEmpty(row.getTitle()) ? DefaultHandleConstant.PUBLISH_TITLE : row.getTitle())
                .viewCount(row.getViewCount() == null ? 0 : row.getViewCount())
                .viewPhoneCount(row.getViewPhoneCount())
                .voiceIntroduce(StringUtils.isEmpty(row.getVoiceIntroduce()) ? null : row.getVoiceIntroduce())
                .voiceIntroduceTime(row.getVoiceIntroduceTime())

                .brandId(row.getBrandId())
                .brandName(brandDO == null ? "" : brandDO.getName())
                .usageHours(StringUtils.isEmpty(row.getUsageHours()) ? "" : row.getUsageHours())
                .hasInvoice(row.getHasInvoice())
                .hasCertificate(row.getHasCertificate())
                .contact(StringUtils.isEmpty(row.getContact()) ? "" : row.getContact())

                .build();
    }

    private Boolean getIsDownShelf(Byte shelfStatus) {
        return PublishShelfStatusEnum.byCode(shelfStatus) == null ? true : PublishShelfStatusEnum.isOff(shelfStatus);
    }

    private List<OperateDTO> getOperates(PublishDO row, Integer requestUserId, PublishCallSceneEnum publishCallSceneEnum) {
        List<OperateDTO> data = Lists.newArrayList();
        switch (publishCallSceneEnum) {
            case MY_PUBLISH:
                if (Objects.equals(row.getCarUserId(), requestUserId)) {
                    if (getIsDownShelf(row.getShelfStatus())) {
                        data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_RE_ON));
                    } else {
                        data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_OFF, OperateTypeEnum.PUBLISH_MODIFY));
                    }
                }
                break;
            case MY_COLLECTION:
                data.addAll(OperateDTO.getOperate(OperateTypeEnum.PUBLISH_REMOVE_COLLECTION));
                break;
            case SQUARE:
            default:
                break;
        }

        return data;
    }

    private LocationDetailDTO getLocationDetail(PublishDO row) {
        return LocationDetailDTO.builder()
                .areaCode(row.getAreaCode())
                .areaName(row.getAreaName())
                .cityCode(row.getCityCode())
                .cityName(row.getCityName())
                .location(userService.getUserLocation(row.getProvinceName(), row.getCityName(), row.getAreaName()))
                .provinceCode(row.getProvinceCode())
                .provinceName(row.getProvinceName())
                .build();
    }

    private void checkForHandle(Integer userId, Integer publishId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (publishId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));

        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(publishId);
        if (publishDO == null) throw new RuntimeException("发布不存在");

        UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
        if (userDO == null) throw new RuntimeException("用户不存在");
    }

    private void checkForView(Integer userId, Integer publishId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (publishId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));
    }
}

