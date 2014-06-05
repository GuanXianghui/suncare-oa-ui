package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ������action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudRenameAction extends BaseAction implements CloudInterface {
    //�޸Ķ���id
    String id;
    //���ļ���
    String newName;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id + ",newName:" + newName);

        Cloud cloud = CloudDao.getCloudById(Integer.parseInt(id));
        if(cloud == null || cloud.getUserId() != getUser().getId() || cloud.getState() != CloudInterface.STATE_NORMAL){
            message = "��Ĳ�������������!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), cloud.getDir());

        /**
         * �е�ǰĿ¼���µ������Ƿ��Ѿ�����
         * �����û�ID��Ŀ¼���ļ������ļ����Ͳ��� ״̬Ϊ����
         */
        Cloud newNameCloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), cloud.getDir(), newName, cloud.getType());
        if(null != newNameCloud){
            message = "��Ŀ¼[" + cloud.getDir() + "]���Ѵ�����ͬ����[" + newName + "]��" + BaseUtil.getCloudTypeDesc(cloud.getType()) + "!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //�޸�����
        cloud.setName(newName);
        /**
         * route varchar(1100) comment '���·�� ��Ϊ��
         * ������ļ��������ļ���·��route=dir+name+/ ������ļ���Ϊ��ʵ�ļ����·��'
         * route=dir+name+/
         */
        if(cloud.getType() == TYPE_DIR){
            cloud.setRoute(cloud.getDir() + newName + SymbolInterface.SYMBOL_SLASH);
        }
        CloudDao.updateCloud(cloud);

        /**
         * �޸��ļ�������ʱ�����ļ��е��¼����ж����dir�����޸�
         */
        if(cloud.getType() == TYPE_DIR){
            List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), cloud.getId());
            for(Cloud tempCloud : clouds){
                tempCloud.setDir(cloud.getRoute());
                CloudDao.updateCloud(tempCloud);
            }
        }

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), cloud.getPid());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //���ؽ��
        String resp = "{isSuccess:true,message:'�������ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
