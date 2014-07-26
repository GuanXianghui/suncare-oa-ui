package com.gxx.oa.dao;

import com.gxx.oa.entities.OperateLog;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.utils.PropertyUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ������־ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 20:22
 */
public class OperateLogDao {
    /**
     * ��������ģ����ѯ������־
     *
     * @param userId
     * @param type
     * @param date
     * @param pageNum
     * @return
     * @throws Exception
     */
    public static List<OperateLog> queryOperateLogsByLike(int userId, int type, String date, int pageNum) throws Exception {
        //������־�б�ÿҳ��С
        int pageSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.OPERATE_LOG_PAGE_SIZE));
        //������־����
        List<OperateLog> list = new ArrayList<OperateLog>();
        //sql���
        String sql = "SELECT id,user_id,type,content,date,time,ip FROM operate_log WHERE 1=1";
        //���userId>0����Ϊ����
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        //���type>0����Ϊ����
        if(type > 0){
            sql += " AND type=" + type;
        }
        //���date�ǿմ���Ϊ����
        if(StringUtils.isNotBlank(date)){
            sql += " AND date='" + date + "'";
        }
        sql += " ORDER BY id DESC LIMIT " + ((pageNum-1) * pageSize) + "," + pageSize;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId2 = rs.getInt("user_id");
                int type2 = rs.getInt("type");
                String content = rs.getString("content");
                String date2 = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                OperateLog operateLog = new OperateLog(id, userId2, type2, content, date2, time, ip);
                list.add(operateLog);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��������ģ����ѯ������־����
     *
     * @param userId
     * @param type
     * @param date
     * @return
     * @throws Exception
     */
    public static int countOperateLogsByLike(int userId, int type, String date) throws Exception {
        //���
        int count = 0;
        //sql���
        String sql = "SELECT count(1) count_num FROM operate_log WHERE 1=1";
        //���userId>0����Ϊ����
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        //���type>0����Ϊ����
        if(type > 0){
            sql += " AND type=" + type;
        }
        //���date�ǿմ���Ϊ����
        if(StringUtils.isNotBlank(date)){
            sql += " AND date='" + date + "'";
        }
        sql += " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                count = rs.getInt("count_num");
            }
            return count;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id�������־
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static OperateLog getOperateLogById(int id) throws Exception {
        String sql = "SELECT id,user_id,type,content,date,time,ip FROM operate_log WHERE id=" + id + " order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int type = rs.getInt("type");
                String content = rs.getString("content");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                OperateLog operateLog = new OperateLog(id, userId, type, content, date, time, ip);
                return operateLog;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����������־
     *
     * @param operateLog
     * @throws Exception
     */
    public static void insertOperateLog(OperateLog operateLog) throws Exception {
        String sql = "insert into operate_log" +
                "(id,user_id,type,content,date,time,ip)" +
                "values" +
                "(null," + operateLog.getUserId() + "," + operateLog.getType() + ",'" + operateLog.getContent() +
                "','" + operateLog.getDate() + "','" + operateLog.getTime() + "','" + operateLog.getIp() + "')";
        DB.executeUpdate(sql);
    }
}