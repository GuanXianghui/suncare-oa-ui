package com.gxx.oa;

import com.gxx.oa.dao.UserDao;
import com.gxx.oa.entities.User;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * �ϴ�ͷ��action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class UploadHeadPhotoAction extends BaseAction {
    /**
     * ͷ���ļ�
     */
    private File headPhoto;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        //Ȩ��У��
        BaseUtil.checkRightWithException(getUser().getId(), RIGHT_0001_USER_MANAGE);
        logger.info("headPhoto:" + headPhoto);
        //��ͷ��Ϊ��
        if(null == headPhoto)
        {
            message = "������δ�յ�ͼƬ!";
            return ERROR;
        }
        //�µ�ͼƬ����
        String headPhotoName = getUser().getId() + "_" + new Date().getTime() + ".jpg";
        //�������ϵ�·��
        String headPhotoPath = ServletActionContext.getServletContext().getRealPath("images/head") + "/" + headPhotoName;
        //ҳ������λ�� ���·��
        String headPhotoPagePath = "images/head" + "/" + headPhotoName;
        //�������ϵ�·����Ӧ���ļ�
        File imageFile = new File(headPhotoPath);
        //�����ļ�
        FileUtil.copy(headPhoto, imageFile);

        //�����û�ͷ��
        User user = getUser();
        user.setHeadPhoto(headPhotoPagePath);
        UserDao.updateUserHeadPhoto(user);

        message = "�ϴ�ͷ��ɹ���";
        return SUCCESS;
    }

    public File getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(File headPhoto) {
        this.headPhoto = headPhoto;
    }
}
