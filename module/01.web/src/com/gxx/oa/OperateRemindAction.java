package com.gxx.oa;

import com.gxx.oa.dao.RemindDao;
import com.gxx.oa.entities.Remind;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

/**
 * ��������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 16:14
 */
public class OperateRemindAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_QUERY = "query";//��ѯ
    private static final String TYPE_INSERT_REMIND = "insertRemind";//��������
    private static final String TYPE_UPDATE_REMIND = "updateRemind";//�޸�����
    private static final String TYPE_DELETE_REMIND = "deleteRemind";//ɾ������

    /**
     * ��������
     */
    String type;//��������
    String date;//����
    String content;//��������
    String remindType;//��������
    String remindDateTime;//����ʱ��
    String updateRemindId;//�޸�����id
    String deleteRemindId;//ɾ������id

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",date=" + date);
        //ajax���
        String resp;
        if(TYPE_QUERY.equals(type)){//��ѯ
            //�����û�id�����ڲ�����
            String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                    (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_REMIND, "���ѹ��� ��ѯ���ѳɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'��ѯ���ѳɹ���',remindsJson:'" + remindsJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_INSERT_REMIND.equals(type)){//��������
            //��������
            Remind remind = new Remind(getUser().getId(), date, content, Integer.parseInt(remindType),
                    remindDateTime, StringUtils.EMPTY, super.date, time, getIp(), StringUtils.EMPTY,
                    StringUtils.EMPTY, StringUtils.EMPTY);
            RemindDao.insertRemind(remind);
            //�����û�id�����ڲ�����
            String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                    (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_REMIND, "���ѹ��� �������ѳɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'�������ѳɹ���',remindsJson:'" + remindsJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_UPDATE_REMIND.equals(type)){//�޸�����
            //��������
            Remind remind = RemindDao.getRemindById(Integer.parseInt(updateRemindId));
            if(null == remind || remind.getUserId() != getUser().getId()){
                //���ؽ��
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                remind.setDate(date);
                remind.setContent(content);
                remind.setRemindType(Integer.parseInt(remindType));
                remind.setRemindDateTime(remindDateTime);
                remind.setUpdateDate(super.date);
                remind.setUpdateTime(time);
                remind.setUpdateIp(getIp());
                RemindDao.updateRemind(remind);
                //�����û�id�����ڲ�����
                String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                        (getUser().getId(), date)).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_REMIND, "���ѹ��� �޸����ѳɹ���", date, time, getIp());

                //���ؽ��
                resp = "{isSuccess:true,message:'�޸����ѳɹ���',remindsJson:'" + remindsJson +
                        "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REMIND.equals(type)){//ɾ������
            //��������
            Remind remind = RemindDao.getRemindById(Integer.parseInt(deleteRemindId));
            if(null == remind || remind.getUserId() != getUser().getId()){
                //���ؽ��
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                RemindDao.deleteRemind(remind);
                //�����û�id�����ڲ�����
                String remindsJson = BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndDate
                        (getUser().getId(), remind.getDate())).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"");

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_REMIND, "���ѹ��� ȡ�����ѳɹ���", date, time, getIp());

                //���ؽ��
                resp = "{isSuccess:true,message:'ȡ�����ѳɹ���',remindsJson:'" + remindsJson +
                        "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
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

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRemindDateTime() {
        return remindDateTime;
    }

    public void setRemindDateTime(String remindDateTime) {
        this.remindDateTime = remindDateTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdateRemindId() {
        return updateRemindId;
    }

    public void setUpdateRemindId(String updateRemindId) {
        this.updateRemindId = updateRemindId;
    }

    public String getDeleteRemindId() {
        return deleteRemindId;
    }

    public void setDeleteRemindId(String deleteRemindId) {
        this.deleteRemindId = deleteRemindId;
    }
}
