package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 删除提问action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * 提问id
     */
    private String askId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId);
        //判提问id不为空
        if(StringUtils.isBlank(askId)){
            message = "提问id不能为空!";
            return ERROR;
        }
        // 提问idInt类型
        int askIdInt = Integer.parseInt(askId);
        //判文档存在
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(askIdInt);
        if(null == cloudKnowAsk || cloudKnowAsk.getUserId() != getUser().getId() || cloudKnowAsk.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //更新申成知道提问
        cloudKnowAsk.setState(STATE_DELETE);
        cloudKnowAsk.setUpdateDate(date);
        cloudKnowAsk.setUpdateTime(time);
        cloudKnowAsk.setUpdateIp(getIp());
        CloudKnowAskDao.updateCloudKnowAsk(cloudKnowAsk);

        message = "删除提问成功！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_DELETE_ASK, message, date, time, getIp());

        //申成知道-删除提问 提问者申成币-1，所有回答的人申成币-2
        UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK);
        User user = UserDao.getUserById(getUser().getId());

        //创建申成币变动日志
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                "申成币变动 申成知道-删除提问 提问者" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK, date, time, getIp());

        //刷新缓存
        request.getSession().setAttribute(BaseInterface.USER_KEY, user);

        //公众账号给用户发一条消息
        BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                "申成知道-删除提问[" + cloudKnowAsk.getQuestion() + "]成功，提问者申成币" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK + "！", getIp());

        //根据 提问id 查 申成知道所有回答者id，distinct排除相同的
        List<Integer> integerList = CloudKnowAnswerDao.queryCloudKnowAnswerUserIdsByAskId(askIdInt);
        for(Integer userInteger : integerList){
            //申成知道-删除提问 提问者申成币-1，所有回答的人申成币-2
            UserDao.updateUserMoney(userInteger.intValue(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER);

            //创建申成币变动日志
            BaseUtil.createOperateLog(userInteger.intValue(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "申成币变动 申成知道-删除提问 回答者" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER, date, time, getIp());

            //公众账号给用户发一条消息
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, userInteger.intValue(),
                    getUser().getName() + "申成知道-删除提问[" + cloudKnowAsk.getQuestion() + "]成功，回答者申成币" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER + "！", getIp());

            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), userInteger,
                    getUser().getName() + "删除了申成知道的提问[" + cloudKnowAsk.getQuestion() + "]", getIp());
        }

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }
}
