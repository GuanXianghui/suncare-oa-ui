package com.gxx.oa;

import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.entities.Message;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.MessageInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ������Ϣaction
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 00:19
 */
public class OperateMessageAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_READ = "read";
    private static final String TYPE_DELETE = "delete";
    private static final String TYPE_NEXT_PAGE = "nextPage";

    /**
     * ��������
     */
    String type;
    String messageId;
    String countNow;


    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",messageId=" + messageId + ",countNow=" + countNow);
        //ajax���
        String resp;
        if(TYPE_READ.equals(type)){
            Message message = MessageDao.getMessageById(Integer.parseInt(messageId));
            if(null != message && MessageInterface.STATE_READED != message.getState()){//û�м�¼�����ѱ�ɾ��
                message.setState(MessageInterface.STATE_READED);
                message.setDate(date);
                message.setTime(time);
                message.setIp(getIp());
                MessageDao.updateMessage(message);
            }
            resp = "{isSuccess:true,message:'�Ķ���Ϣ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){
            Message message = MessageDao.getMessageById(Integer.parseInt(messageId));
            if(null != message){//û�м�¼�����ѱ�ɾ��
                MessageDao.deleteMessage(message);
            }
            resp = "{isSuccess:true,message:'ɾ����Ϣ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_NEXT_PAGE.equals(type)) {
            //��һҳ�� limit countNow~countNow+pageSize
            List<Message> nextPageMessages = MessageDao.queryMessagesByUserIdAndFromTo(getUser().getId(),
                    Integer.parseInt(countNow), Integer.parseInt(PropertyUtil.getInstance().
                            getProperty(BaseInterface.MESSAGE_PAGE_SIZE)));
            /**
             * ��һҳ��ϢJson��
             * replaceAll("\\\'", "\\\\\\\'")��ת��������
             * replaceAll("\\\"", "\\\\\\\"")��ת��˫����
             * replaceAll("\r\n", uuid)��ת�����з���uuid
             */
            String nextPageJson = BaseUtil.getJsonArrayFromMessages(nextPageMessages).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));
            //���ؽ��
            resp = "{isSuccess:true,message:'������һҳ��Ϣ�ɹ���',nextPageJson:'" + nextPageJson +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }
}
