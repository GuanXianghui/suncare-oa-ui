<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
    <li id="m_home"><a href="home.jsp">主页</a></li>
    <li id="m_message"><a href="#">消息</a>
        <ul id="sub_message" class="menu_sub">
            <li><img src="images/icon_notice.png"/><a href="notice.jsp">公告</a></li>
            <li><img src="images/icon_myMsg.png"/><a href="message.jsp">我的消息</a></li>
            <li><img src="images/icon_mail.png"/><a href="letter.jsp">站内信</a></li>
        </ul>
    </li>
    <li id="m_work"><a href="#">工作</a>
        <ul id="sub_work" class="menu_sub">
            <li><img src="images/icon_myMsg.png"/><a href="contacts.jsp">通讯录</a></li>
            <li><img src="images/icon_task.png"/><a href="task.jsp">我的任务</a></li>
            <li><img src="images/icon_daily.png"/><a href="diary.jsp">我的日志</a></li>
            <li><img src="images/icon_calendar.png"/><a href="calendar.jsp">我的日历</a></li>
        </ul>
    </li>
    <li id="m_tool"><a href="#">工具</a>
        <ul id="sub_tool" class="menu_sub">
            <li><img src="images/icon_disk.png"/><a href="cloud.jsp">申成网盘</a></li>
            <li><img src="images/icon_doc.png"/><a href="cloudDoc.jsp">申成文库</a></li>
            <li><img src="images/icon_wiki.png"/><a href="cloudKnow.jsp">申成知道</a></li>
            <li><img src="images/icon_mail.png"/><a href="sms.jsp">短信工具</a></li>
        </ul>
    </li>
    <li id="m_set"><a href="#">设置</a>
        <ul id="sub_set" class="menu_sub">
            <li><img src="images/icon_myMsg.png"/><a href="userManage.jsp">个人信息</a></li>
            <li><img src="images/icon_disk.png"/><a href="javascript: logOut()">退出</a></li>
        </ul>
    </li>
</ul>