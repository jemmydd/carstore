package com.luoyanjie.mechanical.service.collection;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Verify;
import com.luoyanjie.mechanical.bean.dto.publish.PublishDTO;
import com.luoyanjie.mechanical.bean.entity.CollectionDO;
import com.luoyanjie.mechanical.bean.enumBean.PublishCallSceneEnum;
import com.luoyanjie.mechanical.component.result.PageData;
import com.luoyanjie.mechanical.dao.mapper.CollectionDOMapper;
import com.luoyanjie.mechanical.dao.mapper.PublishDOMapper;
import com.luoyanjie.mechanical.dao.mapper.UserDOMapper;
import com.luoyanjie.mechanical.service.publish.PublishService;
import com.luoyanjie.mechanical.sys.ExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyanjie
 * @date 2019-07-31 15:06
 * Coding happily every day!
 */
@Slf4j
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDOMapper collectionDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private PublishDOMapper publishDOMapper;

    @Autowired
    private PublishService publishService;

    @Override
    public PageData<PublishDTO> getPageData(Integer pageNum, Integer pageSize, Integer userId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        PageData.checkPageParam(pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        Page<CollectionDO> pd = (Page<CollectionDO>) collectionDOMapper.search(userId);

        List<CollectionDO> data = pd.getResult();
        if (ObjectUtils.isEmpty(data)) return PageData.noData(pageSize);

        return PageData.data(
                pd,
                publishService.getPublishByIds(data.stream().map(CollectionDO::getPublishId).distinct().collect(Collectors.toList()), userId, PublishCallSceneEnum.MY_COLLECTION)
        );
    }

    @Transactional
    @Override
    public Boolean toCollect(Integer userId, Integer publishId) {
        checkForCollect(userId, publishId);

        userDOMapper.addCollectionCount(userId);
        publishDOMapper.addCollectionCount(publishId);

        try {
            collectionDOMapper.insertSelective(
                    CollectionDO.builder()
                            .publishId(publishId)
                            .userId(userId)
                            .build()
            );
        } catch (DuplicateKeyException ex) {
            throw new RuntimeException("已经收藏，无须再收藏");
        }

        return true;
    }

    @Transactional
    @Override
    public Boolean unCollect(Integer userId, Integer publishId) {
        checkForCollect(userId, publishId);

        List<Integer> publishIds = collectionDOMapper.selectPublishByUser(userId);
        if (!publishIds.contains(publishId)) throw new RuntimeException("没有收藏该发布，无须取消收藏");

        userDOMapper.subtractCollectionCount(userId);
        publishDOMapper.subtractCollectionCount(publishId);

        collectionDOMapper.deleteByUserPublish(userId, publishId);

        return true;
    }

    private void checkForCollect(Integer userId, Integer publishId) {
        if (userId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "用户ID"));
        if (publishId == null) throw new RuntimeException(String.format(ExceptionAdvice.NOT_EMPTY, "发布ID"));

        Integer userIdDb = userDOMapper.selectId(userId);
        if (userIdDb == null) throw new RuntimeException("用户不存在");

        Integer publishIdDb = publishDOMapper.selectId(publishId);
        if (publishIdDb == null) throw new RuntimeException("发布不存在");
    }
}
