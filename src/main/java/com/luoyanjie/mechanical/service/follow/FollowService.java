package com.luoyanjie.mechanical.service.follow;

import com.luoyanjie.mechanical.bean.dto.my.FansDTO;
import com.luoyanjie.mechanical.bean.entity.FollowDO;
import com.luoyanjie.mechanical.component.result.PageData;

import java.util.Map;

public interface FollowService {
    PageData<FansDTO> getFansPageData(Integer userId, Integer pageNum, Integer pageSize, Integer toId);

    PageData<FansDTO> getFollowPageData(Integer userId, Integer pageNum, Integer pageSize, Integer fromId);

    Map<Integer, FollowDO> getByFrom(Integer fromId);

    Map<Integer, FollowDO> getByTo(Integer toId);

    Boolean toFollow(Integer userId, Integer objectId);

    Boolean unFollow(Integer userId, Integer objectId);
}
