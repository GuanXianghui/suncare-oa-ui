package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * ���֪���޸�����
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowUpdateAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * ����id
     */
    private String askId;
    /**
     * ����
     */
    private String question;
    /**
     * ���ⲹ��
     */
    private String description;
    /**
     * ����
     */
    private String type;
    /**
     * ����
     */
    private String urgent;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",question:" + question + ",description:" + description +
                ",type:" + type + ",urgent:" + urgent);
        //�б����ֶ�Ϊ��
        if(StringUtils.isBlank(askId) || StringUtils.isBlank(question) || StringUtils.isBlank(type) ||
                StringUtils.isBlank(urgent))
        {
            message = "�������ֶβ���Ϊ��!";
            return ERROR;
        }

        //�����֪�����ʴ��ڣ�״̬���ǲ����Լ���
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(request.getParameter("askId")));
        if(cloudKnowAsk == null || cloudKnowAsk.getUserId() != getUser().getId() ||
                cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "�Ҳ��������֪������!";
            return ERROR;
        }

        //�������֪������
        cloudKnowAsk.setQuestion(question);
        cloudKnowAsk.setDescription(description);
        cloudKnowAsk.setType(type);
        cloudKnowAsk.setUrgent(Integer.parseInt(urgent));
        cloudKnowAsk.setUpdateDate(date);
        cloudKnowAsk.setUpdateTime(time);
        cloudKnowAsk.setUpdateIp(getIp());
        CloudKnowAskDao.updateCloudKnowAsk(cloudKnowAsk);

        message = "�������֪�����ʳɹ���";
        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
}
