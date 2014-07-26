<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.gxx.oa.dao.OperateLogDao" %>
    <%@ page import="com.gxx.oa.entities.OperateLog" %>
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ include file="header.jsp" %>
    <%
        //权限校验
        if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0006_OPERATE_LOG)){
            //域名链接
            response.sendRedirect(baseUrl + "index.jsp");
            return;
        }
        //外层
        outLayer = "日志模块";
        //内层
        inLayer = "查看日志";

        //用户id
        String userIdStr = StringUtils.trimToEmpty(request.getParameter("userId"));
        //用户id
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (Exception e) {
            userId = 0;
        }
        //操作类型
        String typeStr = StringUtils.trimToEmpty(request.getParameter("type"));
        //操作类型
        int type;
        try {
            type = Integer.parseInt(typeStr);
        } catch (Exception e) {
            type = 0;
        }
        //日期
        String date = StringUtils.trimToEmpty(request.getParameter("date"));

        //当前页数
        String pageStr = StringUtils.trimToEmpty(request.getParameter("pageNum"));
        //当前页数
        int pageNum;
        try {
            pageNum = Integer.parseInt(pageStr);
        } catch (Exception e) {
            pageNum = 1;
        }

        //操作日志列表每页大小
        int pageSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.OPERATE_LOG_PAGE_SIZE));
        //操作日志总数
        int count = OperateLogDao.countOperateLogsByLike(userId, type, date);
        //是否为空
        boolean isEmpty = count == 0;
        //总页数
        int pageCount = (count - 1) / pageSize + 1;
        //删除最后一条，可能会少掉一页
        if(pageNum > pageCount){
            pageNum = pageCount;
        }
        //操作日志列表
        List<OperateLog> operateLogs = OperateLogDao.queryOperateLogsByLike(userId, type, date, pageNum);
    %>
    <title>申成-订单信息系统</title>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset_back.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="scripts/operateLog.js"></script>
    <%@ include file="datepicker_base.jsp" %>
    <script type="text/javascript">
        //当前页数
        var pageNum = <%=pageNum%>;
        //日期
        var date = "<%=date%>";
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //初始化用户id
        var initUserId = <%=userId%>;
    </script>
