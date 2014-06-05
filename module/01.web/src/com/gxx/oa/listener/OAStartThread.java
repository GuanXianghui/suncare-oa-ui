package com.gxx.oa.listener;

import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.dao.RemindDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Message;
import com.gxx.oa.entities.PublicUser;
import com.gxx.oa.entities.Remind;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.MessageInterface;
import com.gxx.oa.interfaces.PublicUserInterface;
import com.gxx.oa.interfaces.UserInterface;
import com.gxx.oa.utils.DateUtil;
import com.gxx.oa.utils.PublicUserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OA�����߳�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 19:56
 */
public class OAStartThread extends Thread
{
    /**
     * ��־������
     */
    Logger logger = Logger.getLogger(OAStartThread.class);

    /**
     * �߳�ִ����
     */
    public void run() {
        while(!this.isInterrupted()) {//�߳�δ�ж�ִ��ѭ��
            try {
                Thread.sleep(60000); //ÿ��60sִ��һ��
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm00");
            String remindBeginDateStr = format.format(date);
            String remindEndDateStr = format.format(DateUtils.addMinutes(date, 1));
            //------------------ ��ʼִ�� ---------------------------
            logger.info("����ѭ������ʱ���remindBeginDateStr:" + remindBeginDateStr
                    + ",remindEndDateStr:" + remindEndDateStr);
            //������ֹ�������ڲ�����
            try {
                List<Remind> reminds = RemindDao.queryRemindsBetweenRemindDateTime
                        (remindBeginDateStr, remindEndDateStr);
                for(Remind remind : reminds){
                    User user = UserDao.getUserById(remind.getUserId());
                    PublicUser publicUser = PublicUserUtil.getInstance().getPublicUserByEnglishName
                            (PublicUserInterface.SUNCARE_OA_MESSAGE);
                    //����ʱ�䵽�ˣ���д�������ѵ��û�������Ϣ
                    Message message = new Message(publicUser.getId(), UserInterface.USER_TYPE_PUBLIC,
                            user.getId(), "�����������ʱ�䵽�������<a target=\"_blank\" " +
                            "href=\"/calendar.jsp\">����</>", MessageInterface.STATE_NOT_READED,
                            DateUtil.getNowDate(), DateUtil.getNowTime(), StringUtils.EMPTY);
                    MessageDao.insertMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
