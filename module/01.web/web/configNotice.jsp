<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.NoticeDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //权限校验
        if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0003_CONFIG_NOTICE)){
            //域名链接
            response.sendRedirect(baseUrl + "index.jsp");
            return;
        }
        //外层
        outLayer = "消息模块";
        //内层
        inLayer = "公告管理";
    %>
    <title>公告管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/configNotice.js"></script>
    <%@ include file="ueditor_base.jsp" %>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset_back.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        /**
         * 公告Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var noticeJsonStr = "<%=BaseUtil.getJsonArrayFromNotices(NoticeDao.queryNoticesByPage(1,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //公告总数
        var noticeCount = <%=NoticeDao.countAllNotices()%>;
    </script>
</head>
<body>
<%@ include file="facebox_message.jsp" %>
<div id="body-wrapper">
    <div id="sidebar">
        <div id="sidebar-wrapper">
            <h1 id="sidebar-title"><a href="#">申成-OA系统</a></h1>
            <img id="logo" src="images/suncare-files-logo.png" alt="Simpla Admin logo"/>
            <div id="profile-links">
                Hello, [<%=user.getName()%>],
                <a href="http://www.suncarechina.com" target="_blank">申成</a>欢迎您！
                <br/>
                <br/>
                <a href="home.jsp" title="Sign Out">退出</a>
            </div>
            <%@ include file="layers.jsp" %>
        </div>
    </div>
    <div id="main-content">
        <div>
            <input class="button" type="button" onclick="changeMode();" value="切换模式" />
        </div>
        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>
            <div id="message_id_content"> 提示信息！</div>
        </div>
        <div id="content-box1" class="content-box">
            <div class="content-box-header">
                <h3>公告</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <table id="notice_table" border="1" width="80%" style="text-align: center"></table>
                    <div class="clear"></div>
                    <div id="nextPageDiv" style="display: none; text-align: center;">
                        <input class="button" type="button" onclick="showNextPageNotices();" value="加载下一页" />
                    </div>
                </div>
            </div>
        </div>
        <div id="content-box2" class="content-box">
            <div class="content-box-header">
                <h3>详细</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <div id="showNoticeDiv" style="display: none;">
                        <div id="showNoticeTitleDiv" align="center"></div>
                        <div id="showNoticeContentDiv"></div>
                    </div>
                    <div id="configNoticeDiv" style="display: none;">
                        <form name="configNoticeForm" action="<%=baseUrl%>configNotice.do" method="post">
                            <table>
                                <input type="hidden" id="token" name="token" value="<%=token%>">
                                <input id="type" type="hidden" name="type">
                                <input id="noticeId" type="hidden" name="noticeId">
                                <textarea style="display: none;" id="content" name="content"></textarea>
                                <b>标题:</b>&nbsp;&nbsp;<input class="text-input between-medium-large-input" type="text" id="title" name="title">
                            </table>
                        </form>
                        <script id="editor" type="text/plain"></script>
                        <input id="configButton" class="button" type="button" onclick="createNotice();" value="新增公告" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>