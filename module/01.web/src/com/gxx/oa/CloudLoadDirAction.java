package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * �����ļ���action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudLoadDirAction extends BaseAction implements CloudInterface {
    //Ŀ¼
    String dir;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir);

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //��Ŀ¼id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //���ؽ��
        String resp = "{isSuccess:true,message:'�����ļ���[" + dir + "]�ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
