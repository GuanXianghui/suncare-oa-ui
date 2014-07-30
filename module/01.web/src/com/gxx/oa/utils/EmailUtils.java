package com.gxx.oa.utils;

import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.utils.javamail.MailSenderInfo;
import com.gxx.oa.utils.javamail.SimpleMailSender;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件工具类
 * User: Gxx
 * Time: 2014-07-30 16:22
 */
public class EmailUtils implements BaseInterface
{
    /**
     * 日志处理器
     */
    public static Logger logger = Logger.getLogger(EmailUtils.class);

    /**
     * 发送邮件 单发
     * @param title
     * @param content
     * @param email
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String title, String content, String email) throws Exception
    {
        String emailHost = PropertyUtil.getInstance().getProperty(EMAIL_HOST);
        String emailPort = PropertyUtil.getInstance().getProperty(EMAIL_PORT);
        String emailName = PropertyUtil.getInstance().getProperty(EMAIL_NAME);
        String emailPassword = PropertyUtil.getInstance().getProperty(EMAIL_PASSWORD);
        logger.info("emailHost=[" + emailHost + "],emailPort=[" + emailPort + "],emailName=[" + emailName +
                "],emailPassword=[" + emailPassword + "],title=[" + title + "],content=[" + content +
                "],email=[" + email + "]");
        try{
            //发送邮件需要使用的基本信息
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost(emailHost);//发送邮件服务器接受
            mailInfo.setMailServerPort(emailPort);//端口
            mailInfo.setValidate(true);//校验用户名
            mailInfo.setUserName(emailName);//邮箱用户名
            mailInfo.setPassword(emailPassword);//邮箱密码
            mailInfo.setFromAddress(emailName);//邮箱用户名
            mailInfo.setToAddress(email);//接收方邮箱
            mailInfo.setSubject(title);//邮件抬头
            mailInfo.setContent(content);//邮件内容
            //简单邮件（不带附件的邮件）发送器
            SimpleMailSender sms = new SimpleMailSender();
            //sms.sendTextMail(mailInfo);//发送文体格式
            sms.sendHtmlMail(mailInfo);//发送html格式
        }catch (Exception e) {
            logger.error("异常发生", e);
        }catch (Error e) {
            logger.error("错误发生", e);
        }finally{

        }
        return true;
    }

    /**
     * 发送邮件 群发
     * @param title
     * @param content
     * @param emailList 邮件集合
     * @return
     * @throws Exception
     */
    public static boolean sendEmailFetch(String title, String content, List<String> emailList) throws Exception
    {
        String emailHost = PropertyUtil.getInstance().getProperty(EMAIL_HOST);
        String emailPort = PropertyUtil.getInstance().getProperty(EMAIL_PORT);
        String emailName = PropertyUtil.getInstance().getProperty(EMAIL_NAME);
        String emailPassword = PropertyUtil.getInstance().getProperty(EMAIL_PASSWORD);
        logger.info("emailHost=[" + emailHost + "],emailPort=[" + emailPort + "],emailName=[" + emailName +
                "],emailPassword=[" + emailPassword + "],title=[" + title + "],content=[" + content +
                "],emailList.size=[" + emailList.size() + "]");
        try{
            //群发邮件批次大小 即一批可以群发多少个邮件
            int emailFetchSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(EMAIL_FETCH_SIZE));
            //管理员邮箱，用途：群发邮件时带上管理员邮箱，查看管理员邮箱是否收到邮件，用于检测该批次是否被邮件服务器屏蔽
            String emailAdministrator = PropertyUtil.getInstance().getProperty(EMAIL_ADMINISTRATOR);
            /**
             * 多个邮箱分批次群发，批次上限从配置文件读取
             */
            List<String> tempEmailList = new ArrayList<String>();
            for(int i=0;i<emailList.size();i++){
                if(StringUtils.isBlank(emailList.get(i))){
                    continue;
                }
                tempEmailList.add(emailList.get(i));
                //一旦达到 群发邮件批次大小 就群发
                if(tempEmailList.size() == emailFetchSize){
                    //群发前带上管理员邮箱 用于检测该批次是否被邮件服务器屏蔽
                    tempEmailList.add(emailAdministrator);
                    //将集合转化成数组
                    String[] toEmailArray = new String[tempEmailList.size()];
                    for(int j=0;j<tempEmailList.size();j++){
                        String email = tempEmailList.get(j);
                        toEmailArray[j] = email;
                    }
                    //发送邮件需要使用的基本信息 群发邮件
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost(emailHost);//发送邮件服务器接受
                    mailInfo.setMailServerPort(emailPort);//端口
                    mailInfo.setValidate(true);//校验用户名
                    mailInfo.setUserName(emailName);//邮箱用户名
                    mailInfo.setPassword(emailPassword);//邮箱密码
                    mailInfo.setFromAddress(emailName);//邮箱用户名
                    mailInfo.setToAddressArray(toEmailArray);//接收方邮箱
                    mailInfo.setSubject(title);//邮件抬头
                    mailInfo.setContent(content);//邮件内容
                    //简单邮件（不带附件的邮件）发送器
                    SimpleMailSender sms = new SimpleMailSender();
                    //sms.sendTextMail(mailInfo);//发送文体格式
                    sms.sendHtmlMailFetch(mailInfo);//发送html格式

                    //初始化邮件集合
                    tempEmailList = new ArrayList<String>();
                }
            }

            //判余下最后一批
            if(tempEmailList.size() > 0){
                //将集合转化成数组
                String[] toEmailArray = new String[tempEmailList.size()];
                for(int j=0;j<tempEmailList.size();j++){
                    String email = tempEmailList.get(j);
                    toEmailArray[j] = email;
                }
                //发送邮件需要使用的基本信息 群发邮件
                MailSenderInfo mailInfo = new MailSenderInfo();
                mailInfo.setMailServerHost(emailHost);//发送邮件服务器接受
                mailInfo.setMailServerPort(emailPort);//端口
                mailInfo.setValidate(true);//校验用户名
                mailInfo.setUserName(emailName);//邮箱用户名
                mailInfo.setPassword(emailPassword);//邮箱密码
                mailInfo.setFromAddress(emailName);//邮箱用户名
                mailInfo.setToAddressArray(toEmailArray);//接收方邮箱
                mailInfo.setSubject(title);//邮件抬头
                mailInfo.setContent(content);//邮件内容
                //简单邮件（不带附件的邮件）发送器
                SimpleMailSender sms = new SimpleMailSender();
                //sms.sendTextMail(mailInfo);//发送文体格式
                sms.sendHtmlMailFetch(mailInfo);//发送html格式
            }
        }catch (Exception e) {
            logger.error("异常发生", e);
        }catch (Error e) {
            logger.error("错误发生", e);
        }finally{

        }
        return true;
    }

    /**
     * main函数
     * @param param
     */
    public static void main(String[] param) throws Exception {
        EmailUtils.sendEmail("测试title", "<font color='green'>设置邮箱内容xxxxxxxxx</font>", "419066357@qq.com,419066357@163.com");
    }
}
