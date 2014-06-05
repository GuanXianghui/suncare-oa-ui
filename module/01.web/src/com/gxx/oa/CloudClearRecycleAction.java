package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ��ջ���վaction
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudClearRecycleAction extends BaseAction implements CloudInterface {
    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //��ջ���վ
        CloudDao.clearRecycle(getUser().getId());

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryRecycleClouds(getUser().getId());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //���ؽ��
        String resp = "{isSuccess:true,message:'��ջ���վ�ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }
}
