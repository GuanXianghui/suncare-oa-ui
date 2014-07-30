package com.gxx.oa;

import com.gxx.oa.dao.TaskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Task;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import com.gxx.oa.utils.SMSUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 修改任务action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 21:16
 */
public class UpdateTaskAction extends BaseAction {
    //任务id
    private String taskId;
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
        logger.info("taskId:" + taskId + ",toUserId:" + toUserId + ",title:" + title + ",beginDate:" +
                beginDate + ",endDate:" + endDate + ",content:" + content);
        Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
        //判不存在该任务，或者，!(任务来源和接受任务的人可以修改任务) 不能修改
        if(task == null || (task.getFromUserId() != getUser().getId() && task.getToUserId() != getUser().getId())){
            message = "你的操作有误！";
            return ERROR;
        }
        task.setToUserId(Integer.parseInt(toUserId));
        task.setTitle(title);
        task.setBeginDate(beginDate);
        task.setEndDate(endDate);
        task.setContent(content);
        task.setUpdateDate(date);
        task.setUpdateTime(time);
        task.setUpdateIp(getIp());
        TaskDao.updateTask(task);

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_UPDATE_TASK, "修改任务成功！", date, time, getIp());

        //邮件相关信息
        String email;//邮件
        String userName;//用户名

        if(getUser().getId() != task.getFromUserId()){
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), task.getFromUserId(),
                    getUser().getName() + "修改了你的任务，见<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">链接</a>", getIp());

            User fromUser = UserDao.getUserById(task.getFromUserId());
            //发送短信
            if(StringUtils.isNotBlank(fromUser.getMobileTel())){
                String content = getUser().getName() + "在申成门窗OA系统修改了任务：[" + task.getTitle() + "]，赶紧去查看吧！祝您工作顺利！";
                SMSUtil.sendSMS(fromUser.getMobileTel(), content);
            }

            //邮件相关信息
            email = fromUser.getEmail();
            userName = fromUser.getName();
        } else {
            //普通用户触发给用户发一条消息
            BaseUtil.createNormalMessage(getUser().getId(), task.getToUserId(),
                    getUser().getName() + "修改了你的任务，见<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">链接</a>", getIp());

            User toUser = UserDao.getUserById(task.getToUserId());
            //发送短信
            if(StringUtils.isNotBlank(toUser.getMobileTel())){
                String content = getUser().getName() + "在申成门窗OA系统修改了任务：[" + task.getTitle() + "]，赶紧去查看吧！祝您工作顺利！";
                SMSUtil.sendSMS(toUser.getMobileTel(), content);
            }

            //邮件相关信息
            email = toUser.getEmail();
            userName = toUser.getName();
        }

        //发邮件
        if(StringUtils.isNotBlank(email)){
            //邮件title
            String title = "申成门窗OA系统-修改任务";
            //邮件内容
            String content = userName + "你好：<br><br>" +
                    getUser().getName() + "在申成门窗OA系统修改了任务：[" + task.getTitle() + "]，见<a href=\"http://www.suncare-sys.com:10000/showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">链接</a>！<br><br>" +
                    "祝您工作顺利！<br><br>" +
                    "申成门窗OA系统";
            //发送邮件
            EmailUtils.sendEmail(title, content, email);
        }

        message = "修改任务成功！";
        return SUCCESS;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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