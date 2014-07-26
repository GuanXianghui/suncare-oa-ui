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
import com.gxx.oa.interfaces.RemindInterface;
import com.gxx.oa.interfaces.UserInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.DateUtil;
import com.gxx.oa.utils.PublicUserUtil;
import com.gxx.oa.utils.SMSUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OA启动线程
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 19:56
 */
public class OAStartThread extends Thread
{
    /**
     * 日志处理器
     */
    Logger logger = Logger.getLogger(OAStartThread.class);

    /**
     * 线程执行体
     */
    public void run() {
        while(!this.isInterrupted()) {//线程未中断执行循环
            try {
                Thread.sleep(60000); //每隔60s执行一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm00");
            String remindBeginDateStr = format.format(date);
            String remindEndDateStr = format.format(DateUtils.addMinutes(date, 1));
            //------------------ 开始执行 ---------------------------
            logger.info("本次循环提醒时间段remindBeginDateStr:" + remindBeginDateStr
                    + ",remindEndDateStr:" + remindEndDateStr);
            //根据起止提醒日期查提醒
            try {
                List<Remind> reminds = RemindDao.queryRemindsBetweenRemindDateTime
                        (remindBeginDateStr, remindEndDateStr);
                for(Remind remind : reminds){
                    User user = UserDao.getUserById(remind.getUserId());
                    //消息提醒
                    if(remind.getRemindType() == RemindInterface.REMIND_TYPE_MESSAGE){
                        //公众账号给用户发一条消息
                        BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                                "你的提醒时间到啦！请见<a target=\"_blank\" href=\"/calendar.jsp?id=" +
                                        remind.getId() + "\">链接</>", StringUtils.EMPTY);
                    } else if(remind.getRemindType() == RemindInterface.REMIND_TYPE_SMS){//短信提醒
                        //没有设置手机号 则 消息提醒
                        if(StringUtils.isBlank(user.getMobileTel())){
                            //公众账号给用户发一条消息
                            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                                    "你的提醒时间到啦，由于你未配置你的手机，所以不能成功短信提醒！请见<a target=\"_blank\" " +
                                            "href=\"/calendar.jsp?id=" + remind.getId() + "\">链接</>", StringUtils.EMPTY);
                        } else {//短信提醒
                            String content = "来自申成门窗OA系统的提醒，提醒内容：[" + remind.getContent() + "]！祝您工作顺利！";
                            SMSUtil.sendSMS(user.getMobileTel(), content);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
