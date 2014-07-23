package com.gxx.oa.interfaces;

/**
 * 用户基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserRightInterface extends BaseInterface {
    /**
     * 后台用户管理	0001
     * 组织架构管理	0002
     * 公告管理	0003
     * 默认权限	0004
     * 权限管理	0005
     */
    public static final String RIGHT_0001_USER_OPERATE = "0001";
    public static final String RIGHT_0002_ORG_STRUCTURE_MANAGE = "0002";
    public static final String RIGHT_0003_CONFIG_NOTICE = "0003";
    public static final String RIGHT_0004_DEFAULT_RIGHT = "0004";
    public static final String RIGHT_0005_USER_RIGHT = "0005";
}
