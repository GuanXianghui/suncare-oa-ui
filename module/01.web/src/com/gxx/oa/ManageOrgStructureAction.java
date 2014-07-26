package com.gxx.oa;

import com.gxx.oa.dao.StructureDao;
import com.gxx.oa.entities.Structure;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * ������֯�ܹ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 20:10
 */
public class ManageOrgStructureAction extends BaseAction {
    /**
     * �������;�̬����
     */
    private static String  CONFIG_TYPE_MOVE2LEFT = "move2Left";
    private static String  CONFIG_TYPE_MOVE2RIGHT = "move2Right";
    private static String  CONFIG_TYPE_ADD_NODE = "addNode";
    private static String  CONFIG_TYPE_UPDATE_NODE = "updateNode";
    private static String  CONFIG_TYPE_DELETE_NODE = "deleteNode";

    /**
     * ��������
     */
    String configType;
    /**
     * id
     */
    int id;
    /**
     * ��֯�ṹ����
     */
    int type;
    /**
     * ��֯�ṹ����
     */
    String name;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithAjaxException(getUser().getId(), RIGHT_0002_ORG_STRUCTURE_MANAGE);
        logger.info("configType:" + configType + ",id:" + id + ",type:" + type + ",name:" + name);
        String resp;
        if(CONFIG_TYPE_MOVE2LEFT.equals(configType)) {//����
            Structure structure = StructureDao.getStructureById(id);
            Structure leftOne = StructureDao.getLeftOne(structure);
            if(null != leftOne) {
                int tempIndexId = structure.getIndexId();
                structure.setIndexId(leftOne.getIndexId());
                StructureDao.updateStructure(structure);
                leftOne.setIndexId(tempIndexId);
                StructureDao.updateStructure(leftOne);
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_MANAGE_ORG_STRUCTURE, "��֯�ܹ����� ���ƽڵ�ɹ�", date, time, getIp());

            resp = "{isSuccess:true,message:'���ƽڵ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_MOVE2RIGHT.equals(configType)) {//����
            Structure structure = StructureDao.getStructureById(id);
            Structure rightOne = StructureDao.getRightOne(structure);
            if(null != rightOne) {
                int tempIndexId = structure.getIndexId();
                structure.setIndexId(rightOne.getIndexId());
                StructureDao.updateStructure(structure);
                rightOne.setIndexId(tempIndexId);
                StructureDao.updateStructure(rightOne);
            }

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_MANAGE_ORG_STRUCTURE, "��֯�ܹ����� ���ƽڵ�ɹ�", date, time, getIp());

            resp = "{isSuccess:true,message:'���ƽڵ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_ADD_NODE.equals(configType)) {//�����ڵ�
            Structure structure = new Structure(type, name, id, StructureDao.getMaxIndexIdByPid(id) + 1);
            StructureDao.insertStructure(structure);

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_MANAGE_ORG_STRUCTURE, "��֯�ܹ����� �����ڵ�ɹ�", date, time, getIp());

            resp = "{isSuccess:true,message:'�����ڵ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_UPDATE_NODE.equals(configType)) {//���½ڵ�
            Structure structure = StructureDao.getStructureById(id);
            structure.setType(type);
            structure.setName(name);
            StructureDao.updateStructure(structure);

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_MANAGE_ORG_STRUCTURE, "��֯�ܹ����� ���½ڵ�ɹ�", date, time, getIp());

            resp = "{isSuccess:true,message:'���½ڵ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else if(CONFIG_TYPE_DELETE_NODE.equals(configType)) {//ɾ���ڵ�
            deleteStructure(id);

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_MANAGE_ORG_STRUCTURE, "��֯�ܹ����� ɾ���ڵ�ɹ�", date, time, getIp());

            resp = "{isSuccess:true,message:'ɾ���ڵ�ɹ���',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "', newStructureJsonStr:\"" +
                    BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures()) + "\"}";
        } else {//�쳣��֧
            resp = "{isSuccess:true,message:'�쳣������',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        }
        write(resp);
        return null;
    }

    /**
     * ѭ��ɾ���ڵ�
     * @param deleteId
     */
    private void deleteStructure(int deleteId) throws Exception{
        List<Structure> list = StructureDao.queryStructuresByPid(deleteId);
        for(Structure tempStructure : list) {
            deleteStructure(tempStructure.getId());
        }
        StructureDao.deleteStructure(deleteId);
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
