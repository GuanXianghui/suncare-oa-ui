package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAnswerDao;
import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ɾ������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudKnowDeleteAskAction extends BaseAction implements CloudKnowAskInterface {
    /**
     * ����id
     */
    private String askId;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("askId:" + askId);
        //������id��Ϊ��
        if(StringUtils.isBlank(askId)){
            message = "����id����Ϊ��!";
            return ERROR;
        }
        // ����idInt����
        int askIdInt = Integer.parseInt(askId);
        //���ĵ�����
        CloudKnowAsk cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(askIdInt);
        if(null == cloudKnowAsk || cloudKnowAsk.getUserId() != getUser().getId() || cloudKnowAsk.getState() != STATE_NORMAL){
            message = "��Ĳ���������ˢ��ҳ������!";
            return ERROR;
        }

        //�������֪������
        cloudKnowAsk.setState(STATE_DELETE);
        cloudKnowAsk.setUpdateDate(date);
        cloudKnowAsk.setUpdateTime(time);
        cloudKnowAsk.setUpdateIp(getIp());
        CloudKnowAskDao.updateCloudKnowAsk(cloudKnowAsk);

        message = "ɾ�����ʳɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_KNOW_DELETE_ASK, message, date, time, getIp());

        //���֪��-ɾ������ ��������ɱ�-1�����лش������ɱ�-2
        UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK);
        User user = UserDao.getUserById(getUser().getId());

        //������ɱұ䶯��־
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                "��ɱұ䶯 ���֪��-ɾ������ ������" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK, date, time, getIp());

        //ˢ�»���
        request.getSession().setAttribute(BaseInterface.USER_KEY, user);

        //�����˺Ÿ��û���һ����Ϣ
        BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                "���֪��-ɾ������[" + cloudKnowAsk.getQuestion() + "]�ɹ�����������ɱ�" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK + "��", getIp());

        //���� ����id �� ���֪�����лش���id��distinct�ų���ͬ��
        List<Integer> integerList = CloudKnowAnswerDao.queryCloudKnowAnswerUserIdsByAskId(askIdInt);
        for(Integer userInteger : integerList){
            //���֪��-ɾ������ ��������ɱ�-1�����лش������ɱ�-2
            UserDao.updateUserMoney(userInteger.intValue(), MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER);

            //������ɱұ䶯��־
            BaseUtil.createOperateLog(userInteger.intValue(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "��ɱұ䶯 ���֪��-ɾ������ �ش���" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER, date, time, getIp());

            //�����˺Ÿ��û���һ����Ϣ
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, userInteger.intValue(),
                    getUser().getName() + "���֪��-ɾ������[" + cloudKnowAsk.getQuestion() + "]�ɹ����ش�����ɱ�" + MoneyInterface.ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER + "��", getIp());

            //��ͨ�û��������û���һ����Ϣ
            BaseUtil.createNormalMessage(getUser().getId(), userInteger,
                    getUser().getName() + "ɾ�������֪��������[" + cloudKnowAsk.getQuestion() + "]", getIp());
        }

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }
}
