<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.dao.StructureDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA 登陆系统</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/base.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/chooseMultiplePhoneContact.js"></script>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
    </script>
</head>

<body>
<div id="userListArea">
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
<div align="center">
    <input name="dosubmit" value="确认" type="submit" class="subBtn" onclick="closeWindow()" />
</div>
</body>
</html>
