package com.gxx.oa.dao;

import com.gxx.oa.entities.DiaryReview;
import com.gxx.oa.interfaces.DiaryReviewInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ������־����ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 13:26
 */
public class DiaryReviewDao {
    /**
     * ���ݹ�����־id�鹤����־����
     *
     * @param diaryId
     * @return
     * @throws Exception
     */
    public static List<DiaryReview> queryDiaryReviews(int diaryId) throws Exception {
        List<DiaryReview> list = new ArrayList<DiaryReview>();
        String sql = "SELECT id,user_id,diary_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM diary_review WHERE diary_id=" + diaryId +
                " ORDER BY ID DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                DiaryReview diaryReview = new DiaryReview(id, userId, diaryId, type, repliedUerId, content, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
                list.add(diaryReview);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id����־����
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static DiaryReview getDiaryReviewById(int id) throws Exception {
        DiaryReview diaryReview = null;
        String sql = "SELECT user_id,diary_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM diary_review WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int diaryId = rs.getInt("diary_id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                diaryReview = new DiaryReview(id, userId, diaryId, type, repliedUerId, content, createDate, createTime,
                        createIp, updateDate, updateTime, updateIp);
            }
            return diaryReview;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���ݹ�����־id���û�id���Ƿ��ѵ��޹�
     *
     * @param diaryId
     * @param userId
     * @return
     * @throws Exception
     */
    public static boolean hasAllReadyZan(int diaryId, int userId) throws Exception {
        String sql = "SELECT count(1) count_zan FROM diary_review WHERE diary_id=" + diaryId +
                " AND user_id=" + userId + " AND type=" + DiaryReviewInterface.TYPE_ZAN;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int countZan = rs.getInt("count_zan");
                if(countZan > 0){
                    return true;
                }
            }
            return false;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���ݹ�����־id���û�id�õ��޵�����
     *
     * @param diaryId
     * @param userId
     * @return
     * @throws Exception
     */
    public static DiaryReview getZan(int diaryId, int userId) throws Exception {
        DiaryReview diaryReview = null;
        String sql = "SELECT id,type,replied_user_id,content,create_date,create_time,create_ip," +
                "update_date,update_time,update_ip FROM diary_review WHERE diary_id=" + diaryId +
                " AND user_id=" + userId + " AND type=" + DiaryReviewInterface.TYPE_ZAN;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int type = rs.getInt("type");
                int repliedUerId = rs.getInt("replied_user_id");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                diaryReview = new DiaryReview(id, userId, diaryId, type, repliedUerId, content, createDate, createTime,
                        createIp, updateDate, updateTime, updateIp);
            }
            return diaryReview;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ������־����
     *
     * @param diaryReview
     * @throws Exception
     */
    public static void insertDiaryReview(DiaryReview diaryReview) throws Exception {
        String sql = "insert into diary_review(id,user_id,diary_id,type,replied_user_id,content,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip) values (null," + diaryReview.getUserId() + "," +
                diaryReview.getDiaryId() + "," + diaryReview.getType() + "," + diaryReview.getRepliedUserId() + ",'" +
                diaryReview.getContent() + "','" + diaryReview.getCreateDate() + "','" + diaryReview.getCreateTime() +
                "','" + diaryReview.getCreateIp() + "','" + diaryReview.getUpdateDate() + "','" +
                diaryReview.getUpdateTime() + "','" + diaryReview.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ������־����
     *
     * @param diaryReview
     * @throws Exception
     */
    public static void updateDiaryReview(DiaryReview diaryReview) throws Exception {
        String sql = "update diary_review set content='" + diaryReview.getContent() + "',update_date='" +
                diaryReview.getUpdateDate() + "',update_time='" + diaryReview.getUpdateTime() + "',update_ip='" +
                diaryReview.getUpdateIp() + "' where id=" + diaryReview.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ����־����
     *
     * @param diaryReview
     * @throws Exception
     */
    public static void deleteDiaryReview(DiaryReview diaryReview) throws Exception {
        String sql = "delete from diary_review where id=" + diaryReview.getId();
        DB.executeUpdate(sql);
    }
}