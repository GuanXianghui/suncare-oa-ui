package com.gxx.oa.interfaces;

/**
 * �û������ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserRightInterface extends BaseInterface {
    /**
     * ��̨�û�����	0001
     * ��֯�ܹ�����	0002
     * �������	0003
     * Ĭ��Ȩ��	0004
     * Ȩ�޹���	0005
     */
    public static final String RIGHT_0001_USER_OPERATE = "0001";
    public static final String RIGHT_0002_ORG_STRUCTURE_MANAGE = "0002";
    public static final String RIGHT_0003_CONFIG_NOTICE = "0003";
    public static final String RIGHT_0004_DEFAULT_RIGHT = "0004";
    public static final String RIGHT_0005_USER_RIGHT = "0005";
}
