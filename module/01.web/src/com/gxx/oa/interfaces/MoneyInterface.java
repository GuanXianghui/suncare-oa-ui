package com.gxx.oa.interfaces;

/**
 * 申成币基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-7-26 23:02
 */
public interface MoneyInterface extends BaseInterface {
    /**
     * 申成币变动配置
     *    行为                               申成币变动
     *    初始化                                +10
     *    登陆                                  +1(一天只有一次)
     *    申成文库-上传文档                     +3
     *    申成文库-下载文档                     -1(如果是自己的文档，不扣申成币)
     *    申成文库-删除文档                     -3
     *    申成文库-管理员审核文档不通过         -3
     *    申成知道-提问                         +1
     *    (由于OA上线文档较少，一段
     *    时间内支持主动提问， 之后
     *    某个时间提问不再奖励申成币)
     *    申成知道-回答               多个人多次回答，每个人只+2
     *    申成知道-删除提问            提问者-1，所有回答的人-2
     *    申成知道-删除回答           仍然还有回答不减申成币，没有则-2
     *    申成知道-审核提问不通过      提问者-1，所有回答的人-2
     *    申成知道-审核回答不通过   仍然还有回答不减申成币，没有则-2
     *
     * 暂定，用户申成币可为负数，比如用户申成币为0，用户仍可以下载文档，-1申成币，结果申成币为-1
     */
    public static final String ACT_INITIAL = "+10";
    public static final String ACT_LOG_IN = "+1";
    public static final String ACT_CLOUD_DOC_UPLOAD = "+3";
    public static final String ACT_CLOUD_DOC_DOWNLOAD = "-1";
    public static final String ACT_CLOUD_DOC_DELETE = "-3";
    public static final String ACT_CLOUD_DOC_CHECK_FAIL = "-3";
    public static final String ACT_CLOUD_KNOW_ASK = "+1";
    public static final String ACT_CLOUD_KNOW_ANSWER = "+2";
    public static final String ACT_CLOUD_KNOW_DELETE_ASK_TO_ASK = "-1";
    public static final String ACT_CLOUD_KNOW_DELETE_ASK_TO_ANSWER = "-2";
    public static final String ACT_CLOUD_KNOW_DELETE_ANSWER = "-2";
    public static final String ACT_CLOUD_KNOW_CHECK_FAIL_ASK_TO_ASK = "-1";
    public static final String ACT_CLOUD_KNOW_CHECK_FAIL_ASK_TO_ANSWER = "-2";
    public static final String ACT_CLOUD_KNOW_CHECK_FAIL_ANSWER = "-2";
}
