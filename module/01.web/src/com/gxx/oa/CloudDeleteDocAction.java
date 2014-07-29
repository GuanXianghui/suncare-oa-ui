package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 删除文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudDeleteDocAction extends BaseAction implements CloudDocInterface {
    /**
     * 文档id
     */
    private String docId;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("docId:" + docId);
        //判文档id不为空
        if(StringUtils.isBlank(docId)){
            message = "文档id不能为空!";
            return ERROR;
        }
        // 文档idInt类型
        int docInInt = Integer.parseInt(docId);
        //判文档存在
        CloudDoc cloudDoc = CloudDocDao.getCloudDocById(docInInt);
        if(null == cloudDoc || cloudDoc.getUserId() != getUser().getId() || cloudDoc.getState() != STATE_NORMAL){
            message = "你的操作有误，请刷新页面重试!";
            return ERROR;
        }

        cloudDoc.setState(STATE_DELETE);
        cloudDoc.setUpdateDate(date);
        cloudDoc.setUpdateTime(time);
        cloudDoc.setUpdateIp(getIp());
        CloudDocDao.updateCloudDoc(cloudDoc);

        message = "删除文档成功！";

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_DELETE_DOC, message, date, time, getIp());

        //申成文库-删除文档 申成币-3
        UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_DOC_DELETE);
        User user = UserDao.getUserById(getUser().getId());

        //创建申成币变动日志
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                "申成币变动 申成文库-删除文档" + MoneyInterface.ACT_CLOUD_DOC_DELETE, date, time, getIp());

        //刷新缓存
        request.getSession().setAttribute(BaseInterface.USER_KEY, user);

        //公众账号给用户发一条消息
        BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                "申成文库-删除文档[" + cloudDoc.getTitle() + "]成功，申成币" + MoneyInterface.ACT_CLOUD_DOC_DELETE + "！", getIp());

        return SUCCESS;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
