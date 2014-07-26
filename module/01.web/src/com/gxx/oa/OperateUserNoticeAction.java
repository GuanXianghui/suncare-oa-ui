package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.dao.UserNoticeDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.entities.UserNotice;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.interfaces.UserNoticeInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * �����û�����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 22:22
 */
public class OperateUserNoticeAction extends BaseAction {
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
    String noticeId;
    String countNow;


    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",noticeId=" + noticeId + ",countNow=" + countNow);
        //ajax���
        String resp;
        if(TYPE_READ.equals(type)){
            UserNotice notice = UserNoticeDao.getUserNoticeByUserIdAndNoticeId(getUser().getId(), Integer.parseInt(noticeId));
            if(null == notice){//û�м�¼������һ���Ѷ���¼
                notice = new UserNotice(getUser().getId(), Integer.parseInt(noticeId),
                        UserNoticeInterface.STATE_READED, date, time, getIp());
                UserNoticeDao.insertUserNotice(notice);
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_USER_NOTICE, "���˹������ �Ķ�����ɹ���", date, time, getIp());

            //�м�¼˵�������Ѷ�������ɾ���������޸�
            resp = "{isSuccess:true,message:'�Ķ�����ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){
            UserNotice notice = UserNoticeDao.getUserNoticeByUserIdAndNoticeId(getUser().getId(), Integer.parseInt(noticeId));
            if(null == notice){//û�м�¼������һ��ɾ����¼
                notice = new UserNotice(getUser().getId(), Integer.parseInt(noticeId),
                        UserNoticeInterface.STATE_DELETED, date, time, getIp());
                UserNoticeDao.insertUserNotice(notice);
            } else {//�м�¼˵�������Ѷ�������ɾ�����ظ����޸�
                notice.setState(UserNoticeInterface.STATE_DELETED);
                notice.setDate(date);
                notice.setTime(time);
                notice.setIp(getIp());
                UserNoticeDao.updateUserNotice(notice);
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_USER_NOTICE, "���˹������ ɾ������ɹ���", date, time, getIp());

            resp = "{isSuccess:true,message:'ɾ������ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else if(TYPE_NEXT_PAGE.equals(type)) {
            //��ɾ����ids
            String deleteIds = UserNoticeDao.queryUserNoticeIdsByUserIdAndState(getUser().getId(),
                    UserNoticeInterface.STATE_DELETED);
            //��һҳ���� limit countNow~countNow+pageSize
            List<Notice> nextPageNotices = NoticeDao.queryNoticesByFromToAndWithoutIds(Integer.parseInt(countNow),
                    Integer.parseInt(PropertyUtil.getInstance().
                            getProperty(BaseInterface.NOTICE_PAGE_SIZE)), deleteIds);
            /**
             * ��һҳ����Json��
             * replaceAll("\\\'", "\\\\\\\'")��ת��������
             * replaceAll("\\\"", "\\\\\\\"")��ת��˫����
             * replaceAll("\r\n", uuid)��ת�����з���uuid
             */
            String nextPageJson = BaseUtil.getJsonArrayFromNotices(nextPageNotices).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_USER_NOTICE, "���˹������ ������һҳ����ɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'������һҳ����ɹ���',nextPageJson:'" + nextPageJson +
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

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }
}
