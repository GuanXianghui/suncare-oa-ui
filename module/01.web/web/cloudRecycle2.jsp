<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.CloudDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <link href="css/imessage.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <script type="text/javascript" src="scripts/cloudRecycle2.js"></script>
    <script type="text/javascript">
        /**
         * 回收站文件Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         */
        var filesJsonStr = "<%=BaseUtil.getJsonArrayFromClouds(CloudDao.queryRecycleClouds(user.getId()))%>";
        //回收站里的对象
        var files = new Array();
        //选择对象
        var chooseClouds = new Array();
        //ctrl键是否按下
        var isCtrlDown = false;
    </script>
</head>

<body onkeydown="keyDown(event)" onkeyup="keyUp(event)">
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
    <div class="normalTitle">申成网盘
        <div class="diskArea">
            <input value="还原"  type="button" class="minBtn" onclick="recover();">
            <input value="彻底删除"  type="button" class="minBtn" onclick="ctrlDelete();">
            <input value="清空回收站"  type="button" class="minBtn" onclick="clearRecycle();">
            <input value="申成网盘"  type="button" class="minBtn" onclick="location.href='cloud2.jsp'">
        </div>
    </div>
    <div id="wikiArea">
        <div class="diskPath" id="dir_route">
            <span class="dir">回收站/</span>
        </div>
        <div class="diskList" id="files">
            <ul>
                <li><img src="images/ext/dir.gif" /><a href="#">我的文件夹</a></li>
                <li><img src="images/ext/png.gif" /><a href="#">FOTO TIME FOR A SNACK promo sticker.png</a></li>
            </ul>
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