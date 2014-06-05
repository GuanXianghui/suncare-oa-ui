package com.gxx.oa;

import com.gxx.oa.dao.CloudDocDao;
import com.gxx.oa.entities.CloudDoc;
import com.gxx.oa.interfaces.CloudDocInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.Date;

/**
 * ����Ŀ��ϴ��ĵ�action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-1 11:22
 */
public class CloudUploadDocAction extends BaseAction implements CloudDocInterface {
    /**
     * ����
     */
    private String title;
    /**
     * ����
     */
    private String description;
    /**
     * ����
     */
    private String type;
    /**
     * ��ǩ
     */
    private String tags;
    /**
     * �ĵ�
     */
    private File doc;
    /**
     * �ĵ���
     */
    private String docFileName;
    /**
     * �ĵ�����
     */
    private String docContentType;

    /**
     * ���
     * @return
     */
    public String execute() throws Exception {
        logger.info("title:" + title + ",description:" + description + ",type:" + type +
                ",tags:" + tags + ",doc:" + doc + ",docFileName:" + docFileName + ",docContentType:" +
                docContentType + ",size:" + (doc==null?"":FileUtil.formatFileSize(doc.length())));
        //�б����ֶ�Ϊ��
        if(StringUtils.isBlank(title) || StringUtils.isBlank(type))
        {
            message = "����ͷ��಻��Ϊ��!";
            return ERROR;
        }

        //���ĵ�Ϊ��
        if(doc == null){
            message = "������δ�յ��ļ�!";
            return ERROR;
        }

        //�ļ�Ϊ��
        if(doc.length() == 0){
            message = "�ļ�����Ϊ��!";
            return ERROR;
        }

        //�µ��ļ�����
        String fileType = StringUtils.EMPTY;
        int dotIndex = docFileName.lastIndexOf(SymbolInterface.SYMBOL_DOT);
        if(dotIndex > -1){
            fileType = docFileName.substring(dotIndex);
        }

        /**
         * �Ƿ���֧�ֵ��ļ�����
         */
        if(!BaseUtil.isSupportCloudDocType(
                fileType.startsWith(SymbolInterface.SYMBOL_DOT)?fileType.substring(1):fileType)){
            message = "��֧�ֵ��ļ�����" + (StringUtils.isBlank(fileType)?"":"[" + fileType + "]") + "!";
            return ERROR;
        }

        //�µ��ļ�����
        String fileName = getUser().getId() + "_" + new Date().getTime() + fileType;
        //�������ϵ�·��
        String filePath = ServletActionContext.getServletContext().getRealPath("files/cloud_doc") + "/" + fileName;
        //ҳ������λ�� ���·��
        String filePagePath = "files/cloud_doc/" + fileName;
        //�������ϵ�·����Ӧ���ļ�
        File newFile = new File(filePath);
        //�����ļ�
        FileUtil.copy(doc, newFile);

        //�����û�ID��Ŀ¼���ļ������� ״̬Ϊ����
        CloudDoc cloudDoc = new CloudDoc(getUser().getId(), title, STATE_NORMAL, description, type, tags, filePagePath,
                doc.length(), 0, 0, date, time, getIp(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        CloudDocDao.insertCloudDoc(cloudDoc);

        message = "�ϴ��ĵ�������Ŀ�ɹ���";
        return SUCCESS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public File getDoc() {
        return doc;
    }

    public void setDoc(File doc) {
        this.doc = doc;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }
}
