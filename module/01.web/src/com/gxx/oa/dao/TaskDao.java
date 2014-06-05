package com.gxx.oa.dao;

import com.gxx.oa.entities.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ����ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:22
 */
public class TaskDao {
    /**
     * ��ѯ��������
     *
     * @param fromUserId
     * @param toUserId
     * @param state
     * @return
     * @throws Exception
     */
    public static int countTasksByUserIdAndState(int fromUserId, int toUserId, int state) throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM task WHERE 1=1";
        /**
         * ���fromUserId����0���ϸ�����
         */
        if(fromUserId > 0){
            sql += " AND from_user_id=" + fromUserId;
        }
        /**
         * ���toUserId����0���ϸ�����
         */
        if(toUserId > 0){
            sql += " AND to_user_id=" + toUserId;
        }
        /**
         * ���state����0���ϸ�����
         */
        if(state > 0){
            sql += " AND state=" + state;
        }
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                countNum = rs.getInt("count_num");
            }
            return countNum;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����������Դ�û�id,��������û�id,״̬,��Χ������
     * ע�⣺���fromUserId����0���ϸ�����
     * ע�⣺���toUserId����0���ϸ�����
     * ע�⣺���state����0���ϸ�����
     *
     * @param fromUserId
     * @param toUserId
     * @param state
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    public static List<Task> queryTasksByFromTo(int fromUserId, int toUserId, int state, int from, int to)
            throws Exception {
        List<Task> list = new ArrayList<Task>();
        String sql = "SELECT id,from_user_id,to_user_id,title,content,state,begin_date,end_date," +
                "create_date,create_time,create_ip,update_date,update_time,update_ip FROM task WHERE 1=1";
        /**
         * ���fromUserId����0���ϸ�����
         */
        if(fromUserId > 0){
            sql += " AND from_user_id=" + fromUserId;
        }
        /**
         * ���toUserId����0���ϸ�����
         */
        if(toUserId > 0){
            sql += " AND to_user_id=" + toUserId;
        }
        /**
         * ���state����0���ϸ�����
         */
        if(state > 0){
            sql += " AND state=" + state;
        }
        sql += " ORDER BY id DESC limit " + from + "," + to;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                fromUserId = rs.getInt("from_user_id");
                toUserId = rs.getInt("to_user_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                state = rs.getInt("state");
                String beginDate = rs.getString("begin_date");
                String endDate = rs.getString("end_date");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Task task = new Task(id, fromUserId, toUserId, title, content, state, beginDate, endDate, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
                list.add(task);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id������
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Task getTaskById(int id) throws Exception {
        Task task = null;
        String sql = "SELECT from_user_id,to_user_id,title,content,state,begin_date,end_date," +
                "create_date,create_time,create_ip,update_date,update_time,update_ip FROM task WHERE " +
                "id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int fromUserId = rs.getInt("from_user_id");
                int toUserId = rs.getInt("to_user_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                int state = rs.getInt("state");
                String beginDate = rs.getString("begin_date");
                String endDate = rs.getString("end_date");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                task = new Task(id, fromUserId, toUserId, title, content, state, beginDate, endDate, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
            }
            return task;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��������
     *
     * @param task
     * @throws Exception
     */
    public static void insertTask(Task task) throws Exception {
        String sql = "insert into task(id,from_user_id,to_user_id,title,content,state,begin_date," +
                "end_date,create_date,create_time,create_ip,update_date,update_time,update_ip) values " +
                "(null," + task.getFromUserId() + "," + task.getToUserId() + ",'" + task.getTitle() + "','" +
                task.getContent() + "'," + task.getState() + ",'" + task.getBeginDate() + "','" + task.getEndDate() +
                "','" + task.getCreateDate() + "','" + task.getCreateTime() + "','" + task.getCreateIp() + "','" +
                task.getUpdateDate() + "','" + task.getUpdateTime() + "','" + task.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ��������(�����޸�״̬���޸�״̬������ķ���)
     *
     * @param task
     * @throws Exception
     */
    public static void updateTask(Task task) throws Exception {
        String sql = "update task set to_user_id=" + task.getToUserId() + ",title='" + task.getTitle() +
                "',content='" + task.getContent() + "',begin_date='" + task.getBeginDate() + "',end_date='" +
                task.getEndDate() + "',update_date='" + task.getUpdateDate() + "',update_time='" +
                task.getUpdateTime() + "',update_ip='" + task.getUpdateIp() + "' where id=" + task.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ��������״̬
     *
     * @param task
     * @throws Exception
     */
    public static void updateTaskState(Task task) throws Exception {
        String sql = "update task set state=" + task.getState() + ",update_date='" + task.getUpdateDate() +
                "',update_time='" + task.getUpdateTime() + "',update_ip='" + task.getUpdateIp() +
                "' where id=" + task.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ������
     *
     * @param task
     * @throws Exception
     */
    public static void deleteTask(Task task) throws Exception {
        String sql = "delete from task where id=" + task.getId();
        DB.executeUpdate(sql);
    }
}