package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 申成文库上传文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * 提问id
     */
    private String askId;
    /**
     * 回答
     */
    private String answer;
    /**
     * 回答类型 type: insert新增回答 update修改回答
     */
    private String type;
    /**
     * 修改回答ID
     */
    private String answerId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answer:" + answer + ",type:" + type + ",answerId:" + answerId);
        //判必输字段为空
        if(StringUtils.isBlank(askId) || StringUtils.isBlank(answer) || StringUtils.isBlank(type))
        {
            message = "提问id，回答，回答类型不能为空!";
            return ERROR;
        }

        //判类型为修改回答
        if(StringUtils.equals(type, "update")){
            if(StringUtils.isBlank(answerId)){
                message = "修改回答ID不能为空!";
                return ERROR;
            }
            CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(Integer.parseInt(answerId));
            if(cloudKnowAnswer == null || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
                message = "该回答状态不正常!";
                return ERROR;
            }
            //根据id查申成知道提问
            CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
            if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
                message = "该提问状态不正常!";
                return ERROR;
            }
            cloudKnowAnswer.setAnswer(answer);
            cloudKnowAnswer.setUpdateDate(date);
            cloudKnowAnswer.setUpdateTime(time);
            cloudKnowAnswer.setUpdateIp(getIp());
            CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);
            message = "修改回答成功！";

            //创建操作日志
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_ANSWER, message, date, time, getIp());

            //普通用户给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAsk.getUserId(),
                    getUser().getName() + "修改了你申成知道提问的回答" + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());

            return SUCCESS;
        }
        //以下类型为新增回答
        //根据id查申成知道提问
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
        if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "该提问状态不正常!";
            return ERROR;
        }

        message = "你的回答已提交，申成知道感谢你的贡献！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_ANSWER, message, date, time, getIp());

        //普通用户给用户发一条消息
        BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAsk.getUserId(),
                getUser().getName() + "回答了你申成知道提问" + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());

        //根据 提问id+回答者id(状态为正常) 查 一个人回答一个申成知道问题的个数
        int count = CloudKnowAnswerDao.countCloudKnowAnswersByAskIdAndUserId(cloudKnowAsk.getId(), getUser().getId());

        //多个人多次回答，每个人只+2
        if(count == 0){
            //申成知道-回答 多个人多次回答，申成币每个人只+2
            UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_KNOW_ANSWER);
            User user = UserDao.getUserById(getUser().getId());

            //创建申成币变动日志
            BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "申成币变动 申成知道-回答" + MoneyInterface.ACT_CLOUD_KNOW_ANSWER, date, time, getIp());

            //刷新缓存
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);

            //公众账号给用户发一条消息
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                    "申成知道-回答成功，申成币" + MoneyInterface.ACT_CLOUD_KNOW_ANSWER + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());
        }

        //新增申成知道回答
        CloudKnowAnswer cloudKnowAnswer = new CloudKnowAnswer(cloudKnowAsk.getId(), getUser().getId(), answer, 0,
                STATE_NORMAL, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        CloudKnowAnswerDao.insertCloudKnowAnswer(cloudKnowAnswer);

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
