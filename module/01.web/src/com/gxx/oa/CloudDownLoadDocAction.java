package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * ����Ŀ������ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudDownLoadDocAction extends BaseAction {
    /**
     * �ĵ�id
     */
    String docId;
    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        CloudDoc cloudDoc = CloudDocDao.getCloudDocById(Integer.parseInt(docId));
        if(cloudDoc.getState() == CloudDocInterface.STATE_DELETE){
            //���ؽ��
            String resp = "{isSuccess:false,message:'���ĵ��ѱ�ɾ���������ĵ�ʧ�ܣ�'," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_DOWNLOAD_DOC,
                "�����ĵ��ɹ����ĵ�ID:[" + cloudDoc.getId() + "],�ĵ�����:[" + cloudDoc.getTitle() + "]",
                date, time, getIp());

        //������Լ����ĵ���������ɱ�
        if(cloudDoc.getUserId() != getUser().getId()){
            //����Ŀ�-�����ĵ� ��ɱ�-1
            UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD);
            User user = UserDao.getUserById(getUser().getId());

            //������ɱұ䶯��־
            BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "��ɱұ䶯 ����Ŀ�-�����ĵ�" + MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD, date, time, getIp());

            //ˢ�»���
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);

            //�����˺Ÿ��û���һ����Ϣ
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                    "����Ŀ�-�����ĵ��ɹ�����ɱ�" + MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD + "����<a href=\"cloudViewDoc.jsp?id=" + docId + "\" target=\"_blank\">�ĵ�</a>", getIp());
        }

        //���ؽ��
        String resp = "{isSuccess:true,message:'�����ĵ��ɹ���'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);

        return null;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
