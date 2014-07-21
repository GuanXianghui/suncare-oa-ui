<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.DiaryDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        int userId;
        try{
            userId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("userId")));
        } catch (Exception e){
            userId = 0;
        }
        String date = StringUtils.trimToEmpty(request.getParameter("date"));
    %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <%@ include file="datepicker_base.jsp" %>
    <script language="javascript" type="text/javascript" src="scripts/diary.js"></script>
    <script type="text/javascript">
        //塞选用户id
        var userId = <%=userId%>;
        //塞选日期
        var date = "<%=date%>";
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //工作日志Json串
        var diaryJsonStr = "<%=BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(userId, date, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.DIARY_PAGE_SIZE)),
                         rightUserWithComma)).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //工作日志总数
        var diaryCount = <%=DiaryDao.countDiaries(userId, date, rightUserWithComma)%>;
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
    </script>
</head>

<body>
<%@ include file="facebox_message.jsp" %>
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

    <div class="normalTitle">工作日志
        <select id="userId" name="userId" class="inputArea">
            <option value="">全部用户</option>
        </select>
        <input id="date" type="text" name="date" value="<%=date%>" class="inputArea">
        <input class="minBtn" type="button" onclick="selectDiary();" value="选择" />
        <input class="minBtn" type="button" onclick="location.href='writeDiary.jsp'" value="写日志" />
    </div>
    <div id="daily">
        <div class="diarlArea">
            <!-- 用于缩略信息展示 -->
            <div id="initDiaryTxt" style="display: none;"></div>
            <ul id="diaryList">
                <li>
                    <img src="images/header.jpg" alt="关向辉"/>
                    <a href="#">2014-6-28</a><span>关向辉</span>
                    <p>上海申成门窗有限公司20年来致力于高端铝合金门窗系统产品的设计，开发和应用。公司总投资高达5亿，占地面积约4.5万平方米
                        上海申成门窗有限公司20年来致力于高端铝合金门窗系统产品的设计，开发和应用。公司总投资高达5亿，占地面积约4.5万平方米..
                        上海申成门窗有限公司20年来致力于高端铝合金门窗系统产品的设计，开发和应用。公司总投资高达5亿，占地面积约4.5万平方米..
                        上海申成门窗有限公司20年来致力于高端铝合金门窗系统产品的设计，开发和应用。公司总投资高达5亿，占地面积约4.5万平方米....</p>
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
            <li><a href="#"><img src="images/header.jpg" /></a><span>关向辉</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>关关</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>张飞</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>飞飞</span></li>
            <li><a href="#"><img src="images/header.jpg" /></a><span>关辉</span></li>
        </ul>
    </div>
    <div id="SCIM_groupSel">分组选择</div>
</div>
<!--右侧IM 结束-->
</body>
</html>