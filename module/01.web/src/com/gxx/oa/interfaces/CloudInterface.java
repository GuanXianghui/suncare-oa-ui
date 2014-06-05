package com.gxx.oa.interfaces;

/**
 * ����ƽӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface CloudInterface extends BaseInterface {
    /**
     * ��Ŀ¼Ϊ��б��/
     */
    public static final String FRONT_DIR = SymbolInterface.SYMBOL_SLASH;
    /**
     * ��Ŀ¼PID��λ0
     */
    public static final int FRONT_DIR_PID = 0;

    /**
     * ״̬ 1 ���� 2 ɾ�� 3 ����ɾ��
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_DELETE = 2;
    public static final int STATE_CTRL_DELETE = 3;

    /**
     * ���� 1 �ļ� 2 �ļ��� 3 ϵͳ�ļ�
     */
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DIR = 2;
    public static final int TYPE_SYSTEM_FILE = 3;
}
