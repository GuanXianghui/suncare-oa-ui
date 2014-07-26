package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.dao.UserRightDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.entities.User;
import com.gxx.oa.entities.UserRight;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.ParamInterface;
import com.gxx.oa.interfaces.UserInterface;
import com.gxx.oa.utils.*;
import org.apache.commons.lang.StringUtils;

/**
 * �����û�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-14 10:51
 */
public class CreateUserAction extends BaseAction {
    String name;
    String letter;
    String positionId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0001_USER_OPERATE);
        logger.info("name:" + name + ",letter=" + letter + ",positionId=" + positionId);
        String resp;

        //�������ֲ��Ƿ������û�ʹ��
        User user = UserDao.getUserByName(name);
        if(null != user){

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CREATE_USER, "�����û� ���û����ѱ��ã�", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:false,message:'���û����ѱ��ã�������û�����',hasNewToken:true," +
                    "token:'" + TokenUtil.createToken(request) + "'}";
        } else {
            // �鿴��˾�����ţ�ְλ��Ϣ
            Structure position = StructureDao.getStructureById(Integer.parseInt(positionId));
            Structure dept = BaseUtil.getDeptByPosition(Integer.parseInt(positionId));
            Structure company = BaseUtil.getCompanyByPosition(Integer.parseInt(positionId));

            int companyInt = company==null?0:company.getId();
            int deptInt = dept==null?0:dept.getId();
            int positionInt = position==null?0:position.getId();

            String defaultPhoto = PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_HEAD_PHOTO);
            user = new User(name, BaseUtil.generateDefaultPwd(), letter, UserInterface.STATE_NORMAL, 0, companyInt, deptInt,
                    positionInt, StringUtils.EMPTY, UserInterface.SEX_X, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, defaultPhoto, StringUtils.EMPTY,
                    date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            UserDao.insertUser(user);

            //����Ȩ��
            user = UserDao.getUserByName(name);
            UserRight userRight = new UserRight(user.getId(), ParamUtil.getInstance().getValueByName(ParamInterface.DEFAULT_RIGHT));
            UserRightDao.insertUserRight(userRight);

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CREATE_USER, "�����û� �����û��ɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'�����û��ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
