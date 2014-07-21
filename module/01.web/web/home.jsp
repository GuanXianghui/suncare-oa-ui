<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.dao.DiaryDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.TaskDao" %>
    <%@ page import="com.gxx.oa.dao.RemindDao" %>
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
    <script language="javascript" type="text/javascript" src="scripts/home.js"></script>
    <script type="text/javascript">
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //工作日志Json串
        var diaryJsonStr = "<%=BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(0, StringUtils.EMPTY, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.DIARY_PAGE_SIZE)),
                        rightUserWithComma)).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //工作日志总数
        var diaryCount = <%=DiaryDao.countDiaries(0, StringUtils.EMPTY, rightUserWithComma)%>;
        /**
         * 任务Json串 根据用户id查找最近的2个任务
         */
        var taskJsonStr = "<%=BaseUtil.getJsonArrayFromTasks(TaskDao.queryTasksByUserIdAndFromTo(user.getId(), 0, 2)).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id和提醒日期查提醒
        var remindJsonStr = "<%=BaseUtil.getJsonArrayFromReminds(RemindDao.queryRemindsByUserIdAndRemindDate
                    (user.getId(), DateUtil.getNowDate())).replaceAll("\\\'", "\\\\\\\'").replaceAll("\\\"", "\\\\\\\"")%>";
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
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
        <div class="taskBox" id="taskBoxDiv">
            <div class="normalTitle">任务<span class="titleSelect"><a href="task.jsp">全部任务</a></span></div>
            <div class="task">
                <div class="task_status"><span>17</span>小时</div>
                <div class="task_title"><a href="#">任务名称任务名称</a></div>
                <div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>
                <div class="task_time">创建时间： 2014-5-20 14:21</div>
                <div class="task_time">结束时间： 2014-5-20 14:21</div>
                <div class="clearBoth"></div>
            </div>
            <div class="task">
                <div class="task_status"><span>17</span>小时</div>
                <div class="task_title"><a href="#">任务名称任务名称</a></div>
                <div class="task_from"><a href="#">关向辉</a>指派给<a href="#">张飞</a></div>
                <div class="task_time">创建时间： 2014-5-20 14:21</div>
                <div class="task_time">结束时间： 2014-5-20 14:21</div>
                <div class="clearBoth"></div>
            </div>
        </div>
        <div class="noticeBox" id="noticeBoxDiv">
            <div class="normalTitle">提醒<span class="titleSelect"><a href="calendar.jsp">全部提醒</a></span></div>
            <div class="notice">
                <div class="notice_box">
                    <div class="notice_time">今天 15：00</div>
                    <div class="notice_info">打印文件。去一号会议室开会。</div>
                </div>
            </div>
            <div class="notice">
                <div class="notice_box">
                    <div class="notice_time">今天 15：00</div>
                    <div class="notice_info">打印文件。去一号会议室开会。</div>
                </div>
            </div>
            <div class="notice">
                <div class="notice_box">
                    <div class="notice_time">今天 15：00</div>
                    <div class="notice_info">打印文件。去一号会议室开会。</div>
                </div>
            </div>
            <div class="notice">
                <div class="notice_box">
                    <div class="notice_time">今天 15：00</div>
                    <div class="notice_info">打印文件。去一号会议室开会。</div>
                </div>
            </div>
        </div>
    </div>
    <div id="left_Box">
        <div class="msgBox">
            <div class="normalTitle">工作日志
                <input class="minBtn" type="button" onclick="location.href='writeDiary.jsp'" value="写日志" />
                <span class="titleSelect">
                    <a href="diary.jsp">全部</a>
                </span>
            </div>
            <!-- 用于缩略信息展示 -->
            <div id="initDiaryTxt" style="display: none;"></div>
            <ul id="diaryList">
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