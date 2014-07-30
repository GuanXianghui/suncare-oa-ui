package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 删除回答action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * 提问id
     */
    private String askId;
    /**
     * 回答id
     */
    private String answerId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answerId:" + answerId);
        //判回答id不为空
        if(StringUtils.isBlank(answerId)){
            message = "回答id不能为空!";
            return ERROR;
        }
        // 回答idInt类型
        int answerIdInt = Integer.parseInt(answerId);
        //判文档存在
        CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(answerIdInt);
        if(null == cloudKnowAnswer || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //根据id查申成知道提问
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(cloudKnowAnswer.getAskId());
        if(null == cloudKnowAsk || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        //赋值 提问id 用于跳转
        askId = StringUtils.EMPTY + cloudKnowAsk.getId();

        message = "删除回答成功！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_DELETE_ANSWER, message, date, time, getIp());

        //不是同一个人则通知
        if(getUser().getId() != cloudKnowAsk.getUserId()){
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAsk.getUserId(),
                    getUser().getName() + "删除了你申成知道提问的回答" + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());

            //提问用户
            User cloudKnowAskUser = UserDao.getUserById(cloudKnowAsk.getUserId());
            //发邮件
            if(StringUtils.isNotBlank(cloudKnowAskUser.getEmail())){
                //邮件title
                String title = "申成门窗OA系统-申成知道删除回答";
                //邮件内容
                String content = cloudKnowAskUser.getName() + "你好：<br><br>" +
                        getUser().getName() + "在申成门窗OA系统删除了你申成知道提问的回答：[" + cloudKnowAsk.getQuestion() + "]，见<a href=\"http://www.suncare-sys.com:10000/cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\" target=\"_blank\">链接</a>！<br><br>" +
                        "祝您工作顺利！<br><br>" +
                        "申成门窗OA系统";
                //发送邮件
                EmailUtils.sendEmail(title, content, cloudKnowAskUser.getEmail());
            }
        }

        //根据 提问id+回答者id(状态为正常) 查 一个人回答一个申成知道问题的个数
        int count = CloudKnowAnswerDao.countCloudKnowAnswersByAskIdAndUserId(cloudKnowAsk.getId(), getUser().getId());

        //仍然还有回答不减申成币，没有则申成币-2
        if(count == 1){
            //申成知道-删除回答 仍然还有回答不减申成币，没有则申成币-2
            UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER);
            User user = UserDao.getUserById(getUser().getId());

            //创建申成币变动日志
            BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "申成币变动 申成知道-删除回答" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER, date, time, getIp());

            //刷新缓存
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);

            //公众账号给用户发一条消息
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                    "申成知道-删除回答成功，申成币" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());
        }

        //更新申成知道提问
        cloudKnowAnswer.setState(STATE_DELETE);
        cloudKnowAnswer.setUpdateDate(date);
        cloudKnowAnswer.setUpdateTime(time);
        cloudKnowAnswer.setUpdateIp(getIp());
        CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
