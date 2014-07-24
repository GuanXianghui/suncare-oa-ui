<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.TaskDao" %>
    <%@ page import="com.gxx.oa.interfaces.TaskInterface" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //类型
        int type;
        try{
            type = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("type")));
        } catch (Exception e){
            type = TaskInterface.TYPE_TO_ME;//分配给我的
        }

        //用户id
        int userId;
        try{
            userId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("userId")));
        } catch (Exception e){
            userId = 0;
        }

        int fromUserId;
        int toUserId;
        if(type == TaskInterface.TYPE_TO_ME){
            fromUserId = userId;
            toUserId = user.getId();
        } else if(type == TaskInterface.TYPE_FROM_ME){
            fromUserId = user.getId();
            toUserId = userId;
        } else {
            response.sendRedirect(baseUrl + "task.jsp");
            return;
        }

        //如果state大于0带上该条件
        int state;//状态
        try{
            state = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("state")));
        } catch (Exception e){
            state = 0;
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script type="text/javascript" src="scripts/task.js"></script>
    <script type="text/javascript">
        //选择用户id
        var userId = <%=userId%>;
        //任务来源用户id
        var fromUserId = <%=fromUserId%>;
        //任务接受用户id
        var toUserId = <%=toUserId%>;
        //状态
        var state = <%=state%>;
        /**
         * 任务Json串
         */
        var taskJsonStr = "<%=BaseUtil.getJsonArrayFromTasks(TaskDao.queryTasksByFromTo(fromUserId, toUserId, state, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.TASK_PAGE_SIZE)))).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //任务总数
        var taskCount = <%=TaskDao.countTasksByUserIdAndState(fromUserId, toUserId, state)%>;
        //所有员工json串
        <%--var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";--%>
    </script>
</head>

<body>
<%@ include file="facebox_message.jsp" %>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg" /></a></div>
    <%@ include file="menu.jsp" %>
    <div class="menu_info">
        <a href="#"><img src="images/header.jpg" /></a>
    </div>
</div>
<!-- 头部固定菜单层 结束-->
<!-- 主显示区 开始-->
<div id="mainArea">
    <div>
        <div class="taskBox">
            <div class="normalTitle">任务 类型:
                <select id="type" class="inputArea">
                    <option value="<%=TaskInterface.TYPE_TO_ME%>"<%=type == TaskInterface.TYPE_TO_ME?" selected":""%>>分配给我的</option>
                    <option value="<%=TaskInterface.TYPE_FROM_ME%>"<%=type == TaskInterface.TYPE_FROM_ME?" selected":""%>>我分配的</option>
                </select>
                用户:
                <select name="userId" id="userId" class="inputArea">
                    <option value="">全部用户</option>
                </select>
                状态:
                <select id="state" class="inputArea">
                    <option value="">全部</option>
                    <option value="<%=TaskInterface.STATE_NEW%>"<%=state == TaskInterface.STATE_NEW?" selected":""%>>新建</option>
                    <option value="<%=TaskInterface.STATE_ING%>"<%=state == TaskInterface.STATE_ING?" selected":""%>>进行中</option>
                    <option value="<%=TaskInterface.STATE_DONE%>"<%=state == TaskInterface.STATE_DONE?" selected":""%>>完成</option>
                    <option value="<%=TaskInterface.STATE_DROP%>"<%=state == TaskInterface.STATE_DROP?" selected":""%>>废除</option>
                </select>
                <input class="minBtn" type="button" onclick="selectTask()" value="选择">
                <input class="minBtn" type="button" onclick="location.href='writeTask.jsp'" value="分配任务">
            </div>
            <div align="center">
                <table id="task_table" width="95%">
                    <tr class="alt-row">
                        <th width="80">任务来源</th>
                        <th width="80">任务指派给</th>
                        <th>任务名称</th>
                        <th width="60">状态</th>
                        <th width="80">开始日期</th>
                        <th width="80">结束日期</th>
                    </tr>
                    <tr>
                        <td><a href="http://www.suncare-sys.com:10000/user.jsp?id=1" target="_blank">关向辉</a></td>
                        <td><a href="http://www.suncare-sys.com:10000/user.jsp?id=1" target="_blank">关向辉</a></td>
                        <td><a href="http://www.suncare-sys.com:10000/showTask.jsp?id=2">块来新厂</a></td>
                        <td>进行中</td><td>20140609</td><td>20140610</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="clearBoth"></div>
</div>
<!-- 主显示区 结束-->
<!--右侧IM 开始-->
<%@ include file="im.jsp" %>
<!--右侧IM 结束-->
</body>
</html>