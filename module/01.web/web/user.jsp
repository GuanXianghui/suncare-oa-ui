<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.StructureDao" %>
    <%@ page import="com.gxx.oa.entities.Structure" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.interfaces.UserInterface" %>
    <%@ page import="com.gxx.oa.interfaces.UserRightInterface" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(baseUrl + "main.jsp");
            return;
        }
        User user2 = UserDao.getUserById(id);
        if(null == user2) {
            response.sendRedirect(baseUrl + "main.jsp");
            return;
        }
        Structure company = StructureDao.getStructureById(user2.getCompany());
        Structure dept = StructureDao.getStructureById(user2.getDept());
        Structure position = StructureDao.getStructureById(user2.getPosition());
    %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <link href="css/imessage.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
</head>
<body>

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
    <div class="normalTitle">个人信息</div>
    <div id="user_head">
        <div id="head_thumb"><img src="<%=baseUrl + user2.getHeadPhoto()%>" /></div>
    </div>
    <div id="user_info">
        <table  cellpadding="0" cellspacing="0" width="100%" class="information">
            <tr>
                <td class="table_title">姓名：</td>
                <td><%=user2.getName()%></td>
                <td class="table_title">性别：</td>
                <td id="sex_td_2">
                    <%=UserInterface.SEX_X==user2.getSex()?"男":"女"%>
                </td>
            </tr>
            <tr>
                <td class="table_title">职位：</td>
                <td><%=null==position?"无":position.getName()%></td>
                <td class="table_title">部门：</td>
                <td><%=null==dept?"无":dept.getName()%></td>
            </tr>
            <tr>
                <td class="table_title">生日：</td>
                <td id="birthday_td_2" colspan="3">
                    <%=user2.getBirthday()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">办公电话：</td>
                <td id="office_tel_td_2" colspan="3">
                    <%=user2.getOfficeTel()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">移动电话：</td>
                <td id="mobile_tel_td_2" colspan="3">
                    <%=user2.getMobileTel()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">工位：</td>
                <td id="desk_td_2" colspan="3">
                    <%=user2.getDesk()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">邮件：</td>
                <td id="email_td_2" colspan="3">
                    <%=user2.getEmail()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">qq：</td>
                <td id="qq_td_2" colspan="3">
                    <%=user2.getQq()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">msn：</td>
                <td id="msn_td_2" colspan="3">
                    <%=user2.getMsn()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">地址：</td>
                <td id="address_td_2" colspan="3">
                    <%=user2.getAddress()%>
                </td>
            </tr>
            <tr>
                <td class="table_title">个人网站：</td>
                <td id="website_td_2" colspan="3">
                    <%
                        if(StringUtils.isNotBlank(user2.getWebsite())){
                    %>
                    <a href="<%=user2.getWebsite()%>" target="_blank"><%=user2.getWebsite()%></a>
                    <%
                        }
                    %>
                </td>
            </tr>
        </table>

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