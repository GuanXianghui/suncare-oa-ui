<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
    <%@ page import="com.gxx.oa.dao.SMSDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.dao.StructureDao" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        String date = request.getParameter("date");
        date = StringUtils.trimToEmpty(date);
        if(StringUtils.isBlank(date)){
            date = DateUtil.getNowDate();
        }
    %>
    <title>Suncare-OA</title>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/homeLayout.js"></script>
    <script language="javascript" type="text/javascript" src="scripts/sms.js"></script>
    <%@ include file="datepicker_base.jsp" %>
    <script type="text/javascript">
        //日期
        var date = "<%=date%>";
        /**
         * 短信Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var smsJsonStr = "<%=BaseUtil.getJsonArrayFromSMS(SMSDao.querySMSByUserIdAndStateAndDate(user.getId(),
                        0, date)).replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE2,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id，状态和日期查短信量
        var smsCount = <%=SMSDao.countSMSByUserIdAndStateAndDate(user.getId(), 0, date)%>;
        <%--//所有员工json串--%>
        <%--var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";--%>
        <%--//所有公司结构json串--%>
        <%--var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";--%>
        //短信运营商_发送短信屏蔽词汇
        var smsDeniedWords = "<%=PropertyUtil.getInstance().getProperty(BaseInterface.SMS_DENIED_WORDS)%>";
    </script>
    <style type="text/css">
        #sms_table tr td {
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
            <div class="normalTitle">发送短信</div>
            <div class="create_task">
                <div id="view_sms_div" style="display: none;">
                    <div id="remind_table_head" align="center">
                        <%=DateUtil.getLongDate(DateUtil.getDate(date))%>
                    </div>
                    <table id="sms_table" width="90%" border="1" align="center">
                        <tr class="alt-row">
                            <th width="80">手机号</th>
                            <th>内容</th>
                            <th width="80">状态</th>
                            <th width="80">时间</th>
                            <th width="80">操作</th>
                        </tr>
                        <tr>
                            <td><a href="http://www.suncare-sys.com:10000/showTask.jsp?id=2">块来新厂</a></td>
                            <td><a href="http://www.suncare-sys.com:10000/user.jsp?id=1" target="_blank">关向辉</a></td>
                            <td>进行中</td><td>20140609</td><td>20140609</td>
                        </tr>
                    </table>
                    <div align="center">
                        <input name="dosubmit" value="发送短信" type="submit" class="subBtn" onclick="beforeOperateSMS()" />
                    </div>
                </div>

                <div id="operate_sms_table">
                    <table cellpadding="0" cellspacing="0" width="100%" class="information">
                        <tr>
                            <td class="table_title">注意:</td>
                            <td>
                                1.发送多个手机号逗号符分隔(注意不是中文逗号符)，如：13361915872,13764603603
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                2.如果发送给公司同事，可从通讯录选择
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                3.<span style="color: red">短信内容一定要正规，否则短信发送不成功，而且会被短信服务提供商关闭账号，这个尤为重要！</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                4.不合法短信例子：(1)尼玛，小严走啦~吃饭去！(2)我擦，中午食堂什么菜？(3)哟哟切克闹，煎饼果子来一套
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                5.<span style="color: red">合法短信例子</span>：您好，上海申成门窗有限公司SAP需求蓝图确认会议将于明天即13号早上10点在新厂1楼大会议室召开，请准时到场，多谢您的配合。
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                6.短信发送一般<span style="color: red">3~5分钟</span>内会收到，请耐心等待
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td>
                                7.<span style="color: red">禁止发送以下信息，如果发现立刻封号：</span>
                                1、中奖类信息 2、骂人短信 3、诽谤诈骗短信 4、赌博违法短信 5、彩票类短信 6、欺骗短信
                                <span style="color: red">7、聊天短信</span>
                                8、暧昧信息 9、房产广告 10、移动充值业务 11、银行类业务 12、考试作弊
                            </td>
                        </tr>
                        <tr>
                            <td>手机号:</td>
                            <td>
                                <input class="inputArea inputWidthLong" type="text" id="phone">
                                <input name="dosubmit" value="从通讯录选择" type="submit" class="minBtn" onclick="choose()" />
                            </td>
                        </tr>
                        <tr>
                            <td class="table_title">内容：</td>
                            <td><textarea class="inputArea inputWidthLong" id="content"></textarea></td>
                        </tr>
                        <tr>
                            <td class="table_title"></td>
                            <td><input name="dosubmit" value="发送" type="submit" class="subBtn" onclick="operateSMS()" />
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