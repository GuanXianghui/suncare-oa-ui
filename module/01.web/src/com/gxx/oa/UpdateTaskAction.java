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
 * �޸�����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 21:16
 */
public class UpdateTaskAction extends BaseAction {
    //����id
    private String taskId;
    //��������û�id
    private String toUserId;
    //��������
    private String title;
    //��ʼ����
    private String beginDate;
    //��������
    private String endDate;
    //��������
    private String content;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("taskId:" + taskId + ",toUserId:" + toUserId + ",title:" + title + ",beginDate:" +
                beginDate + ",endDate:" + endDate + ",content:" + content);
        Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
        //�в����ڸ����񣬻��ߣ�!(������Դ�ͽ���������˿����޸�����) �����޸�
        if(task == null || (task.getFromUserId() != getUser().getId() && task.getToUserId() != getUser().getId())){
            message = "��Ĳ�������";
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

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_UPDATE_TASK, "�޸�����ɹ���", date, time, getIp());

        //�ʼ������Ϣ
        String email;//�ʼ�
        String userName;//�û���

        if(getUser().getId() != task.getFromUserId()){
            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(getUser().getId(), task.getFromUserId(),
                    getUser().getName() + "�޸���������񣬼�<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">����</a>", getIp());

            User fromUser = UserDao.getUserById(task.getFromUserId());
            //���Ͷ���
            if(StringUtils.isNotBlank(fromUser.getMobileTel())){
                String content = getUser().getName() + "������Ŵ�OAϵͳ�޸�������[" + task.getTitle() + "]���Ͻ�ȥ�鿴�ɣ�ף������˳����";
                SMSUtil.sendSMS(fromUser.getMobileTel(), content);
            }

            //�ʼ������Ϣ
            email = fromUser.getEmail();
            userName = fromUser.getName();
        } else {
            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(getUser().getId(), task.getToUserId(),
                    getUser().getName() + "�޸���������񣬼�<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">����</a>", getIp());

            User toUser = UserDao.getUserById(task.getToUserId());
            //���Ͷ���
            if(StringUtils.isNotBlank(toUser.getMobileTel())){
                String content = getUser().getName() + "������Ŵ�OAϵͳ�޸�������[" + task.getTitle() + "]���Ͻ�ȥ�鿴�ɣ�ף������˳����";
                SMSUtil.sendSMS(toUser.getMobileTel(), content);
            }

            //�ʼ������Ϣ
            email = toUser.getEmail();
            userName = toUser.getName();
        }

        //���ʼ�
        if(StringUtils.isNotBlank(email)){
            //�ʼ�title
            String title = "����Ŵ�OAϵͳ-�޸�����";
            //�ʼ�����
            String content = userName + "��ã�<br><br>" +
                    getUser().getName() + "������Ŵ�OAϵͳ�޸�������[" + task.getTitle() + "]����<a href=\"http://www.suncare-sys.com:10000/showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                    "ף������˳����<br><br>" +
                    "����Ŵ�OAϵͳ";
            //�����ʼ�
            EmailUtils.sendEmail(title, content, email);
        }

        message = "�޸�����ɹ���";
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