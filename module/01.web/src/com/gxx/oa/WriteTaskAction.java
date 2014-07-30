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

        //����ͬһ������֪ͨ
        if(getUser().getId() != task.getToUserId()){
            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(task.getFromUserId(), task.getToUserId(),
                    getUser().getName() + "������������񣬼�<a href=\"showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">����</a>", getIp());

            User toUser = UserDao.getUserById(task.getToUserId());

            //���Ͷ���
            if(StringUtils.isNotBlank(toUser.getMobileTel())){
                String content = getUser().getName() + "������Ŵ�OAϵͳ��������������[" + task.getTitle() + "]���Ͻ�ȥ��ɰɣ�ף������˳����";
                SMSUtil.sendSMS(toUser.getMobileTel(), content);
            }

            //���ʼ�
            if(StringUtils.isNotBlank(toUser.getEmail())){
                //�ʼ�title
                String title = "����Ŵ�OAϵͳ-��������";
                //�ʼ�����
                String content = toUser.getName() + "��ã�<br><br>" +
                        getUser().getName() + "������Ŵ�OAϵͳ��������������[" + task.getTitle() + "]����<a href=\"http://www.suncare-sys.com:10000/showTask.jsp?id=" + task.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                        "ף������˳����<br><br>" +
                        "����Ŵ�OAϵͳ";
                //�����ʼ�
                EmailUtils.sendEmail(title, content, toUser.getEmail());
            }
        }

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
