package com.gxx.oa.dao;

import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ���֪���ش�ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:22
 */
public class CloudKnowAnswerDao implements CloudKnowAnswerInterface {
    /**
     * ���� ����id �� ���֪���ش�
     *
     * @param askId
     * @return
     * @throws Exception
     */
    public static List<CloudKnowAnswer> queryCloudKnowAnswersByAskId(int askId) throws Exception {
        List<CloudKnowAnswer> list = new ArrayList<CloudKnowAnswer>();
        String sql = "SELECT id,ask_id,user_id,answer,zan,state,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM cloud_know_answer WHERE ask_id=" +
                askId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
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
                String answer = rs.getString("answer");
                int zan = rs.getInt("zan");
                int state = rs.getInt("state");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                CloudKnowAnswer cloudKnowAnswer = new CloudKnowAnswer(id, askId, userId, answer, zan, state, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
                list.add(cloudKnowAnswer);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����id �� ���֪���ش����
     *
     * @param askId
     * @return
     * @throws Exception
     */
    public static int countCloudKnowAnswersByAskId(int askId) throws Exception {
        List<CloudKnowAnswer> list = new ArrayList<CloudKnowAnswer>();
        String sql = "SELECT count(1) count_num FROM cloud_know_answer WHERE ask_id=" +
                askId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int countNum = rs.getInt("count_num");
                return countNum;
            }
            return 0;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����id+�ش���id(״̬Ϊ����) �� һ���˻ش�һ�����֪������ĸ���
     * @param askId
     * @return
     * @throws Exception
     */
    public static int countCloudKnowAnswersByAskIdAndUserId(int askId, int userId) throws Exception {
        List<CloudKnowAnswer> list = new ArrayList<CloudKnowAnswer>();
        String sql = "SELECT count(1) count_num FROM cloud_know_answer WHERE ask_id=" +
                askId + " AND user_id=" + userId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int countNum = rs.getInt("count_num");
                return countNum;
            }
            return 0;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ����id �� ���֪�����лش���id��distinct�ų���ͬ��
     *
     * @param askId
     * @return
     * @throws Exception
     */
    public static List<Integer> queryCloudKnowAnswerUserIdsByAskId(int askId) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT distinct user_id FROM cloud_know_answer WHERE ask_id=" +
                askId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                list.add(userId);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� �û�id �� �������֪���ش��Ӧ������
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<CloudKnowAsk> queryCloudKnowAsksByUserId(int userId) throws Exception {
        List<CloudKnowAsk> list = new ArrayList<CloudKnowAsk>();
        String sql = "SELECT distinct ask_id FROM cloud_know_answer WHERE user_id=" +
                userId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int askId = rs.getInt("ask_id");
                CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(askId);
                if(cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
                    continue;
                }
                list.add(cloudKnowAsk);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id�����֪���ش�
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static CloudKnowAnswer getCloudKnowAnswerById(int id) throws Exception {
        CloudKnowAnswer cloudKnowAnswer = null;
        String sql = "SELECT id,ask_id,user_id,answer,zan,state,create_date,create_time," +
                "create_ip,update_date,update_time,update_ip FROM cloud_know_answer WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int askId = rs.getInt("ask_id");
                int userId = rs.getInt("user_id");
                String answer = rs.getString("answer");
                int zan = rs.getInt("zan");
                int state = rs.getInt("state");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                cloudKnowAnswer = new CloudKnowAnswer(id, askId, userId, answer, zan, state, createDate,
                        createTime, createIp, updateDate, updateTime, updateIp);
            }
            return cloudKnowAnswer;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �������֪���ش�
     *
     * @param cloudKnowAnswer
     * @throws Exception
     */
    public static void insertCloudKnowAnswer(CloudKnowAnswer cloudKnowAnswer) throws Exception {
        String sql = "insert into cloud_know_answer(id,ask_id,user_id,answer,zan,state,create_date," +
                "create_time,create_ip,update_date,update_time,update_ip) values " +
                "(null," + cloudKnowAnswer.getAskId() + "," + cloudKnowAnswer.getUserId() + ",'" +
                cloudKnowAnswer.getAnswer() + "'," + cloudKnowAnswer.getZan() + "," +
                cloudKnowAnswer.getState() + ",'" + cloudKnowAnswer.getCreateDate() + "','" +
                cloudKnowAnswer.getCreateTime() + "','" +  cloudKnowAnswer.getCreateIp() + "','" +
                cloudKnowAnswer.getUpdateDate() + "','" + cloudKnowAnswer.getUpdateTime() + "','" +
                cloudKnowAnswer.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * �������֪���ش�
     *
     * @param cloudKnowAnswer
     * @throws Exception
     */
    public static void updateCloudKnowAnswer(CloudKnowAnswer cloudKnowAnswer) throws Exception {
        String sql = "update cloud_know_answer set answer='" + cloudKnowAnswer.getAnswer() + "',zan=" +
                cloudKnowAnswer.getZan() + ",state=" + cloudKnowAnswer.getState() + ",update_date='" +
                cloudKnowAnswer.getUpdateDate() + "',update_time='" + cloudKnowAnswer.getUpdateTime() +
                "',update_ip='" + cloudKnowAnswer.getUpdateIp() + "' where id=" + cloudKnowAnswer.getId();
        DB.executeUpdate(sql);
    }
}