package com.gxx.oa.interfaces;

/**
 * ������־�ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface OperateLogInterface extends BaseInterface {
    /**
     * 1:��½ 2:�˳� 3:�޸����� 4:�ϴ�ͷ�� 5:�޸���Ϣ 6:���˹������ 7:������Ϣ���� 8:дվ���� 9:վ���Ź���
     * 10:������־���� 11:д������־ 12:�޸Ĺ�����־ 13:���ѹ��� 14:������� 15:д���� 16:�޸����� 17:���Ź���
     * 18:�����û� 19:��ѯ�û� 20:�޸��û� 21:��֯�ܹ����� 22:������� 23:�޸�Ĭ��Ȩ�� 24:��ȡ�û�Ȩ�� 25:�޸��û�Ȩ��
     * 26:��������ļ��ϴ� 27:������̼����ļ��� 28:��������½��ļ��� 29:�������ɾ���ļ�(��)
     * 30:��������������ļ�(��) 31:������̻�ԭ�ļ�(��) 32:������̳���ɾ���ļ�(��) 33:���������ջ���վ
     * 34:����Ŀ��ϴ��ĵ� 35:����Ŀ��޸��ĵ� 36:����Ŀ�ɾ���ĵ� 37:���֪������ 38:���֪���ش� 39:���֪���޸�����
     * 40:���֪��ɾ������ 41:���֪��ɾ���ش� 42:���֪���޻ش�
     */
    public static final int TYPE_LOG_IN = 1;
    public static final int TYPE_LOG_OUT = 2;
    public static final int TYPE_UPDATE_PASSWORD = 3;
    public static final int TYPE_UPLOAD_HEAD_PHOTO = 4;
    public static final int TYPE_UPDATE_INFO = 5;
    public static final int TYPE_OPERATE_USER_NOTICE = 6;
    public static final int TYPE_OPERATE_MESSAGE = 7;
    public static final int TYPE_WRITE_LETTER = 8;
    public static final int TYPE_OPERATE_LETTER = 9;
    public static final int TYPE_OPERATE_DIARY = 10;
    public static final int TYPE_WRITE_DIARY = 11;
    public static final int TYPE_UPDATE_DIARY = 12;
    public static final int TYPE_OPERATE_REMIND = 13;
    public static final int TYPE_OPERATE_TASK = 14;
    public static final int TYPE_WRITE_TASK = 15;
    public static final int TYPE_UPDATE_TASK = 16;
    public static final int TYPE_OPERATE_SMS = 17;
    public static final int TYPE_CREATE_USER = 18;
    public static final int TYPE_QUERY_USER = 19;
    public static final int TYPE_UPDATE_USER = 20;
    public static final int TYPE_MANAGE_ORG_STRUCTURE = 21;
    public static final int TYPE_CONFIG_NOTICE = 22;
    public static final int TYPE_UPDATE_DEFAULT_RIGHT = 23;
    public static final int TYPE_READ_USER_RIGHT = 24;
    public static final int TYPE_UPDATE_USER_RIGHT = 25;
    public static final int TYPE_CLOUD_UPLOAD = 26;
    public static final int TYPE_CLOUD_LOAD_DIR = 27;
    public static final int TYPE_CLOUD_NEW_DIR = 28;
    public static final int TYPE_CLOUD_DELETE_FILE = 29;
    public static final int TYPE_CLOUD_RENAME = 30;
    public static final int TYPE_CLOUD_RECOVER = 31;
    public static final int TYPE_CLOUD_CTRL_DELETE = 32;
    public static final int TYPE_CLOUD_CLEAR_RECYCLE = 33;
    public static final int TYPE_CLOUD_UPLOAD_DOC = 34;
    public static final int TYPE_CLOUD_UPDATE_DOC = 35;
    public static final int TYPE_CLOUD_DELETE_DOC = 36;
    public static final int TYPE_CLOUD_KNOW_ASK = 37;
    public static final int TYPE_CLOUD_KNOW_ANSWER = 38;
    public static final int TYPE_CLOUD_KNOW_UPDATE_ASK = 39;
    public static final int TYPE_CLOUD_KNOW_DELETE_ASK = 40;
    public static final int TYPE_CLOUD_KNOW_DELETE_ANSWER = 41;
    public static final int TYPE_CLOUD_KNOW_ZAN_ANSWER = 42;
}
