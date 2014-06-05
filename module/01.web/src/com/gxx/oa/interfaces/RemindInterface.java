package com.gxx.oa.interfaces;

/**
 * ���ѽӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 15:10
 */
public interface RemindInterface extends BaseInterface {
    /**
     * �������� 1:������ 2:��Ϣ���� 3:�������� 4:�ʼ�����(Ŀǰֻ��1����2)
     */
    public static final int REMIND_TYPE_NO_REMIND = 1;
    public static final int REMIND_TYPE_MESSAGE = 2;
    public static final int REMIND_TYPE_SMS = 3;
    public static final int REMIND_TYPE_MAIL = 4;
}
