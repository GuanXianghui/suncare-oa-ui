package com.gxx.oa;

import com.gxx.oa.dao.*;
import com.gxx.oa.entities.*;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ��������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 16:47
 */
public class OperateTaskAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//������һҳ
    private static final String TYPE_UPDATE_TASK_STATE = "updateTaskState";//�޸�����״̬
    private static final String TYPE_CUI = "cui";//��
    private static final String TYPE_DELETE = "delete";//ɾ��
    private static final String TYPE_CREATE_REVIEW = "createReview";//��������
    private static final String TYPE_UPDATE_REVIEW = "updateReview";//�޸�����
    private static final String TYPE_DELETE_REVIEW = "deleteReview";//ɾ������
    private static final String TYPE_REPLY_REVIEW = "replyReview";//�ظ�����

    /**
     * ��������
     */
    String type;//��������
    String countNow;//Ŀǰ����
    String fromUserId;//������Դ�û�id
    String toUserId;//��������û�id
    String state;//״̬
    String taskId;//����id
    String newState;//�µ�����״̬
    String updateReviewId;//�޸�����id
    String content;//��������
    String deleteReviewId;//ɾ������id

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0012_TASK);
        logger.info("type:" + type + ",countNow=" + countNow + ",fromUserId=" + fromUserId + ",toUserId=" +
                toUserId + ",state=" + state + ",taskId=" + taskId + ",newState=" + newState);
        //ajax���
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//������һҳ
            int countNowInt = Integer.parseInt(countNow);
            int fromUserIdInt = Integer.parseInt(fromUserId);//û����Ϊ0
            int toUserIdInt = Integer.parseInt(toUserId);//û����Ϊ0
            int stateInt = Integer.parseInt(state);//û����Ϊ0
            //����������Դ�û�id,��������û�id,״̬,��Χ������
            String nextPageTasks = BaseUtil.getJsonArrayFromTasks(TaskDao.queryTasksByFromTo(fromUserIdInt,
                    toUserIdInt, stateInt, countNowInt, Integer.parseInt(PropertyUtil.getInstance().
                    getProperty(BaseInterface.TASK_PAGE_SIZE)))).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //���ؽ��
            resp = "{isSuccess:true,message:'������һҳ����ɹ���',nextPageJson:'" + nextPageTasks +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_UPDATE_TASK_STATE.equals(type)){//�޸�����״̬
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //�в����ڸ����񣬻��ߣ�!(������Դ�ͽ���������˿����޸�����) �����޸�
            if(task == null || (task.getFromUserId() != getUser().getId() && task.getToUserId() != getUser().getId())){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                task.setState(Integer.parseInt(newState));
                task.setUpdateDate(date);
                task.setUpdateTime(time);
                task.setUpdateIp(getIp());
                TaskDao.updateTaskState(task);
                //���ؽ��
                resp = "{isSuccess:true,message:'�޸�����״̬�ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_CUI.equals(type)){//��
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //�в����ڸ������ܴ�(�κ��˶����Դ߽���)
            if(task == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview = new TaskReview(getUser().getId(), Integer.parseInt(taskId),
                        TaskReviewInterface.TYPE_CUI, 0, StringUtils.EMPTY, date, time, getIp(), StringUtils.EMPTY,
                        StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview);
                //��������ܵ��˷�����Ϣ
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "�и��˴������������������<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">����</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'��������ȳɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE.equals(type)){//ɾ��
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //�в����ڸ����񣬻��ߣ�(ֻ�д������˿���ɾ������) ����ɾ��
            if(task == null || task.getFromUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //ɾ������
                TaskDao.deleteTask(task);
                //ɾ�����б�����������
                List<TaskReview> taskReviewList = TaskReviewDao.queryCuiTaskReviews(task.getId());
                for(TaskReview taskReview : taskReviewList){
                    TaskReviewDao.deleteTaskReview(taskReview);
                }
                //ɾ�����зǱ�����������
                taskReviewList = TaskReviewDao.queryNotCuiTaskReviews(task.getId());
                for(TaskReview taskReview : taskReviewList){
                    TaskReviewDao.deleteTaskReview(taskReview);
                }
                resp = "{isSuccess:true,message:'ɾ������ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_CREATE_REVIEW.equals(type)){//��������
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            //�в����ڸ�����������
            if(task == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview = new TaskReview(getUser().getId(), task.getId(),
                        TaskReviewInterface.TYPE_MESSAGE, 0, content, super.date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview);
                //��������ܵ��˷�����Ϣ
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "�и�����������������������<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">����</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'��������ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_UPDATE_REVIEW.equals(type)){//�޸�����
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(updateReviewId));
            //�в����ڸ����񣬻��ߣ������ڸ��������ۣ����ߣ����������۵����߲��Ǹ������û��������޸�����
            if(task == null || taskReview == null || taskReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                taskReview.setContent(content);
                taskReview.setUpdateDate(super.date);
                taskReview.setUpdateTime(time);
                taskReview.setUpdateIp(getIp());
                TaskReviewDao.updateTaskReview(taskReview);
                resp = "{isSuccess:true,message:'�޸��������۳ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REVIEW.equals(type)){//ɾ������
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(deleteReviewId));
            //�в����ڸ����񣬻��ߣ������ڸ��������ۣ����ߣ����������۵����߲��Ǹ������û��������޸�����
            if(task == null || taskReview == null || taskReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReviewDao.deleteTaskReview(taskReview);
                //��������ܵ��˷�����Ϣ
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "�и���ɾ����������������������<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">����</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'ɾ���������۳ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_REPLY_REVIEW.equals(type)){//�ظ�����
            Task task = TaskDao.getTaskById(Integer.parseInt(taskId));
            TaskReview taskReview = TaskReviewDao.getTaskReviewById(Integer.parseInt(updateReviewId));
            //�в����ڸ����񣬻��ߣ������ڸ��������ۣ����ܻظ�����
            if(task == null || taskReview == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                TaskReview taskReview2 = new TaskReview(getUser().getId(), task.getId(),
                        TaskReviewInterface.TYPE_REPLY, taskReview.getUserId(), content, super.date, time,
                        getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                TaskReviewDao.insertTaskReview(taskReview2);
                //��������ܵ��˷�����Ϣ
                Message message2 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        task.getToUserId(), "�и��˻ظ���������������������<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">����</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message2);
                resp = "{isSuccess:true,message:'�ظ���������ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
                //����ԭʼ�������۵��˷�����Ϣ
                Message message3 = new Message(getUser().getId(), UserInterface.USER_TYPE_NORMAL,
                        taskReview.getUserId(), "�и��˻ظ���������������������<a target=\"_blank\" " +
                        "href=\"/showTask.jsp?id=" + task.getId() + "\">����</>",
                        MessageInterface.STATE_NOT_READED, super.date, time, getIp());
                MessageDao.insertMessage(message3);
                resp = "{isSuccess:true,message:'�ظ���������ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else {
            resp = "{isSuccess:false,message:'������������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUpdateReviewId() {
        return updateReviewId;
    }

    public void setUpdateReviewId(String updateReviewId) {
        this.updateReviewId = updateReviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeleteReviewId() {
        return deleteReviewId;
    }

    public void setDeleteReviewId(String deleteReviewId) {
        this.deleteReviewId = deleteReviewId;
    }
}
