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
    <link href="css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <link href="css/imessage.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/mailLayout.js"></script>
    <!-- ueditor控件 -->
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <!--这一行显示ie会有问题xxxxxx-->
    <%--<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <script type="text/javascript" src="scripts/notice2.js"></script>
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
<div id="sc_IM">
    <div id="SCIM_search">查找</div>
    <div id="SCIM_uList">
        <ul>
            <li><a href="#"><img src="images/header.jpg"/></a><span>关向辉</span></li>
            <li><a href="#"><img src="images/header.jpg"/></a><span>关关</span></li>
            <li><a href="#"><img src="images/header.jpg"/></a><span>张飞</span></li>
            <li><a href="#"><img src="images/header.jpg"/></a><span>飞飞</span></li>
            <li><a href="#"><img src="images/header.jpg"/></a><span>关辉</span></li>
        </ul>
    </div>
    <div id="SCIM_groupSel">分组选择</div>
</div>
<!--右侧IM 结束-->
</body>
</html>