package com.gxx.oa.utils;

import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.utils.javamail.MailSenderInfo;
import com.gxx.oa.utils.javamail.SimpleMailSender;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * �ʼ�������
 * User: Gxx
 * Time: 2014-07-30 16:22
 */
public class EmailUtils implements BaseInterface
{
    /**
     * ��־������
     */
    public static Logger logger = Logger.getLogger(EmailUtils.class);

    /**
     * �����ʼ� ����
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
            //�����ʼ���Ҫʹ�õĻ�����Ϣ
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost(emailHost);//�����ʼ�����������
            mailInfo.setMailServerPort(emailPort);//�˿�
            mailInfo.setValidate(true);//У���û���
            mailInfo.setUserName(emailName);//�����û���
            mailInfo.setPassword(emailPassword);//��������
            mailInfo.setFromAddress(emailName);//�����û���
            mailInfo.setToAddress(email);//���շ�����
            mailInfo.setSubject(title);//�ʼ�̧ͷ
            mailInfo.setContent(content);//�ʼ�����
            //���ʼ��������������ʼ���������
            SimpleMailSender sms = new SimpleMailSender();
            //sms.sendTextMail(mailInfo);//���������ʽ
            sms.sendHtmlMail(mailInfo);//����html��ʽ
        }catch (Exception e) {
            logger.error("�쳣����", e);
        }catch (Error e) {
            logger.error("������", e);
        }finally{

        }
        return true;
    }

    /**
     * �����ʼ� Ⱥ��
     * @param title
     * @param content
     * @param emailList �ʼ�����
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
            //Ⱥ���ʼ����δ�С ��һ������Ⱥ�����ٸ��ʼ�
            int emailFetchSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(EMAIL_FETCH_SIZE));
            //����Ա���䣬��;��Ⱥ���ʼ�ʱ���Ϲ���Ա���䣬�鿴����Ա�����Ƿ��յ��ʼ������ڼ��������Ƿ��ʼ�����������
            String emailAdministrator = PropertyUtil.getInstance().getProperty(EMAIL_ADMINISTRATOR);
            /**
             * ������������Ⱥ�����������޴������ļ���ȡ
             */
            List<String> tempEmailList = new ArrayList<String>();
            for(int i=0;i<emailList.size();i++){
                if(StringUtils.isBlank(emailList.get(i))){
                    continue;
                }
                tempEmailList.add(emailList.get(i));
                //һ���ﵽ Ⱥ���ʼ����δ�С ��Ⱥ��
                if(tempEmailList.size() == emailFetchSize){
                    //Ⱥ��ǰ���Ϲ���Ա���� ���ڼ��������Ƿ��ʼ�����������
                    tempEmailList.add(emailAdministrator);
                    //������ת��������
                    String[] toEmailArray = new String[tempEmailList.size()];
                    for(int j=0;j<tempEmailList.size();j++){
                        String email = tempEmailList.get(j);
                        toEmailArray[j] = email;
                    }
                    //�����ʼ���Ҫʹ�õĻ�����Ϣ Ⱥ���ʼ�
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost(emailHost);//�����ʼ�����������
                    mailInfo.setMailServerPort(emailPort);//�˿�
                    mailInfo.setValidate(true);//У���û���
                    mailInfo.setUserName(emailName);//�����û���
                    mailInfo.setPassword(emailPassword);//��������
                    mailInfo.setFromAddress(emailName);//�����û���
                    mailInfo.setToAddressArray(toEmailArray);//���շ�����
                    mailInfo.setSubject(title);//�ʼ�̧ͷ
                    mailInfo.setContent(content);//�ʼ�����
                    //���ʼ��������������ʼ���������
                    SimpleMailSender sms = new SimpleMailSender();
                    //sms.sendTextMail(mailInfo);//���������ʽ
                    sms.sendHtmlMailFetch(mailInfo);//����html��ʽ

                    //��ʼ���ʼ�����
                    tempEmailList = new ArrayList<String>();
                }
            }

            //���������һ��
            if(tempEmailList.size() > 0){
                //������ת��������
                String[] toEmailArray = new String[tempEmailList.size()];
                for(int j=0;j<tempEmailList.size();j++){
                    String email = tempEmailList.get(j);
                    toEmailArray[j] = email;
                }
                //�����ʼ���Ҫʹ�õĻ�����Ϣ Ⱥ���ʼ�
                MailSenderInfo mailInfo = new MailSenderInfo();
                mailInfo.setMailServerHost(emailHost);//�����ʼ�����������
                mailInfo.setMailServerPort(emailPort);//�˿�
                mailInfo.setValidate(true);//У���û���
                mailInfo.setUserName(emailName);//�����û���
                mailInfo.setPassword(emailPassword);//��������
                mailInfo.setFromAddress(emailName);//�����û���
                mailInfo.setToAddressArray(toEmailArray);//���շ�����
                mailInfo.setSubject(title);//�ʼ�̧ͷ
                mailInfo.setContent(content);//�ʼ�����
                //���ʼ��������������ʼ���������
                SimpleMailSender sms = new SimpleMailSender();
                //sms.sendTextMail(mailInfo);//���������ʽ
                sms.sendHtmlMailFetch(mailInfo);//����html��ʽ
            }
        }catch (Exception e) {
            logger.error("�쳣����", e);
        }catch (Error e) {
            logger.error("������", e);
        }finally{

        }
        return true;
    }

    /**
     * main����
     * @param param
     */
    public static void main(String[] param) throws Exception {
        EmailUtils.sendEmail("����title", "<font color='green'>������������xxxxxxxxx</font>", "419066357@qq.com,419066357@163.com");
    }
}
