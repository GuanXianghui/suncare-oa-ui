package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * ������ϴ��ļ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUploadAction extends BaseAction implements CloudInterface {
    /**
     * Ŀ¼
     */
    private String dir;
    /**
     * �ļ�
     */
    private File file;
    /**
     * �ļ���
     */
    private String fileFileName;
    /**
     * �ļ�����
     */
    private String fileContentType;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("dir:" + dir + ",file:" + file + ",fileFileName:" + fileFileName +
                ",fileContentType:" + fileContentType + ",size:" +
                (file==null?"":FileUtil.formatFileSize(file.length())));
        //��ͷ��Ϊ��
        if(null == file)
        {
            message = "������δ�յ��ļ�!";
            return ERROR;
        }

        //�ļ�Ϊ��
        if(file.length() == 0){
            message = "�ļ�����Ϊ��!";
            return ERROR;
        }

        /**
         * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
         * 1.���dirΪ��б��/������
         * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
         */
        BaseUtil.checkCloudDir(getUser().getId(), dir);

        //�����û�ID��Ŀ¼���ļ������� ״̬Ϊ����
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), dir, fileFileName, TYPE_FILE);
        if(cloud != null){
            message = "��Ŀ¼[" + dir + "]���Ѵ�����ͬ����[" + fileFileName + "]���ļ�!";

            //����������־
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_UPLOAD, "��������ļ��ϴ� " + message, date, time, getIp());

            return ERROR;
        }

        //�µ��ļ�����
        String fileType = StringUtils.EMPTY;
        int dotIndex = fileFileName.lastIndexOf(SymbolInterface.SYMBOL_DOT);
        if(dotIndex > -1){
            fileType = fileFileName.substring(dotIndex);
        }
        //�µ��ļ�����
        String fileName = getUser().getId() + "_" + new Date().getTime() + fileType;
        //�������ϵ�·��
        String filePath = ServletActionContext.getServletContext().getRealPath("files/cloud") + "/" + fileName;
        //ҳ������λ�� ���·��
        String filePagePath = "files/cloud/" + fileName;
        //�������ϵ�·����Ӧ���ļ�
        File newFile = new File(filePath);
        //�����ļ�
        FileUtil.copy(file, newFile);

        //��Ŀ¼id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, dir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), dir);
            pid = dirCloud.getId();
        }

        //������
        cloud = new Cloud(getUser().getId(), TYPE_FILE, pid, fileFileName, STATE_NORMAL, dir, filePagePath,
                file.length(), date, time, getIp());
        CloudDao.insertCloud(cloud);

        message = "�ϴ�������ļ��ɹ���";

        //����������־
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_UPLOAD, "��������ļ��ϴ� " + message, date, time, getIp());

        return SUCCESS;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
