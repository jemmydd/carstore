package com.luoyanjie.mechanical.service.follow;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.luoyanjie.mechanical.bean.dto.my.FansDTO;
import com.luoyanjie.mechanical.bean.entity.FollowDO;
import com.luoyanjie.mechanical.bean.entity.UserDO;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.FollowDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.user.UserService;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-07-29 16:06
 * Coding happily every day!
 */
@Slf4j
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDOMapper followDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserService userService;

    @Override
    public PageData<FansDTO> getFansPageData(Integer userId, Integer pageNum, Integer pageSize, Integer toId) {
        if (toId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "被关注者"));
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<FollowDO> pd = (Page<FollowDO>) followDOMapper.selectByTo(toId);

        List<FollowDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(FollowDO::getFromId).distinct().collect(Collectors.toList()));
        List<Integer> myAllToIds = followDOMapper.selectToByFrom(toId);

        return PageData.data(pd, data.stream().map(row -> covForFans(row, userId, userDOMap.get(row.getFromId()), myAllToIds)).collect(Collectors.toList()));
    }

    @Override
    public PageData<FansDTO> getFollowPageData(Integer userId, Integer pageNum, Integer pageSize, Integer fromId) {
        if (fromId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "被关注者"));
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<FollowDO> pd = (Page<FollowDO>) followDOMapper.selectByFrom(fromId);

        List<FollowDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        Map<Integer, UserDO> userDOMap = userService.getUserMap(data.stream().map(FollowDO::getToId).distinct().collect(Collectors.toList()));

        return PageData.data(pd, data.stream().map(row -> covForFollow(row, userId, userDOMap.get(row.getToId()))).collect(Collectors.toList()));
    }

    @Override
    public Map<Integer, FollowDO> getByFrom(Integer fromId) {
        return followDOMapper.selectByFrom(fromId).stream().collect(Collectors.toMap(FollowDO::getToId, f -> f));
    }

    @Override
    public Map<Integer, FollowDO> getByTo(Integer toId) {
        return followDOMapper.selectByTo(toId).stream().collect(Collectors.toMap(FollowDO::getFromId, f -> f));
    }

    @Transactional
    @Override
    public Boolean toFollow(Integer userId, Integer objectId) {
        checkForFollow(userId, objectId);

        userDOMapper.addFollowCount(userId);
        userDOMapper.addFansCount(objectId);

        try {
            followDOMapper.insertSelective(
                    FollowDO.builder()
                            .fromId(userId)
                            .toId(objectId)
                            .build()
            );
        } catch (DuplicateKeyException ex) {
            throw new RuntimeException("已经关注，无须再关注");
        }

        return true;
    }

    @Transactional
    @Override
    public Boolean unFollow(Integer userId, Integer objectId) {
        checkForFollow(userId, objectId);

        List<Integer> tos = followDOMapper.selectToByFrom(userId);
        if (!tos.contains(objectId)) throw new RuntimeException("没有关注对方，无须取消关注");

        userDOMapper.subtractFollowCount(userId);
        userDOMapper.subtractFansCount(objectId);

        followDOMapper.deleteByFromTo(userId, objectId);

        return true;
    }

    private void checkForFollow(Integer userId, Integer objectId) {
        if (Objects.equals(userId, objectId)) throw new RuntimeException("不可以对自己操作");

        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (objectId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "被关注者"));

        List<Integer> userIds = userDOMapper.selectIdBatch(Lists.newArrayList(userId, objectId));
        if (!userIds.contains(userId)) throw new RuntimeException("用户不存在");
        if (!userIds.contains(objectId)) throw new RuntimeException("被关注者不存在");
    }

    private FansDTO covForFans(FollowDO row, Integer requestId, UserDO from, List<Integer> myAllToIds) {
        return FansDTO.builder()
                .personInfo(userService.getUser(from))
                .followed(myAllToIds.contains(row.getFromId()))
                .id(row.getId())
                .build();
    }

    private FansDTO covForFollow(FollowDO row, Integer requestId, UserDO to) {
        return FansDTO.builder()
                .personInfo(userService.getUser(to))
                .followed(true)
                .id(row.getId())
                .build();
    }
}
