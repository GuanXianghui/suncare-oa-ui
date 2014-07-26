package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * �½��ļ���action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudNewDirDirAction extends BaseAction implements CloudInterface {
    //����Ŀ¼
    String dir;
    //�½�Ŀ¼
    String newDir;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",newDir:" + newDir);

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //�����û�ID��Ŀ¼���ļ������ļ����Ͳ��� ״̬Ϊ����
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), dir, newDir, TYPE_DIR);
        if(cloud != null){
            message = "��Ŀ¼[" + dir + "]���Ѵ�����ͬ����[" + newDir + "]���ļ���!";

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_NEW_DIR, message, date, time, getIp());

            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //��Ŀ¼id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //�������ļ���
        cloud = new Cloud(getUser().getId(), TYPE_DIR, pid, newDir, STATE_NORMAL, dir,
                dir+newDir+ SymbolInterface.SYMBOL_SLASH, 0, date, time, getIp());
        CloudDao.insertCloud(cloud);

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), pid);
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //���ؽ��
        String resp = "{isSuccess:true,message:'�½��ļ��гɹ���',filesJsonStr:'" + filesJsonStr + "'," +
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

    public String getNewDir() {
        return newDir;
    }

    public void setNewDir(String newDir) {
        this.newDir = newDir;
    }
}
