<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.entities.CloudDoc" %>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //搜索文档
        String doc = StringUtils.trimToEmpty(request.getParameter("doc"));
        //是否执行了查询
        boolean isQuery = false;
        //搜索结果
        List<CloudDoc> cloudDocs = new ArrayList<CloudDoc>();
        if(StringUtils.isNotBlank(doc)){
            isQuery = true;
            cloudDocs = BaseUtil.queryCloudDocs(doc);
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/cloudDoc.js"></script>
</head>
<%@ include file="facebox_message.jsp" %>
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
                if(isQuery && cloudDocs.size() == 0){
            %>
            <dl>
                <dt>
                    没有符合条件[<%=doc%>]的文档！
                </dt>
            </dl>
            <%
                }
                if(cloudDocs.size() == 0){
            %>
            <dl>
                <dt>
                    热门词汇：
                    <a href="javascript:queryDoc('foxmail')">foxmail</a>
                    <a href="javascript:queryDoc('doc')">doc</a>
                    <a href="javascript:queryDoc('txt')">txt</a>
                </dt>
            </dl>
            <%
                }
            %>
            <%

                for(CloudDoc cloudDoc : cloudDocs){
                    User cloudUser = UserDao.getUserById(cloudDoc.getUserId());
            %>
            <dl>
                <dt>
                    <img src="images/cloud_doc/<%=BaseUtil.getFileType(cloudDoc.getRoute())%>.jpg" width="24" height="20" />
                    <a class="title" href="cloudViewDoc.jsp?id=<%=cloudDoc.getId()%>" target="_blank">
                        <%=BaseUtil.displayCloudDocTitle(cloudDoc.getTitle(), doc)%>
                    </a>
                    <%=BaseUtil.displayCloudDocDescription(cloudDoc.getDescription(), doc)%>
                    <div class="wikiInfo"><%=BaseUtil.displayCloudDocTags(cloudDoc.getTags(), doc)%>
                        &nbsp;
                        <span>
                            贡献者
                            <a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                            <%=DateUtil.getLongDate(DateUtil.getDate(cloudDoc.getCreateDate()))%>
                        </span>
                    </div>
                </dt>
            </dl>
            <%
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