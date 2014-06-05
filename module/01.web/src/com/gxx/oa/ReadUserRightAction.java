package com.gxx.oa;

import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.UserRight;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * ��ȡȨ��action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class ReadUserRightAction extends BaseAction {
    //�û�Id
    String userId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0015_USER_RIGHT);
        logger.info("userId:" + userId);

        //��ѯȨ��
        UserRight userRight = UserRightDao.getUserRightByUserId(Integer.parseInt(userId));
        String userRightStr = userRight.getUserRight();

        //���ؽ��
        String resp = "{isSuccess:true,message:'��ȡȨ�޳ɹ���',userRight:'" + userRightStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
