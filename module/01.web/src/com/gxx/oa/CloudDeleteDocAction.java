package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
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

        return SUCCESS;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
