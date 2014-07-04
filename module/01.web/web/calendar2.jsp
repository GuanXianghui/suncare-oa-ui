<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <title>Suncare-OA</title>
    <link href="css/reset.css" rel="stylesheet" type="text/css" />
    <link href="css/main.css" rel="stylesheet" type="text/css" />
    <link href="css/imessage.css" rel="stylesheet" type="text/css" />
    <script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/base.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/calendar2.js"></script>
    <!--日期控件-->
    <link rel="stylesheet" href="css/jquery-ui.css">
    <script src="scripts/jquery-1.10.2.js"></script>
    <script src="scripts/jquery-ui.js"></script>
    <script type="text/javascript">
        chooseDate = "<%=DateUtil.getNowDate()%>";
    </script>
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
    <div id="right_Box">
        <div class="taskBox">
            <div id="date" type="text" name="date" class="inputArea"></div>
        </div>
    </div>
    <div id="left_Box">
        <div class="msgBox">
            <div class="normalTitle">日历提醒</div>
            <div class="create_task">
                <div id="view_remind_div">
                    <div id="remind_table_head" align="center">
                        <%=DateUtil.getLongDate(DateUtil.getDate(DateUtil.getNowDate()))%>
                    </div>
                    <table id="remind_table" width="90%" border="1" align="center">
                        <tr class="alt-row">
                            <th>内容</th>
                            <th width="80">是否提醒</th>
                            <th width="80">提醒时间</th>
                            <th width="80">操作</th>
                        </tr>
                        <tr>
                            <td><a href="http://www.suncare-sys.com:10000/user.jsp?id=1" target="_blank">关向辉</a></td>
                            <td><a href="http://www.suncare-sys.com:10000/showTask.jsp?id=2">块来新厂</a></td>
                            <td>进行中</td><td>20140609</td>
                        </tr>
                    </table>
                    <div align="center">
                        <input name="dosubmit" value="新增提醒" type="submit" class="subBtn" onclick="beforeCreateRemind()" />
                    </div>
                </div>

                <div id="operate_remind_table" style="display: none;">
                    <table cellpadding="0" cellspacing="0" width="100%" class="information">
                        <tr>
                            <td class="table_title">日期：</td>
                            <td id="date_td">
                                <%=DateUtil.getLongDate(DateUtil.getDate(DateUtil.getNowDate()))%>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title">内容：</td>
                            <td><textarea class="inputArea inputWidthLong" id="create_content"></textarea></td>
                        </tr>
                        <tr>
                            <td>提醒类型:</td>
                            <td>
                                <select id="create_remind_type"  class="inputArea inputWidthShort">
                                    <option value="1">不提醒</option>
                                    <option value="2">消息提醒</option>
                                    <option value="3">短信提醒</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>提醒时间:</td>
                            <td>
                                <input class="inputArea inputWidthShort" type="text" id="remind_date">
                                <select id="remind_date_hour"  class="inputArea">
                                    <%
                                        for(int i=0;i<24;i++){
                                    %>
                                    <option value="<%=i<10?"0"+i:i%>"><%=i<10?"0"+i:i%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                :
                                <select id="remind_date_minute"  class="inputArea">
                                    <%
                                        for(int i=0;i<60;i++){
                                    %>
                                    <option value="<%=i<10?"0"+i:i%>"><%=i<10?"0"+i:i%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td><input name="dosubmit" value="提醒" type="submit" class="subBtn" onclick="createRemind()" />
                            <input name="dosubmit" value="返回" type="submit" class="subBtn" onclick="back()" /></td>
                        </tr>
                    </table>
                </div>
            </div>
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