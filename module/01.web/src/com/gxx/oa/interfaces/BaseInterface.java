package com.gxx.oa.interfaces;

/**
 * 基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface BaseInterface {
    /**
     * mysql数据库链接
     */
    public static final String MYSQL_CONNECTION = "mysql_connection";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "default_password";
    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_PHOTO = "default_head_photo";
    /**
     * md5 key
     */
    public static final String MD5_KEY = "md5_key";
    /**
     * session缓存中的token集合
     */
    public static final String SESSION_TOKEN_LIST = "session_token_list";
    /**
     * TOKEN键
     */
    public static final String TOKEN_KEY = "token";
    /**
     * 用户键
     */
    public static final String USER_KEY = "user";
    /**
     * 用户权限键
     */
    public static final String USER_RIGHT_KEY = "user_right";
    /**
     * 公告页面大小
     */
    public static final String NOTICE_PAGE_SIZE = "notice_page_size";
    /**
     * uuid配置
     * UE的代码功能，每行都是换行的，写到json里会有问题，所以把换行先转换成一个uuid串，展示之前再转回来
     * 注意：该标志要与客户端base.js中配置一致
     */
    public static final String GXX_OA_NEW_LINE_UUID = "gxx_oa_new_line_uuid";
    /**
     * 消息页面大小
     */
    public static final String MESSAGE_PAGE_SIZE = "message_page_size";
    /**
     * 站内信页面大小
     */
    public static final String LETTER_PAGE_SIZE = "letter_page_size";
    /**
     * 工作日志页面大小
     */
    public static final String DIARY_PAGE_SIZE = "diary_page_size";
    /**
     * 任务页面大小
     */
    public static final String TASK_PAGE_SIZE = "task_page_size";
    /**
     * 操作日志页面大小
     */
    public static final String OPERATE_LOG_PAGE_SIZE = "operate_log_page_size";
    /**
     * 短信服务相关配置
     * 短信服务用户id
     */
    public static final String SMS_UID = "sms_uid";
    /**
     * 短信服务相关配置
     * 短信服务key
     */
    public static final String SMS_KEY = "sms_key";
    /**
     * 短信服务相关配置
     * 短信日数量限制
     */
    public static final String SMS_DAY_LIMIT = "sms_day_limit";
    /**
     * 所有权限 权限代码1=权限名称1,权限代码2=权限名称2,权限代码3=权限名称3,...
     */
    public static final String RIGHT_LIST = "right_list";
    /**
     * 申成文库支持的文件类型
     */
    public static final String CLOUD_DOC_SUPPORT_TYPES = "cloud_doc_support_types";
    /**
     * 短信运营商_发送短信屏蔽词汇
     */
    public static final String SMS_DENIED_WORDS = "sms_denied_words";
    /**
     * 初始化申成币
     * 创建用户时候，初始化送用户10个申成币
     */
    public static final String INIT_SUNCARE_MONEY = "init_suncare_money";
    /**
     * 对外通讯 邮件服务地址
     */
    public static final String EMAIL_HOST = "email_host";
    /**
     * 对外通讯 邮件服务端口
     */
    public static final String EMAIL_PORT = "email_port";
    /**
     * 对外通讯 邮件名
     */
    public static final String EMAIL_NAME = "email_name";
    /**
     * 对外通讯 邮件密码
     */
    public static final String EMAIL_PASSWORD = "email_password";
    /**
     * 群发邮件批次大小 即一批可以群发多少个邮件
     */
    public static final String EMAIL_FETCH_SIZE = "email_fetch_size";
    /**
     * 管理员邮箱，用途：群发邮件时带上管理员邮箱，查看管理员邮箱是否收到邮件，用于检测该批次是否被邮件服务器屏蔽
     */
    public static final String EMAIL_ADMINISTRATOR = "email_administrator";
}
