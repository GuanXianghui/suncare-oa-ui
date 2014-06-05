package com.gxx.oa.dao;

import com.gxx.oa.entities.Diary;
import com.gxx.oa.entities.Notice;
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
 * @datetime 14-4-7 17:21
 */
public class DiaryDao {
    /**
     * ��ѯ��־����
     * ע�⣺���rightUserWithComma(��Ȩ�޿����¼��û�)�ǿ�����ϸ�����
     *
     * @param userId
     * @param date
     * @param rightUserWithComma ��Ȩ�޿����¼��û�
     * @return
     * @throws Exception
     */
    public static int countDiaries(int userId, String date, String rightUserWithComma) throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM diary WHERE 1=1";
        /**
         * ��� ��Ȩ�޿����¼��û� rightUserWithComma �ǿ� ����ϸ�����
         */
        if(StringUtils.isNotBlank(rightUserWithComma)){
            sql += " AND user_id in (" + rightUserWithComma + ")";
        }
        /**
         * ���userId����0���ϸ�����
         */
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        /**
         * ���date�ǿմ��ϸ�����
         */
        if(StringUtils.isNotBlank(date)){
            sql += " AND date='" + date + "'";
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
     * �����û�id�����ڣ���Χ����־
     * ע�⣺���userId����0���ϸ�����
     * ע�⣺���date�ǿմ��ϸ�����
     * ע�⣺���rightUserWithComma(��Ȩ�޿����¼��û�)�ǿ�����ϸ�����
     *
     * @param userId
     * @param date
     * @param from
     * @param to
     * @param rightUserWithComma ��Ȩ�޿����¼��û�
     * @return
     * @throws Exception
     */
    public static List<Diary> queryDiariesByFromTo(int userId, String date, int from, int to, String rightUserWithComma)
            throws Exception {
        List<Diary> list = new ArrayList<Diary>();
        String sql = "SELECT id,user_id,date,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM diary WHERE 1=1";
        /**
         * ��� ��Ȩ�޿����¼��û� rightUserWithComma �ǿ� ����ϸ�����
         */
        if(StringUtils.isNotBlank(rightUserWithComma)){
            sql += " AND user_id in (" + rightUserWithComma + ")";
        }
        /**
         * ���userId����0���ϸ�����
         */
        if(userId > 0){
            sql += " AND user_id=" + userId;
        }
        /**
         * ���date�ǿմ��ϸ�����
         */
        if(StringUtils.isNotBlank(date)){
            sql += " AND date='" + date + "'";
        }
        sql += " ORDER BY date DESC, id DESC limit " + from + "," + to;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                userId = rs.getInt("user_id");
                date = rs.getString("date");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Diary diary = new Diary(id, userId, date, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
                list.add(diary);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id����־
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Diary getDiaryById(int id) throws Exception {
        Diary diary = null;
        String sql = "SELECT user_id,date,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM diary WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String date = rs.getString("date");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                diary = new Diary(id, userId, date, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
            }
            return diary;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ������־
     *
     * @param diary
     * @throws Exception
     */
    public static void insertDiary(Diary diary) throws Exception {
        String sql = "insert into diary(id,user_id,date,content,create_date,create_time,create_ip," +
                "update_date,update_time,update_ip) values (null," + diary.getUserId() + ",'" +
                diary.getDate() + "','" + diary.getContent() + "','" + diary.getCreateDate() + "','" +
                diary.getCreateTime() + "','" + diary.getCreateIp() + "','" + diary.getUpdateDate() + "','" +
                diary.getUpdateTime() + "','" + diary.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ������־
     *
     * @param diary
     * @throws Exception
     */
    public static void updateDiary(Diary diary) throws Exception {
        String sql = "update diary set date='" + diary.getDate() + "',content='" + diary.getContent() +
                "',update_date='" + diary.getUpdateDate() + "',update_time='" + diary.getUpdateTime() +
                "',update_ip='" + diary.getUpdateIp() + "' where id=" + diary.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ����־
     *
     * @param diary
     * @throws Exception
     */
    public static void deleteDiary(Diary diary) throws Exception {
        String sql = "delete from diary where id=" + diary.getId();
        DB.executeUpdate(sql);
    }
}