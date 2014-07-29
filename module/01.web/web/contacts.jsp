<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.dao.StructureDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/contacts.js"></script>
    <script type="text/javascript">
        <%--//所有员工json串--%>
        <%--var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";--%>
        <%--//所有公司结构json串--%>
        <%--var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";--%>
    </script>
</head>

<body>
<%@ include file="facebox_message.jsp" %>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg"/></a></div>
    <%@ include file="menu.jsp" %>
</div>
<!-- 头部固定菜单层 结束-->
<!-- 主显示区 开始-->
<div id="mainArea">
    <div class="normalTitle">通讯录</div>
    <div id="userListArea" style="text-align: left;">
        <ul>
            <li>
                <p>董事长</p>
                <a href="#">某某</a>
                <a href="#">某某某</a>
                <a href="#">某某</a>
                <a href="#">某某</a>
            </li>
            <li>
                <p>总经理</p>
                <a href="#" class="userSelected">某某</a>
                <a href="#">某某某</a>
                <a href="#">某某</a>
                <a href="#">某某</a>
            </li>
            <li>
                <p>副总经理</p>
                <a href="#">某某</a>
                <a href="#">某某某</a>
                <a href="#">某某</a>
                <a href="#">某某</a>
            </li>
            <li>
                <p>销售部</p>
                <a href="#">某某<span>销售经理</span></a>
                <a href="#">某某某</a>
                <a href="#">某某</a>
                <a href="#">某某</a>
            </li>
        </ul>
    </div>
    <div class="clearBoth"></div>
</div>
<!-- 主显示区 结束-->
<!--右侧IM 开始-->
<%@ include file="im.jsp" %>
<!--右侧IM 结束-->
</body>
</html>
