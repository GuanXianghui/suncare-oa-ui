package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * �������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 12:38
 */
public class ConfigNoticeAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_ADD = "add";
    private static final String TYPE_UPDATE = "update";
    private static final String TYPE_DELETE = "delete";

    //����id
    private String noticeId;
    //��������
    private String type;
    //����
    private String title;
    //����
    private String content;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0003_CONFIG_NOTICE);
        logger.info("noticeId:" + noticeId + ",type:" + type + ",title:" + title + ",content:" + content);
        //���������ж�
        if(StringUtils.equals(TYPE_ADD, type)) {
            Notice notice = new Notice(title, content, date, time, getIp(),
                    StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            NoticeDao.insertNotice(notice);
            message = "��������ɹ���";

            //���������Ⱥ���ʼ�֪ͨ������
            List<User> userList = UserDao.queryAllUsers();
            List<String> emailList = new ArrayList<String>();
            for(User user : userList){
                if(StringUtils.isNotBlank(user.getEmail())){
                    emailList.add(user.getEmail());
                }
            }
            /**
             * Ⱥ���ʼ�
             */
            //�ʼ�title
            String title = "����Ŵ�OAϵͳ-��˾����";
            //�ʼ�����
            String content = "��ã�<br><br>" +
                    "����Ŵ�OAϵͳ������˾���棺[" + notice.getTitle() + "]����<a href=\"http://www.suncare-sys.com:10000/notice.jsp\" target=\"_blank\">����</a>��<br><br>" +
                    "ף������˳����<br><br>" +
                    "����Ŵ�OAϵͳ";
            //�����ʼ� Ⱥ��
            EmailUtils.sendEmailFetch(title, content, emailList);
        } else if(StringUtils.equals(TYPE_UPDATE, type)) {
            Notice notice = NoticeDao.getNoticeById(Integer.parseInt(noticeId));
            notice.setTitle(title);
            notice.setContent(content);
            notice.setUpdateDate(date);
            notice.setUpdateTime(time);
            notice.setUpdateIp(getIp());
            NoticeDao.updateNotice(notice);
            message = "�޸Ĺ���ɹ���";
        } else if(StringUtils.equals(TYPE_DELETE, type)) {
            Notice notice = NoticeDao.getNoticeById(Integer.parseInt(noticeId));
            NoticeDao.deleteNotice(notice);
            message = "ɾ������ɹ���";
        } else {
            message = "����������������";
            return ERROR;
        }

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CONFIG_NOTICE, "������� " + message, date, time, getIp());

        return SUCCESS;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
