package com.gxx.oa;

import com.gxx.oa.dao.LetterDao;
import com.gxx.oa.entities.Letter;
import com.gxx.oa.interfaces.LetterInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.interfaces.UserInterface;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * д��action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-6 20:02
 */
public class WriteLetterAction extends BaseAction {
    //�ռ���
    private String toUserIds;
    //����
    private String ccUserIds;
    //����
    private String title;
    //����
    private String content;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("toUserIds:" + toUserIds + ",ccUserIds:" + ccUserIds + ",title:" + title + ",content:" + content);
        // 1 ����һ�����͵�վ����
        Letter sendLetter = new Letter(getUser().getId(), UserInterface.USER_TYPE_NORMAL, LetterInterface.SEND,
                getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_READED,
                LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        LetterDao.insertLetter(sendLetter);

        // 2 �������ܵ�վ����
        if(StringUtils.isNotBlank(toUserIds)){
            String[] ids = toUserIds.split(SymbolInterface.SYMBOL_COMMA);
            for(int i=0;i<ids.length;i++){
                sendLetter = new Letter(Integer.parseInt(ids[i]), UserInterface.USER_TYPE_NORMAL, LetterInterface.RECEIVE,
                        getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                        ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_NOT_READED,
                        LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                LetterDao.insertLetter(sendLetter);
            }
        }

        // 3 �������͵�վ����
        if(StringUtils.isNotBlank(ccUserIds)){
            String[] ids = ccUserIds.split(SymbolInterface.SYMBOL_COMMA);
            for(int i=0;i<ids.length;i++){
                sendLetter = new Letter(Integer.parseInt(ids[i]), UserInterface.USER_TYPE_NORMAL, LetterInterface.RECEIVE,
                        getUser().getId(), UserInterface.USER_TYPE_NORMAL, toUserIds, getUserTypesFromUserIds(toUserIds),
                        ccUserIds, getUserTypesFromUserIds(ccUserIds), LetterInterface.READ_STATE_NOT_READED,
                        LetterInterface.DELETE_STATE_NOT_DELETED, title, content, date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                LetterDao.insertLetter(sendLetter);
            }
        }

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_WRITE_LETTER, "дվ���ųɹ���", date, time, getIp());

        message = "д�ųɹ���";
        return SUCCESS;
    }

    /**
     * �����û�ids(���ŷָ�)���õ��û�����(���ŷָ�)
     * todo ��ʱ����ôд����Ϊǰ��û�д������û�����
     * @param userIds
     * @return
     */
    private String getUserTypesFromUserIds(String userIds){
        String userTypes = StringUtils.EMPTY;
        if(StringUtils.isBlank(userIds)){
            return userTypes;
        }
        for(int i=0;i<userIds.split(SymbolInterface.SYMBOL_COMMA).length;i++){
            if(StringUtils.isNotBlank(userTypes)){
                userTypes += SymbolInterface.SYMBOL_COMMA;
            }
            userTypes += UserInterface.USER_TYPE_NORMAL;
        }
        return userTypes;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getCcUserIds() {
        return ccUserIds;
    }

    public void setCcUserIds(String ccUserIds) {
        this.ccUserIds = ccUserIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
