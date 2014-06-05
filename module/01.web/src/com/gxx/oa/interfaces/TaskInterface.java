package com.gxx.oa.interfaces;

/**
 * ����ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 16:38
 */
public interface TaskInterface extends BaseInterface {
    /**
     * ״̬ 1:�½� 2:������ 3:��� 4:�ϳ�
     */
    public static final int STATE_NEW = 1;
    public static final int STATE_ING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_DROP = 4;

    /**
     * ���� 1:������ҵ� 2:�ҷ����
     */
    public static final int TYPE_TO_ME = 1;
    public static final int TYPE_FROM_ME = 2;
}
