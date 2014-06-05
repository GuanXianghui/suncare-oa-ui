package com.gxx.oa.interfaces;

/**
 * վ���Žӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 18:10
 */
public interface LetterInterface extends BaseInterface {
    /**
     * ���ͻ��ǽ��� 1:���� 2:����
     */
    public static final int SEND = 1;
    public static final int RECEIVE = 2;

    /**
     * δ�������Ѷ� 1:δ�� 2:�Ѷ�
     */
    public static final int READ_STATE_NOT_READED = 1;
    public static final int READ_STATE_READED = 2;

    /**
     * δɾ������ɾ�� 1:δɾ�� 2:ɾ��
     */
    public static final int DELETE_STATE_NOT_DELETED = 1;
    public static final int DELETE_STATE_DELETED = 2;

    /**
     * ���� 1:�ѽ��� 2:�ѷ��� 3:��ɾ��
     */
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_SENT = 2;
    public static final int TYPE_DELETED = 3;

    /**
     * վ�������� received:�ռ��� sent:�ѷ��� deleted:��ɾ��
     */
    public static final String BOX_RECEIVED = "received";
    public static final String BOX_SENT = "sent";
    public static final String BOX_DELETED = "deleted";

    /**
     * д������ write:д�� reply:�ظ� replyAll:�ظ�ȫ�� transmit:ת��
     */
    public static final String WRITE_TYPE_WRITE = "write";
    public static final String WRITE_TYPE_REPLY = "reply";
    public static final String WRITE_TYPE_REPLY_ALL = "replyAll";
    public static final String WRITE_TYPE_TRANSMIT = "transmit";
}
