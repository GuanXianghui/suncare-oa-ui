package com.gxx.oa;

import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.UserRight;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * �޸��û�Ȩ��action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class UpdateUserRightAction extends BaseAction {
    //�û�Id
    String userId;
    //�û�Ȩ��
    String userRight;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0015_USER_RIGHT);
        logger.info("userId:" + userId + ",userRight:" + userRight);

        //��ѯȨ��
        UserRight userRightEntity = UserRightDao.getUserRightByUserId(Integer.parseInt(userId));
        userRightEntity.setUserRight(userRight);
        UserRightDao.updateUserRight(userRightEntity);

        //���ؽ��
        String resp = "{isSuccess:true,message:'�޸��û�Ȩ�޳ɹ���',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }
}
