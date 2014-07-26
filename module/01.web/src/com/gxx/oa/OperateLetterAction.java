package com.gxx.oa;

import com.gxx.oa.dao.LetterDao;
import com.gxx.oa.entities.Letter;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

/**
 * ����վ����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-6 22:06
 */
public class OperateLetterAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//������һҳ
    private static final String TYPE_DELETE = "delete";//ɾ��
    private static final String TYPE_CTRL_DELETE = "ctrlDelete";//����ɾ��
    private static final String TYPE_SET_READED = "setReaded";//��ǳ��Ѷ�
    private static final String TYPE_RESTORE = "restore";//��ԭ

    /**
     * ��������
     */
    String type;//��������
    String ids;//ѡ��վ����ids
    String box;//վ����������
    String countNow;//Ŀǰ����

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",box=" + box + ",countNow=" + countNow);
        //ajax���
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//������һҳ
            //�����û�id���û����ͣ���ѯ���ͣ���Χ��վ����
            String nextPageLetters = BaseUtil.getJsonArrayFromLetters(LetterDao.queryLettersByTypeAndFromTo(getUser().getId(),
                    UserInterface.USER_TYPE_NORMAL, getTypeFromBox(box), Integer.parseInt(countNow),
                    Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.LETTER_PAGE_SIZE))), box).
                    replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"").
                    replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                    getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_LETTER, "վ���Ź��� ������һҳ��Ϣ�ɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'������һҳ��Ϣ�ɹ���',nextPageJson:'" + nextPageLetters +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){//ɾ��
            //ѭ��ɾ��վ����
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("��վ���Ų��Ǹ��û��ģ�userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setDeleteState(LetterInterface.DELETE_STATE_DELETED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_LETTER, "վ���Ź��� ɾ��վ���ųɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'ɾ��վ���ųɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_CTRL_DELETE.equals(type)){//����ɾ��
            //ѭ������ɾ��վ����
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("��վ���Ų��Ǹ��û��ģ�userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    LetterDao.deleteLetter(letter);
                }
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_LETTER, "վ���Ź��� ����ɾ��վ���ųɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'����ɾ��վ���ųɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_SET_READED.equals(type)){//��ǳ��Ѷ�
            //ѭ�����վ���ų��Ѷ�
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("��վ���Ų��Ǹ��û��ģ�userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setReadState(LetterInterface.READ_STATE_READED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_LETTER, "վ���Ź��� ���վ���ų��Ѷ��ɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'���վ���ų��Ѷ��ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_RESTORE.equals(type)){//��ԭ
            //ѭ����ԭվ����
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(SymbolInterface.SYMBOL_COMMA);
                for(int i=0;i<idArray.length;i++){
                    Letter letter = LetterDao.getLetterById(Integer.parseInt(idArray[i]));
                    if(letter.getUserId() != getUser().getId()){
                        logger.error("��վ���Ų��Ǹ��û��ģ�userId=" + getUser().getId() + ",letterId=" + letter.getId());
                        continue;
                    }
                    letter.setDeleteState(LetterInterface.DELETE_STATE_NOT_DELETED);
                    letter.setOperateDate(date);
                    letter.setOperateTime(time);
                    letter.setOperateIp(getIp());
                    LetterDao.updateLetter(letter);
                }
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_LETTER, "վ���Ź��� ��ԭվ���ųɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'��ԭվ���ųɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            resp = "{isSuccess:false,message:'������������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    /**
     * ��box�õ�type
     * @param box
     * @return
     */
    private int getTypeFromBox(String box){
        int type;
        if(StringUtils.equals(box, LetterInterface.BOX_SENT)){
            type = LetterInterface.TYPE_SENT;
        } else if(StringUtils.equals(box, LetterInterface.BOX_DELETED)){
            type = LetterInterface.TYPE_DELETED;
        } else {
            type = LetterInterface.TYPE_RECEIVED;
        }
        return type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
