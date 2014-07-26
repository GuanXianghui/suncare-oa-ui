package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ԭ�ļ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudRecoverAction extends BaseAction implements CloudInterface {
    //��ԭ����id
    String recoverIds;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("recoverIds:" + recoverIds);

        //��ԭ�������
        List<Cloud> recoverClouds = new ArrayList<Cloud>();
        String[] fileIds = recoverIds.split(SymbolInterface.SYMBOL_COMMA);
        //���У���Ƿ񶼴���
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null || cloud.getState() != STATE_DELETE){
                message = "��Ĳ���������ˢ��ҳ������!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            recoverClouds.add(cloud);
        }

        //�����ԭ
        for(Cloud cloud : recoverClouds){
            //�����û�ID��Ŀ¼���ļ������ļ����Ͳ��� ״̬Ϊ����
            Cloud existCloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), cloud.getDir(), cloud.getName(), cloud.getType());
            if(existCloud != null){
                message = "Ŀ¼[" + cloud.getDir() + "]���Ѿ���������Ϊ[" + cloud.getName() + "]��" +
                        BaseUtil.getCloudTypeDesc(cloud.getType()) + "!";

                //����������־
                BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_RECOVER, message, date, time, getIp());

                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }

            try{
                //�����û�id��Ŀ¼ ��Ŀ¼�Ϸ��� ������Ϸ� ����㴴��Ŀ¼
                BaseUtil.checkCloudDir(getUser().getId(), cloud.getDir());
            } catch (Exception e){
                //��㴴��Ŀ¼
                BaseUtil.createCloudDir(getUser().getId(), cloud.getDir(), date, time, getIp());
            }

            int pid = 0;
            if(!StringUtils.equals(FRONT_DIR, cloud.getDir())){
                //����pid������״̬
                Cloud pidCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), cloud.getDir());
                pid = pidCloud.getId();
            }
            cloud.setPid(pid);
            cloud.setState(STATE_NORMAL);
            CloudDao.updateCloud(cloud);
        }

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryRecycleClouds(getUser().getId());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_RECOVER, "��ԭ�ļ��ɹ���", date, time, getIp());

        //���ؽ��
        String resp = "{isSuccess:true,message:'��ԭ�ļ��ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getRecoverIds() {
        return recoverIds;
    }

    public void setRecoverIds(String recoverIds) {
        this.recoverIds = recoverIds;
    }
}
