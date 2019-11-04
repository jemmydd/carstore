package com.luoyanjie.mechanical.service.discuss;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luoyanjie.mechanical.bean.common.DefaultHandleConstant;
import com.luoyanjie.mechanical.bean.dto.discuss.AdminDiscussDTO;
import com.luoyanjie.mechanical.bean.dto.discuss.DiscussDTO;
import com.luoyanjie.mechanical.bean.entity.DiscussDO;
import com.luoyanjie.mechanical.bean.entity.PublishDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.bean.entitySelf.PublishSelfDO;
import com.luoyanjie.mechanical.bean.param.discuss.ModifyDiscussParam;
import com.luoyanjie.mechanical.bean.param.discuss.SendDiscussParam;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.DiscussDOMapper;
import com.luoyanjie.mechanical.dao.mapper.PublishDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.service.user.UserServiceImpl;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import com.luoyanjie.mechanical.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-07-31 17:06
 * Coding happily every day!
 */
@Slf4j
@Service
public class DiscussServiceImpl implements DiscussService {
    @Autowired
    private DiscussDOMapper discussDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    @Override
    public PageData<DiscussDTO> getPageDataWithOutRequest(Integer pageNum, Integer pageSize, Integer userId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<DiscussDO> pd = (Page<DiscussDO>) discussDOMapper.searchWithOutRequest(userId);

        List<DiscussDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(DiscussDO::getUserId).distinct().collect(Collectors.toList()));

        List<PublishDO> publishSelfDOS = publishDOMapper.searchByIds(
                data.stream().map(DiscussDO::getPublishId).distinct().collect(Collectors.toList())
        );
        Map<Integer, PublishDO> psMap = publishSelfDOS.stream().collect(Collectors.toMap(PublishDO::getId, p -> p));

        return PageData.data(pd,
                data.stream().map(row -> cov(row, userId, userDOMap.get(row.getUserId()), psMap.get(row.getPublishId()))).collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public Boolean toRead(Integer userId, Integer publishId, Integer discussId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (publishId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));
        if (discussId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "动态ID"));

        discussDOMapper.updateByPrimaryKeySelective(
                DiscussDO.builder()
                        .id(discussId)
                        .publishUserReadStatus((byte) 1)
                        .build()
        );

