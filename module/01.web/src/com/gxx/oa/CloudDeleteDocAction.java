package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * ɾ���ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudDeleteDocAction extends BaseAction implements CloudDocInterface {
    /**
     * �ĵ�id
     */
    private String docId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("docId:" + docId);
        //���ĵ�id��Ϊ��
        if(StringUtils.isBlank(docId)){
            message = "�ĵ�id����Ϊ��!";
            return ERROR;
        }
        // �ĵ�idInt����
        int docInInt = Integer.parseInt(docId);
        //���ĵ�����
        CloudDoc cloudDoc = CloudDocDao.getCloudDocById(docInInt);
        if(null == cloudDoc || cloudDoc.getUserId() != getUser().getId() || cloudDoc.getState() != STATE_NORMAL){
            message = "��Ĳ���������ˢ��ҳ������!";
            return ERROR;
        }

        cloudDoc.setState(STATE_DELETE);
        cloudDoc.setUpdateDate(date);
        cloudDoc.setUpdateTime(time);
        cloudDoc.setUpdateIp(getIp());
        CloudDocDao.updateCloudDoc(cloudDoc);

        message = "ɾ���ĵ��ɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_DELETE_DOC, message, date, time, getIp());

        //����Ŀ�-ɾ���ĵ� ��ɱ�-3
        UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_DOC_DELETE);
        User user = UserDao.getUserById(getUser().getId());

        //������ɱұ䶯��־
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                "��ɱұ䶯 ����Ŀ�-ɾ���ĵ�" + MoneyInterface.ACT_CLOUD_DOC_DELETE, date, time, getIp());

        //ˢ�»���
        request.getSession().setAttribute(BaseInterface.USER_KEY, user);

        //�����˺Ÿ��û���һ����Ϣ
        BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                "����Ŀ�-ɾ���ĵ�[" + cloudDoc.getTitle() + "]�ɹ�����ɱ�" + MoneyInterface.ACT_CLOUD_DOC_DELETE + "��", getIp());

        return SUCCESS;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
