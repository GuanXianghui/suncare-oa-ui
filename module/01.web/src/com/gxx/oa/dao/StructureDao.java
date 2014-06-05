package com.gxx.oa.dao;

import com.gxx.oa.entities.Structure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ��˾�ṹʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 16:06
 */
public class StructureDao {
    /**
     * ��ѯ���й�˾�ṹ
     *
     * @return
     * @throws Exception
     */
    public static List<Structure> queryAllStructures() throws Exception {
        List<Structure> list = new ArrayList<Structure>();
        String sql = "SELECT id,type,name,pid,index_id FROM structure order by index_id";
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
                String name = rs.getString("name");
                int pid = rs.getInt("pid");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                list.add(structure);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ��ѯpid���ӹ�˾�ṹ
     *
     * @return
     * @throws Exception
     */
    public static List<Structure> queryStructuresByPid(int pid) throws Exception {
        List<Structure> list = new ArrayList<Structure>();
        String sql = "SELECT id,type,name,index_id FROM structure where pid=" + pid + " order by index_id";
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
                String name = rs.getString("name");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                list.add(structure);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id��ѯ��˾�ṹ
     *
     * @return
     * @throws Exception
     */
    public static Structure getStructureById(int id) throws Exception {
        String sql = "SELECT type,name,pid,index_id FROM structure where id=" + id;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int type = rs.getInt("type");
                String name = rs.getString("name");
                int pid = rs.getInt("pid");
                int indexId = rs.getInt("index_id");
                Structure structure = new Structure(id, type, name, pid, indexId);
                return structure;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����pid,indexId��ѯ��˾�ṹ
     *
     * @return
     * @throws Exception
     */
    public static Structure getStructureByPidAndIndexId(int pid, int indexId) throws Exception {
        String sql = "SELECT id,type,name FROM structure where pid=" + pid + " AND index_id=" + indexId;
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
                String name = rs.getString("name");
                Structure structure = new Structure(id, type, name, pid, indexId);
                return structure;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ������˾�ṹ
     *
     * @param structure
     * @throws Exception
     */
    public static void insertStructure(Structure structure) throws Exception {
        String sql = "insert structure(id,type,name,pid,index_id) values (null," + structure.getType() +
                ",'" + structure.getName() + "'," + structure.getPid() + "," + structure.getIndexId() + ")";
        DB.executeUpdate(sql);
    }

    /**
     * ���¹�˾�ṹ
     *
     * @param structure
     * @throws Exception
     */
    public static void updateStructure(Structure structure) throws Exception {
        String sql = "update structure set type=" + structure.getType() + ",name='" + structure.getName() +
                "',pid=" + structure.getPid() + ",index_id=" + structure.getIndexId() + " where id=" +
                structure.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ɾ����˾�ṹ
     *
     * @param id
     * @throws Exception
     */
    public static void deleteStructure(int id) throws Exception {
        String sql = "delete from structure where id=" + id;
        DB.executeUpdate(sql);
    }

    /**
     * ����pid������indexId
     * @param pid
     * @return
     */
    public static int getMaxIndexIdByPid(int pid) throws Exception{
        int maxIndexId = 0;
        String sql = "SELECT MAX(index_id) max_index_id FROM structure WHERE pid=" + pid;
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                maxIndexId = rs.getInt("max_index_id");
            }
            return maxIndexId;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �õ�����Ǹ���˾�ܹ�
     * @param structure
     * @return
     */
    public static Structure getLeftOne(Structure structure) throws Exception{
        int maxIndexId = 0;
        String sql = "SELECT MAX(index_id) max_index_id FROM structure WHERE pid=" + structure.getPid() +
                " AND index_id<" + structure.getIndexId();
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                maxIndexId = rs.getInt("max_index_id");
            }
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }

        // ˵��û�����Ĺ�˾�ṹ
        if(maxIndexId == 0) {
            return null;
        }

        return getStructureByPidAndIndexId(structure.getPid(), maxIndexId);
    }

    /**
     * �õ��ұ��Ǹ���˾�ܹ�
     * @param structure
     * @return
     */
    public static Structure getRightOne(Structure structure) throws Exception{
        int minIndexId = 0;
        String sql = "SELECT MIN(index_id) min_index_id FROM structure WHERE pid=" + structure.getPid() +
                " AND index_id>" + structure.getIndexId();
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                minIndexId = rs.getInt("min_index_id");
            }
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }

        // ˵��û���Ҳ�Ĺ�˾�ṹ
        if(minIndexId == 0) {
            return null;
        }

        return getStructureByPidAndIndexId(structure.getPid(), minIndexId);
    }
}
