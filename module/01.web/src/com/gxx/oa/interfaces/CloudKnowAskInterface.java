package com.gxx.oa.interfaces;

/**
 * ���֪�����ʽӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface CloudKnowAskInterface extends BaseInterface {
    /**
     * �Ƿ���� 1 ������ 2 ����
     */
    public static final int URGENT_NO = 1;
    public static final int URGENT_YES = 2;

    /**
     * ״̬ 1 ���� 2 ɾ��
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_DELETE = 2;
}
