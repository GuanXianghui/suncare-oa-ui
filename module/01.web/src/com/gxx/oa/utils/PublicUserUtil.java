package com.gxx.oa.utils;

import com.gxx.oa.dao.PublicUserDao;
import com.gxx.oa.entities.PublicUser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * �����˺Ŷ�ȡ������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:11
 */
public class PublicUserUtil {
    /**
     * ��־������
     */
    static Logger logger = Logger.getLogger(PublicUserUtil.class);

    private static PublicUserUtil instance;

    public static PublicUserUtil getInstance() {
        if (null == instance) {
            instance = new PublicUserUtil();
        }
        return instance;
    }

    static List<PublicUser> publicUserList;

    private PublicUserUtil() {
        refresh();
    }

    /**
     * ��ѯ���й����˺�
     */
    public static void refresh() {
        try {
            publicUserList = PublicUserDao.queryAllPublicUsers();
        } catch (Exception e) {
            logger.error("��ѯ���й����˺��쳣������", e);
        }
    }

    /**
     * ����englishName��ȡ�����˺�
     *
     * @param englishName
     * @return
     */
    public PublicUser getPublicUserByEnglishName(String englishName) {
        for(PublicUser publicUser : publicUserList) {
            if(StringUtils.equals(publicUser.getEnglishName(), englishName)){
                return publicUser;
            }
        }
        return null;
    }

    /**
     * ����id��ȡ�����˺�
     *
     * @param id
     * @return
     */
    public PublicUser getPublicUserById(int id) {
        for(PublicUser publicUser : publicUserList) {
            if(id == publicUser.getId()){
                return publicUser;
            }
        }
        return null;
    }
}