        return true;
    }

    @Transactional
    @Override
    public Boolean sendDiscuss(SendDiscussParam param) {
        if (param.getUserId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (param.getPublishId() == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));
        if (StringUtils.isEmpty(param.getContent())) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "内容"));

        UserDO userDO = userDOMapper.selectByPrimaryKey(param.getUserId());
        if (userDO == null) throw new RuntimeException("用户不存在");

        PublishDO publishDO = publishDOMapper.selectByPrimaryKey(param.getPublishId());
        if (publishDO == null) throw new RuntimeException("发布不存在");

        Integer beDisUserId = null;
        if (param.getBeDiscussId() != null) {
            DiscussDO discussDO = discussDOMapper.selectByPrimaryKey(param.getBeDiscussId());
            if (discussDO == null) throw new RuntimeException("被引用的动态不存在");

            beDisUserId = discussDO.getUserId();
        }

        Integer floorDiscussUserId = null;
        if (param.getFloorDiscussId() != null) {
            DiscussDO discussDO = discussDOMapper.selectByPrimaryKey(param.getFloorDiscussId());
            if (discussDO == null) throw new RuntimeException("被引用的主楼动态不存在");

            floorDiscussUserId = discussDO.getUserId();
        }

        publishDOMapper.addDiscussCount(param.getPublishId());

        discussDOMapper.insertSelective(
                DiscussDO.builder()
                        .beReplyDiscussId(param.getBeDiscussId())
                        .beReplyReplyDiscussUserId(beDisUserId)
                        .content(param.getContent())
                        .floorDiscussId(param.getFloorDiscussId())
                        .floorDiscussUserId(floorDiscussUserId)
                        .publishId(param.getPublishId())
                        .publishUserId(publishDO.getUserId())
                        .publishUserReadStatus((byte) 0)
                        .userId(param.getUserId())
                        .build()
        );

        return true;
    }

    @Override
    public PageData<AdminDiscussDTO> searchForAdmin(Integer pageNum, Integer pageSize, String title) {
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<DiscussDO> pd = (Page<DiscussDO>) discussDOMapper.searchForAdmin(title);

        List<DiscussDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        List<Integer> a = data.stream().map(DiscussDO::getUserId).distinct().collect(Collectors.toList());
        List<Integer> b = data.stream().map(DiscussDO::getPublishUserId).distinct().collect(Collectors.toList());
        a.addAll(b);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(a);

        List<PublishDO> publishSelfDOS = publishDOMapper.searchByIds(
                data.stream().map(DiscussDO::getPublishId).distinct().collect(Collectors.toList())
        );
        Map<Integer, PublishDO> psMap = publishSelfDOS.stream().collect(Collectors.toMap(PublishDO::getId, p -> p));

        return PageData.data(
                pd,
                data.stream().map(row -> covForAdmin(row, userDOMap.get(row.getUserId()), userDOMap.get(row.getPublishUserId()), psMap.get(row.getPublishId()))).collect(Collectors.toList())
        );
    }

    @Override
    public Boolean deleteDiscuss(Integer discussId) {
        DiscussDO discussDO = discussDOMapper.selectByPrimaryKey(discussId);

        if (discussDO != null) {
            discussDOMapper.deleteByPrimaryKey(discussId);

            publishDOMapper.subtractDiscussCount(discussDO.getPublishId());
        }

        return true;
    }

    @Override
    public Boolean modifyDiscuss(ModifyDiscussParam param) {
        discussDOMapper.updateByPrimaryKeySelective(
                DiscussDO.builder()
                        .id(param.getId())
                        .content(param.getContent())
                        .build()
        );

        return true;
    }

    @Override
    public Boolean toReadBatch(List<Integer> ids) {
        if (!ObjectUtils.isEmpty(ids)) {
            discussDOMapper.readBatch(ids);
        }

        return true;
    }

    private DiscussDTO cov(DiscussDO row, Integer requestId, UserDO discussUser, PublishDO publishSelfDO) {
        return DiscussDTO.builder()
                .content(StringUtils.isEmpty(row.getContent()) ? "" : row.getContent())
                .createdTime(DateUtil.formatDate(row.getCreateTime()))
                .headPortrait(discussUser == null ? DefaultHandleConstant.USER_HEAD_PORTRAIT : UserServiceImpl.getHeadPortrait(discussUser.getHeadPortrait()))
                .id(row.getId())
                .mainMedia(publishSelfDO == null ? "" : publishSelfDO.getMainMedia())
                .nickName(discussUser == null ? DefaultHandleConstant.USER_NICKNAME : UserServiceImpl.getNickName(discussUser.getNickName()))
                .publishId(row.getPublishId())
                .publishTitle(publishSelfDO == null ? "" : (StringUtils.isEmpty(publishSelfDO.getTitle()) ? "" : publishSelfDO.getTitle()))
                .sendUserId(row.getUserId())
                .build();
    }

    private AdminDiscussDTO covForAdmin(DiscussDO row, UserDO dis, UserDO pub, PublishDO publishSelfDO) {
        return AdminDiscussDTO.builder()
                .discussContent(StringUtils.isEmpty(row.getContent()) ? "" : row.getContent())
                .discussCreateTime(DateFormatUtils.format(row.getCreateTime(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()))
                .discussId(row.getId())
                .discussUserInfo(userService.getUser(dis))
                .publishCreateTime(publishSelfDO == null ? "" : DateFormatUtils.format(publishSelfDO.getCreateTime(), DateFormatUtils.ISO_DATE_FORMAT.getPattern()))
                .publishId(publishSelfDO == null ? null : publishSelfDO.getId())
                .publishTitle(publishSelfDO == null ? "" : (StringUtils.isEmpty(publishSelfDO.getTitle()) ? "" : publishSelfDO.getTitle()))
                .publishUserInfo(userService.getUser(pub))
                .build();
    }
}
