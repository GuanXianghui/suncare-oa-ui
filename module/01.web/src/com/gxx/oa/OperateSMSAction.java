package com.gxx.oa;

import com.gxx.oa.dao.SMSDao;
import com.gxx.oa.entities.SMS;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.SMSUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * ��������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-11 11:07
 */
public class OperateSMSAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_SEND = "send";//���Ͷ���

    /**
     * ��������
     */
    String type;//��������
    String phone;//�ֻ���
    String content;//����

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",phone=" + phone + ",content=" + content);
        //ajax���
        String resp;
        if(TYPE_SEND.equals(type)){//������һҳ
            //��������������
            int limit = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.SMS_DAY_LIMIT));
            int countToday = SMSDao.countSMSByUserIdAndStateAndDate(getUser().getId(), SMSInterface.STATE_SUCCESS, date);
            if(limit <= countToday){
                resp = "{isSuccess:" + false + ",message:'�㷢�Ͷ����Ѵ����ޣ�����ϵ����Ա�������������ԣ�'," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            } else {
                String[] phoneArray = phone.split(SymbolInterface.SYMBOL_COMMA);
                boolean isAllSuccess = true;//�Ƿ񶼳ɹ�
                for(String phone : phoneArray){
                    //���Ͷ���
                    int result = SMSUtil.sendSMS(phone, content);
                    boolean isSuccess = SMSUtil.checkSuccess(result);
                    if(!isSuccess){
                        isAllSuccess = false;
                    }
                    //�����¼
                    SMS sms = new SMS(getUser().getId(), phone, content,
                            isSuccess?SMSInterface.STATE_SUCCESS:SMSInterface.STATE_FAIL, date, time, getIp());
                    SMSDao.insertSMS(sms);
                }
                resp = "{isSuccess:" + isAllSuccess + ",message:'" + (isAllSuccess?"������ɣ�":"δȫ�����ͳɹ�!") +
                        "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            }
        } else {
            resp = "{isSuccess:false,message:'������������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
