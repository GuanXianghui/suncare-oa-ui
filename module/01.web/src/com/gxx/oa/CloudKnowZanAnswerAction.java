package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.CloudKnowAnswerInterface;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �޻ش�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowZanAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
    /**
     * ����id
     */
    private String askId;
    /**
     * �ش�id
     */
    private String answerId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId + ",answerId:" + answerId);
        //�лش�id��Ϊ��
        if(StringUtils.isBlank(answerId)){
            message = "�ش�id����Ϊ��!";
            return ERROR;
        }
        // �ش�idInt����
        int answerIdInt = Integer.parseInt(answerId);
        //���ĵ�����
        CloudKnowAnswer cloudKnowAnswer = CloudKnowAnswerDao.getCloudKnowAnswerById(answerIdInt);
        if(null == cloudKnowAnswer || cloudKnowAnswer.getState() != STATE_NORMAL){
            message = "��Ĳ���������ˢ��ҳ������!";
            return ERROR;
        }

        //����id�����֪������
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(cloudKnowAnswer.getAskId());
        if(null == cloudKnowAsk || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            message = "��Ĳ���������ˢ��ҳ������!";
            return ERROR;
        }

        //��ֵ ����id ������ת
        askId = StringUtils.EMPTY + cloudKnowAsk.getId();

        //�������֪������
        cloudKnowAnswer.setZan(cloudKnowAnswer.getZan() + 1);
        CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);

        message = "�޻ش�ɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_ZAN_ANSWER, message, date, time, getIp());

        //����ͬһ������֪ͨ
        if(getUser().getId() != cloudKnowAnswer.getUserId()){
            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAnswer.getUserId(),
                    getUser().getName() + "���������֪�����ʵĻش�" + "����<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">����</a>", getIp());

            //�ش��û�
            User cloudKnowAnswerUser = UserDao.getUserById(cloudKnowAnswer.getUserId());
            //���ʼ�
            if(StringUtils.isNotBlank(cloudKnowAnswerUser.getEmail())){
                //�ʼ�title
                String title = "����Ŵ�OAϵͳ-���֪���޻ش�";
                //�ʼ�����
                String content = cloudKnowAnswerUser.getName() + "��ã�<br><br>" +
                        getUser().getName() + "������Ŵ�OAϵͳ���������֪�����ʵĻش�[" + cloudKnowAsk.getQuestion() + "]����<a href=\"http://www.suncare-sys.com:10000/cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                        "ף������˳����<br><br>" +
                        "����Ŵ�OAϵͳ";
                //�����ʼ�
                EmailUtils.sendEmail(title, content, cloudKnowAnswerUser.getEmail());
            }
        }

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
}
