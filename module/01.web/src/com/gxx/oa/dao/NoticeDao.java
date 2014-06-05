package com.gxx.oa.dao;

import com.gxx.oa.entities.Notice;
import org.apache.commons.lang.StringUtils;

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
 * @datetime 14-4-3 10:37
 */
public class NoticeDao {
    /**
     * ��ѯ���й�������
     *
     * @return
     * @throws Exception
     */
    public static int countAllNotices() throws Exception {
        int countNum = 0;
        String sql = "SELECT count(1) count_num FROM notice";
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
     * ���ݲ�������ids��ѯδɾ���Ĺ�������
     *
     * @return
     * @throws Exception
     */
    public static int countAllNoticesWithoutIds(String withoutIds) throws Exception {
        int countNum = 0;
        List<Notice> list = new ArrayList<Notice>();
        String sql = "SELECT count(1) count_num FROM notice";
        if(StringUtils.isNotBlank(withoutIds)) {
            sql += " WHERE id NOT IN(" + withoutIds + ")";
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
     * ��ѯ���й���
     *
     * @return
     * @throws Exception
     */
    public static List<Notice> queryAllNotices() throws Exception {
        List<Notice> list = new ArrayList<Notice>();
        String sql = "SELECT id,title,content,create_date,create_time,create_ip,update_date,update_time," +
                "update_ip FROM notice";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Notice notice = new Notice(id, title, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
                list.add(notice);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����ҳ���ҳ��С��ѯ����(����id����)
     *
     * @return
     * @throws Exception
     */
    public static List<Notice> queryNoticesByPage(int pageNum, int pageSize) throws Exception {
        List<Notice> list = new ArrayList<Notice>();
        String sql = "SELECT id,title,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM notice ORDER BY id DESC limit " + ((pageNum-1)*pageSize) + "," +
                pageSize;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Notice notice = new Notice(id, title, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
                list.add(notice);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����ҳ���ҳ��С�Ͳ�������ids��ѯδɾ���Ĺ���(����id����)
     *
     * @return
     * @throws Exception
     */
    public static List<Notice> queryNoticesByPageAndWithoutIds(int pageNum, int pageSize, String withoutIds) throws Exception {
        List<Notice> list = new ArrayList<Notice>();
        String sql = "SELECT id,title,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM notice";
        if(StringUtils.isNotBlank(withoutIds)) {
            sql += " WHERE id NOT IN(" + withoutIds + ")";
        }
        sql += " ORDER BY id DESC limit " + ((pageNum-1)*pageSize) + "," +
                pageSize;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Notice notice = new Notice(id, title, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
                list.add(notice);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����ҳ��ͷ�Χ�Ͳ�������ids��ѯδɾ���Ĺ���(����id����)
     *
     * @return
     * @throws Exception
     */
    public static List<Notice> queryNoticesByFromToAndWithoutIds(int from, int to, String withoutIds) throws Exception {
        List<Notice> list = new ArrayList<Notice>();
        String sql = "SELECT id,title,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM notice";
        if(StringUtils.isNotBlank(withoutIds)) {
            sql += " WHERE id NOT IN(" + withoutIds + ")";
        }
        sql += " ORDER BY id DESC limit " + from + "," +
                to;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                Notice notice = new Notice(id, title, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
                list.add(notice);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id�鹫��
     *
     * @return
     * @throws Exception
     */
    public static Notice getNoticeById(int id) throws Exception {
        Notice notice = null;
        String sql = "SELECT title,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip FROM notice where id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                notice = new Notice(id, title, content, createDate, createTime, createIp, updateDate,
                        updateTime, updateIp);
            }
            return notice;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��������
     *
     * @param notice
     * @throws Exception
     */
    public static void insertNotice(Notice notice) throws Exception {
        String sql = "insert into notice(id,title,content,create_date,create_time,create_ip,update_date," +
                "update_time,update_ip) values (null,'" + notice.getTitle() + "','" + notice.getContent() +
                "','" + notice.getCreateDate() + "','" + notice.getCreateTime() + "','" + notice.getCreateIp() +
                "','" + notice.getUpdateDate() + "','" + notice.getUpdateTime() + "','" + notice.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ���¹���
     *
     * @param notice
     * @throws Exception
     */
    public static void updateNotice(Notice notice) throws Exception {
        String sql = "update notice set title='" + notice.getTitle() + "',content='" + notice.getContent() +
                "',update_date='" + notice.getUpdateDate() + "',update_time='" + notice.getUpdateTime() +
                "',update_ip='" + notice.getUpdateIp() + "' where id=" + notice.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ������
     *
     * @param notice
     * @throws Exception
     */
    public static void deleteNotice(Notice notice) throws Exception {
        String sql = "delete from notice where id=" + notice.getId();
        DB.executeUpdate(sql);
    }
}
