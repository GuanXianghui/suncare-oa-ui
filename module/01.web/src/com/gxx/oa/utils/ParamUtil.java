package com.gxx.oa.utils;

import com.gxx.oa.dao.ParamDao;
import com.gxx.oa.entities.Param;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * ��������������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 08:39
 */
public class ParamUtil {
    private static ParamUtil instance;

    public static ParamUtil getInstance() throws Exception {
        if (null == instance) {
            instance = new ParamUtil();
        }
        return instance;
    }

    static List<Param> params;

    private ParamUtil() throws Exception {
        refresh();
    }

    /**
     * ���û���ˢ��
     */
    public static void refresh() throws Exception{
        // 1 ��ѯ������������
        params = ParamDao.queryAllParams();
    }

    /**
     * ��ȡֵ
     *
     * @param name
     * @return
     */
    public String getValueByName(String name) {
        for(Param param : params) {
            if(param.getName().equals(name)) {
                return param.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * ��ȡ����
     *
     * @param name
     * @return
     */
    public String getInfoByName(String name) {
        for(Param param : params) {
            if(param.getName().equals(name)) {
                return param.getInfo();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * ����name�޸�value��info
     * @param name
     * @param value
     * @param info
     */
    public void updateParam(String name, String value, String info) throws Exception{
        //����������
        Param param = null;
        for(Param temp : params) {
            if(temp.getName().equals(name)) {
                param = temp;
                break;
            }
        }

        //�Ҳ�������������
        if(null == param){
            throw new RuntimeException("�Ҳ�������������:[" + name + "]");
        }

        //������������
        param.setValue(value);
        param.setInfo(info);
        ParamDao.updateParam(param);

        //���û���ˢ��
        refresh();
    }
}
