package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * ����Ŀ��ϴ��ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUpdateDocAction extends BaseAction implements CloudDocInterface {
    /**
     * �ĵ�id
     */
    private String docId;
    /**
     * ����
     */
    private String title;
    /**
     * ����
     */
    private String description;
    /**
     * ����
     */
    private String type;
    /**
     * ��ǩ
     */
    private String tags;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("title:" + title + ",description:" + description + ",type:" + type +
                ",tags:" + tags + ",docId:" + docId);
        //���ĵ�id��Ϊ��
        if(StringUtils.isBlank(docId)){
            message = "�ĵ�id����Ϊ��!";
            return ERROR;
        }
        //�б����ֶ�Ϊ��
        if(StringUtils.isBlank(title) || StringUtils.isBlank(type))
        {
            message = "����ͷ��಻��Ϊ��!";
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

        cloudDoc.setTitle(title);
        cloudDoc.setDescription(description);
        cloudDoc.setType(type);
        cloudDoc.setTags(tags);
        cloudDoc.setUpdateDate(date);
        cloudDoc.setUpdateTime(time);
        cloudDoc.setUpdateIp(getIp());
        CloudDocDao.updateCloudDoc(cloudDoc);

        message = "�޸��ĵ��ɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_UPDATE_DOC, message, date, time, getIp());

        return SUCCESS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
