package com.gxx.oa.dao;

import com.gxx.oa.entities.UserNotice;
import com.gxx.oa.interfaces.SymbolInterface;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * �û�����ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 20:28
 */
public class UserNoticeDao {
    /**
     * �����û�id��״̬���û�����id�ַ��������ŷָ�
     *
     * @return
     * @throws Exception
     */
    public static String queryUserNoticeIdsByUserIdAndState(int userId, int state) throws Exception {
        String ids = StringUtils.EMPTY;
        String sql = "SELECT notice_id FROM user_notice WHERE user_id=" + userId + " AND state=" + state;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int noticeId = rs.getInt("notice_id");
                if(StringUtils.isNotBlank(ids)) {
                    ids += SymbolInterface.SYMBOL_COMMA;
                }
                ids += noticeId;
            }
            return ids;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �����û�id�͹���id���û�����
     *
     * @return
     * @throws Exception
     */
    public static UserNotice getUserNoticeByUserIdAndNoticeId(int userId, int noticeId) throws Exception {
        UserNotice userNotice = null;
        String sql = "SELECT state,date,time,ip FROM user_notice WHERE user_id=" + userId + " AND " +
                "notice_id=" + noticeId;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int state = rs.getInt("state");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String ip = rs.getString("ip");
                userNotice = new UserNotice(userId, noticeId, state, date, time, ip);
            }
            return userNotice;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �����û�����
     * @param notice
     * @throws Exception
     */
    public static void insertUserNotice(UserNotice notice) throws Exception {
        String sql = "INSERT INTO user_notice(user_id,notice_id,state,date,time,ip) VALUES(" +
                notice.getUserId() + "," + notice.getNoticeId() + "," + notice.getState() + ",'" + notice.getDate() +
                "','" + notice.getTime() + "','" + notice.getIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * �޸��û�����
     * @param notice
     * @throws Exception
     */
    public static void updateUserNotice(UserNotice notice) throws Exception {
        String sql = "UPDATE user_notice SET state=" + notice.getState() + ",date='" + notice.getDate() + "'," +
                "time='" + notice.getTime() + "',ip='" + notice.getIp() + "' WHERE user_id=" + notice.getUserId() +
                " AND notice_id=" + notice.getNoticeId();
        DB.executeUpdate(sql);
    }
}
