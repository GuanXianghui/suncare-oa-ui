<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
    <%@ page import="com.gxx.oa.dao.LetterDao" %>
    <%@ page import="com.gxx.oa.interfaces.UserInterface" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        String box = request.getParameter("box");
        int type;
        if (StringUtils.equals(box, LetterInterface.BOX_SENT)) {
            type = LetterInterface.TYPE_SENT;
        } else if (StringUtils.equals(box, LetterInterface.BOX_DELETED)) {
            type = LetterInterface.TYPE_DELETED;
        } else {
            type = LetterInterface.TYPE_RECEIVED;
            box = LetterInterface.BOX_RECEIVED;
        }
    %>
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
    <script type="text/javascript" src="scripts/letter2.js"></script>
    <script type="text/javascript">
        //标识现在是收件箱，已发送还是已删除
        var box = "<%=box%>";
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有员工Json数组
        var userArray = new Array();
        /**
         * 消息Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var letterJsonStr = "<%=BaseUtil.getJsonArrayFromLetters(LetterDao.queryLettersByTypeAndFromTo(user.getId(),
                        UserInterface.USER_TYPE_NORMAL, type, 0, Integer.parseInt(PropertyUtil.getInstance().
                        getProperty(BaseInterface.LETTER_PAGE_SIZE))), box).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id和用户类型和查询类型 查总共站内信的量
        var letterCount = <%=LetterDao.countLettersByType(user.getId(), UserInterface.USER_TYPE_NORMAL, type)%>;
        // 选择站内信id
        var letterId = 0;
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
        <div id="mailOperate" style="display: none;">
            <input class="minBtn" type="button" onclick="reply();" value="回复" />
            <input class="minBtn" type="button" onclick="replyAll();" value="回复全部" />
            <input class="minBtn" type="button" onclick="transmit();" value="转发" />
            <input class="minBtn" type="button" onclick="deleteLetter();" value="删除" />
            <input class="minBtn" type="button" onclick="ctrlDeleteLetter();" value="彻底删除" />
        </div>
        <div id="mailFrom"></div>
        <div id="mailTo"></div>
        <div id="mailCc"></div>
        <!-- 用于站内信缩略信息展示 -->
        <div id="initMailTxt" style="display: none;"></div>
        <div id="mailTxt"></div>
        <div class="clearBoth"></div>
    </div>
    <div id="mail_Box">
        <div class="msgBox">
            <div class="normalTitle">
                站内信
                <span class="titleSelect">
                    <select id="box" class="selectArea" onchange="location.href='<%=baseUrl%>letter2.jsp?box=' + $('#box').val()">
                        <option value="<%=LetterInterface.BOX_RECEIVED%>"<%=StringUtils.equals(box, LetterInterface.BOX_RECEIVED)?" SELECTED":""%>>收件箱</option>
                        <option value="<%=LetterInterface.BOX_SENT%>"<%=StringUtils.equals(box, LetterInterface.BOX_SENT)?" SELECTED":""%>>已发送</option>
                        <option value="<%=LetterInterface.BOX_DELETED%>"<%=StringUtils.equals(box, LetterInterface.BOX_DELETED)?" SELECTED":""%>>已删除</option>
                    </select>
                </span>
            </div>
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
