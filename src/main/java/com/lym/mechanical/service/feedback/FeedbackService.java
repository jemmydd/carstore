package com.lym.mechanical.service.feedback;

import com.google.common.collect.Lists;
import com.lym.mechanical.bean.entity.FeedbackDO;
import com.lym.mechanical.bean.entity.QuestionAnswerDO;
import com.lym.mechanical.bean.param.feedback.FeedbackParam;
import com.lym.mechanical.dao.mapper.FeedbackDOMapper;
import com.lym.mechanical.dao.mapper.QuestionAnswerDOMapper;
import com.lym.mechanical.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @Classname FeedbackService
 * @Description
 * @Date 2020/1/14 18:18
 * @Created by jimy
 * good good code, day day up!
 */
@Service
public class FeedbackService {

    @Autowired
    private QuestionAnswerDOMapper questionAnswerDOMapper;

    @Autowired
    private FeedbackDOMapper feedbackDOMapper;

    public List<QuestionAnswerDO> questionAnswers() {
        List<QuestionAnswerDO> questionAnswerDOS = questionAnswerDOMapper.selectAll();
        return ObjectUtils.isEmpty(questionAnswerDOS) ? Lists.newArrayList() :
                questionAnswerDOS;
    }

    public Boolean feedback(FeedbackParam param) {
        feedbackDOMapper.insertSelective(FeedbackDO.builder()
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .userId(param.getUserId())
                .content(param.getContent())
                .images(ObjectUtils.isEmpty(param.getImages()) ? "" : StringUtils.join(param.getImages(), ","))
                .build());
        return Boolean.TRUE;
    }
}
