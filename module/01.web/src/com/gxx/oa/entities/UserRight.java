package com.gxx.oa.entities;

/**
 * Ȩ��ʵ��
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:39
 */
public class UserRight {
    int userId;
    String userRight;

    /**
     * �����Ͳ�ѯʱʹ��
     * @param userId
     * @param userRight
     */
    public UserRight(int userId, String userRight) {
        this.userId = userId;
        this.userRight = userRight;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }
}
