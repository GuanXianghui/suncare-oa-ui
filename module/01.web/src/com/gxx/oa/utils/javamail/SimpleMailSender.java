package com.gxx.oa.utils.javamail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * ���ʼ��������������ʼ���������
 */
public class SimpleMailSender  {
    /**
     * ���ı���ʽ�����ʼ�
     * @param mailInfo �����͵��ʼ�����Ϣ
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) {
        // �ж��Ƿ���Ҫ������֤
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // �����Ҫ������֤���򴴽�һ��������֤��
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // ����session����һ���ʼ���Ϣ
            Message mailMessage = new MimeMessage(sendMailSession);
            // �����ʼ������ߵ�ַ
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // �����ʼ���Ϣ�ķ�����
            mailMessage.setFrom(from);
            // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO,to);
            // �����ʼ���Ϣ������
            mailMessage.setSubject(mailInfo.getSubject());
            // �����ʼ���Ϣ���͵�ʱ��
            mailMessage.setSentDate(new Date());
            // �����ʼ���Ϣ����Ҫ����
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // �����ʼ�
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * ��HTML��ʽ�����ʼ�
     * @param mailInfo �����͵��ʼ���Ϣ
     */
    public static boolean sendHtmlMail(MailSenderInfo mailInfo){
        // �ж��Ƿ���Ҫ������֤
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //�����Ҫ������֤���򴴽�һ��������֤��
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // ����session����һ���ʼ���Ϣ
            Message mailMessage = new MimeMessage(sendMailSession);
            // �����ʼ������ߵ�ַ
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // �����ʼ���Ϣ�ķ�����
            mailMessage.setFrom(from);
            // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
            Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO
            mailMessage.setRecipient(Message.RecipientType.TO,to);
            // �����ʼ���Ϣ������
            mailMessage.setSubject(mailInfo.getSubject());
            // �����ʼ���Ϣ���͵�ʱ��
            mailMessage.setSentDate(new Date());
            // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
            Multipart mainPart = new MimeMultipart();
            // ����һ������HTML���ݵ�MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // ����HTML����
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // ��MiniMultipart��������Ϊ�ʼ�����
            mailMessage.setContent(mainPart);
            // �����ʼ�
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * ��HTML��ʽȺ�����ʼ�
     * @param mailInfo �����͵��ʼ���Ϣ
     */
    public static boolean sendHtmlMailFetch(MailSenderInfo mailInfo){
        // �ж��Ƿ���Ҫ������֤
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //�����Ҫ������֤���򴴽�һ��������֤��
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // ����session����һ���ʼ���Ϣ
            Message mailMessage = new MimeMessage(sendMailSession);
            // �����ʼ������ߵ�ַ
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // �����ʼ���Ϣ�ķ�����
            mailMessage.setFrom(from);
            // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ�� ����
            //Address to = new InternetAddress(mailInfo.getToAddress());
            // Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO ����
            //mailMessage.setRecipient(Message.RecipientType.TO,to);

            //�����ʼ��Ľ����ߵ�ַ���飬�����õ��ʼ���Ϣ�� Ⱥ��
            InternetAddress address[]=new InternetAddress[mailInfo.getToAddressArray().length];
            for(int i=0;i<mailInfo.getToAddressArray().length;i++){
                address[i]=new InternetAddress(mailInfo.getToAddressArray()[i]);
            }
            //Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO Ⱥ��
            mailMessage.addRecipients(Message.RecipientType.TO, address);

            // �����ʼ���Ϣ������
            mailMessage.setSubject(mailInfo.getSubject());
            // �����ʼ���Ϣ���͵�ʱ��
            mailMessage.setSentDate(new Date());
            // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
            Multipart mainPart = new MimeMultipart();
            // ����һ������HTML���ݵ�MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // ����HTML����
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // ��MiniMultipart��������Ϊ�ʼ�����
            mailMessage.setContent(mainPart);
            // �����ʼ�
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }
} 
