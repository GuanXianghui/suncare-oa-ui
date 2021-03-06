package com.gxx.oa;

import com.gxx.oa.dao.CloudDao;
import com.gxx.oa.entities.Cloud;
import com.gxx.oa.interfaces.CloudInterface;
import com.gxx.oa.interfaces.OperateLogInterface;
import com.gxx.oa.interfaces.SymbolInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.TokenUtil;

import java.util.List;

/**
 * 重命名action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 14:10
 */
public class CloudRenameAction extends BaseAction implements CloudInterface {
    //修改对象id
    String id;
    //新文件名
    String newName;

    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("id:" + id + ",newName:" + newName);

        Cloud cloud = CloudDao.getCloudById(Integer.parseInt(id));
        if(cloud == null || cloud.getUserId() != getUser().getId() || cloud.getState() != CloudInterface.STATE_NORMAL){
            message = "你的操作有误，请重试!";
            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        /**
         * 根据用户id和目录 判目录合法性
         * 1.如果dir为左斜杠/则允许
         * 2.其他则dir根据/截取，每段判dir是否存在而且状态正常
         */
        BaseUtil.checkCloudDir(getUser().getId(), cloud.getDir());

        /**
         * 判当前目录下新的名字是否已经被用
         * 根据用户ID和目录和文件名和文件类型查云 状态为正常
         */
        Cloud newNameCloud = CloudDao.getCloudByUserIdAndDirAndNameAndType(getUser().getId(), cloud.getDir(), newName, cloud.getType());
        if(null != newNameCloud){

            message = "该目录[" + cloud.getDir() + "]下已存在相同名字[" + newName + "]的" + BaseUtil.getCloudTypeDesc(cloud.getType()) + "!";

            //创建操作日志
            BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_RENAME, message, date, time, getIp());

            String resp = "{isSuccess:false,message:'" + message + "',filesJsonStr:''," +
                    "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";
            write(resp);
            return null;
        }

        //修改名字
        cloud.setName(newName);
        /**
         * route varchar(1100) comment '相对路径 可为空
         * 如果是文件夹则有文件夹路径route=dir+name+/ 如果是文件则为真实文件相对路径'
         * route=dir+name+/
         */
        if(cloud.getType() == TYPE_DIR){
            cloud.setRoute(cloud.getDir() + newName + SymbolInterface.SYMBOL_SLASH);
        }
        CloudDao.updateCloud(cloud);

        //如果是文件夹
        if(cloud.getType()==CloudInterface.TYPE_DIR){
            /**
             * 当[重命名文件夹]或者[移动文件夹至其他目录]时，除了要改变自己的pid，dir和route，还需要递归查询该文件夹下所有文件或者文件夹，将dir和route字段刷新
             * 如果是[文件][重命名]或者[移动至其他目录]，只需改变自己的pid，dir和route即可
             * 注意：对于route，如果是文件夹存 相对路径 要做修改，如果是文件 存 服务器上的绝对路径 不做修改
             */
            BaseUtil.refreshAllCloudsBelow(getUser().getId(), cloud);
        }

        //从申成云集合得到Json数组
        List<Cloud> clouds = CloudDao.queryCloudsByPid(getUser().getId(), cloud.getPid());
        String filesJsonStr = BaseUtil.getJsonArrayFromClouds(clouds).replaceAll("\\\'", "\\\\\\\'").
                replaceAll("\\\"", "\\\\\\\"");

        //创建操作日志
        BaseUtil.createOperateLog(getUser().getId(), OperateLogInterface.TYPE_CLOUD_RENAME, "重命名成功！", date, time, getIp());

        //返回结果
        String resp = "{isSuccess:true,message:'重命名成功！',filesJsonStr:'" + filesJsonStr + "'," +
                "hasNewToken:true,token:'" + TokenUtil.createToken(request) + "'}";

        write(resp);
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
