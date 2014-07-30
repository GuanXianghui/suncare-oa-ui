package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.dao.DiaryReviewDao;
import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.Diary;
import com.gxx.oa.entities.DiaryReview;
import com.gxx.oa.entities.Message;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.EmailUtils;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ����������־action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-7 21:53
 */
public class OperateDiaryAction extends BaseAction {
    /**
     * ��������
     */
    private static final String TYPE_NEXT_PAGE = "nextPage";//������һҳ
    private static final String TYPE_DELETE = "delete";//ɾ��
    private static final String TYPE_ZAN = "zan";//����
    private static final String TYPE_CANCEL_ZAN = "cancelZan";//ȡ����
    private static final String TYPE_CREATE_REVIEW = "createReview";//��������
    private static final String TYPE_UPDATE_REVIEW = "updateReview";//�޸�����
    private static final String TYPE_DELETE_REVIEW = "deleteReview";//ɾ������
    private static final String TYPE_REPLY_REVIEW = "replyReview";//�ظ�����

    /**
     * ��������
     */
    String type;//��������
    String countNow;//Ŀǰ����
    String userId;//��ѡ�û�id
    String date;//��ѡ����
    String diaryId;//������־id
    String content;//��������
    String updateReviewId;//�޸�����id
    String deleteReviewId;//ɾ������id

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("type:" + type + ",countNow=" + countNow + ",userId=" + userId + ",date=" + date +
                ",diaryId=" + diaryId);
        //ajax���
        String resp;
        if(TYPE_NEXT_PAGE.equals(type)){//������һҳ ��Ϊ���޷�ҳ ���Բ������÷�֧
            int userIdInt = Integer.parseInt(userId);//û��ѡ������Ϊ0
            int countNowInt = Integer.parseInt(countNow);
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(getUser().getPosition());//��Ȩ�޿����¼��û�
            //�����û�id�����ڣ���Χ�鹤����־
            String nextPageDiaries = BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(userIdInt, date,
                    countNowInt, Integer.parseInt(PropertyUtil.getInstance().
                    getProperty(BaseInterface.DIARY_PAGE_SIZE)), rightUserWithComma)).replaceAll("\\\'", "\\\\\\\'").
                    replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                    PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ������һҳ������־�ɹ���", date, time, getIp());

