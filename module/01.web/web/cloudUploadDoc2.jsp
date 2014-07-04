<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <link href="css/imessage.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/base.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudDocBase.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudUploadDoc2.js"></script>
</head>

<body>
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

    <form name="cloudQueryDocForm" action="cloudDoc.jsp" method="post">
        <input type="hidden" name="doc" id="cloudQueryDocName">
    </form>

    <div class="normalTitle">申成文库
        <div class="searchArea">
            <input class="inputArea inputWidthLong" type="text" id="doc_name" value=""><input value="搜索文库" type="button" class="minBtn" onclick="queryDoc($('#doc_name').val());">
        </div>
    </div>
    <div id="wikiArea">
        <div class="wikiMenu">
            <ul>
                <li><a href="cloudUploadDoc2.jsp">上传文档</a></li>
                <li><a href="cloudMyDoc2.jsp">我的文档</a></li>
            </ul>
        </div>
        <div class="wikiList">
            <form action="<%=baseUrl%>/cloudUploadDoc.do?token=<%=token%>" name="cloudUploadDocFrom"
                  method="post" autocomplete="off" enctype="multipart/form-data">
                <table cellpadding="0" cellspacing="0" width="100%" class="information">
                    <tr>
                        <td class="table_title">标题：</td>
                        <td><input id="title" type="text" name="title" value=""
                                   class="inputArea inputWidthLong" onfocus="focusTitle(this)"
                                   onblur="blurTitle(this)"></td>
                    </tr>
                    <tr>
                        <td class="table_title">简介：</td>
                        <td><textarea id="description" type="text" name="description" value=""
                                      class="inputArea inputWidthLong" onfocus="focusDescription(this)"
                                      onblur="blurDescription(this)"></textarea></td>
                    </tr>
                    <tr>
                        <td class="table_title">分类：</td>
                        <td><span id="show_type"></span>
                            <input name="type" type="hidden" id="type"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="table_title">标签：</td>
                        <td><input id="tags" type="text" name="tags" value=""
                                   class="inputArea inputWidthLong" onfocus="focusTags(this)"
                                   onblur="blurTags(this)"></td>
                    </tr>
                    <tr>
                        <td class="table_title">文档：</td>
                        <td>
                            <input type="file" name="doc" id="doc" onchange="changeDoc()">
                        </td>
                    </tr>
                    <tr>
                        <td class="table_title">限制：</td>
                        <td>
                            &nbsp;支持文件大小<=200M，文件类型：
                            <img src="images/cloud_doc/doc.jpg" width="19px">doc,docx
                            <img src="images/cloud_doc/ppt.jpg" width="19px">ppt,pptx
                            <img src="images/cloud_doc/xls.jpg" width="19px">xls,xlsx
                            <img src="images/cloud_doc/vsd.jpg" width="19px">vsd
                            <img src="images/cloud_doc/pot.jpg" width="19px">pot
                            <img src="images/cloud_doc/pps.jpg" width="19px">pps
                            <img src="images/cloud_doc/rtf.jpg" width="19px">rtf
                            <img src="images/cloud_doc/wps.jpg" width="19px">wps
                            <img src="images/cloud_doc/et.jpg" width="19px">et
                            <img src="images/cloud_doc/dps.jpg" width="19px">dps
                            <img src="images/cloud_doc/pdf.jpg" width="19px">pdf
                            <img src="images/cloud_doc/txt.jpg" width="19px">txt
                            <img src="images/cloud_doc/epub.jpg" width="19px">epub
                        </td>
                    </tr>
                    <tr>
                        <td class="table_title"></td>
                        <td><input name="dosubmit" value="上传文档" type="button" class="subBtn" onclick="cloudUploadDoc()"/></td>
                    </tr>
                </table>
            </form>
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