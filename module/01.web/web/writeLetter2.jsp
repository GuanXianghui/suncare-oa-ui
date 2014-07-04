<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
    <%@ page import="com.gxx.oa.entities.Letter" %>
    <%@ page import="com.gxx.oa.dao.LetterDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.dao.StructureDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        String toUserIds = StringUtils.EMPTY;//收件人
        String ccUserIds = StringUtils.EMPTY;//抄送人
        String title = StringUtils.EMPTY;//标题
        String content = StringUtils.EMPTY;//内容

        String type = request.getParameter("type");//写信类型type
        if(StringUtils.isNotBlank(type) && !StringUtils.equals(type, LetterInterface.WRITE_TYPE_WRITE)){//写信类型type不为空而且不是写信
            //判入参id合法性
            int id = 0;
            try{
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e){
                response.sendRedirect(baseUrl + "letter.jsp");
                return;
            }
            //判站内信是否属于该登陆用户
            Letter letter = LetterDao.getLetterById(id);
            if(letter.getUserId() != user.getId()){
                response.sendRedirect(baseUrl + "letter.jsp");
                return;
            }
            if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY)){//回复
                toUserIds = StringUtils.EMPTY + letter.getFromUserId();
                ccUserIds = StringUtils.EMPTY;
                title = "回复：" + letter.getTitle();
            } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY_ALL)){//回复全部
                toUserIds = letter.getToUserIds();
                ccUserIds = letter.getCcUserIds();
                title = "回复：" + letter.getTitle();
            } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_TRANSMIT)){//转发
                toUserIds = StringUtils.EMPTY;
                ccUserIds = StringUtils.EMPTY;
                title = "转发：" + letter.getTitle();
            }
            if(!StringUtils.EMPTY.equals(letter.getContent())){
                content = "<div class='quote'><blockquote>" + letter.getContent() + "</blockquote></div><p></p>";
            }
        }
    %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="css/main.css" rel="stylesheet" type="text/css"/>
    <link href="css/imessage.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <!-- ueditor控件 -->
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="scripts/writeLetter2.js"></script>
    <!--日期控件-->
    <link rel="stylesheet" href="css/jquery-ui.css">
    <script src="scripts/jquery-1.10.2.js"></script>
    <script src="scripts/jquery-ui.js"></script>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //初始接受用户id
        var initToUserIds = "<%=toUserIds%>";
        //初始抄送用户id
        var initCcUserIds = "<%=ccUserIds%>";
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
    <div class="normalTitle">写站内信</div>
    <div id="daily">
        <form name="writeLetterForm" action="<%=baseUrl%>writeLetter.do" method="post">
            <input name="toUserIds" id="toUserIdsValue" type="hidden" value="<%=toUserIds%>">
            <input name="ccUserIds" id="ccUserIdsValue" type="hidden" value="<%=ccUserIds%>">
            <input type="hidden" id="token" name="token" value="<%=token%>">
            <textarea style="display: none;" id="content" name="content"><%=content%></textarea>
            <table cellpadding="0" cellspacing="0" width="100%" class="information">
                <tr>
                    <td class="table_title">收件人：</td>
                    <td>
                        <span id="toUsersSpan"></span>
                        <input value="从通讯录选择" type="button" class="minBtn" onclick="chooseToUsers()" />
                    </td>
                </tr>
                <tr>
                    <td class="table_title">抄送人：</td>
                    <td>
                        <span id="ccUsersSpan"></span>
                        <input value="从通讯录选择" type="button" class="minBtn" onclick="chooseCcUsers()" />
                    </td>
                </tr>
                <tr>
                    <td class="table_title">标题：</td>
                    <td>
                        <input class="inputArea inputWidthLong" type="text" id="title" name="title" value="<%=title%>">
                    </td>
                </tr>
                <tr>
                    <td class="table_title">内容：</td>
                    <td>
                        <span id="editor" style="width: 85%;"></span>
                    </td>
                </tr>
                <tr>
                    <td class="table_title"></td>
                    <td><input name="dosubmit" value="发送" type="button" onclick="writeLetter();"
                                           class="subBtn"/></td>
                </tr>
            </table>
        </form>
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