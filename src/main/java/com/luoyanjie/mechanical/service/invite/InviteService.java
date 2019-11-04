package com.luoyanjie.mechanical.service.invite;

import com.luoyanjie.mechanical.bean.dto.invite.InviteDTO;
import com.luoyanjie.mechanical.component.result.PageData;

public interface InviteService {
    Boolean inviteBind(Integer fromId, Integer toId);

    PageData<InviteDTO> pageData(Integer pageNum, Integer pageSize, Integer userId);
}
