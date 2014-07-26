package com.gxx.oa.interfaces;

/**
 * ��ɱһ����ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-7-26 23:02
 */
public interface MoneyInterface extends BaseInterface {
    /**
     * ��ɱұ䶯����
     *    ��Ϊ                               ��ɱұ䶯
     *    ��ʼ��                                +10
     *    ��½                                  +1(һ��ֻ��һ��)
     *    ����Ŀ�-�ϴ��ĵ�                     +3
     *    ����Ŀ�-�����ĵ�                     -1(������Լ����ĵ���������ɱ�)
     *    ����Ŀ�-ɾ���ĵ�                     -3
     *    ����Ŀ�-����Ա����ĵ���ͨ��         -3
     *    ���֪��-����                         +1
     *    (����OA�����ĵ����٣�һ��
     *    ʱ����֧���������ʣ� ֮��
     *    ĳ��ʱ�����ʲ��ٽ�����ɱ�)
     *    ���֪��-�ش�               ����˶�λش�ÿ����ֻ+2
     *    ���֪��-ɾ������            ������-1�����лش����-2
     *    ���֪��-ɾ���ش�           ��Ȼ���лش𲻼���ɱң�û����-2
     *    ���֪��-������ʲ�ͨ��      ������-1�����лش����-2
     *    ���֪��-��˻ش�ͨ��   ��Ȼ���лش𲻼���ɱң�û����-2
     *
     * �ݶ����û���ɱҿ�Ϊ�����������û���ɱ�Ϊ0���û��Կ��������ĵ���-1��ɱң������ɱ�Ϊ-1
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
