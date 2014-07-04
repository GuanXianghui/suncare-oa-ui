<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
    <%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //搜索申成知道
        String ask = StringUtils.trimToEmpty(request.getParameter("ask"));
        //是否执行了查询
        boolean isQuery = false;
        //搜索结果
        List<CloudKnowAsk> cloudKnowAsks = new ArrayList<CloudKnowAsk>();
//    if(StringUtils.isNotBlank(ask)){
        isQuery = true;
        cloudKnowAsks = BaseUtil.queryCloudKnowAsks(ask);
//    }
    %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <link href="css/imessage.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <script type="text/javascript" src="scripts/cloudKnow2.js"></script>
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

    <form name="cloudQueryAskForm" action="cloudKnow2.jsp" method="post">
        <input type="hidden" name="ask" id="cloudQueryAsk">
    </form>

    <div class="normalTitle">申成知道
        <div class="searchArea">
            <input class="inputArea inputWidthLong" type="text" id="ask" value="<%=StringUtils.trimToEmpty(ask)%>"><input value="搜索知道" type="button" class="minBtn" onclick="queryAsk($('#ask').val());">
        </div>
    </div>
    <div id="wikiArea">
        <div class="wikiMenu">
            <ul>
                <li><a href="cloudKnowAsk2.jsp">我要提问</a></li>
                <li><a href="cloudKnowMyAsk2.jsp">我的提问</a></li>
                <li><a href="cloudKnowMyAnswer2.jsp">我的回答</a></li>
            </ul>
        </div>
        <div class="wikiList">

            <%
                if(isQuery && cloudKnowAsks.size() == 0){
            %>
            <dl>
                <dt>
                    暂无符合条件[<%=ask%>]的问题！
                </dt>
            </dl>
            <%
                }
                for(CloudKnowAsk cloudKnowAsk : cloudKnowAsks){
                    User cloudUser = UserDao.getUserById(cloudKnowAsk.getUserId());
                    int answerCount = CloudKnowAnswerDao.countCloudKnowAnswersByAskId(cloudKnowAsk.getId());
            %>
            <dl>
                <dt>
                    <a class="title" href="javascript:window.open('cloudViewKnow2.jsp?id=<%=cloudKnowAsk.getId()%>')">
                        <%=BaseUtil.displayCloudKnowQuestion(cloudKnowAsk.getQuestion(), ask)%>
                    </a>

                    <span class="answer-num">
                        <%=answerCount%>个回答
                        | 提问者：<a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                        | 时间：<%=DateUtil.getCNDate(DateUtil.getDateTime(cloudKnowAsk.getCreateDate(), cloudKnowAsk.getCreateTime()))%>
                    </span>
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