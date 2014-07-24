<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page import="com.gxx.oa.entities.Remind" %>
    <%@ page import="com.gxx.oa.dao.RemindDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        String id = request.getParameter("id");
        String remindContent = StringUtils.EMPTY;
        String date = DateUtil.getNowDate();
        if(StringUtils.isNotBlank(id)){
            try{
                Remind remind = RemindDao.getRemindById(Integer.parseInt(id));
                if(remind != null && remind.getUserId() == user.getId()){
                    remindContent = remind.getContent();
                    date = remind.getCreateDate();
                }
            } catch (Exception e){
                remindContent = StringUtils.EMPTY;
                date = DateUtil.getNowDate();
            }
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/calendar.js"></script>
    <%@ include file="datepicker_base.jsp" %>
    <script type="text/javascript">
        //弹出提醒内容
        var remindContent = "<%=remindContent.replaceAll("\n", "").replaceAll("\"", "\\\\")%>";
        //选择当前日期 如果提醒ID跳转过来 则 获取当天的提醒
        var chooseDate = "<%=date%>";
        //当前时间戳
        var nowDateTime = "<%=DateUtil.getNowDate() + DateUtil.getNowTime()%>";
        //短信运营商_发送短信屏蔽词汇
        var smsDeniedWords = "<%=PropertyUtil.getInstance().getProperty(BaseInterface.SMS_DENIED_WORDS)%>";
    </script>
    <style type="text/css">
        #remind_table th{
            text-align: center;
        }
        #remind_table td{
            text-align: center;
        }
    </style>
</head>

 <body>
<%@ include file="facebox_message.jsp" %>
<!-- 头部固定菜单层 开始-->
<div id="menu">
    <div class="logo"><a href="#"><img src="images/logo.jpg" /></a></div>
    <%@ include file="menu.jsp" %>
    <div class="menu_info">
        <a href="#"><img src="<%=user.getHeadPhoto()%>" /></a>
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
                        <%=DateUtil.getLongDate(DateUtil.getDate(date))%>
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
<%@ include file="im.jsp" %>
<!--右侧IM 结束-->
</body>
</html>