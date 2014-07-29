<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.MessageDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script type="text/javascript" src="scripts/message.js"></script>
    <script type="text/javascript">
        <%--//所有员工json串--%>
        <%--var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";--%>
        <%--//所有员工Json数组--%>
        <%--var userArray = new Array();--%>
        /**
         * 消息Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var messageJsonStr = "<%=BaseUtil.getJsonArrayFromMessages(MessageDao.queryMessagesByUserIdAndFromTo(user.getId(),
                        0, Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.MESSAGE_PAGE_SIZE)))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id查总共消息的量
        var messageCount = <%=MessageDao.countAllMessagesByUserId(user.getId())%>;
    </script>
</head>
<body>
<%@ include file="facebox_message.jsp" %>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg"/></a></div>
    <%@ include file="menu.jsp" %>
</div>
<!-- 头部固定菜单层 结束-->
<!-- 主显示区 开始-->
<div id="mainArea">
    <div id="mailDetail">
        <div id="mailTitle" class="normalTitle"></div>
        <div id="mailOperate">
            <%--<input class="minBtn" type="button" onclick="deleteLetter();" value="删除" />--%>
        </div>
        <!-- 用于站内信缩略信息展示 -->
        <div id="initMailTxt" style="display: none;"></div>
        <div id="mailTxt"></div>
        <div class="clearBoth"></div>
    </div>
    <div id="mail_Box">
        <div class="msgBox">
            <div class="normalTitle">消息<span class="titleSelect">全部</span></div>
            <ul id="mailList">
                <li>
                    <img src="images/header.jpg" alt="关向辉"/>
                    <a href="#">消息</a><span>2014-5-24</span>

                    <p>上海申成门窗有限公司20年来致力于高端铝合金门窗系统产品的设计，开发和应用。公司总投资高达5亿，占地面积约4.5万平方米..</p>

                    <div class="clearBoth"></div>
                </li>
            </ul>
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