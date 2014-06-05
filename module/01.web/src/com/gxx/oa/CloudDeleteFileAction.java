package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ɾ���ļ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudDeleteFileAction extends BaseAction implements CloudInterface {
    //����Ŀ¼
    String dir;
    //ɾ���ļ�ID
    String deleteIds;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",deleteIds:" + deleteIds);

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //ɾ���������
        List<Cloud> deleteClouds = new ArrayList<Cloud>();
        String[] fileIds = deleteIds.split(SymbolInterface.SYMBOL_COMMA);
        //���У���Ƿ񶼴���
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null){
                message = "��Ŀ¼[" + dir + "]�²�����IDΪ[" + id + "]�Ķ���!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            deleteClouds.add(cloud);
        }

        //���ɾ��
        for(Cloud cloud : deleteClouds){
            cloud.setState(STATE_DELETE);
            CloudDao.updateCloud(cloud);
        }

        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud cloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = cloud.getId();
        }

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //���ؽ��
        String resp = "{isSuccess:true,message:'ɾ���ļ��ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
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

    public String getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(String deleteIds) {
        this.deleteIds = deleteIds;
    }
}
