package com.gxx.oa.entities;

import com.gxx.oa.interfaces.OperateLogInterface;
import org.apache.commons.lang.StringUtils;

/**
 * ������־ʵ��
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 18:21
 */
public class OperateLog implements OperateLogInterface {
    int id;
    /**
     * һ���û��н������Ͳ���ɾ���û�����ཫ�û��ó���ְ
     */
    int userId;
    /**
     * �����ֵ��ڽӿ���������+ά��
     */
    int type;
    String content;
    String date;
    String time;
    String ip;

    /**
     * ����ʱʹ��
     * @param userId
     * @param type
     * @param content
     * @param date
     * @param time
     * @param ip
     */
    public OperateLog(int userId, int type, String content, String date, String time, String ip) {
        this.userId = userId;
        this.type = type;
        this.content = content;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    /**
     * ��ѯʱʹ��
     * @param id
     * @param userId
     * @param type
     * @param content
     * @param date
     * @param time
     * @param ip
     */
    public OperateLog(int id, int userId, int type, String content, String date, String time, String ip) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.content = content;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * ���ز�����������
     * 1:��½ 2:�˳� 3:�޸����� 4:�ϴ�ͷ�� 5:�޸���Ϣ 6:���˹������ 7:������Ϣ���� 8:дվ���� 9:վ���Ź���
     * 10:������־���� 11:д������־ 12:�޸Ĺ�����־ 13:���ѹ��� 14:������� 15:д���� 16:�޸����� 17:���Ź���
     * 18:�����û� 19:��ѯ�û� 20:�޸��û� 21:��֯�ܹ����� 22:������� 23:�޸�Ĭ��Ȩ�� 24:��ȡ�û�Ȩ�� 25:�޸��û�Ȩ��
     * 26:��������ļ��ϴ� 27:������̼����ļ��� 28:��������½��ļ��� 29:�������ɾ���ļ�(��)
     * 30:��������������ļ�(��) 31:������̻�ԭ�ļ�(��) 32:������̳���ɾ���ļ�(��) 33:���������ջ���վ
     * 34:����Ŀ��ϴ��ĵ� 35:����Ŀ��޸��ĵ� 36:����Ŀ�ɾ���ĵ� 37:���֪������ 38:���֪���ش� 39:���֪���޸�����
     * 40:���֪��ɾ������ 41:���֪��ɾ���ش� 42:���֪���޻ش� 43:��ɱұ䶯 44:����Ŀ������ĵ� 45:��������ƶ�Ŀ¼
     * @return
     */
    public String getTypeDesc(){
        String typeDesc = StringUtils.EMPTY;
         if(type == TYPE_LOG_IN){
            typeDesc = "��½";
        } else if(type == TYPE_LOG_OUT){
            typeDesc = "�˳�";
        } else if(type == TYPE_UPDATE_PASSWORD){
            typeDesc = "�޸�����";
        } else if(type == TYPE_UPLOAD_HEAD_PHOTO){
            typeDesc = "�ϴ�ͷ��";
        } else if(type == TYPE_UPDATE_INFO){
             typeDesc = "�޸���Ϣ";
         } else if(type == TYPE_OPERATE_USER_NOTICE){
             typeDesc = "���˹������";
         } else if(type == TYPE_OPERATE_MESSAGE){
             typeDesc = "������Ϣ����";
         } else if(type == TYPE_WRITE_LETTER){
             typeDesc = "дվ����";
         } else if(type == TYPE_OPERATE_LETTER){
             typeDesc = "վ���Ź���";
         } else if(type == TYPE_OPERATE_DIARY){
             typeDesc = "������־����";
         } else if(type == TYPE_WRITE_DIARY){
             typeDesc = "д������־";
         } else if(type == TYPE_UPDATE_DIARY){
             typeDesc = "�޸Ĺ�����־";
         } else if(type == TYPE_OPERATE_REMIND){
             typeDesc = "���ѹ���";
         } else if(type == TYPE_OPERATE_TASK){
             typeDesc = "�������";
         } else if(type == TYPE_WRITE_TASK){
             typeDesc = "д����";
         } else if(type == TYPE_UPDATE_TASK){
             typeDesc = "�޸�����";
         } else if(type == TYPE_OPERATE_SMS){
             typeDesc = "���Ź���";
         } else if(type == TYPE_CREATE_USER){
             typeDesc = "�����û�";
         } else if(type == TYPE_QUERY_USER){
             typeDesc = "��ѯ�û�";
         } else if(type == TYPE_UPDATE_USER){
             typeDesc = "�޸��û�";
         } else if(type == TYPE_MANAGE_ORG_STRUCTURE){
             typeDesc = "��֯�ܹ�����";
         } else if(type == TYPE_CONFIG_NOTICE){
             typeDesc = "�������";
         } else if(type == TYPE_UPDATE_DEFAULT_RIGHT){
             typeDesc = "�޸�Ĭ��Ȩ��";
         } else if(type == TYPE_READ_USER_RIGHT){
             typeDesc = "��ȡ�û�Ȩ��";
         } else if(type == TYPE_UPDATE_USER_RIGHT){
             typeDesc = "�޸��û�Ȩ��";
         } else if(type == TYPE_CLOUD_UPLOAD){
             typeDesc = "��������ļ��ϴ�";
         } else if(type == TYPE_CLOUD_LOAD_DIR){
             typeDesc = "������̼����ļ���";
         } else if(type == TYPE_CLOUD_NEW_DIR){
             typeDesc = "��������½��ļ���";
         } else if(type == TYPE_CLOUD_DELETE_FILE){
             typeDesc = "�������ɾ���ļ�(��)";
         } else if(type == TYPE_CLOUD_RENAME){
             typeDesc = "��������������ļ�(��)";
         } else if(type == TYPE_CLOUD_RECOVER){
             typeDesc = "������̻�ԭ�ļ�(��)";
         } else if(type == TYPE_CLOUD_CTRL_DELETE){
             typeDesc = "������̳���ɾ���ļ�(��)";
         } else if(type == TYPE_CLOUD_CLEAR_RECYCLE){
             typeDesc = "���������ջ���վ";
         } else if(type == TYPE_CLOUD_UPLOAD_DOC){
             typeDesc = "����Ŀ��ϴ��ĵ�";
         } else if(type == TYPE_CLOUD_UPDATE_DOC){
             typeDesc = "����Ŀ��޸��ĵ�";
         } else if(type == TYPE_CLOUD_DELETE_DOC){
             typeDesc = "����Ŀ�ɾ���ĵ�";
         } else if(type == TYPE_CLOUD_KNOW_ASK){
             typeDesc = "���֪������";
         } else if(type == TYPE_CLOUD_KNOW_ANSWER){
             typeDesc = "���֪���ش�";
         } else if(type == TYPE_CLOUD_KNOW_UPDATE_ASK){
             typeDesc = "���֪���޸�����";
         } else if(type == TYPE_CLOUD_KNOW_DELETE_ASK){
             typeDesc = "���֪��ɾ������";
         } else if(type == TYPE_CLOUD_KNOW_DELETE_ANSWER){
             typeDesc = "���֪��ɾ���ش�";
         } else if(type == TYPE_CLOUD_KNOW_ZAN_ANSWER){
             typeDesc = "���֪���޻ش�";
         } else if(type == TYPE_SUNCARE_MONEY_CHANGE){
             typeDesc = "��ɱұ䶯";
         } else if(type == TYPE_CLOUD_DOWNLOAD_DOC){
             typeDesc = "����Ŀ������ĵ�";
         } else if(type == TYPE_CLOUD_MOVE_TO_DIR){
             typeDesc = "��������ƶ�Ŀ¼";
         }
        return typeDesc;
    }
}