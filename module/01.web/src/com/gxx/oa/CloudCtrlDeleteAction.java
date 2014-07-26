package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ����ɾ���ļ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudCtrlDeleteAction extends BaseAction implements CloudInterface {
    //����ɾ���ļ�ID
    String ctrlDeleteIds;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("ctrlDeleteIds:" + ctrlDeleteIds);

        //ɾ���������
        List<Cloud> ctrlDeleteClouds = new ArrayList<Cloud>();
        String[] fileIds = ctrlDeleteIds.split(SymbolInterface.SYMBOL_COMMA);
        //���У���Ƿ񶼴���
        for(int i=0;i<fileIds.length;i++){
            int id = Integer.parseInt(fileIds[i]);
            Cloud cloud = CloudDao.getCloudById(id);
            if(cloud == null){
                message = "��Ĳ������Ϸ�����ˢ��ҳ������!";
                String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                        "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
                write(resp);
                return null;
            }
            ctrlDeleteClouds.add(cloud);
        }

        //�������ɾ��
        for(Cloud cloud : ctrlDeleteClouds){
            cloud.setState(STATE_CTRL_DELETE);
            CloudDao.updateCloud(cloud);
        }

        //������Ƽ��ϵõ�Json����
        List<Cloud> clouds = CloudDao.queryRecycleClouds(getUser().getId());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_CTRL_DELETE, "����ɾ���ļ��ɹ���", date, time, getIp());

        //���ؽ��
        String resp = "{isSuccess:true,message:'����ɾ���ļ��ɹ���',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getCtrlDeleteIds() {
        return ctrlDeleteIds;
    }

    public void setCtrlDeleteIds(String ctrlDeleteIds) {
        this.ctrlDeleteIds = ctrlDeleteIds;
    }
}
