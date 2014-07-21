<%@ page import="com.gxx.oa.utils.DateUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <%@ include file="datepicker_base.jsp" %>
    <script type="text/javascript" charset="utf-8" src="scripts/writeDiary.js"></script>
    <script type="text/javascript">
        //当前日期
        var nowDate = "<%=DateUtil.getNowDate()%>";
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
    <div class="normalTitle">写日志</div>
    <div id="daily">
        <form name="writeDiaryForm" action="writeDiary.do" method="post">
            <input type="hidden" id="token" name="token" value="<%=token%>">
            <table cellpadding="0" cellspacing="0" width="100%" class="information">
                <tr>
                    <td class="table_title">日期：</td>
                    <td colspan="3">
                        <input class="inputArea inputWidthShort" type="text" id="date" name="date" value="">
                    </td>
                </tr>
                <tr>
                    <td class="table_title">日志内容：</td>
                    <td colspan="3">
                        <textarea style="display: none;" id="content" name="content"></textarea>
                        <span id="editor" style="width: 85%;"></span>
                    </td>
                </tr>
                <tr>
                    <td class="table_title"></td>
                    <td colspan="3"><input name="dosubmit" value="提交" type="button" onclick="writeDiary();"
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
