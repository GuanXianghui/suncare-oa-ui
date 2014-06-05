package com.gxx.oa;

import com.gxx.oa.dao.NoticeDao;
import com.gxx.oa.entities.Notice;
import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.PropertyUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ������һҳ����action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 14:25
 */
public class ShowNextPageNoticesAction extends BaseAction {
    /**
     * Ŀǰ������
     */
    int countNow;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0007_CONFIG_NOTICE);
        logger.info("countNow:" + countNow);
        //�㵱ǰҳ��
        int nowPage = countNow / Integer.parseInt(PropertyUtil.getInstance().
                getProperty(BaseInterface.NOTICE_PAGE_SIZE));
        //��һҳ
        int nextPage = nowPage + 1;
        //��һҳ����
        List<Notice> nextPageNotices = NoticeDao.queryNoticesByPage(nextPage,
                Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)));
        /**
         * ��һҳ����Json��
         * replaceAll("\\\'", "\\\\\\\'")��ת��������
         * replaceAll("\\\"", "\\\\\\\"")��ת��˫����
         * replaceAll("\r\n", uuid)��ת�����з���uuid
         */
        String nextPageJson = BaseUtil.getJsonArrayFromNotices(nextPageNotices).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID));

        //���ؽ��
        String resp = "{isSuccess:true,message:'������һҳ����ɹ���',nextPageJson:'" + nextPageJson +
                "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }

    public int getCountNow() {
        return countNow;
    }

    public void setCountNow(int countNow) {
        this.countNow = countNow;
    }
}
