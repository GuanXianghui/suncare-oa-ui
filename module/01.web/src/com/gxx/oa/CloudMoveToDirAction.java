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
 * 移动文件或者文件夹action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudMoveToDirAction extends BaseAction implements CloudInterface {
    //移动对象ID
    String id;
    //移动至新目录路径
    String moveDir;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id + ",moveDir:" + moveDir);

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), moveDir);

        //查看移动对象是否存在
        Cloud moveCloud = CloudDao.getCloudById(Integer.parseInt(id));
        if(moveCloud == null){
            String resp = "{isSuccess:false,message:'你的操作有误',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //根据用户ID和目录和文件名和文件类型查云 状态为正常
        Cloud cloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), moveDir, moveCloud.getName(), moveCloud.getType());
        if(cloud != null){
            message = "该目录[" + moveDir + "]下已存在相同名字[" + moveCloud.getName() + "]的文件" +
                    (moveCloud.getType()==CloudInterface.TYPE_DIR?"夹":"") + "!不能移动到该目录下!";

            //创建操作日志
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_MOVE_TO_DIR, message, date, time, getIp());

            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //父目录id
        int pid = FRONT_DIR_PID;
        if(!StringUtils.equals(FRONT_DIR, moveDir)){
            Cloud dirCloud = CloudDao.getCloudByUserIdAndRoute(getUser().getId(), moveDir);
            pid = dirCloud.getId();
        }

        //修改父目录ID，父目录路径，路径
        moveCloud.setPid(pid);
        moveCloud.setDir(moveDir);
        /**
         * 对于route
         * 如果是文件夹存 相对路径 要做修改
         * 如果是文件 存 服务器上的绝对路径 不做修改
         */
        if(moveCloud.getType() == CloudInterface.TYPE_DIR){
            moveCloud.setRoute(moveDir + moveCloud.getName() + SymbolInterface.SYMBOL_SLASH);
        }
        CloudDao.updateCloud(moveCloud);

        //如果是文件夹
        if(moveCloud.getType()==CloudInterface.TYPE_DIR){
            /**
             * 当[重命名文件夹]或者[移动文件夹至其他目录]时，除了要改变自己的pid，dir和route，还需要递归查询该文件夹下所有文件或者文件夹，将dir和route字段刷新
             * 如果是[文件][重命名]或者[移动至其他目录]，只需改变自己的pid，dir和route即可
             * 注意：对于route，如果是文件夹存 相对路径 要做修改，如果是文件 存 服务器上的绝对路径 不做修改
             */
            BaseUtil.refreshAllCloudsBelow(getUser().getId(), moveCloud);
        }

        message = "移动文件" + (moveCloud.getType()==CloudInterface.TYPE_DIR?"夹":"") + "[" + moveCloud.getName() +
                "]至目录[" + moveDir + "]下成功！";

        //返回结果
        String resp = "{isSuccess:true,message:'" + message + "',hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        //创建操作日志
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
