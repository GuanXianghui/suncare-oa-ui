<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page import="com.gxx.oa.dao.NoticeDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.UserNoticeDao" %>
    <%@ page import="com.gxx.oa.interfaces.UserNoticeInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script type="text/javascript" src="scripts/notice.js"></script>
    <script type="text/javascript">
        //已读的公告id
        var readedIds = "<%=UserNoticeDao.queryUserNoticeIdsByUserIdAndState(user.getId(), UserNoticeInterface.STATE_READED)%>";
        //已删除的公告id
        var deletedIds = "<%=UserNoticeDao.queryUserNoticeIdsByUserIdAndState(user.getId(), UserNoticeInterface.STATE_DELETED)%>";
        /**
         * 未删除的公告Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var noticeJsonStr = "<%=BaseUtil.getJsonArrayFromNotices(NoticeDao.queryNoticesByPageAndWithoutIds(1,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)),
                        UserNoticeDao.queryUserNoticeIdsByUserIdAndState(user.getId(), UserNoticeInterface.STATE_DELETED))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //未删除公告总数
        var notDeletedNoticeCount = <%=NoticeDao.countAllNoticesWithoutIds(UserNoticeDao.
        queryUserNoticeIdsByUserIdAndState(user.getId(), UserNoticeInterface.STATE_DELETED))%>;
        //在本页面中删除的个数
        var deleteCount = 0;
    </script>
</head>
<body>
<%@ include file="facebox_message.jsp" %>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg"/></a></div>
    <%@ include file="menu.jsp" %>
    <div class="menu_info">
        <a href="#"><img src="images/header.jpg"/></a>
    </div>
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
            <div class="normalTitle">公告<span class="titleSelect">全部</span></div>
            <ul id="mailList">
                <li>
                    <img src="images/header.jpg" alt="关向辉"/>
                    <a href="#">工作日志标题工作日志标题</a><span>2014-5-24</span>

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