            //���ؽ��
            resp = "{isSuccess:true,message:'������һҳ������־�ɹ���',nextPageJson:'" + nextPageDiaries +
                    "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        } else if(TYPE_DELETE.equals(type)){//ɾ��
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //�в����ڸù�����־�����ߣ��ù�����־�����ڸ��û��Ĳ����޸�
            if(diary == null || diary.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryDao.deleteDiary(diary);
                //ɾ�����е�����
                List<DiaryReview> diaryReviewList = DiaryReviewDao.queryDiaryReviews(diary.getId());
                for(DiaryReview diaryReview : diaryReviewList){
                    DiaryReviewDao.deleteDiaryReview(diaryReview);
                }

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ɾ��������־�ɹ���", date, time, getIp());

                resp = "{isSuccess:true,message:'ɾ��������־�ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_ZAN.equals(type)){//����
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //�в����ڸù�����־
            if(diary == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //���Ƿ��ѵ��޹�
                boolean hasAllReadyZan = DiaryReviewDao.hasAllReadyZan(diary.getId(), getUser().getId());
                if(hasAllReadyZan){
                    resp = "{isSuccess:false,message:'���Ѿ�����ޣ�',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                } else {
                    DiaryReview diaryReview = new DiaryReview(getUser().getId(), diary.getId(),
                            DiaryReviewInterface.TYPE_ZAN, 0, StringUtils.EMPTY, super.date, time, getIp(),
                            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                    DiaryReviewDao.insertDiaryReview(diaryReview);

                    //����������־
                    BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ���޳ɹ���", date, time, getIp());

                    //����ͬһ������֪ͨ
                    if(getUser().getId() != diary.getUserId()){
                        //��ͨ�û��������û���һ����Ϣ
                        BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                                getUser().getName() + "�������־�����ˣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                        User diaryUser = UserDao.getUserById(diary.getUserId());

                        //���ʼ�
                        if(StringUtils.isNotBlank(diaryUser.getEmail())){
                            //�ʼ�title
                            String title = "����Ŵ�OAϵͳ-��־����";
                            //�ʼ�����
                            String content = diaryUser.getName() + "��ã�<br><br>" +
                                    getUser().getName() + "������Ŵ�OAϵͳ�������־�����ˣ���<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                    "ף������˳����<br><br>" +
                                    "����Ŵ�OAϵͳ";
                            //�����ʼ�
                            EmailUtils.sendEmail(title, content, diaryUser.getEmail());
                        }
                    }

                    resp = "{isSuccess:true,message:'���޳ɹ���',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                }
            }
        } else if(TYPE_CANCEL_ZAN.equals(type)){//ȡ����
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //�в����ڸù�����־
            if(diary == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                //���Ƿ��ѵ��޹�
                boolean hasAllReadyZan = DiaryReviewDao.hasAllReadyZan(diary.getId(), getUser().getId());
                if(!hasAllReadyZan){
                    resp = "{isSuccess:false,message:'��δ����ޣ�',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                } else {
                    DiaryReview diaryReview = DiaryReviewDao.getZan(diary.getId(), getUser().getId());
                    DiaryReviewDao.deleteDiaryReview(diaryReview);

                    //����������־
                    BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ȡ���޳ɹ���", date, time, getIp());

                    //����ͬһ������֪ͨ
                    if(getUser().getId() != diary.getUserId()){
                        //��ͨ�û��������û���һ����Ϣ
                        BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                                getUser().getName() + "�������־ȡ�����ˣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                        User diaryUser = UserDao.getUserById(diary.getUserId());

                        //���ʼ�
                        if(StringUtils.isNotBlank(diaryUser.getEmail())){
                            //�ʼ�title
                            String title = "����Ŵ�OAϵͳ-��־ȡ����";
                            //�ʼ�����
                            String content = diaryUser.getName() + "��ã�<br><br>" +
                                    getUser().getName() + "������Ŵ�OAϵͳ�������־ȡ�����ˣ���<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                    "ף������˳����<br><br>" +
                                    "����Ŵ�OAϵͳ";
                            //�����ʼ�
                            EmailUtils.sendEmail(title, content, diaryUser.getEmail());
                        }
                    }

                    resp = "{isSuccess:true,message:'ȡ���޳ɹ���',hasNewToken:true,token:'" +
                            TokenUtil.createToken(request) + "'}";
                }
            }
        } else if(TYPE_CREATE_REVIEW.equals(type)){//��������
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            //�в����ڸù�����־��������
            if(diary == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReview diaryReview = new DiaryReview(getUser().getId(), diary.getId(),
                        DiaryReviewInterface.TYPE_MESSAGE, 0, content, super.date, time, getIp(),
                        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                DiaryReviewDao.insertDiaryReview(diaryReview);

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ���۹�����־�ɹ���", date, time, getIp());

                //����ͬһ������֪ͨ
                if(getUser().getId() != diary.getUserId()){
                    //��ͨ�û��������û���һ����Ϣ
                    BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                            getUser().getName() + "�����������־����<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                    User diaryUser = UserDao.getUserById(diary.getUserId());

                    //���ʼ�
                    if(StringUtils.isNotBlank(diaryUser.getEmail())){
                        //�ʼ�title
                        String title = "����Ŵ�OAϵͳ-������־";
                        //�ʼ�����
                        String content = diaryUser.getName() + "��ã�<br><br>" +
                                getUser().getName() + "������Ŵ�OAϵͳ�����������־����<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                "ף������˳����<br><br>" +
                                "����Ŵ�OAϵͳ";
                        //�����ʼ�
                        EmailUtils.sendEmail(title, content, diaryUser.getEmail());
                    }
                }

                resp = "{isSuccess:true,message:'���۹�����־�ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_UPDATE_REVIEW.equals(type)){//�޸�����
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(updateReviewId));
            //�в����ڸù�����־�����ߣ������ڸù�����־���ۣ����ߣ��ù�����־���۵����߲��Ǹ������û��������޸�����
            if(diary == null || diaryReview == null || diaryReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                diaryReview.setContent(content);
                diaryReview.setUpdateDate(super.date);
                diaryReview.setUpdateTime(time);
                diaryReview.setUpdateIp(getIp());
                DiaryReviewDao.updateDiaryReview(diaryReview);

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� �޸Ĺ�����־���۳ɹ���", date, time, getIp());

                //����ͬһ������֪ͨ
                if(getUser().getId() != diary.getUserId()){
                    //��ͨ�û��������û���һ����Ϣ
                    BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                            getUser().getName() + "�޸��������־���ۣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                    User diaryUser = UserDao.getUserById(diary.getUserId());

                    //���ʼ�
                    if(StringUtils.isNotBlank(diaryUser.getEmail())){
                        //�ʼ�title
                        String title = "����Ŵ�OAϵͳ-�޸���־����";
                        //�ʼ�����
                        String content = diaryUser.getName() + "��ã�<br><br>" +
                                getUser().getName() + "������Ŵ�OAϵͳ�޸��������־���ۣ���<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                "ף������˳����<br><br>" +
                                "����Ŵ�OAϵͳ";
                        //�����ʼ�
                        EmailUtils.sendEmail(title, content, diaryUser.getEmail());
                    }
                }

                resp = "{isSuccess:true,message:'�޸Ĺ�����־���۳ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_DELETE_REVIEW.equals(type)){//ɾ������
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(deleteReviewId));
            //�в����ڸù�����־�����ߣ������ڸù�����־���ۣ����ߣ��ù�����־���۵����߲��Ǹ������û�������ɾ������
            if(diary == null || diaryReview == null || diaryReview.getUserId() != getUser().getId()){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReviewDao.deleteDiaryReview(diaryReview);

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� ɾ��������־���۳ɹ���", date, time, getIp());

                //����ͬһ������֪ͨ
                if(getUser().getId() != diary.getUserId()){
                    //��ͨ�û��������û���һ����Ϣ
                    BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                            getUser().getName() + "ɾ���������־���ۣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                    User diaryUser = UserDao.getUserById(diary.getUserId());

                    //���ʼ�
                    if(StringUtils.isNotBlank(diaryUser.getEmail())){
                        //�ʼ�title
                        String title = "����Ŵ�OAϵͳ-ɾ����־����";
                        //�ʼ�����
                        String content = diaryUser.getName() + "��ã�<br><br>" +
                                getUser().getName() + "������Ŵ�OAϵͳɾ���������־���ۣ���<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                "ף������˳����<br><br>" +
                                "����Ŵ�OAϵͳ";
                        //�����ʼ�
                        EmailUtils.sendEmail(title, content, diaryUser.getEmail());
                    }
                }

                resp = "{isSuccess:true,message:'ɾ��������־���۳ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else if(TYPE_REPLY_REVIEW.equals(type)){//�ظ�����
            Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
            DiaryReview diaryReview = DiaryReviewDao.getDiaryReviewById(Integer.parseInt(updateReviewId));
            //�в����ڸù�����־�����ߣ������ڸù�����־���ۣ����ܻظ�����
            if(diary == null || diaryReview == null){
                resp = "{isSuccess:false,message:'��Ĳ�������',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                DiaryReview diaryReview2 = new DiaryReview(getUser().getId(), diary.getId(),
                        DiaryReviewInterface.TYPE_REPLY, diaryReview.getUserId(), content, super.date, time,
                        getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
                DiaryReviewDao.insertDiaryReview(diaryReview2);

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_OPERATE_DIARY, "������־���� �ظ����۹�����־�ɹ���", date, time, getIp());

                //����ͬһ������֪ͨ
                if(getUser().getId() != diary.getUserId()){
                    //��ͨ�û��������û���һ����Ϣ
                    BaseUtil.createNormalMessage(getUser().getId(), diary.getUserId(),
                            getUser().getName() + "�������־�лظ������ۣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                }

                //����ͬһ������֪ͨ
                if(diary.getUserId() != diaryReview.getUserId() && getUser().getId() != diaryReview.getUserId()){
                    //��ͨ�û��������û���һ����Ϣ
                    BaseUtil.createNormalMessage(getUser().getId(), diaryReview.getUserId(),
                            getUser().getName() + "�ظ��������־���ۣ���<a href=\"showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>", getIp());

                    User diaryReviewUser = UserDao.getUserById(diaryReview.getUserId());

                    //���ʼ�
                    if(StringUtils.isNotBlank(diaryReviewUser.getEmail())){
                        //�ʼ�title
                        String title = "����Ŵ�OAϵͳ-�ظ���־����";
                        //�ʼ�����
                        String content = diaryReviewUser.getName() + "��ã�<br><br>" +
                                getUser().getName() + "������Ŵ�OAϵͳ�ظ��������־���ۣ���<a href=\"http://www.suncare-sys.com:10000/showDiary.jsp?id=" + diary.getId() + "\" target=\"_blank\">����</a>��<br><br>" +
                                "ף������˳����<br><br>" +
                                "����Ŵ�OAϵͳ";
                        //�����ʼ�
                        EmailUtils.sendEmail(title, content, diaryReviewUser.getEmail());
                    }
                }

                resp = "{isSuccess:true,message:'�ظ����۹�����־�ɹ���',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            }
        } else {
            resp = "{isSuccess:false,message:'������������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountNow() {
        return countNow;
    }

    public void setCountNow(String countNow) {
        this.countNow = countNow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateReviewId() {
        return updateReviewId;
    }

    public void setUpdateReviewId(String updateReviewId) {
        this.updateReviewId = updateReviewId;
    }

    public String getDeleteReviewId() {
        return deleteReviewId;
    }

    public void setDeleteReviewId(String deleteReviewId) {
        this.deleteReviewId = deleteReviewId;
    }
}
