<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.interfaces.CloudKnowAskInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudKnowBase.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/cloudKnowAsk.js"></script>
    <%@ include file="ueditor_base.jsp" %>
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
            <form action="<%=baseUrl%>cloudKnowAsk.do?token=<%=token%>" name="cloudKnowAskFrom" method="post">
                <table cellpadding="0" cellspacing="0" width="100%" class="information">
                    <tr>
                        <td class="table_title">问题：</td>
                        <td><input id="question" type="text" name="question" value=""
                                   class="inputArea inputWidthLong" onfocus="focusQuestion(this)"
                                   onblur="blurQuestion(this)"></td>
                    </tr>
                    <tr>
                        <textarea style="display: none;" id="description" name="description"></textarea>
                        <td class="table_title">补充：</td>
                        <td>
                            可以填写问题补充，让你的问题看起来不会太奇葩~
                            <input value="展开" type="button" class="minBtn" onclick="writeDescription(this)">
                            <span id="editor_div" style="display: none; width: 600px;">
                                <script id="editor" type="text/plain"></script>
                            </span>
                        </td>
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
                        <td class="table_title">紧急：</td>
                        <td>
                            <select name="urgent">
                                <option value="<%=CloudKnowAskInterface.URGENT_NO%>">不紧急</option>
                                <option value="<%=CloudKnowAskInterface.URGENT_YES%>">紧急</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="table_title"></td>
                        <td><input name="dosubmit" value="提问" type="button" class="subBtn" onclick="cloudKnowAsk()"/></td>
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