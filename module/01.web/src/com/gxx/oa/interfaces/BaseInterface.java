package com.gxx.oa.interfaces;

/**
 * �����ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface BaseInterface {
    /**
     * mysql���ݿ�����
     */
    public static final String MYSQL_CONNECTION = "mysql_connection";
    /**
     * Ĭ������
     */
    public static final String DEFAULT_PASSWORD = "default_password";
    /**
     * Ĭ��ͷ��
     */
    public static final String DEFAULT_HEAD_PHOTO = "default_head_photo";
    /**
     * md5 key
     */
    public static final String MD5_KEY = "md5_key";
    /**
     * session�����е�token����
     */
    public static final String SESSION_TOKEN_LIST = "session_token_list";
    /**
     * TOKEN��
     */
    public static final String TOKEN_KEY = "token";
    /**
     * �û���
     */
    public static final String USER_KEY = "user";
    /**
     * �û�Ȩ�޼�
     */
    public static final String USER_RIGHT_KEY = "user_right";
    /**
     * ����ҳ���С
     */
    public static final String NOTICE_PAGE_SIZE = "notice_page_size";
    /**
     * uuid����
     * UE�Ĵ��빦�ܣ�ÿ�ж��ǻ��еģ�д��json��������⣬���԰ѻ�����ת����һ��uuid����չʾ֮ǰ��ת����
     * ע�⣺�ñ�־Ҫ��ͻ���base.js������һ��
     */
    public static final String GXX_OA_NEW_LINE_UUID = "gxx_oa_new_line_uuid";
    /**
     * ��Ϣҳ���С
     */
    public static final String MESSAGE_PAGE_SIZE = "message_page_size";
    /**
     * վ����ҳ���С
     */
    public static final String LETTER_PAGE_SIZE = "letter_page_size";
    /**
     * ������־ҳ���С
     */
    public static final String DIARY_PAGE_SIZE = "diary_page_size";
    /**
     * ����ҳ���С
     */
    public static final String TASK_PAGE_SIZE = "task_page_size";
    /**
     * ������־ҳ���С
     */
    public static final String OPERATE_LOG_PAGE_SIZE = "operate_log_page_size";
    /**
     * ���ŷ����������
     * ���ŷ����û�id
     */
    public static final String SMS_UID = "sms_uid";
    /**
     * ���ŷ����������
     * ���ŷ���key
     */
    public static final String SMS_KEY = "sms_key";
    /**
     * ���ŷ����������
     * ��������������
     */
    public static final String SMS_DAY_LIMIT = "sms_day_limit";
    /**
     * ����Ȩ�� Ȩ�޴���1=Ȩ������1,Ȩ�޴���2=Ȩ������2,Ȩ�޴���3=Ȩ������3,...
     */
    public static final String RIGHT_LIST = "right_list";
    /**
     * ����Ŀ�֧�ֵ��ļ�����
     */
    public static final String CLOUD_DOC_SUPPORT_TYPES = "cloud_doc_support_types";
    /**
     * ������Ӫ��_���Ͷ������δʻ�
     */
    public static final String SMS_DENIED_WORDS = "sms_denied_words";
}