</head>
<body>
<div id="body-wrapper">
    <div id="sidebar">
        <div id="sidebar-wrapper">
            <h1 id="sidebar-title"><a href="#">申成-OA系统</a></h1>
            <img id="logo" src="images/suncare-files-logo.png" alt="Simpla Admin logo"/>

            <div id="profile-links"> Hello, [<%=user.getName()%>], <a href="http://www.suncarechina.com"
                                                                      target="_blank">申成</a>欢迎您！<br/>
                <br/>
                <a href="home.jsp" title="Sign Out">退出</a>
            </div>
            <%@ include file="layers.jsp" %>
        </div>
    </div>
    <div id="main-content">
        <form action="operateLog.jsp" name="operateLogForm" method="post" style="display: none;">
            <input type="hidden" name="userId" id="operateLogUserId" value="<%=userId%>">
            <input type="hidden" name="type" id="operateLogType" value="<%=type%>">
            <input type="hidden" name="date" id="operateLogDate" value="<%=date%>">
            <input type="hidden" name="pageNum" id="pageNum" value="1">
        </form>
        <form>
            <span>用户</span>&nbsp;&nbsp;
            <input class="text-input" type="text" id="userName" value="全部用户"/>
            <input class="button" type="button" onclick="chooseAll();" value="全部用户"/>
            <input class="button" type="button" onclick="choose();" value="从通讯录选择"/>
            <input type="hidden"  id="userId" value="<%=userId%>">

            <span>操作类型</span>&nbsp;&nbsp;
            <select class="text-input" id="type">
                <option value="">全部</option>
                <%
                    /**
                     * 1:登陆 2:退出 3:修改密码 4:上传头像 5:修改信息 6:个人公告管理 7:个人消息管理 8:写站内信 9:站内信管理
                     * 10:工作日志管理 11:写工作日志 12:修改工作日志 13:提醒管理 14:任务管理 15:写任务 16:修改任务 17:短信管理
                     * 18:创建用户 19:查询用户 20:修改用户 21:组织架构管理 22:公告管理 23:修改默认权限 24:读取用户权限 25:修改用户权限
                     * 26:申成网盘文件上传 27:申成网盘加载文件夹 28:申成网盘新建文件夹 29:申成网盘删除文件(夹)
                     * 30:申成网盘重命名文件(夹) 31:申成网盘还原文件(夹) 32:申成网盘彻底删除文件(夹) 33:申成网盘清空回收站
                     * 34:申成文库上传文档 35:申成文库修改文档 36:申成文库删除文档 37:申成知道提问 38:申成知道回答 39:申成知道修改提问
                     * 40:申成知道删除提问 41:申成知道删除回答 42:申成知道赞回答
                     */
                    String[] types = new String[]{"登陆","退出","修改密码","上传头像","修改信息","个人公告管理",
                            "个人消息管理","写站内信","站内信管理","工作日志管理","写工作日志","修改工作日志",
                            "提醒管理","任务管理","写任务","修改任务","短信管理","创建用户","查询用户",
                            "修改用户","组织架构管理","公告管理","修改默认权限","读取用户权限","修改用户权限",
                            "申成网盘文件上传","申成网盘加载文件夹","申成网盘新建文件夹","申成网盘删除文件(夹)",
                            "申成网盘重命名文件(夹)","申成网盘还原文件(夹)","申成网盘彻底删除文件(夹)",
                            "申成网盘清空回收站","申成文库上传文档","申成文库修改文档","申成文库删除文档",
                            "申成知道提问","申成知道回答","申成知道修改提问","申成知道删除提问",
                            "申成知道删除回答","申成知道赞回答"};
                    for(int i=0;i<types.length;i++){
                %>
                <option value="<%=i+1%>"<%=i+1==type?" SELECTED":""%>><%=types[i]%></option>
                <%
                    }
                %>
            </select>
            <span>日期</span>&nbsp;&nbsp;<input class="text-input" type="text" id="date" value="<%=date%>"/>
            <input class="button" type="button" onclick="queryOperateLogs();" value="查询"/>
            <br/>
            <br/>
        </form>
        <div class="content-box">
            <div class="content-box-header">
                <h3>操作日志列表</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab1" class="default-tab">Table</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab1">
                    <div id="message_id" class="notification information png_bg" style="display: none;">
                        <a href="#" class="close">
                            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                        </a>

                        <div id="message_id_content"> 提示信息！</div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>用户姓名</th>
                            <th>操作类型</th>
                            <th>内容</th>
                            <th>日期</th>
                            <th>时间</th>
                            <th>IP</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <td colspan="7">
                                <div class="pagination">
                                    <a href="javascript: jump2page(1)" title="首页">&laquo; 首页</a>
                                    <%
                                        if(pageNum > 1){
                                    %>
                                    <a href="javascript: jump2page(<%=pageNum-1%>)" title="上一页">&laquo; 上一页</a>
                                    <%
                                        }
                                    %>
                                    <%
                                        //显示前2页，本页，后2页
                                        for(int i=pageNum-2;i<pageNum+3;i++){
                                            if(i >= 1 && i <= pageCount){
                                    %>
                                    <a href="javascript: jump2page(<%=i%>)" class="number<%=(i==pageNum)?" current":""%>" title="<%=i%>"><%=i%></a>
                                    <%
                                            }
                                        }
                                    %>
                                    <%
                                        if(pageNum < pageCount){
                                    %>
                                    <a href="javascript: jump2page(<%=pageNum+1%>)" title="下一页">下一页 &raquo;</a>
                                    <%
                                        }
                                    %>
                                    <a href="javascript: jump2page(<%=pageCount%>)" title="尾页">尾页 &raquo;</a>
                                </div>
                                <div class="clear"></div>
                            </td>
                        </tr>
                        </tfoot>
                        <tbody>
                        <%
                            //判是否为空
                            if (isEmpty) {
                        %>
                        <tr>
                            <td colspan="7">
                                没找到符合条件的操作日志
                            </td>
                        </tr>
                        <%
                        } else {//非空
                            for (OperateLog tempOperateLog : operateLogs) {
                        %>
                        <tr>
                            <td><%=tempOperateLog.getId()%></td>
                            <td><%=tempOperateLog.getUserId()%></td>
                            <td><%=tempOperateLog.getTypeDesc()%></td>
                            <td><%=tempOperateLog.getContent()%></td>
                            <td><%=DateUtil.getLongDate(DateUtil.getDate(tempOperateLog.getDate()))%></td>
                            <td><%=DateUtil.getLongTime(DateUtil.getDateTime(tempOperateLog.getDate(), tempOperateLog.getTime()))%></td>
                            <td><%=tempOperateLog.getIp()%></td>
                        </tr>
                        <%
                                }
                            }
                        %>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="footer">
            <small>
                &#169; Copyright 2014 Suncare | Powered by 关向辉
            </small>
        </div>
    </div>
</div>
</body>
</html>
