package com.gxx.oa;

import com.gxx.oa.dao.TaskDao;
import com.gxx.oa.entities.Task;
import com.gxx.oa.utils.BaseUtil;

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
        //Ȩ��У��
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0012_TASK);
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