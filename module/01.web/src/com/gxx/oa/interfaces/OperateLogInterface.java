package com.gxx.oa.interfaces;

/**
 * 操作日志接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface OperateLogInterface extends BaseInterface {
    /**
     * 1:登陆 2:退出 3:修改密码 4:上传头像 5:修改信息 6:个人公告管理 7:个人消息管理 8:写站内信 9:站内信管理
     * 10:工作日志管理 11:写工作日志 12:修改工作日志 13:提醒管理 14:任务管理 15:写任务 16:修改任务 17:短信管理
     * 18:创建用户 19:查询用户 20:修改用户 21:组织架构管理 22:公告管理 23:修改默认权限 24:读取用户权限 25:修改用户权限
     * 26:申成网盘文件上传 27:申成网盘加载文件夹 28:申成网盘新建文件夹 29:申成网盘删除文件(夹)
     * 30:申成网盘重命名文件(夹) 31:申成网盘还原文件(夹) 32:申成网盘彻底删除文件(夹) 33:申成网盘清空回收站
     * 34:申成文库上传文档 35:申成文库修改文档 36:申成文库删除文档 37:申成知道提问 38:申成知道回答 39:申成知道修改提问
     * 40:申成知道删除提问 41:申成知道删除回答 42:申成知道赞回答
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
