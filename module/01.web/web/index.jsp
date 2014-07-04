<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="headerWithOutCheckLogin.jsp" %>
    <title>Suncare-OA 登陆系统</title>
    <link href="css/login.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="scripts/md5.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <script type="text/javascript" src="scripts/index.js"></script>
</head>

<body onkeyup="keyPress(event)">
<div class="login_panel">
    <div class="login_logo"></div>
    <%
        if(isLogin){
    %>
    <div class="login_loginBtn">
        <input name="dosubmit" value="您已登陆，点击直接进入主页" type="button" style="width:210px;"
               onclick="location.href=baseUrl+'userManage2.jsp';"/>
    </div>
    <%
    } else {
    %>
    <div class="login_inputArea"><input id="i_username" name="username" type="text" value="用户名" /></div>
    <div class="login_inputArea">
        <input id="i_password_text" type="text" value="密码" />
        <input id="i_password" name="password" type="password" value="" />
    </div>
    <div class="login_loginBtn"><input name="dosubmit" value="登 陆" type="button" onclick="login();" style="width:210px;" /></div>
    <%
        }
    %>
</div>
</body>
</html>