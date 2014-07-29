<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.entities.CloudDoc" %>
    <%@ page import="com.gxx.oa.dao.CloudDocDao" %>
    <%@ page import="com.gxx.oa.interfaces.CloudDocInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        int id;
        CloudDoc cloudDoc;
        try{
            id = Integer.parseInt(request.getParameter("id"));
            cloudDoc = CloudDocDao.getCloudDocById(id);
            if(cloudDoc == null || cloudDoc.getUserId() != user.getId() || cloudDoc.getState() != CloudDocInterface.STATE_NORMAL){
                throw new RuntimeException("文档不存在 或者 文档不是你的！");
            }
        } catch (Exception e){
            response.sendRedirect("cloudDoc.jsp");
            return;
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudDocBase.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudUpdateDoc.js"></script>
    <script type="text/javascript">
        var initTitle = '<%=cloudDoc.getTitle()%>';
        var initDescription = '<%=cloudDoc.getDescription()%>';
        var initType = '<%=cloudDoc.getType()%>';
        var initTags = '<%=cloudDoc.getTags()%>';
    </script>
</head>
<%@ include file="facebox_message.jsp" %>
<body>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg" /></a></div>
    <%@ include file="menu.jsp" %>
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
                <li><a href="cloudUploadDoc.jsp">上传文档</a></li>
                <li><a href="cloudMyDoc.jsp">我的文档</a></li>
            </ul>
        </div>
        <div class="wikiList">
            <form action="<%=baseUrl%>cloudUpdateDoc.do" name="cloudUpdateDocFrom" method="post">
                <input type="hidden" name="token" value="<%=token%>">
                <input type="hidden" name="docId" value="<%=cloudDoc.getId()%>">
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
                        <td class="table_title"></td>
                        <td><input name="dosubmit" value="修改" type="button" class="subBtn" onclick="cloudUpdateDoc()"/></td>
                    </tr>
                </table>
            </form>
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