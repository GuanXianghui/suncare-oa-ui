package com.gxx.oa.dao;

import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import com.gxx.oa.interfaces.SymbolInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ����Ŀ�ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:22
 */
public class CloudDocDao implements CloudDocInterface {
    /**
     * ���� �û�id �� ����Ŀ�
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public static List<CloudDoc> queryCloudDocsByUserId(int userId) throws Exception {
        List<CloudDoc> list = new ArrayList<CloudDoc>();
        String sql = "SELECT id,user_id,title,state,description,type,tags,route,size,read_times," +
                "download_times,create_date,create_time,create_ip,update_date,update_time,update_ip FROM" +
                " cloud_doc WHERE user_id=" + userId + " AND state=" + STATE_NORMAL + " ORDER BY id DESC";
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
                int state = rs.getInt("state");
                String description = rs.getString("description");
                String type = rs.getString("type");
                String tags = rs.getString("tags");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                int readTimes = rs.getInt("read_times");
                int downloadTimes = rs.getInt("download_times");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                CloudDoc cloudDoc = new CloudDoc(id, userId, title, state, description, type, tags, route, size, readTimes,
                        downloadTimes, createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(cloudDoc);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id������Ŀ�
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static CloudDoc getCloudDocById(int id) throws Exception {
        CloudDoc cloudDoc = null;
        String sql = "SELECT id,user_id,title,state,description,type,tags,route,size,read_times," +
                "download_times,create_date,create_time,create_ip,update_date,update_time,update_ip FROM" +
                " cloud_doc WHERE id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                int state = rs.getInt("state");
                String description = rs.getString("description");
                String type = rs.getString("type");
                String tags = rs.getString("tags");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                int readTimes = rs.getInt("read_times");
                int downloadTimes = rs.getInt("download_times");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");
                cloudDoc = new CloudDoc(id, userId, title, state, description, type, tags, route, size, readTimes,
                        downloadTimes, createDate, createTime, createIp, updateDate, updateTime, updateIp);
            }
            return cloudDoc;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��������Ŀ�
     *
     * @param cloudDoc
     * @throws Exception
     */
    public static void insertCloudDoc(CloudDoc cloudDoc) throws Exception {
        String sql = "insert into cloud_doc(id,user_id,title,state,description,type,tags,route,size," +
                "read_times,download_times,create_date,create_time,create_ip,update_date,update_time," +
                "update_ip) values " +
                "(null," + cloudDoc.getUserId() + ",'" + cloudDoc.getTitle() + "'," + cloudDoc.getState() + ",'" +
                cloudDoc.getDescription() + "','" + cloudDoc.getType() + "','" + cloudDoc.getTags() + "','" +
                cloudDoc.getRoute() + "'," + cloudDoc.getSize() + "," + cloudDoc.getReadTimes() + "," +
                cloudDoc.getDownloadTimes() + ",'" + cloudDoc.getCreateDate() + "','" + cloudDoc.getCreateTime() +
                "','" + cloudDoc.getCreateIp() + "','" + cloudDoc.getUpdateDate() + "','" +
                cloudDoc.getUpdateTime() + "','" + cloudDoc.getUpdateIp() + "')";
        DB.executeUpdate(sql);
    }

    /**
     * ��������Ŀ�
     *
     * @param cloudDoc
     * @throws Exception
     */
    public static void updateCloudDoc(CloudDoc cloudDoc) throws Exception {
        String sql = "update cloud_doc set title='" + cloudDoc.getTitle() + "',state=" + cloudDoc.getState() +
                ",description='" + cloudDoc.getDescription() + "',type='" + cloudDoc.getType() + "',tags='" +
                cloudDoc.getTags() + "',read_times=" + cloudDoc.getReadTimes() + ",download_times=" +
                cloudDoc.getDownloadTimes() + ",update_date='" + cloudDoc.getUpdateDate() + "',update_time='" +
                cloudDoc.getUpdateTime() + "',update_ip='" + cloudDoc.getUpdateIp() + "' where id=" + cloudDoc.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ��ѯ����Ŀ�
     * #��ѯ����Ŀ�Ĺ���
     * #0.��������Ϊdoc
     * #1.���docΪ�ջ��߿��ַ�����ѯ�����ؽ����
     * #2.��doc�е����Ķ��źͿո� ��� Ӣ�Ķ���
     * #3.��doc��Ӣ�Ķ��ŷָ������(����ΪtempDoc)������������ִ�����²�ѯ��������ϲ���Ϊ�������
     * #3.1.����tempDoc��ѯ�û��������鵽���û�id��Ϊ����user_id���в�ѯ
     * #3.2.����tempDoc�����ֶ�title����ģ����ѯ
     * #3.3.����tempDoc�����ֶ�description����ģ����ѯ
     * #3.4.����tempDoc�����ֶ�tags����ģ����ѯ
     * #3.5.������Щ��������or��ƴ��
     * @param userId
     * @param doc
     * @return
     */
    public static List<CloudDoc> queryCloudDocsByOrConditions(int userId, String doc) throws Exception {
        List<CloudDoc> list = new ArrayList<CloudDoc>();
        String sql = "SELECT id,user_id,title,state,description,type,tags,route,size,read_times," +
                "download_times,create_date,create_time,create_ip,update_date,update_time,update_ip FROM" +
                " cloud_doc WHERE title like '%" + doc + "%' OR description like '%" + doc + "%' OR " +
                "tags like '%" + doc + "%'";
        if(userId > 0){
            sql += " OR user_id=" + userId;
        }
        sql += " ORDER BY id DESC";//AND state=" + STATE_NORMAL +" ǰ����or������and������Ч
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                int newUserId = rs.getInt("user_id");
                String title = rs.getString("title");
                int state = rs.getInt("state");
                String description = rs.getString("description");
                String type = rs.getString("type");
                String tags = rs.getString("tags");
                String route = rs.getString("route");
                long size = rs.getLong("size");
                int readTimes = rs.getInt("read_times");
                int downloadTimes = rs.getInt("download_times");
                String createDate = rs.getString("create_date");
                String createTime = rs.getString("create_time");
                String createIp = rs.getString("create_ip");
                String updateDate = rs.getString("update_date");
                String updateTime = rs.getString("update_time");
                String updateIp = rs.getString("update_ip");

                /**
                 * SQL ����� AND state=" + STATE_NORMAL +" ǰ����or������and������Ч
                 */
                if(state != STATE_NORMAL){
                    continue;
                }

                CloudDoc cloudDoc = new CloudDoc(id, newUserId, title, state, description, type, tags, route, size, readTimes,
                        downloadTimes, createDate, createTime, createIp, updateDate, updateTime, updateIp);
                list.add(cloudDoc);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��ѯ�Ƚϳ��õı�ǩ
     * @return
     * @throws Exception
     */
    public static List<String> queryDistinctTags() throws Exception {
        List<String> list = new ArrayList<String>();
        String sql = "SELECT distinct tags FROM cloud_doc where tags != '' ORDER BY id DESC limit 20";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                String tags = rs.getString("tags");
                for(String tag : tags.split(SymbolInterface.SYMBOL_COMMA)){
                    if(list.indexOf(tag) > -1){
                        continue;
                    }
                    list.add(tag);
                }
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}