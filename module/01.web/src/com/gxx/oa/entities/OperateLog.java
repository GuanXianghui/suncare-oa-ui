package com.gxx.oa.entities;

import com.gxx.oa.interfaces.OperateLogInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 操作日志实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 18:21
 */
public class OperateLog implements OperateLogInterface {
    int id;
    /**
     * 一旦用户有交互，就不能删除用户，最多将用户置成离职
     */
    int userId;
    /**
     * 数据字典在接口类中描述+维护
     */
    int type;
    String content;
    String date;
    String time;
    String ip;

    /**
     * 新增时使用
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
     * 查询时使用
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
     * 返回操作类型描述
     * 1:登陆 2:退出 3:修改密码 4:上传头像 5:修改信息 6:个人公告管理 7:个人消息管理 8:写站内信 9:站内信管理
     * 10:工作日志管理 11:写工作日志 12:修改工作日志 13:提醒管理 14:任务管理 15:写任务 16:修改任务 17:短信管理
     * 18:创建用户 19:查询用户 20:修改用户 21:组织架构管理 22:公告管理 23:修改默认权限 24:读取用户权限 25:修改用户权限
     * 26:申成网盘文件上传 27:申成网盘加载文件夹 28:申成网盘新建文件夹 29:申成网盘删除文件(夹)
     * 30:申成网盘重命名文件(夹) 31:申成网盘还原文件(夹) 32:申成网盘彻底删除文件(夹) 33:申成网盘清空回收站
     * 34:申成文库上传文档 35:申成文库修改文档 36:申成文库删除文档 37:申成知道提问 38:申成知道回答 39:申成知道修改提问
     * 40:申成知道删除提问 41:申成知道删除回答 42:申成知道赞回答 43:申成币变动 44:申成文库下载文档 45:申成网盘移动目录
     * @return
     */
    public String getTypeDesc(){
        String typeDesc = StringUtils.EMPTY;
         if(type == TYPE_LOG_IN){
            typeDesc = "登陆";
        } else if(type == TYPE_LOG_OUT){
            typeDesc = "退出";
        } else if(type == TYPE_UPDATE_PASSWORD){
            typeDesc = "修改密码";
        } else if(type == TYPE_UPLOAD_HEAD_PHOTO){
            typeDesc = "上传头像";
        } else if(type == TYPE_UPDATE_INFO){
             typeDesc = "修改信息";
         } else if(type == TYPE_OPERATE_USER_NOTICE){
             typeDesc = "个人公告管理";
         } else if(type == TYPE_OPERATE_MESSAGE){
             typeDesc = "个人消息管理";
         } else if(type == TYPE_WRITE_LETTER){
             typeDesc = "写站内信";
         } else if(type == TYPE_OPERATE_LETTER){
             typeDesc = "站内信管理";
         } else if(type == TYPE_OPERATE_DIARY){
             typeDesc = "工作日志管理";
         } else if(type == TYPE_WRITE_DIARY){
             typeDesc = "写工作日志";
         } else if(type == TYPE_UPDATE_DIARY){
             typeDesc = "修改工作日志";
         } else if(type == TYPE_OPERATE_REMIND){
             typeDesc = "提醒管理";
         } else if(type == TYPE_OPERATE_TASK){
             typeDesc = "任务管理";
         } else if(type == TYPE_WRITE_TASK){
             typeDesc = "写任务";
         } else if(type == TYPE_UPDATE_TASK){
             typeDesc = "修改任务";
         } else if(type == TYPE_OPERATE_SMS){
             typeDesc = "短信管理";
         } else if(type == TYPE_CREATE_USER){
             typeDesc = "创建用户";
         } else if(type == TYPE_QUERY_USER){
             typeDesc = "查询用户";
         } else if(type == TYPE_UPDATE_USER){
             typeDesc = "修改用户";
         } else if(type == TYPE_MANAGE_ORG_STRUCTURE){
             typeDesc = "组织架构管理";
         } else if(type == TYPE_CONFIG_NOTICE){
             typeDesc = "公告管理";
         } else if(type == TYPE_UPDATE_DEFAULT_RIGHT){
             typeDesc = "修改默认权限";
         } else if(type == TYPE_READ_USER_RIGHT){
             typeDesc = "读取用户权限";
         } else if(type == TYPE_UPDATE_USER_RIGHT){
             typeDesc = "修改用户权限";
         } else if(type == TYPE_CLOUD_UPLOAD){
             typeDesc = "申成网盘文件上传";
         } else if(type == TYPE_CLOUD_LOAD_DIR){
             typeDesc = "申成网盘加载文件夹";
         } else if(type == TYPE_CLOUD_NEW_DIR){
             typeDesc = "申成网盘新建文件夹";
         } else if(type == TYPE_CLOUD_DELETE_FILE){
             typeDesc = "申成网盘删除文件(夹)";
         } else if(type == TYPE_CLOUD_RENAME){
             typeDesc = "申成网盘重命名文件(夹)";
         } else if(type == TYPE_CLOUD_RECOVER){
             typeDesc = "申成网盘还原文件(夹)";
         } else if(type == TYPE_CLOUD_CTRL_DELETE){
             typeDesc = "申成网盘彻底删除文件(夹)";
         } else if(type == TYPE_CLOUD_CLEAR_RECYCLE){
             typeDesc = "申成网盘清空回收站";
         } else if(type == TYPE_CLOUD_UPLOAD_DOC){
             typeDesc = "申成文库上传文档";
         } else if(type == TYPE_CLOUD_UPDATE_DOC){
             typeDesc = "申成文库修改文档";
         } else if(type == TYPE_CLOUD_DELETE_DOC){
             typeDesc = "申成文库删除文档";
         } else if(type == TYPE_CLOUD_KNOW_ASK){
             typeDesc = "申成知道提问";
         } else if(type == TYPE_CLOUD_KNOW_ANSWER){
             typeDesc = "申成知道回答";
         } else if(type == TYPE_CLOUD_KNOW_UPDATE_ASK){
             typeDesc = "申成知道修改提问";
         } else if(type == TYPE_CLOUD_KNOW_DELETE_ASK){
             typeDesc = "申成知道删除提问";
         } else if(type == TYPE_CLOUD_KNOW_DELETE_ANSWER){
             typeDesc = "申成知道删除回答";
         } else if(type == TYPE_CLOUD_KNOW_ZAN_ANSWER){
             typeDesc = "申成知道赞回答";
         } else if(type == TYPE_SUNCARE_MONEY_CHANGE){
             typeDesc = "申成币变动";
         } else if(type == TYPE_CLOUD_DOWNLOAD_DOC){
             typeDesc = "申成文库下载文档";
         } else if(type == TYPE_CLOUD_MOVE_TO_DIR){
             typeDesc = "申成网盘移动目录";
         }
        return typeDesc;
    }
}