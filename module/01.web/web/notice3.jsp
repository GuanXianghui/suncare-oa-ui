<html>
<head>
    <%@ page import="com.gxx.oa.dao.NoticeDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.UserNoticeDao" %>
    <%@ page import="com.gxx.oa.interfaces.UserNoticeInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //外层
        outLayer = "消息模块";
        //内层
        inLayer = "公告";
    %>
    <title>公告</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/notice3.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <!--这一行显示ie会有问题xxxxxx-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
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
                <a href="javascript: logOut()" title="Sign Out">退出</a>
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
        <div id="content-box1" class="content-box column-left">
            <div class="content-box-header">
                <h3>公告</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <table id="notice_table"></table>
                    <div class="clear"></div>
                    <div id="nextPageDiv" style="display: none; text-align: center;">
                        <input class="button" type="button" onclick="showNextPageNotices();" value="加载下一页" />
                    </div>
                </div>
            </div>
        </div>
        <div id="content-box2" class="content-box column-right">
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>