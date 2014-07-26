package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 修改密码action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 16:17
 */
public class UpdatePasswordAction extends BaseAction {
    /**
     * 密码
     */
    String password;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("password:" + password);

        //更新用户
        User user = getUser();
        user.setPassword(password);
        UserDao.updateUserPassword(user);
        refreshSessionUser(user);

        //创建操作日志
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_UPDATE_PASSWORD, "更新密码成功", date, time, getIp());

        //返回结果
        String resp = "{isSuccess:true,message:'更新密码成功！',hasNewToken:true,token:'" +
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
