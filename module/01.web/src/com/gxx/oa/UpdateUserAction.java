package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * �޸��û�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 13:09
 */
public class UpdateUserAction extends BaseAction {
    //��������
    String updateType;
    //�û�id
    String userId;
    //ְλid
    String positionId;
    //״̬
    String state;

    //�޸��û����� �������룺initPassword �޸�ְλ��updatePosition �޸�״̬��updateState
    private static final String UPDATE_TYPE_INIT_PASSWORD = "initPassword";
    private static final String UPDATE_TYPE_UPDATE_POSITION = "updatePosition";
    private static final String UPDATE_TYPE_UPDATE_STATE = "updateState";

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0003_USER_OPERATE);
        logger.info("updateType:" + updateType + ",userId=" + userId + ",positionId=" + positionId);
        String resp;

        if(StringUtils.equals(UPDATE_TYPE_INIT_PASSWORD, updateType)){//��������
            //��ѯ�û�����������
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setPassword(BaseUtil.generateDefaultPwd());
            UserDao.updateUserPassword(user);

            //���ؽ��
            resp = "{isSuccess:true,message:'��������ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(StringUtils.equals(UPDATE_TYPE_UPDATE_POSITION, updateType)){//�޸�ְλ
            // �鿴��˾�����ţ�ְλ��Ϣ
            Structure position = StructureDao.getStructureById(Integer.parseInt(positionId));
            Structure dept = BaseUtil.getDeptByPosition(Integer.parseInt(positionId));
            Structure company = BaseUtil.getCompanyByPosition(Integer.parseInt(positionId));

            //��ѯ�û����޸�ְλ
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setPosition(position==null?0:position.getId());
            user.setDept(dept==null?0:dept.getId());
            user.setCompany(company==null?0:company.getId());
            UserDao.updatePosition(user);

            //���ؽ��
            resp = "{isSuccess:true,message:'�޸�ְλ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(StringUtils.equals(UPDATE_TYPE_UPDATE_STATE, updateType)){//�޸�״̬
            //��ѯ�û����޸�ְλ
            User user = UserDao.getUserById(Integer.parseInt(userId));
            user.setState(Integer.parseInt(state));
            UserDao.updateUserState(user);

            //���ؽ��
            resp = "{isSuccess:true,message:'�޸�ְλ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            //���ؽ��
            resp = "{isSuccess:false,message:'������������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }

        write(resp);
        return null;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
