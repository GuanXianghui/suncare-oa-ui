<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <%@ include file="datepicker_base.jsp" %>
    <script type="text/javascript" charset="utf-8" src="scripts/writeTask.js"></script>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
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
    <%--<div id="right_Box">--%>
        <%--<div class="taskBox">--%>
            <%--<div class="normalTitle">分配给我的任务<span class="titleSelect">全部任务</span></div>--%>
            <%--<div class="task">--%>
                <%--<div class="task_status"><span>17</span>小时</div>--%>
                <%--<div class="task_title"><a href="#">任务名称任务名称</a></div>--%>
                <%--<div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>--%>
                <%--<div class="task_time">创建时间： 2014-5-20 14:21</div>--%>
                <%--<div class="task_time">结束时间： 2014-5-20 14:21</div>--%>
                <%--<div class="clearBoth"></div>--%>
            <%--</div>--%>
            <%--<div class="task">--%>
                <%--<div class="task_status task_emergency"><span>1</span>小时</div>--%>
                <%--<div class="task_title"><a href="#">任务名称任务名称</a></div>--%>
                <%--<div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>--%>
                <%--<div class="task_time">创建时间： 2014-5-20 14:21</div>--%>
                <%--<div class="task_time">结束时间： 2014-5-20 14:21</div>--%>
                <%--<div class="clearBoth"></div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="taskBox">--%>
            <%--<div class="normalTitle">我分配的任务<span class="titleSelect">全部任务</span></div>--%>
            <%--<div class="task">--%>
                <%--<div class="task_status task_finished"><span>完成</span></div>--%>
                <%--<div class="task_title"><a href="#">任务名称任务名称</a></div>--%>
                <%--<div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>--%>
                <%--<div class="task_time">创建时间： 2014-5-20 14:21</div>--%>
                <%--<div class="task_time">结束时间： 2014-5-20 14:21</div>--%>
                <%--<div class="clearBoth"></div>--%>
            <%--</div>--%>
            <%--<div class="task">--%>
                <%--<div class="task_status"><span>17</span>小时</div>--%>
                <%--<div class="task_title"><a href="#">任务名称任务名称</a></div>--%>
                <%--<div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>--%>
                <%--<div class="task_time">创建时间： 2014-5-20 14:21</div>--%>
                <%--<div class="task_time">结束时间： 2014-5-20 14:21</div>--%>
                <%--<div class="clearBoth"></div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div><%-- id="left_Box"--%>
        <div class="msgBox">
            <div class="normalTitle">创建任务</div>
            <div class="create_task">
                <form name="writeTaskForm" action="writeTask.do" method="post">
                    <table cellpadding="0" cellspacing="0" width="100%" class="information">
                        <input type="hidden" id="token" name="token" value="<%=token%>">
                        <input type="hidden" id="fromUserId" name="fromUserId" value="<%=user.getId()%>">
                        <tr>
                            <td class="table_title">指派给：</td>
                            <td>
                                <span id="toUser"></span>
                                <input value="从通讯录选择" type="button" class="minBtn" onclick="choose()" />
                                <input id="toUserId" type="hidden" name="toUserId">
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title">任务名称：</td>
                            <td><input id="title" type="text" name="title" value="" class="inputArea inputWidthLong"></td>
                        </tr>
                        <tr>
                            <td class="table_title">开始时间：</td>
                            <td><input id="beginDate" type="text" name="beginDate" value="" class="inputArea inputWidthLong"></td>
                        </tr>
                        <tr>
                            <td class="table_title">结束时间：</td>
                            <td><input id="endDate" type="text" name="endDate" value="" class="inputArea inputWidthLong"></td>
                        </tr>
                        <tr>
                            <td class="table_title">任务内容：</td>
                            <td>
                                <script id="editor" type="text/plain"></script>
                            </td>
                            <textarea style="display: none;" id="content" name="content"></textarea>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td><input name="dosubmit" value="提交" type="button" class="subBtn" onclick="writeTask()" /></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <div class="clearBoth"></div>
</div>
<!-- 主显示区 结束-->
<!--右侧IM 开始-->
<div id="sc_IM">
    <div id="SCIM_search">查找</div>
    <div id="SCIM_uList">
        <ul>
            <li><a href="#"><img src="images/header.jpg" /></a><span>关向辉</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>关关</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>张飞</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>飞飞</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>关辉</span></li>
        </ul>
    </div>
    <div id="SCIM_groupSel">分组选择</div>
</div>
<!--右侧IM 结束-->
</body>
</html>