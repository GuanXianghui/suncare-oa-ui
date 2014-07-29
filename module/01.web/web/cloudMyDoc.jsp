<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.entities.CloudDoc" %>
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.dao.CloudDocDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/cloudMyDoc.js"></script>
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

    <form name="cloudDeleteDocForm" action="<%=baseUrl%>cloudDeleteDoc.do" method="post">
        <input type="hidden" name="token" value="<%=token%>">
        <input type="hidden" name="docId" id="cloudDeleteDocId">
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
            <%
                List<CloudDoc> cloudDocs = CloudDocDao.queryCloudDocsByUserId(user.getId());
                if(cloudDocs.size() == 0){
            %>
            <dl>
                <dt>
                    暂无文档ToT
                </dt>
            </dl>
            <%
            } else {
                for(CloudDoc cloudDoc : cloudDocs){
            %>
            <dl>
                <dt>
                    <img src="images/ext/txt.gif" width="24" height="20" />
                    <a class="title" href="javascript:window.open('cloudViewDoc.jsp?id=<%=cloudDoc.getId()%>')"><%=cloudDoc.getTitle()%></a>
                    <span class="answer-num">
                        <a class="title" href="#"><%=DateUtil.getLongDate(DateUtil.getDate(cloudDoc.getCreateDate()))%></a>
                        <a href="javascript:window.open('cloudViewDoc.jsp?id=<%=cloudDoc.getId()%>')" class="minBtn">查看</a>
                        <%
                            //文件类型
                            String fileType = StringUtils.EMPTY;
                            int dotIndex = cloudDoc.getRoute().lastIndexOf(SymbolInterface.SYMBOL_DOT);
                            if(dotIndex > -1){
                                fileType = cloudDoc.getRoute().substring(dotIndex);
                            }
                        %>
                        <span onclick="cloudDownLoadDoc(<%=cloudDoc.getId()%>, 'download.jsp?fileRoute=<%=cloudDoc.getRoute()%>&newName=<%=cloudDoc.getTitle() + fileType%>')">
                            <a href="#" class="minBtn">下载</a>
                        </span>
                        <a href="javascript:location.href='cloudUpdateDoc.jsp?id=<%=cloudDoc.getId()%>'" class="minBtn">修改</a>
                        <a href="javascript:cloudDeleteDoc(<%=cloudDoc.getId()%>)" class="minBtn">删除</a>
                    </span>
                </dt>
            </dl>
            <%
                    }
                }
            %>
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