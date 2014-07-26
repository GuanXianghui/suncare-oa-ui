package com.gxx.oa.dao;

import com.gxx.oa.entities.User;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * �û�ʵ�������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 20:22
 */
public class UserDao {
    /**
     * ��ѯ�����û�
     *
     * @return
     * @throws Exception
     */
    public static List<User> queryAllUsers() throws Exception {
        List<User> list = new ArrayList<User>();
        String sql = "SELECT id,name,password,letter,state,score,company,dept,position,desk,sex,birthday," +
                "office_tel,mobile_tel,email,qq,msn,address,head_photo,website,register_date," +
                "register_time,register_ip,visit_date,visit_time,visit_ip FROM user order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String letter = rs.getString("letter");
                int state = rs.getInt("state");
                int score = rs.getInt("score");
                int company = rs.getInt("company");
                int dept = rs.getInt("dept");
                int position = rs.getInt("position");
                String desk = rs.getString("desk");
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                String officeTel = rs.getString("office_tel");
                String mobileTel = rs.getString("mobile_tel");
                String email = rs.getString("email");
                String qq = rs.getString("qq");
                String msn = rs.getString("msn");
                String address = rs.getString("address");
                String headPhoto = rs.getString("head_photo");
                String website = rs.getString("website");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                User user = new User(id, name, password, letter, state, score, company, dept, position, desk, sex, birthday,
                        officeTel, mobileTel, email, qq, msn, address, headPhoto, website, registerDate, registerTime,
                        registerIp, visitDate, visitTime, visitIp);
                list.add(user);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ����id���û�
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static User getUserById(int id) throws Exception {
        String sql = "SELECT name,password,letter,state,score,company,dept,position,desk,sex,birthday," +
                "office_tel,mobile_tel,email,qq,msn,address,head_photo,website,register_date," +
                "register_time,register_ip,visit_date,visit_time,visit_ip FROM user WHERE id=" +
                id + " order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                String letter = rs.getString("letter");
                int state = rs.getInt("state");
                int score = rs.getInt("score");
                int company = rs.getInt("company");
                int dept = rs.getInt("dept");
                int position = rs.getInt("position");
                String desk = rs.getString("desk");
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                String officeTel = rs.getString("office_tel");
                String mobileTel = rs.getString("mobile_tel");
                String email = rs.getString("email");
                String qq = rs.getString("qq");
                String msn = rs.getString("msn");
                String address = rs.getString("address");
                String headPhoto = rs.getString("head_photo");
                String website = rs.getString("website");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                User user = new User(id, name, password, letter, state, score, company, dept, position, desk, sex, birthday,
                        officeTel, mobileTel, email, qq, msn, address, headPhoto, website, registerDate, registerTime,
                        registerIp, visitDate, visitTime, visitIp);
                return user;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �����������û�
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static User getUserByName(String name) throws Exception {
        String sql = "SELECT id,password,letter,state,score,company,dept,position,desk,sex,birthday," +
                "office_tel,mobile_tel,email,qq,msn,address,head_photo,website,register_date," +
                "register_time,register_ip,visit_date,visit_time,visit_ip FROM user WHERE name='" +
                name + "' order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String letter = rs.getString("letter");
                int state = rs.getInt("state");
                int score = rs.getInt("score");
                int company = rs.getInt("company");
                int dept = rs.getInt("dept");
                int position = rs.getInt("position");
                String desk = rs.getString("desk");
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                String officeTel = rs.getString("office_tel");
                String mobileTel = rs.getString("mobile_tel");
                String email = rs.getString("email");
                String qq = rs.getString("qq");
                String msn = rs.getString("msn");
                String address = rs.getString("address");
                String headPhoto = rs.getString("head_photo");
                String website = rs.getString("website");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                User user = new User(id, name, password, letter, state, score, company, dept, position, desk, sex, birthday,
                        officeTel, mobileTel, email, qq, msn, address, headPhoto, website, registerDate, registerTime,
                        registerIp, visitDate, visitTime, visitIp);
                return user;
            }
            return null;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * �и������Ƿ��Ѵ���
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean isNameExist(String name) throws Exception {
        User user = getUserByName(name);
        return user != null;
    }

    /**
     * �����û�
     *
     * @param user
     * @throws Exception
     */
    public static void insertUser(User user) throws Exception {
        String sql = "insert into user" +
                "(id,name,password,letter,state,score,company,dept,position,desk,sex,birthday,office_tel," +
                "mobile_tel,email,qq,msn,address,head_photo,website,register_date,register_time," +
                "register_ip,visit_date,visit_time,visit_ip)" +
                "values" +
                "(null,'" + user.getName() + "','" + user.getPassword() + "','" + user.getLetter() + "'," +
                user.getState() + "," + user.getScore() + "," + user.getCompany() + "," + user.getDept() + "," +
                user.getPosition() + ",'" + user.getDesk() + "'," + user.getSex() + ",'" + user.getBirthday() + "','" +
                user.getOfficeTel() + "','" + user.getMobileTel() + "','" + user.getEmail() + "','" + user.getQq() +
                "','" + user.getMsn() + "','" + user.getAddress() + "','" + user.getHeadPhoto() + "','" +
                user.getWebsite() + "','" + user.getRegisterDate() + "','" + user.getRegisterTime() + "','" +
                user.getRegisterIp() + "','" + user.getVisitDate() + "','" + user.getVisitTime() + "','" +
                user.getVisitIp() +
                "')";
        DB.executeUpdate(sql);
    }

    /**
     * �����û�����
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserPassword(User user) throws Exception {
        String sql = "update user set password='" + user.getPassword() + "' where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * �����û�״̬
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserState(User user) throws Exception {
        String sql = "update user set state=" + user.getState() + " where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * �����û�ͷ��
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserHeadPhoto(User user) throws Exception {
        String sql = "update user set head_photo='" + user.getHeadPhoto() + "' where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * �����û�����
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserScore(User user) throws Exception {
        String sql = "update user set score=" + user.getScore() + " where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * �����û�������Ϣ
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserVisitInfo(User user) throws Exception {
        String sql = "update user set visit_date='" + user.getVisitDate() + "',visit_time='" + user.getVisitTime() +
                "',visit_ip='" + user.getVisitIp() + "' where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * �����û���Ϣ
     *
     * @param user
     * @throws Exception
     */
    public static void updateUserInfo(User user) throws Exception {
        String sql = "update user set letter='" + user.getLetter() + "',company=" + user.getCompany() +
                ",dept=" + user.getDept() + ",position=" + user.getPosition() + ",desk='" + user.getDesk() +
                "',sex=" + user.getSex() + ",birthday='" + user.getBirthday() + "',office_tel='" +
                user.getOfficeTel() + "',mobile_tel='" + user.getMobileTel() + "',email='" + user.getEmail() +
                "',qq='" + user.getQq() + "',msn='" + user.getMsn() + "',address='" + user.getAddress() +
                "',website='" + user.getWebsite() + "' where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ����ְλ
     *
     * @param user
     * @throws Exception
     */
    public static void updatePosition(User user) throws Exception {
        String sql = "update user set company=" + user.getCompany() + ",dept=" + user.getDept() +
                ",position=" + user.getPosition() + " where id=" + user.getId();
        DB.executeUpdate(sql);
    }

    /**
     * ������������ƴ�����û�
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static List<User> queryUserByNameOrLetter(String name) throws Exception {
        List<User> list = new ArrayList<User>();
        String sql = "SELECT id,name,password,letter,state,score,company,dept,position,desk,sex,birthday," +
                "office_tel,mobile_tel,email,qq,msn,address,head_photo,website,register_date," +
                "register_time,register_ip,visit_date,visit_time,visit_ip FROM user WHERE name like '%" +
                name + "%' OR letter like '%" + StringUtils.upperCase(name) + "%' order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("name");
                String password = rs.getString("password");
                String letter = rs.getString("letter");
                int state = rs.getInt("state");
                int score = rs.getInt("score");
                int company = rs.getInt("company");
                int dept = rs.getInt("dept");
                int position = rs.getInt("position");
                String desk = rs.getString("desk");
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                String officeTel = rs.getString("office_tel");
                String mobileTel = rs.getString("mobile_tel");
                String email = rs.getString("email");
                String qq = rs.getString("qq");
                String msn = rs.getString("msn");
                String address = rs.getString("address");
                String headPhoto = rs.getString("head_photo");
                String website = rs.getString("website");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                User user = new User(id, userName, password, letter, state, score, company, dept, position, desk, sex, birthday,
                        officeTel, mobileTel, email, qq, msn, address, headPhoto, website, registerDate, registerTime,
                        registerIp, visitDate, visitTime, visitIp);
                list.add(user);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }

    /**
     * ���� ְλID���ŷָ��� �����û�
     *
     * @param positionWithComma �ǿգ�ְλID���ŷָ���
     * @return
     * @throws Exception
     */
    public static List<User> queryUserByPositionWithComma(String positionWithComma) throws Exception {
        List<User> list = new ArrayList<User>();
        if(StringUtils.isBlank(positionWithComma)){
            return list;
        }
        String sql = "SELECT id,name,password,letter,state,score,company,dept,position,desk,sex,birthday," +
                "office_tel,mobile_tel,email,qq,msn,address,head_photo,website,register_date," +
                "register_time,register_ip,visit_date,visit_time,visit_ip FROM user WHERE position IN " +
                "(" + positionWithComma + ") order by id";
        Connection c = DB.getConn();
        Statement stmt = DB.createStatement(c);
        ResultSet rs = DB.executeQuery(c, stmt, sql);
        try {
            if (rs == null) {
                throw new RuntimeException("���ݿ�������������ԣ�");
            }
            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("name");
                String password = rs.getString("password");
                String letter = rs.getString("letter");
                int state = rs.getInt("state");
                int score = rs.getInt("score");
                int company = rs.getInt("company");
                int dept = rs.getInt("dept");
                int position = rs.getInt("position");
                String desk = rs.getString("desk");
                int sex = rs.getInt("sex");
                String birthday = rs.getString("birthday");
                String officeTel = rs.getString("office_tel");
                String mobileTel = rs.getString("mobile_tel");
                String email = rs.getString("email");
                String qq = rs.getString("qq");
                String msn = rs.getString("msn");
                String address = rs.getString("address");
                String headPhoto = rs.getString("head_photo");
                String website = rs.getString("website");
                String registerDate = rs.getString("register_date");
                String registerTime = rs.getString("register_time");
                String registerIp = rs.getString("register_ip");
                String visitDate = rs.getString("visit_date");
                String visitTime = rs.getString("visit_time");
                String visitIp = rs.getString("visit_ip");
                User user = new User(id, userName, password, letter, state, score, company, dept, position, desk, sex, birthday,
                        officeTel, mobileTel, email, qq, msn, address, headPhoto, website, registerDate, registerTime,
                        registerIp, visitDate, visitTime, visitIp);
                list.add(user);
            }
            return list;
        } finally {
            DB.close(rs);
            DB.close(stmt);
            DB.close(c);
        }
    }
}
