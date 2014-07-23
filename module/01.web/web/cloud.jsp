<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //查看Action传出来的dir
        String dir = (String)request.getAttribute("dir");
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/cloud.js"></script>
    <script type="text/javascript">
        //当前目录 首页为/
        var dir = '<%=StringUtils.isBlank(dir)?SymbolInterface.SYMBOL_SLASH:dir%>';
        //当前目录中的文件
        var files = new Array();
        //选择对象
        var chooseClouds = new Array();
        //ctrl键是否按下
        var isCtrlDown = false;
    </script>
	<style>
	#files ul li{
		cursor: default;
	}
	</style>
</head>
<%@ include file="facebox_message.jsp" %>
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

    <div id="new_dir_div" style="display: none;">
        文件夹名字：<input class="inputArea inputWidthShort" type="text" id="new_dir_name">
        <input class="minBtn" type="button" onclick="newDir(this);" value="新建">
    </div>

    <div id="rename_div" align="center" style="width: 100%; display: none;">
        重命名：<input class="inputArea inputWidthShort" type="text" id="rename_name">
        <input class="minBtn" type="button" onclick="rename(this);" value="修改">
    </div>

    <div align="center" style="width: 100%; display: none;">
        <form name="uploadForm" method="post" autocomplete="off"
              action="" enctype="multipart/form-data">
            <input type="text" name="dir" id="uploadDir">
            <input type="file" name="file" id="uploadFile" onchange="upload(this)">
        </form>
    </div>

    <div class="normalTitle">申成网盘
        <div class="diskArea">
            <div style="display: none;">
                <a id="showNewDirDiv" class="shortcut-button" href="#new_dir_div" rel="modal"></a>
            </div>
            <div style="display: none;">
                <a id="showRenameDiv" class="shortcut-button" href="#rename_div" rel="modal"></a>
            </div>
            <input value="上传"  type="button" class="minBtn" onclick="beforeUpload();">
            <input value="下载"  type="button" class="minBtn" onclick="download();">
            <a href="#new_dir_div" rel="facebox" id="new_dir_a"></a>
            <input value="新建文件夹" type="button" class="minBtn" onclick="beforeNewDir();">
            <a href="#rename_div" rel="facebox" id="rename_div_a"></a>
            <input value="重命名"  type="button" class="minBtn" onclick="beforeRename();">
            <input value="删除"  type="button" class="minBtn" onclick="deleteFile();">
            <input value="返回上一层"  type="button" class="minBtn" onclick="lastDir();">
            <input value="回收站"  type="button" class="minBtn" onclick="location.href='cloudRecycle.jsp'">
        </div>
    </div>
    <div id="wikiArea">
        <div class="diskPath" id="dir_route">
            <span class="dir" onclick="loadDir('/')">我的网盘/</span>
            <span class="dir" onclick="loadDir('/新建文件夹/')">新建文件夹/</span>
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