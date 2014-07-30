package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

/**
 * �ƶ��ļ������ļ���action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudMoveToDirAction extends BaseAction implements CloudInterface {
    //�ƶ�����ID
    String id;
    //�ƶ�����Ŀ¼·��
    String moveDir;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id + ",moveDir:" + moveDir);

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), moveDir);

        //�鿴�ƶ������Ƿ����
        Cloud moveCloud = CloudDao.getCloudById(Integer.parseInt(id));
        if(moveCloud == null){
            String resp = "{isSuccess:false,message:'��Ĳ�������',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //�����û�ID��Ŀ¼���ļ������ļ����Ͳ��� ״̬Ϊ����
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), moveDir, moveCloud.getName(), moveCloud.getType());
        if(cloud != null){
            message = "��Ŀ¼[" + moveDir + "]���Ѵ�����ͬ����[" + moveCloud.getName() + "]���ļ�" +
                    (moveCloud.getType()==CloudInterface.TYPE_DIR?"��":"") + "!�����ƶ�����Ŀ¼��!";

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_MOVE_TO_DIR, message, date, time, getIp());

            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //��Ŀ¼id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, moveDir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), moveDir);
            pid = dirCloud.getId();
        }

        //�޸ĸ�Ŀ¼ID����Ŀ¼·����·��
        moveCloud.setPid(pid);
        moveCloud.setDir(moveDir);
        /**
         * ����route
         * ������ļ��д� ���·�� Ҫ���޸�
         * ������ļ� �� �������ϵľ���·�� �����޸�
         */
        if(moveCloud.getType() == CloudInterface.TYPE_DIR){
            moveCloud.setRoute(moveDir + moveCloud.getName() + SymbolInterface.SYMBOL_SLASH);
        }
        CloudDao.updateCloud(moveCloud);

        //������ļ���
        if(moveCloud.getType()==CloudInterface.TYPE_DIR){
            /**
             * ��[�������ļ���]����[�ƶ��ļ���������Ŀ¼]ʱ������Ҫ�ı��Լ���pid��dir��route������Ҫ�ݹ��ѯ���ļ����������ļ������ļ��У���dir��route�ֶ�ˢ��
             * �����[�ļ�][������]����[�ƶ�������Ŀ¼]��ֻ��ı��Լ���pid��dir��route����
             * ע�⣺����route��������ļ��д� ���·�� Ҫ���޸ģ�������ļ� �� �������ϵľ���·�� �����޸�
             */
            BaseUtil.refreshAllCloudsBelow(getUser().getId(), moveCloud);
        }

        message = "�ƶ��ļ�" + (moveCloud.getType()==CloudInterface.TYPE_DIR?"��":"") + "[" + moveCloud.getName() +
                "]��Ŀ¼[" + moveDir + "]�³ɹ���";

        //���ؽ��
        String resp = "{isSuccess:true,message:'" + message + "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_MOVE_TO_DIR, message, date, time, getIp());

        write(resp);
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoveDir() {
        return moveDir;
    }

    public void setMoveDir(String moveDir) {
        this.moveDir = moveDir;
    }
}
