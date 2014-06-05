package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * ����Ŀ��ϴ��ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * ����id
     */
    private String askId;
    /**
     * �ش�
     */
    private String answer;
    /**
     * �ش����� type: insert�����ش� update�޸Ļش�
     */
    private String type;
    /**
     * �޸Ļش�ID
     */
    private String answerId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answer:" + answer + ",type:" + type + ",answerId:" + answerId);
        //�б����ֶ�Ϊ��
        if(StringUtils.isBlank(askId) || StringUtils.isBlank(answer) || StringUtils.isBlank(type))
        {
            message = "����id���ش𣬻ش����Ͳ���Ϊ��!";
            return ERROR;
        }

        //������Ϊ�޸Ļش�
        if(StringUtils.equals(type, "update")){
            if(StringUtils.isBlank(answerId)){
                message = "�޸Ļش�ID����Ϊ��!";
                return ERROR;
            }
            CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(Integer.parseInt(answerId));
            if(cloudKnowAnswer == null || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
                message = "�ûش�״̬������!";
                return ERROR;
            }
            //����id�����֪������
            CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
            if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
                message = "������״̬������!";
                return ERROR;
            }
            cloudKnowAnswer.setAnswer(answer);
            cloudKnowAnswer.setUpdateDate(date);
            cloudKnowAnswer.setUpdateTime(time);
            cloudKnowAnswer.setUpdateIp(getIp());
            CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);
            message = "�޸Ļش�ɹ���";
            return SUCCESS;
        }
        //��������Ϊ�����ش�
        //����id�����֪������
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(Integer.parseInt(askId));
        if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "������״̬������!";
            return ERROR;
        }

        //�������֪���ش�
        CloudKnowAnswer cloudKnowAnswer = new CloudKnowAnswer(cloudKnowAsk.getId(), getUser().getId(), answer, 0,
                STATE_NORMAL, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        CloudKnowAnswerDao.insertCloudKnowAnswer(cloudKnowAnswer);

        message = "��Ļش����ύ�����֪����л��Ĺ��ף�";
        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
