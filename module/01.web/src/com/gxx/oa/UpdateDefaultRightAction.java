package com.gxx.oa;

import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.ParamInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.ParamUtil;
import com.gxx.oa.utils.TokenUtil;

/**
 * �޸�Ĭ��Ȩ��action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class UpdateDefaultRightAction extends BaseAction {
    //Ȩ�޴���
    String rightCodes;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0004_DEFAULT_RIGHT);
        logger.info("rightCodes:" + rightCodes);

        //��ѯ����
        String info = ParamUtil.getInstance().getInfoByName(ParamInterface.DEFAULT_RIGHT);

        //����name�޸�value��info
        ParamUtil.getInstance().updateParam(ParamInterface.DEFAULT_RIGHT, rightCodes, info);

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_UPDATE_DEFAULT_RIGHT, "�޸�Ĭ��Ȩ�޳ɹ���", date, time, getIp());

        //���ؽ��
        String resp = "{isSuccess:true,message:'�޸�Ĭ��Ȩ�޳ɹ���',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getRightCodes() {
        return rightCodes;
    }

    public void setRightCodes(String rightCodes) {
        this.rightCodes = rightCodes;
    }
}
