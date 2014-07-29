package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.entities.User;
import com.gxx.oa.interfaces.*;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * 申成文库下载文档action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudDownLoadDocAction extends BaseAction {
    /**
     * 文档id
     */
    String docId;
    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        CloudDoc cloudDoc = CloudDocDao.getCloudDocById(Integer.parseInt(docId));
        if(cloudDoc.getState() == CloudDocInterface.STATE_DELETE){
            //返回结果
            String resp = "{isSuccess:false,message:'该文档已被删除，下载文档失败！'," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_DOWNLOAD_DOC,
                "下载文档成功！文档ID:[" + cloudDoc.getId() + "],文档标题:[" + cloudDoc.getTitle() + "]",
                date, time, getIp());

        //如果是自己的文档，不扣申成币
        if(cloudDoc.getUserId() != getUser().getId()){
            //申成文库-下载文档 申成币-1
            UserDao.updateUserMoney(getUser().getId(), MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD);
            User user = UserDao.getUserById(getUser().getId());

            //创建申成币变动日志
            BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE,
                    "申成币变动 申成文库-下载文档" + MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD, date, time, getIp());

            //刷新缓存
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);

            //公众账号给用户发一条消息
            BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                    "申成文库-下载文档成功，申成币" + MoneyInterface.ACT_CLOUD_DOC_DOWNLOAD + "，见<a href=\"cloudViewDoc.jsp?id=" + docId + "\" target=\"_blank\">文档</a>", getIp());
        }

        //返回结果
        String resp = "{isSuccess:true,message:'下载文档成功！'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
        write(resp);

        return null;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
