package com.lym.mechanical.service.message;

import com.lym.mechanical.bean.dto.message.MessageDTO;
import com.lym.mechanical.dao.mapper.MessageDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname MessageService
 * @Description
 * @Date 2019/11/8 17:38
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class MessageService {

    @Autowired
    private MessageDOMapper messageDOMapper;

//    public List<MessageDTO> list(Integer userId) {
//
//    }
}
