<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
    <%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //根据 用户id 查 所有申成知道回答对应的提问
        List<CloudKnowAsk> cloudKnowAsks = CloudKnowAnswerDao.queryCloudKnowAsksByUserId(user.getId());
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/cloudKnowMyAnswer.js"></script>
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

    <form name="cloudQueryAskForm" action="cloudKnow.jsp" method="post">
        <input type="hidden" name="ask" id="cloudQueryAsk">
    </form>

    <div class="normalTitle">申成知道
        <div class="searchArea">
            <input class="inputArea inputWidthLong" type="text" id="ask" value=""><input value="搜索知道" type="button" class="minBtn" onclick="queryAsk($('#ask').val());">
        </div>
    </div>
    <div id="wikiArea">
        <div class="wikiMenu">
            <ul>
                <li><a href="cloudKnowAsk.jsp">我要提问</a></li>
                <li><a href="cloudKnowMyAsk.jsp">我的提问</a></li>
                <li><a href="cloudKnowMyAnswer.jsp">我的回答</a></li>
            </ul>
        </div>
        <div class="wikiList">

            <%
                if(cloudKnowAsks.size() == 0){
            %>
            <dl>
                <dt>
                    你还没有回答过问题哦，请搜索问题，查看并回答！
                </dt>
            </dl>
            <%
                }
                for(CloudKnowAsk cloudKnowAsk : cloudKnowAsks){
                    int answerCount = CloudKnowAnswerDao.countCloudKnowAnswersByAskId(cloudKnowAsk.getId());
            %>
            <dl>
                <dt>
                    <a class="title" href="javascript:window.open('cloudViewKnow.jsp?id=<%=cloudKnowAsk.getId()%>')" target="_blank">
                        <%
                            String question = cloudKnowAsk.getQuestion();
                            if(question.length() > 18){
                                question = question.substring(0, 18) + "...";
                            }
                            out.print(question);
                        %>
                    </a>

                    <span class="answer-num">
                        <%=answerCount%>个回答
                        | 提问者：<a href="user.jsp?id=<%=user.getId()%>" target="_blank"><%=user.getName()%></a>
                        | 时间：<%=DateUtil.getLongDate(DateUtil.getDateTime(cloudKnowAsk.getCreateDate(), cloudKnowAsk.getCreateTime()))%>
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
<%@ include file="im.jsp" %>
<!--右侧IM 结束-->
</body>
</html>