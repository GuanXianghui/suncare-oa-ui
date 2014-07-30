package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 赞回答action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowZanAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
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
        if(null == cloudKnowAnswer || cloudKnowAnswer.getState() != STATE_NORMAL){
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

        //更新申成知道提问
        cloudKnowAnswer.setZan(cloudKnowAnswer.getZan() + 1);
        CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);

        message = "赞回答成功！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_ZAN_ANSWER, message, date, time, getIp());

        //不是同一个人则通知
        if(getUser().getId() != cloudKnowAnswer.getUserId()){
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAnswer.getUserId(),
                    getUser().getName() + "赞了你申成知道提问的回答" + "，见<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">提问</a>", getIp());

            //回答用户
            User cloudKnowAnswerUser = UserDao.getUserById(cloudKnowAnswer.getUserId());
            //发邮件
            if(StringUtils.isNotBlank(cloudKnowAnswerUser.getEmail())){
                //邮件title
                String title = "申成门窗OA系统-申成知道赞回答";
                //邮件内容
                String content = cloudKnowAnswerUser.getName() + "你好：<br><br>" +
                        getUser().getName() + "在申成门窗OA系统赞了你申成知道提问的回答：[" + cloudKnowAsk.getQuestion() + "]，见<a href=\"http://www.suncare-sys.com:10000/cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\" target=\"_blank\">链接</a>！<br><br>" +
                        "祝您工作顺利！<br><br>" +
                        "申成门窗OA系统";
                //发送邮件
                EmailUtils.sendEmail(title, content, cloudKnowAnswerUser.getEmail());
            }
        }

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
