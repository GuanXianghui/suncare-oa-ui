package com.gxx.oa;

import com.gxx.oa.dao.DiaryDao;
import com.gxx.oa.entities.Diary;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * д������־action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-7 22:16
 */
public class WriteDiaryAction extends BaseAction {
    //����
    private String date;
    //����
    private String content;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("date:" + date + ",content:" + content);
        Diary diary = new Diary(getUser().getId(), date, content, super.date, time, getIp(), StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY);
        DiaryDao.insertDiary(diary);
        message = "д������־�ɹ���";
        return SUCCESS;
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
