package com.gxx.oa;

import com.gxx.oa.dao.TaskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Task;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.PublicUserInterface;
import com.gxx.oa.interfaces.TaskInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import com.gxx.oa.utils.SMSUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 写任务action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 18:10
 */
public class WriteTaskAction extends BaseAction {
    //任务来源用户id
    private String fromUserId;
    //任务接受用户id
    private String toUserId;
    //任务名称
    private String title;
    //开始日期
    private String beginDate;
    //结束日期
    private String endDate;
    //任务内容
    private String content;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("fromUserId:" + fromUserId + ",toUserId:" + toUserId + ",title:" + title +
                ",beginDate:" + beginDate + ",endDate:" + endDate + ",content:" + content);
        Task task = new Task(Integer.parseInt(fromUserId), Integer.parseInt(toUserId), title, content,
                TaskInterface.STATE_NEW, beginDate, endDate, date, time, getIp(), StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY);
        TaskDao.insertTask(task);

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_WRITE_TASK, "写任务成功！", date, time, getIp());

        //不是同一个人则通知
        if(getUser().getId() != task.getToUserId()){
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(task.getFromUserId(), task.getToUserId(),
                    getUser().getName() + "给你分配了任务，见<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">链接</a>", getIp());

            User toUser = UserDao.getUserById(task.getToUserId());

            //发送短信
            if(StringUtils.isNotBlank(toUser.getMobileTel())){
                String content = getUser().getName() + "在申成门窗OA系统给您分配了任务：[" + task.getTitle() + "]，赶紧去完成吧！祝您工作顺利！";
                SMSUtil.sendSMS(toUser.getMobileTel(), content);
            }

            //发邮件
            if(StringUtils.isNotBlank(toUser.getEmail())){
                //邮件title
                String title = "申成门窗OA系统-分配任务";
                //邮件内容
                String content = toUser.getName() + "你好：<br><br>" +
                        getUser().getName() + "在申成门窗OA系统给您分配了任务：[" + task.getTitle() + "]，见<a href=\"http://www.suncare-sys.com:10000/showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">链接</a>！<br><br>" +
                        "祝您工作顺利！<br><br>" +
                        "申成门窗OA系统";
                //发送邮件
                EmailUtils.sendEmail(title, content, toUser.getEmail());
            }
        }

        message = "写任务成功！";
        return SUCCESS;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
