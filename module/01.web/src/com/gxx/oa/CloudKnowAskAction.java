package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * ����Ŀ��ϴ��ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowAskAction extends BaseAction implements CloudKnowAskInterface {
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
        logger.info("question:" + question + ",description:" + description + ",type:" + type +
                ",urgent:" + urgent);
        //�б����ֶ�Ϊ��
        if(StringUtils.isBlank(question) || StringUtils.isBlank(type) || StringUtils.isBlank(urgent))
        {
            message = "���⣬����ͽ�������Ϊ��!";
            return ERROR;
        }

        //�������֪������
        CloudKnowAsk cloudKnowAsk = new CloudKnowAsk(getUser().getId(), question, description, type,
                Integer.parseInt(urgent), STATE_NORMAL, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY);
        CloudKnowAskDao.insertCloudKnowAsk(cloudKnowAsk);

        message = "���ʵ����֪���ɹ������ǻ��ҵ���Ӧ���˰�����";
        return SUCCESS;
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