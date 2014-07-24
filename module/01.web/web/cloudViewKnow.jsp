<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.interfaces.CloudKnowAskInterface" %>
    <%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
    <%@ page import="com.gxx.oa.dao.CloudKnowAskDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.entities.CloudKnowAnswer" %>
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //申成知道提问
        CloudKnowAsk cloudKnowAsk;
        //提问人
        User cloudUser;
        //是否自己的问题
        boolean isMine;
        //回答集合
        List<CloudKnowAnswer> cloudKnowAnswers;
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(id);
            if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
                throw new RuntimeException("找不到该申成知道提问！");
            }
            isMine = cloudKnowAsk.getUserId() == user.getId();
            cloudUser = UserDao.getUserById(cloudKnowAsk.getUserId());
            cloudKnowAnswers = CloudKnowAnswerDao.queryCloudKnowAnswersByAskId(cloudKnowAsk.getId());
        } catch (Exception e){
            response.sendRedirect("cloudKnow.jsp");
            return;
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudKnowBase.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudViewKnow.js"></script>
    <%@ include file="ueditor_base.jsp" %>
    <script type="text/javascript">
        //问题id
        var askId = <%=cloudKnowAsk.getId()%>;
        //初始类型
        var initType = "<%=cloudKnowAsk.getType()%>";
        /**
         * 初始问题补充
         *
         * 注意这里ueditor提交的内容不会有单引号，只有双引号
         * 这里如果用双引号括起来会有问题，所以改成单引号
         */
        var initDescription = '<%=cloudKnowAsk.getDescription()%>';
    </script>
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

    <form name="cloudQueryAskForm" action="cloudKnow.jsp" method="post">
        <input type="hidden" name="ask" id="cloudQueryAsk">
    </form>

    <form name="cloudKnowDeleteAskForm" action="cloudKnowDeleteAsk.do" method="post">
        <input type="hidden" name="token" value="<%=token%>">
        <input type="hidden" name="askId" value="<%=cloudKnowAsk.getId()%>">
    </form>

    <form name="cloudKnowDeleteAnswerForm" action="cloudKnowDeleteAnswer.do" method="post">
        <input type="hidden" name="token" value="<%=token%>">
        <input type="hidden" name="answerId" id="answerId">
    </form>

    <form name="cloudKnowZanAnswerForm" action="cloudKnowZanAnswer.do" method="post">
        <input type="hidden" name="token" value="<%=token%>">
        <input type="hidden" name="answerId" id="cloudKnowZanAnswerId">
    </form>

    <form style="display: none;" action="<%=baseUrl%>cloudKnowAnswer.do" name="cloudKnowAnswerFrom" method="post">
        <input type="hidden" name="token" value="<%=token%>">
        <input type="hidden" name="askId" value="<%=cloudKnowAsk.getId()%>">
        <textarea id="answer" name="answer"></textarea>

        <input type="hidden" name="type" id="cloudKnowAnswerType" value="insert">
        <input type="hidden" name="answerId" id="cloudKnowAnswerAnswerId">
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
        <div class="wikiDetail">
            <div class="d-detail">
                <h2>
                    <span class="d-detail-title">
                        <%=cloudKnowAsk.getQuestion()%>
                        <%
                            if(isMine){
                        %>
                        <input value="修改" type="button" class="minBtn" onclick="location.href='cloudKnowUpdateAsk.jsp?id=<%=cloudKnowAsk.getId()%>'">
                        <input value="删除" type="button" class="minBtn" onclick="cloudKnowDeleteAsk()">
                        <%
                            }
                        %>
                    </span>
                </h2>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">提问者：
                        <span class="qt-name">
                            <a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper" id="description" style="display: <%=StringUtils.isBlank(cloudKnowAsk.getDescription())?"none":""%>;">
                    <span class="qt-wrapper">补充：
                        <div class="reply-wrapper">
                            <div class="d-detail-txt" id="description_content" style="overflow: auto;">
                                <%=cloudKnowAsk.getDescription()%>
                            </div>
                        </div>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">分类：
                        <span class="qt-name">
                            <span id="show_type"></span>
                            <input name="type" type="hidden" id="type"/>
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">紧急：
                        <span class="qt-name">
                            <%=cloudKnowAsk.getUrgent()==CloudKnowAskInterface.URGENT_NO?"不紧急":"紧急"%>
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">提问时间：
                        <span class="qt-name">
                            <%=DateUtil.getCNDateTime(DateUtil.getDateTime(cloudKnowAsk.getCreateDate(), cloudKnowAsk.getCreateTime()))%>
                            <a name="writeAnswerButtonA"></a>
                            <input value="展开回答" type="button" class="minBtn" onclick="showAnswer(this)">
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">
                        <span id="editor_div" style="display: none; width: 600px;">
                            <script id="editor" type="text/plain"></script>
                            <input value="回答" type="button" class="minBtn" onclick="cloudKnowAnswer()">
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">回答：
                        <span class="qt-name">
                            共<%=cloudKnowAnswers.size()%>个
                        </span>
                    </span>
                </div>
                <%
                    for(CloudKnowAnswer cloudKnowAnswer : cloudKnowAnswers){
                        User cloudKnowAnswerUser = UserDao.getUserById(cloudKnowAnswer.getUserId());
                %>
                <div class="reply-wrapper">
                    <div class="d-reply-wrapper clearfix">
                        <span class="qt-wrapper grid">回答者：<span class="qt-name"><a href="user.jsp?id=<%=cloudKnowAnswerUser.getId()%>" target="_blank"><%=cloudKnowAnswerUser.getName()%></a></span></span>
                        <span class="d-detail-date grid-r">获得<em><%=cloudKnowAnswer.getZan()%></em>赞</span>
                        <span class="d-detail-date grid-r"><%=DateUtil.getCNDateTime(DateUtil.getDateTime(cloudKnowAnswer.getCreateDate(), cloudKnowAnswer.getCreateTime()))%></span>
                        <span class="d-detail-date grid-r">
                            <input value="赞" type="button" class="minBtn" onclick="cloudKnowZanAnswer(<%=cloudKnowAnswer.getId()%>)">
                            <%
                                if(user.getId() == cloudKnowAnswerUser.getId()){
                            %>
                            <input value="修改" type="button" class="minBtn" onclick="showCloudKnowUpdateAnswer(<%=cloudKnowAnswer.getId()%>)">
                            <input value="删除" type="button" class="minBtn" onclick="cloudKnowDeleteAnswer(<%=cloudKnowAnswer.getId()%>)">
                            <%
                                }
                            %>
                        </span>
                    </div>
                    <div class="d-detail-txt answer_content" id="answer_<%=cloudKnowAnswer.getId()%>" style="overflow: auto;">
                        <%=cloudKnowAnswer.getAnswer()%>
                    </div>
                    <%--<div class="d-detail-opt clearfix"></div>--%>
                </div>
                <%
                    }
                %>
            </div>
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