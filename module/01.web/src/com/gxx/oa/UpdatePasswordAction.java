package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * �޸�����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 16:17
 */
public class UpdatePasswordAction extends BaseAction {
    /**
     * ����
     */
    String password;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("password:" + password);

        //�����û�
        User user = getUser();
        user.setPassword(password);
        UserDao.updateUserPassword(user);
        refreshSessionUser(user);

        //���ؽ��
        String resp = "{isSuccess:true,message:'��������ɹ���',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
