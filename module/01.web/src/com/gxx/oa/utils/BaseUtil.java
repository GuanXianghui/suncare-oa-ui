package com.gxx.oa.utils;

import com.gxx.oa.dao.*;
import com.gxx.oa.entities.*;
import com.gxx.oa.exceptions.AjaxException;
import com.gxx.oa.interfaces.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ����������
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-30 12:09
 */
public class BaseUtil implements SymbolInterface {
    /**
     * �е�¼
     *
     * @param request
     */
    public static boolean isLogin(HttpServletRequest request) throws Exception {
        if(request.getSession().getAttribute(BaseInterface.USER_KEY) == null) {
            return false;
        }
        return true;
    }

    /**
     * ����dao+session�û�������Ϣ
     *
     * @param request
     */
    public static void refreshUserVisit(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(BaseInterface.USER_KEY);
        user.setVisitDate(DateUtil.getNowDate());
        user.setVisitTime(DateUtil.getNowTime());
        user.setVisitIp(IPAddressUtil.getIPAddress(request));
        try {
            UserDao.updateUserVisitInfo(user);
            request.getSession().setAttribute(BaseInterface.USER_KEY, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �ӹ�˾�ṹ���ϵõ�Json����
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromStructures(List<Structure> list) {
        String result = StringUtils.EMPTY;
        for(Structure structure : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            result += "{id:" + structure.getId() + ",type:" + structure.getType() + ",name:'" +
                    structure.getName() + "',pid:" + structure.getPid() + ",indexId:" + structure.getIndexId() + "}";
        }
        return result;
    }

    /**
     * �����û��Ա�
     *
     * @param sex
     * @return
     */
    public static String translateUserSex(int sex) {
        if(UserInterface.SEX_X == sex) {
            return "��";
        }
        if(UserInterface.SEX_O == sex) {
            return "Ů";
        }
        return StringUtils.EMPTY;
    }

    /**
     * ���û����ϵõ�Json����
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromUsers(List<User> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(User user : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_BIT_AND;
            }
            Structure company = StructureDao.getStructureById(user.getCompany());
            Structure dept = StructureDao.getStructureById(user.getDept());
            Structure position = StructureDao.getStructureById(user.getPosition());
            result += "{id:" + user.getId() + ",name:'" + user.getName() + "',letter:'" + user.getLetter() +
                    "',state:" + user.getState() + ",money:" + user.getMoney() + ",company:" + user.getCompany() +
                    ",dept:" + user.getDept() + ",position:" + user.getPosition() + ",desk:'" + user.getDesk() +
                    "',sex:" + user.getSex() +",birthday:'" + user.getBirthday() +"',officeTel:'" + user.getOfficeTel() +
                    "',mobileTel:'" + user.getMobileTel() +"',email:'" + user.getEmail() +"',qq:'" + user.getQq() +
                    "',msn:'" + user.getMsn() +"',address:'" + user.getAddress() +"',headPhoto:'" + user.getHeadPhoto() +
                    "',website:'" + user.getWebsite() + "',companyName:'" + (null==company?"��":company.getName()) +
                    "',deptName:'" + (null==dept?"��":dept.getName()) + "',positionName:'" +
                    (null==position?"��":position.getName()) + "'}";
        }
        return result;
    }

    /**
     * �ӹ��漯�ϵõ�Json����
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromNotices(List<Notice> list) {
        String result = StringUtils.EMPTY;
        for(Notice notice : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + notice.getId() + ",title:'" + notice.getTitle() + "',content:'" +
                    notice.getContent() + "',createDate:'" + notice.getCreateDate() + "',createTime:'" +
                    notice.getCreateTime() + "',createIp:'" + notice.getCreateIp() + "',updateDate:'" +
                    notice.getUpdateDate() + "',updateTime:'" + notice.getUpdateTime() + "',updateIp:'" +
                    notice.getUpdateIp() +"'}";
        }
        return result;
    }

    /**
     * ����Ϣ���ϵõ�Json����
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromMessages(List<Message> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Message message : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            String fromUserName;
            String headPhoto;
            String url;
            if(UserInterface.USER_TYPE_NORMAL == message.getFromUserType()){//��ͨ�û�
                User user = UserDao.getUserById(message.getFromUserId());
                fromUserName = user.getName();
                headPhoto = user.getHeadPhoto();
                url = "/user.jsp?id=" + message.getFromUserId();
            } else {//�����˺�
                PublicUser user = PublicUserUtil.getInstance().getPublicUserById(message.getFromUserId());
                fromUserName = user.getName();
                headPhoto = user.getHeadPhoto();
                url = user.getUrl();
            }
            result += "{id:" + message.getId() + ",fromUserId:" + message.getFromUserId() + ",fromUserType:" +
                    message.getFromUserType() + ",toUserId:" + message.getToUserId() + ",content:'" +
                    message.getContent() + "',state:" + message.getState() + ",date:'" + message.getDate() +
                    "',time:'" + message.getTime() + "',ip:'" + message.getIp() + "',fromUserName:'" +
                    fromUserName + "',headPhoto:'" + headPhoto + "',url:'" + url + "'}";
        }
        return result;
    }

    /**
     * ��վ���ż��ϵõ�Json����
     *
     * @param list
     * @param box
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromLetters(List<Letter> list, String box) throws Exception {
        String result = StringUtils.EMPTY;
        for(Letter letter : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += getJsonStrFromLetter(letter, box);
        }
        return result;
    }

    /**
     * ��վ���ŵõ�Json��
     *
     * @param letter
     * @param box
     * @return
     * @throws Exception
     */
    public static String getJsonStrFromLetter(Letter letter, String box) throws Exception{
        String result = StringUtils.EMPTY;
        /**
         * �ѷ�����ʾ�ռ���
         * �ռ������ɾ����ʾ������
         */
        int displayUserId = 0;//��ʾ�û�id
        int displayUserType = 0;//��ʾ�û�����
        if(StringUtils.equals(LetterInterface.BOX_SENT, box)){
            int commaIdIndex = letter.getToUserIds().indexOf(SYMBOL_COMMA);
            int commaTypeIndex = letter.getToUserTypes().indexOf(SYMBOL_COMMA);
            if(commaIdIndex == -1){//ֻ��һ���ռ���
                displayUserId = Integer.parseInt(letter.getToUserIds());
                displayUserType = Integer.parseInt(letter.getToUserTypes());
            } else {//�ж���ռ���
                displayUserId = Integer.parseInt(letter.getToUserIds().substring(0, commaIdIndex));
                displayUserType = Integer.parseInt(letter.getToUserTypes().substring(0, commaTypeIndex));
            }
        } else {
            displayUserId = letter.getFromUserId();
            displayUserType = letter.getFromUserType();
        }
        String fromUserName;
        String headPhoto;
        String url;
        if(UserInterface.USER_TYPE_NORMAL == displayUserType){//��ͨ�û�
            User user = UserDao.getUserById(displayUserId);
            fromUserName = user.getName();
            headPhoto = user.getHeadPhoto();
            url = "/user.jsp?id=" + displayUserId;
        } else {//�����˺�
            PublicUser user = PublicUserUtil.getInstance().getPublicUserById(displayUserId);
            fromUserName = user.getName();
            headPhoto = user.getHeadPhoto();
            url = user.getUrl();
        }

        result += "{id:" + letter.getId() + ",userId:" + letter.getUserId() + ",userType:" + letter.getUserType() +
                ",sendOrReceive:" + letter.getSendOrReceive() + ",fromUserId:" + letter.getFromUserId() +
                ",fromUserType:" + letter.getFromUserType() + ",toUserIds:'" + letter.getToUserIds() +
                "',toUserTypes:'" + letter.getToUserTypes() + "',ccUserIds:'" + letter.getCcUserIds() +
                "',ccUserTypes:'" + letter.getCcUserTypes() + "',readState:" + letter.getReadState() +
                ",deleteState:" + letter.getDeleteState() + ",title:'" + letter.getTitle() + "',content:'" +
                letter.getContent() + "',createDate:'" + letter.getCreateDate() + "',createTime:'" +
                letter.getCreateTime() + "',createIp:'" + letter.getCreateIp() + "',operateDate:'" +
                letter.getOperateDate() + "',operateTime:'" + letter.getOperateTime() + "',operateIp:'" +
                letter.getOperateIp() + "',fromUserName:'" + fromUserName + "',headPhoto:'" + headPhoto +
                "',url:'" + url + "'}";
        return result;
    }

    /**
     * �ӹ�����־���ϵõ�Json����
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromDiaries(List<Diary> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Diary diary : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            User user = UserDao.getUserById(diary.getUserId());
            result += "{id:" + diary.getId() + ",userId:" + diary.getUserId() + ",date:'" + diary.getDate() +
                    "',content:'" + diary.getContent() + "',createDate:'" + diary.getCreateDate() +
                    "',createTime:'" + diary.getCreateTime() + "',createIp:'" + diary.getCreateIp() +
                    "',updateDate:'" + diary.getUpdateDate() + "',updateTime:'" + diary.getUpdateTime() +
                    "',updateIp:'" + diary.getUpdateIp() + "',userName:'" + user.getName() + "'}";
        }
        return result;
    }

    /**
     * �ӹ�����־���ϵõ�Json����
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromReminds(List<Remind> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Remind remind : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + remind.getId() + ",userId:" + remind.getUserId() + ",date:'" + remind.getDate() +
                    "',content:'" + remind.getContent().replaceAll("\n", "") + "',remindType:" + remind.getRemindType() +
                    ",remindDateTime:'" + remind.getRemindDateTime() + "',remindTarget:'" + remind.getRemindTarget() +
                    "',createDate:'" + remind.getCreateDate() + "',createTime:'" + remind.getCreateTime() +
                    "',createIp:'" + remind.getCreateIp() + "',updateDate:'" + remind.getUpdateDate() +
                    "',updateTime:'" + remind.getUpdateTime() + "',updateIp:'" + remind.getUpdateIp() +
                    "',remindTypeDesc:'" + remind.getRemindTypeDesc() + "'}";
        }
        return result;
    }

    /**
     * �����񼯺ϵõ�Json����
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromTasks(List<Task> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(Task task : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            User fromUser = UserDao.getUserById(task.getFromUserId());
            User toUser = UserDao.getUserById(task.getToUserId());
            result += "{id:" + task.getId() + ",fromUserId:" + task.getFromUserId() + ",toUserId:" +
                    task.getToUserId() + ",title:'" + task.getTitle() + "',content:'" + task.getContent() +
                    "',state:" + task.getState() + ",beginDate:'" + task.getBeginDate() + "',endDate:'" +
                    task.getEndDate() + "',createDate:'" + task.getCreateDate() + "',createTime:'" +
                    task.getCreateTime() + "',createIp:'" + task.getCreateIp() + "',updateDate:'" +
                    task.getUpdateDate() + "',updateTime:'" + task.getUpdateTime() + "',updateIp:'" +
                    task.getUpdateIp() + "',fromUserName:'" + fromUser.getName() + "',toUserName:'" +
                    toUser.getName() + "',stateDesc:'" + task.getStateDesc() + "'}";
        }
        return result;
    }

    /**
     * �Ӷ��ż��ϵõ�Json����
     *
     * @param list
     * @return
     */
    public static String getJsonArrayFromSMS(List<SMS> list) throws Exception {
        String result = StringUtils.EMPTY;
        for(SMS sms : list) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + sms.getId() + ",userId:" + sms.getId() + ",phone:'" + sms.getPhone() +
                    "',content:'" + sms.getContent() + "',state:" + sms.getState() + ",date:'" + sms.getDate() +
                    "',time:'" + sms.getTime() + "',ip:'" + sms.getIp() + "',stateDesc:'" + sms.getStateDesc() +
                    "'}";
        }
        return result;
    }

    /**
     * �õ�Ĭ������
     * md5(defaultPwd + md5Key)
     *
     * @return
     */
    public static String generateDefaultPwd(){
        String md5Key = PropertyUtil.getInstance().getProperty(BaseInterface.MD5_KEY);
        String defaultPwd = PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_PASSWORD);
        MD5Util md5 = new MD5Util();
        String password = md5.md5(defaultPwd + md5Key);
        return password;
    }

    /**
     * ����ְλid�鲿��
     * ע�⣺�κ�һ��ְλ��������ĳ����˾ ����һ���������ĸ����� ���磺���³�
     *
     * @param positionId
     * @return
     */
    public static Structure getDeptByPosition(int positionId) throws Exception{
        // �鿴��˾�����ţ�ְλ��Ϣ
        Structure company = null;
        Structure dept = null;
        Structure position = StructureDao.getStructureById(positionId);
        int pid = position.getPid();
        while (pid != 0) {
            Structure temp = StructureDao.getStructureById(pid);
            if(null == temp) {
                break;
            }
            if(temp.getType() == StructureInterface.TYPE_COMPANY) {
                if(null == company) {
                    company = temp;
                }
                break;//���˹�˾ �Ͳ��ܲ���
            }
            if(temp.getType() == StructureInterface.TYPE_DEPT) {
                if(null == dept) {
                    dept = temp;
                }
                pid = dept.getPid();
            }
            if(temp.getType() == StructureInterface.TYPE_POSITION) {
                pid = temp.getPid();//��������
            }
        }
        return dept;
    }

    /**
     * ����ְλid�鹫˾
     * ע�⣺�κ�һ��ְλ��������ĳ����˾ ����һ���������ĸ����� ���磺���³�
     *
     * @param positionId
     * @return
     */
    public static Structure getCompanyByPosition(int positionId) throws Exception{
        // �鿴��˾�����ţ�ְλ��Ϣ
        Structure company = null;
        Structure dept = null;
        Structure position = StructureDao.getStructureById(positionId);
        int pid = position.getPid();
        while (pid != 0) {
            Structure temp = StructureDao.getStructureById(pid);
            if(null == temp) {
                break;
            }
            if(temp.getType() == StructureInterface.TYPE_COMPANY) {
                if(null == company) {
                    company = temp;
                }
                break;//���˹�˾ �Ͳ��ܲ���
            }
            if(temp.getType() == StructureInterface.TYPE_DEPT) {
                if(null == dept) {
                    dept = temp;
                }
                pid = dept.getPid();
            }
            if(temp.getType() == StructureInterface.TYPE_POSITION) {
                pid = temp.getPid();//��������
            }
        }
        return company;
    }

    /**
     * ���ݽṹ�õ��¼�λ�ýṹ�û�ID�ö��Ÿ���
     *
     * @param structureId
     * @return
     * @throws Exception
     */
    public static String getLowerLevelPositionUserIdWithComma(int structureId) throws Exception {
        List<User> users = getLowerLevelPositionUsers(structureId);
        String userIdWithComma = StringUtils.EMPTY;
        for(User temp : users){
            if(StringUtils.isNotBlank(userIdWithComma)){
                userIdWithComma += SYMBOL_COMMA;
            }
            userIdWithComma += temp.getId();
        }
        return userIdWithComma;
    }

    /**
     * ���ݽṹ�õ��¼�λ�ýṹ�û�����
     *
     * @param structureId
     * @return
     * @throws Exception
     */
    public static List<User> getLowerLevelPositionUsers(int structureId) throws Exception {
        Structure structure = StructureDao.getStructureById(structureId);
        List<Structure> positions = getLowerLevelPositions(structure);
        String positionWithComma = StringUtils.EMPTY;
        for(Structure position : positions){
            if(StringUtils.isNotBlank(positionWithComma)){
                positionWithComma += SYMBOL_COMMA;
            }
            positionWithComma += position.getId();
        }
        List<User> users = UserDao.queryUserByPositionWithComma(positionWithComma);
        return users;
    }

    /**
     * ���ݽṹID�õ��¼�λ�ýṹ����
     *
     * @param structureId
     * @return
     */
    public static List<Structure> getLowerLevelPositions(int structureId) throws Exception {
        Structure structure = StructureDao.getStructureById(structureId);
        List<Structure> positions = getLowerLevelPositions(structure);
        return positions;
    }

    /**
     * ���ݽṹ�õ��¼�λ�ýṹ����
     *
     * @param structure
     * @return
     */
    public static List<Structure> getLowerLevelPositions(Structure structure) throws Exception {
        List<Structure> allStructureList = StructureDao.queryAllStructures();
        List<Structure> positions = getLowerLevelPositions(structure, allStructureList);
        if(structure.getType() == StructureInterface.TYPE_POSITION){
            positions.add(structure);
        }
        return positions;
    }

    /**
     * ���ݽṹ�����й�˾�ṹ�õ��¼�λ�ýṹ
     *
     * @param structure
     * @param allStructureList
     * @return
     */
    public static List<Structure> getLowerLevelPositions(Structure structure, List<Structure> allStructureList){
        List<Structure> positions = new ArrayList<Structure>();
        for(Structure temp : allStructureList){
            if(temp.getPid() == structure.getId()){
                if(temp.getType() == StructureInterface.TYPE_POSITION){
                    positions.add(temp);
                }
                positions.addAll(getLowerLevelPositions(temp, allStructureList));
            }
        }
        return positions;
    }

    /**
     * �����û�id��չʾ�û�
     *
     * @param request
     * @param userIds
     * @return
     */
    public static String displayUsersByIds(HttpServletRequest request, String userIds) throws Exception {
        //��������
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        String result = StringUtils.EMPTY;
        String[] idArray = userIds.split(SYMBOL_COMMA);
        for(String userId : idArray){
            User user = UserDao.getUserById(Integer.parseInt(userId));
            if(StringUtils.isNotBlank(result)){
                result += SYMBOL_COMMA;
            }
            result += "<a href=\"" + baseUrl + "user.jsp?id=" + userId + "\" target=\"_blank\">" +
                    user.getName() + "</a>";
        }
        return result;
    }

    /**
     * Ȩ��У��
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static void checkRightWithException(int userId, String rightCode) throws Exception {
        if(!checkRight(userId,  rightCode)){
            throw new RuntimeException("���޸�Ȩ�ޣ�");
        }
    }

    /**
     * Ȩ��У��
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static void checkRightWithAjaxException(int userId, String rightCode) throws Exception {
        if(!checkRight(userId,  rightCode)){
            throw new AjaxException("���޸�Ȩ�ޣ�");
        }
    }

    /**
     * Ȩ��У��
     * @param userId
     * @param rightCode
     * @throws Exception
     */
    public static boolean checkRight(int userId, String rightCode) throws Exception {
        //��ѯȨ��
        UserRight userRight = UserRightDao.getUserRightByUserId(userId);
        if(userRight.getUserRight().indexOf(rightCode) == -1){
            return false;
        }
        return true;
    }

    /**
     * ���Ƿ���Ȩ��
     * @param rights
     * @param right
     * @return
     */
    public static boolean haveRight(String rights, String right){
        return rights.indexOf(right) > -1;
    }

    /**
     * ������Ƽ��ϵõ�Json����
     *
     * @param clouds
     * @return
     * @throws Exception
     */
    public static String getJsonArrayFromClouds(List<Cloud> clouds) throws Exception {
        String result = StringUtils.EMPTY;
        for(Cloud cloud : clouds) {
            if(StringUtils.isNotBlank(result)) {
                result += SYMBOL_LOGIC_AND;
            }
            result += "{id:" + cloud.getId() + ",userId:" + cloud.getUserId() + ",type:" +
                    cloud.getType() + ",pid:" + cloud.getPid() + ",name:'" + cloud.getName() +
                    "',state:" + cloud.getState() + ",dir:'" + cloud.getDir() + "',route:'" +
                    cloud.getRoute() + "',size:" + ((cloud.getType()==CloudInterface.TYPE_FILE)?cloud.getSize():"0") +
                    ",formatSize:'" + ((cloud.getType()==CloudInterface.TYPE_FILE)?FileUtil.formatFileSize(cloud.getSize()):"0") +
                    "',createDate:'" + cloud.getCreateDate() + "',createTime:'" + cloud.getCreateTime() +
                    "',createIp:'" + cloud.getCreateIp() + "'}";
        }
        return result;
    }

    /**
     * ������������͵õ�����
     * @param type
     * @return
     */
    public static String getCloudTypeDesc(int type){
        if(CloudInterface.TYPE_FILE == type){
            return "�ļ�";
        }
        if(CloudInterface.TYPE_DIR == type){
            return "�ļ���";
        }
        if(CloudInterface.TYPE_SYSTEM_FILE == type){
            return "ϵͳ�ļ�";
        }
        return StringUtils.EMPTY;
    }

    /**
     * �����û�id��Ŀ¼ ��Ŀ¼�Ϸ���
     * 1.���dirΪ��б��/������
     * 2.������dir����/��ȡ��ÿ����dir�Ƿ���ڶ���״̬����
     * @param userId
     * @param dir
     * @throws Exception
     */
    public static void checkCloudDir(int userId, String dir) throws Exception {
        if(StringUtils.isBlank(dir)){
            throw new AjaxException("�ļ�Ŀ¼����:[" + dir + "]");
        }
        //���dirΪ��б��/������
        if(StringUtils.equals(CloudInterface.FRONT_DIR, dir)){
            return;
        }
        //ȥ��ǰ�����б��/
        dir = dir.substring(1);
        if(dir.endsWith(SYMBOL_SLASH)){
            dir = dir.substring(0, dir.length() - 1);
        }
        //����ļ���У��״̬
        String[] dirParts = dir.split(SYMBOL_SLASH);
        for(int i=0;i<dirParts.length;i++){
            String tempDir = SYMBOL_SLASH;
            for(int j=0;j<i+1;j++){
                tempDir += dirParts[j];
                tempDir += SYMBOL_SLASH;
            }
            Cloud tempCloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
            if(null == tempCloud || tempCloud.getType() != CloudInterface.TYPE_DIR){
                throw new AjaxException("�ļ�Ŀ¼����:[" + tempDir + "]");
            }
        }
    }

    /**
     * ��㴴��Ŀ¼
     * @param userId
     * @param dir
     */
    public static void createCloudDir(int userId, String dir, String date, String time, String ip) throws Exception {
        if(StringUtils.isBlank(dir)){
            throw new AjaxException("�ļ�Ŀ¼����:[" + dir + "]");
        }
        //���dirΪ��б��/������
        if(StringUtils.equals(CloudInterface.FRONT_DIR, dir)){
            return;
        }
        //ȥ��ǰ�����б��/
        dir = dir.substring(1);
        if(dir.endsWith(SYMBOL_SLASH)){
            dir = dir.substring(0, dir.length() - 1);
        }
        int pid = 0;
        //�Ƿ� �ҵ���һ����ɾ����Ŀ¼
        boolean findFirstDeleteDir = false;
        //����ļ���У��״̬
        String[] dirParts = dir.split(SYMBOL_SLASH);
        for(int i=0;i<dirParts.length;i++){
            String newDir = StringUtils.EMPTY;
            String tempDir = SYMBOL_SLASH;
            for(int j=0;j<i+1;j++){
                newDir = dirParts[j];
                tempDir += dirParts[j];
                tempDir += SYMBOL_SLASH;
            }
            /**
             * ���û���ҵ���һ����ɾ����Ŀ¼��һֱ��
             * һ���ҵ�������ÿ��Ŀ¼����Ҫ�ֶ�������
             */
            if(!findFirstDeleteDir){
                Cloud tempCloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
                if(null != tempCloud){
                    //����pid
                    pid = tempCloud.getId();
                    continue;
                } else {
                    findFirstDeleteDir = true;
                }
            }
            //ÿ��Ŀ¼����Ҫ�ֶ�������
            Cloud cloud = new Cloud(userId, CloudInterface.TYPE_DIR, pid, newDir, CloudInterface.STATE_NORMAL,
                    tempDir.substring(0, tempDir.length()-newDir.length()-1), tempDir, 0, date, time, ip);
            CloudDao.insertCloud(cloud);
            //����pid
            cloud = CloudDao.getCloudByUserIdAndRoute(userId, tempDir);
            pid = cloud.getId();
        }
    }

    /**
     * �Ƿ���֧�ֵ��ļ�����
     * @param type
     * @return
     */
    public static boolean isSupportCloudDocType(String type){
        if(StringUtils.isBlank(type)){
            return false;
        }
        type = StringUtils.trim(type);
        String[] types = PropertyUtil.getInstance().getProperty(BaseInterface.CLOUD_DOC_SUPPORT_TYPES).split(SYMBOL_COMMA);
        for(String tempType :  types){
            if(StringUtils.equalsIgnoreCase(tempType, type)){
                return true;
            }
        }
        return false;
    }

    /**
     * ��������Ŀ�
     * #��ѯ����Ŀ�Ĺ���
     * #0.��������Ϊdoc
     * #1.���docΪ�ջ��߿��ַ�����ѯ�����ؽ����
     * #2.��doc�е����Ķ��źͿո� ��� Ӣ�Ķ���
     * #3.��doc��Ӣ�Ķ��ŷָ������(����ΪtempDoc)������������ִ�����²�ѯ��������ϲ���Ϊ�������
     * #3.1.����tempDoc��ѯ�û��������鵽���û�id��Ϊ����user_id���в�ѯ
     * #3.2.����tempDoc�����ֶ�title����ģ����ѯ
     * #3.3.����tempDoc�����ֶ�description����ģ����ѯ
     * #3.4.����tempDoc�����ֶ�tags����ģ����ѯ
     * #3.5.������Щ��������or��ƴ��
     * @param doc
     * @return
     */
    public static List<CloudDoc> queryCloudDocs(String doc) throws Exception{
        //������
        List<CloudDoc> cloudDocs = new ArrayList<CloudDoc>();
        //���docΪ�շ���
        if(StringUtils.isBlank(doc)){
            return cloudDocs;
        }
        //�����Ķ��źͿո� ��� Ӣ�Ķ���
        doc = doc.replaceAll("��", SYMBOL_COMMA);
        doc = doc.replaceAll(" ", SYMBOL_COMMA);
        String[] docs = doc.split(SYMBOL_COMMA);
        for(String tempDoc : docs){
            if(StringUtils.isBlank(tempDoc)){
                continue;
            }
            //����ǿ��������Ϊ����
            User user = UserDao.getUserByName(tempDoc);
            int userId = 0;
            if(user != null){
                userId = user.getId();
            }
            cloudDocs.addAll(CloudDocDao.queryCloudDocsByOrConditions(userId, tempDoc));
        }
        return cloudDocs;
    }

    /**
     * �����ļ����õ��ļ�����
     * @param name
     * @return
     */
    public static String getFileType(String name){
        String fileType = StringUtils.EMPTY;
        if(StringUtils.isBlank(name)){
            return fileType;
        }
        int dotIndex = name.lastIndexOf(SymbolInterface.SYMBOL_DOT);
        if(dotIndex > -1){
            fileType = name.substring(dotIndex + 1);
        }
        return fileType;
    }

    /**
     * ���ݹؼ��ʶ��ĵ���չʾ title
     * @param title
     * @param doc
     * @return
     */
    public static String displayCloudDocTitle(String title, String doc) {
        if(StringUtils.isBlank(title)){
            return StringUtils.EMPTY;
        }
        if(title.length() > 100){
            title = title.substring(0, 100) + "...";
        }
        title = title.replaceAll(doc, "<span style=\"color: red\">" + doc + "</span>");
        title = "<span style=\"color: blue\">" + title + "</span>";
        return title;
    }

    /**
     * ���ݹؼ��ʶ��ĵ���չʾ description
     * @param description
     * @param doc
     * @return
     */
    public static String displayCloudDocDescription(String description, String doc) {
        if(StringUtils.isBlank(description)){
            return StringUtils.EMPTY;
        }
        if(description.length() > 100){
            description = description.substring(0, 100) + "...";
        }
        description = "<div class=\"wikiInfo\">������" + description.replaceAll(doc, "<font color=\"red\">" + doc + "</font>") + "</div>";
        return description;
    }

    /**
     * ���ݹؼ��ʶ��ĵ���չʾ tags
     * @param tags
     * @param doc
     * @return
     */
    public static String displayCloudDocTags(String tags, String doc) {
        if(StringUtils.isBlank(tags)){
            return StringUtils.EMPTY;
        }
        String html = StringUtils.EMPTY;
        String[] tagArray = tags.split(SYMBOL_COMMA);
        for(String tag : tagArray){
            if(StringUtils.isNotBlank(html)){
                html += "&nbsp;" + SYMBOL_COMMA + "&nbsp;";
            }
            html += "<a href=\"javascript:queryDoc('" + tag + "')\">" +
                    tag.replaceAll(doc, "<font color=\"red\">" + doc + "</font>") + "</a>";
        }
        html = "��ǩ��" + html;
        return html;
    }

    /**
     * �������֪������
     * @return
     */
    public static List<CloudKnowAsk> queryCloudKnowAsks(String ask) throws Exception{
        //������
        List<CloudKnowAsk> cloudKnowAsks = new ArrayList<CloudKnowAsk>();
        //���askΪ�շ���
//        if(StringUtils.isBlank(ask)){
//            return cloudKnowAsks;
//        }
        //�����Ķ��źͿո� ��� Ӣ�Ķ���
        ask = ask.replaceAll("��", SYMBOL_COMMA);
        ask = ask.replaceAll(" ", SYMBOL_COMMA);
        String[] asks = ask.split(SYMBOL_COMMA);
        for(String tempAsk : asks){
//            if(StringUtils.isBlank(tempAsk)){
//                continue;
//            }
            cloudKnowAsks.addAll(CloudKnowAskDao.queryCloudKnowAsksByQuestion(tempAsk));
        }
        return cloudKnowAsks;
    }

    /**
     * ���ݹؼ��ʶ����֪����չʾ question
     * @param question
     * @param ask
     * @return
     */
    public static String displayCloudKnowQuestion(String question, String ask) {
        if(StringUtils.isBlank(question)){
            return StringUtils.EMPTY;
        }
        if(question.length() > 18){
            question = question.substring(0, 18) + "...";
        }
        question = question.replaceAll(ask, "<span style=\"color: red\">" + ask + "</span>");
        question = "<span style=\"color: blue\">" + question + "</span>";
        return question;
    }

    /**
     * ����request��ȡ����
     * @param request
     * @return
     */
    public static String getRequestStr(HttpServletRequest request){
        String url = request.getScheme() + "://";//����Э�� http �� https
        url += request.getHeader("host");//���������
        url += request.getRequestURI();//������
        if(request.getQueryString()!=null){//�ж���������Ƿ�Ϊ��
            url += "?" + request.getQueryString();//����
        }
        return url;
    }

    /**
     * ����������־
     *
     * @param userId
     * @param type
     * @param content
     * @param date
     * @param time
     * @param ip
     * @throws Exception
     */
    public static void createOperateLog(int userId, int type, String content, String date, String time,
                                        String ip) throws Exception{
        OperateLog operateLog = new OperateLog(userId, type, content, date, time, ip);
        OperateLogDao.insertOperateLog(operateLog);
    }

    /**
     * �����˺Ÿ��û���һ����Ϣ
     * @param publicUserEnglishName �����˺�Ӣ������
     * @param userId �û�Id
     * @param content ����
     * @param ip ����
     */
    public static void createPublicMessage(String publicUserEnglishName, int userId, String content, String ip) throws Exception {
        //����englishName��ȡ�����˺�
        PublicUser publicUser = PublicUserUtil.getInstance().getPublicUserByEnglishName(publicUserEnglishName);
        //��д��־���˷�����Ϣ
        Message message = new Message(publicUser.getId(), UserInterface.USER_TYPE_PUBLIC, userId, content,
                MessageInterface.STATE_NOT_READED, DateUtil.getNowDate(), DateUtil.getNowTime(), ip);
        MessageDao.insertMessage(message);
    }

    /**
     * ��ͨ�û��������û���һ����Ϣ
     * @param fromUserId ������ͨ�û�Id
     * @param toUserId ������ͨ�û�Id
     * @param content ����
     * @param ip ����
     */
    public static void createNormalMessage(int fromUserId, int toUserId, String content, String ip) throws Exception {
        //��д��־���˷�����Ϣ
        Message message = new Message(fromUserId, UserInterface.USER_TYPE_NORMAL, toUserId, content,
                MessageInterface.STATE_NOT_READED, DateUtil.getNowDate(), DateUtil.getNowTime(), ip);
        MessageDao.insertMessage(message);
    }
}
