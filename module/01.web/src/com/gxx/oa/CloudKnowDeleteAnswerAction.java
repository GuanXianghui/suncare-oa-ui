package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAnswer;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import org.apache.commons.lang.StringUtils;

/**
 * ɾ���ش�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAnswerAction extends BaseAction implements CloudKnowAnswerInterface {
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
        if(null == cloudKnowAnswer || cloudKnowAnswer.getUserId() != getUser().getId() || cloudKnowAnswer.getState() != STATE_NORMAL){
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

        message = "ɾ���ش�ɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_DELETE_ANSWER, message, date, time, getIp());

        //����ͬһ������֪ͨ
        if(getUser().getId() != cloudKnowAsk.getUserId()){
            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(getUser().getId(), cloudKnowAsk.getUserId(),
                    getUser().getName() + "ɾ���������֪�����ʵĻش�" + "����<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">����</a>", getIp());

            //�����û�
            User cloudKnowAskUser = UserDao.getUserById(cloudKnowAsk.getUserId());
            //���ʼ�
            if(StringUtils.isNotBlank(cloudKnowAskUser.getEmail())){
                //�ʼ�title
                String title = "����Ŵ�OAϵͳ-���֪��ɾ���ش�";
                //�ʼ�����
                String content = cloudKnowAskUser.getName() + "��ã�<br><br>" +
                        getUser().getName() + "������Ŵ�OAϵͳɾ���������֪�����ʵĻش�[" + cloudKnowAsk.getQuestion() + "]����<a href=\"http://www.suncare-sys.com:10000/cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                        "ף������˳����<br><br>" +
                        "����Ŵ�OAϵͳ";
                //�����ʼ�
                EmailUtils.sendEmail(title, content, cloudKnowAskUser.getEmail());
            }
        }

        //���� ����id+�ش���id(״̬Ϊ����) �� һ���˻ش�һ�����֪������ĸ���
        int count = CloudKnowAnswerDao.countCloudKnowAnswersByAskIdAndUserId(cloudKnowAsk.getId(), getUser().getId());

        //��Ȼ���лش𲻼���ɱң�û������ɱ�-2
        if(count == 1){
            //���֪��-ɾ���ش� ��Ȼ���лش𲻼���ɱң�û������ɱ�-2
            UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER);
            User user = UserDao.getUserById(getUser().getId());

            //������ɱұ䶯��־
            BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "��ɱұ䶯 ���֪��-ɾ���ش�" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER, date, time, getIp());

            //ˢ�»���
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);

            //�����˺Ÿ��û���һ����Ϣ
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                    "���֪��-ɾ���ش�ɹ�����ɱ�" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ANSWER + "����<a target=\"_blank\" href=\"cloudViewKnow.jsp?id=" + cloudKnowAsk.getId() + "\">����</a>", getIp());
        }

        //�������֪������
        cloudKnowAnswer.setState(STATE_DELETE);
        cloudKnowAnswer.setUpdateDate(date);
        cloudKnowAnswer.setUpdateTime(time);
        cloudKnowAnswer.setUpdateIp(getIp());
        CloudKnowAnswerDao.updateCloudKnowAnswer(cloudKnowAnswer);

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
