package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ��ѯ�û�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 13:09
 */
public class QueryUserAction extends BaseAction {
    String name;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0003_USER_OPERATE);
        logger.info("name:" + name);

        //������������ƴ�����û�
        List<User> list = UserDao.queryUserByNameOrLetter(name);
        String json = BaseUtil.getJsonArrayFromUsers(list).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");
        //���ؽ��
        String resp = "{isSuccess:true,message:'��ѯ�û��ɹ���',jsonStr:'" + json + "',hasNewToken:true," +
                "token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
