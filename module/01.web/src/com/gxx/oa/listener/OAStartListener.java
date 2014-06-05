package com.gxx.oa.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * OA����������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 19:56
 */
public class OAStartListener implements ServletContextListener
{
    /**
     * �߳�
     */
    private OAStartThread startThread;

    /**
     * ��ʼ��
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (startThread == null) {
            startThread = new OAStartThread();
            startThread.start();
        }
    }

    /**
     * ����
     * @param servletContextEvent
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (startThread != null && startThread.isInterrupted()) {
            startThread.interrupt();
        }
    }
}
