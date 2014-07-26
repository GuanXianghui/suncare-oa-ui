package com.gxx.oa;

import com.gxx.oa.dao.TaskDao;
import com.gxx.oa.entities.Task;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.TaskInterface;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * д����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 18:10
 */
public class WriteTaskAction extends BaseAction {
    //������Դ�û�id
    private String fromUserId;
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
        logger.info("fromUserId:" + fromUserId + ",toUserId:" + toUserId + ",title:" + title +
                ",beginDate:" + beginDate + ",endDate:" + endDate + ",content:" + content);
        Task task = new Task(Integer.parseInt(fromUserId), Integer.parseInt(toUserId), title, content,
                TaskInterface.STATE_NEW, beginDate, endDate, date, time, getIp(), StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY);
        TaskDao.insertTask(task);

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_WRITE_TASK, "д����ɹ���", date, time, getIp());

        message = "д����ɹ���";
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
