package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.entities.Diary;
import com.gxx.oa.utils.BaseUtil;

/**
 * �޸Ĺ�����־action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 11:45
 */
public class UpdateDiaryAction extends BaseAction {
    //������־id
    private String diaryId;
    //����
    private String date;
    //����
    private String content;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("diaryId:" + diaryId + ",date:" + date + ",content:" + content);
        Diary diary = DiaryDao.getDiaryById(Integer.parseInt(diaryId));
        //�в����ڸù�����־�����ߣ��ù�����־�����ڸ��û��Ĳ����޸�
        if(diary == null || diary.getUserId() != getUser().getId()){
            message = "��Ĳ�������";
            return ERROR;
        }
        diary.setDate(date);
        diary.setContent(content);
        diary.setUpdateDate(super.date);
        diary.setUpdateTime(time);
        diary.setUpdateIp(getIp());
        DiaryDao.updateDiary(diary);
        message = "�޸Ĺ�����־�ɹ���";
        return SUCCESS;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
