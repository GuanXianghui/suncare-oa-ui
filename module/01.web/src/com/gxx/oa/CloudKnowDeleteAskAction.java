package com.gxx.oa;

import com.gxx.oa.dao.CloudKnowAskDao;
import com.gxx.oa.entities.CloudKnowAsk;
import com.gxx.oa.interfaces.CloudKnowAskInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

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

        return SUCCESS;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }
}
