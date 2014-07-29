package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 申成知道修改提问
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowUpdateAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * 提问id
     */
    private String askId;
    /**
     * 问题
     */
    private String question;
    /**
     * 问题补充
     */
    private String description;
    /**
     * 分类
     */
    private String type;
    /**
     * 紧急
     */
    private String urgent;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",question:" + question + ",description:" + description +
                ",type:" + type + ",urgent:" + urgent);
        //判必输字段为空
        if(StringUtils.isBlank(askId) || StringUtils.isBlank(question) || StringUtils.isBlank(type) ||
                StringUtils.isBlank(urgent))
        {
            message = "必输入字段不能为空!";
            return ERROR;
        }

        //判申成知道提问存在，状态，是不是自己的
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(request.getParameter("askId")));
        if(cloudKnowAsk == null || cloudKnowAsk.getUserId() != getUser().getId() ||
                cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "找不到该申成知道提问!";
            return ERROR;
        }

        //更新申成知道提问
        cloudKnowAsk.setQuestion(question);
        cloudKnowAsk.setDescription(description);
        cloudKnowAsk.setType(type);
        cloudKnowAsk.setUrgent(Integer.parseInt(urgent));
        cloudKnowAsk.setUpdateDate(date);
        cloudKnowAsk.setUpdateTime(time);
        cloudKnowAsk.setUpdateIp(getIp());
        CloudKnowAskDao.updateCloudKnowAsk(cloudKnowAsk);

        message = "更新申成知道提问成功！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_UPDATE_ASK, message, date, time, getIp());

        //根据 提问id 查 申成知道所有回答者id，distinct排除相同的
        List<Integer> integerList = CloudKnowAnswerDao.queryCloudKnowAnswerUserIdsByAskId(cloudKnowAsk.getId());
        for(Integer userInteger : integerList){
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), userInteger,
                    getUser().getName() + "修改了申成知道的提问" + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());
        }

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
}
