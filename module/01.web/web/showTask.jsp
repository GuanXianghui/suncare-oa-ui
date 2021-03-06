<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.dao.*" %>
    <%@ page import="com.gxx.oa.entities.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="com.gxx.oa.interfaces.TaskReviewInterface" %>
    <%@ page import="com.gxx.oa.interfaces.TaskInterface" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //判入参id合法性
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e){
            response.sendRedirect(baseUrl + "task.jsp");
            return;
        }
        Task task = TaskDao.getTaskById(id);
        if(null == task){
            response.sendRedirect(baseUrl + "task.jsp");
            return;
        }
        /**
         * 不能查看与你无关的任务
         */
        if(user.getId() != task.getFromUserId() && user.getId() != task.getToUserId()){
            response.sendRedirect(baseUrl + "task.jsp?message=not your task");
            return;
        }
        //任务来源和接受用户
        User fromUser = UserDao.getUserById(task.getFromUserId());
        User toUser = UserDao.getUserById(task.getToUserId());
        //根据任务id查 被催和非被催 任务评论
        List<TaskReview> cuiTaskReviews = TaskReviewDao.queryCuiTaskReviews(task.getId());
        List<TaskReview> notCuiTaskReviews = TaskReviewDao.queryNotCuiTaskReviews(task.getId());
    %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <%@ include file="datepicker_base.jsp" %>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script type="text/javascript" charset="utf-8" src="scripts/showTask.js"></script>
    <script type="text/javascript">
        //任务id
        var taskId = <%=id%>;
        //任务接受用户id
        var toUserId = <%=task.getToUserId()%>;
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //所有员工json串
        <%--var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";--%>
        //起止日期
        var beginDate = "<%=task.getBeginDate()%>";
        var endDate = "<%=task.getEndDate()%>";
    </script>
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
    <div class="normalTitle">查看任务</div>
    <div id="wikiArea">
        <div class="wikiDetail">
            <div class="d-detail" id="showDiv">

                <h2><span class="d-detail-title"></td><td><%=task.getTitle()%></span></h2>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">任务来源用户：
                        <span class="qt-name">
                            <a href="<%=baseUrl%>user.jsp?id=<%=task.getFromUserId()%>" target="_blank"><%=fromUser.getName()%></a>
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">任务接受用户：
                        <span class="qt-name">
                            <a href="<%=baseUrl%>user.jsp?id=<%=task.getToUserId()%>" target="_blank"><%=toUser.getName()%></a>
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">状态：
                        <span class="qt-name">
                            <%=task.getStateDesc()%>
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">开始日期：
                        <span class="qt-name">
                            <%=DateUtil.getCNDate(DateUtil.getDate(task.getBeginDate()))%>
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">结束日期：
                        <span class="qt-name">
                            <%=DateUtil.getCNDate(DateUtil.getDate(task.getEndDate()))%>
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">创建时间：
                        <span class="qt-name">
                            <%=DateUtil.getCNDateTime(DateUtil.getDateTime(task.getCreateDate(), task.getCreateTime()))%>
                        </span>
                    </span>
                </div>

                <%
                    if(StringUtils.isNotBlank(task.getUpdateDate())){
                %>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">修改时间：
                        <span class="qt-name">
                            <%=DateUtil.getCNDateTime(DateUtil.getDateTime(task.getUpdateDate(), task.getUpdateTime()))%>
                        </span>
                    </span>
                </div>
                <%
                    }
                %>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">催：
                        <span class="qt-name">
                            <%
                                //催过的用户id，已经催过的不重复显示
                                List<Integer> cuiUserIdList = new ArrayList<Integer>();
                                for(TaskReview taskReview : cuiTaskReviews){
                                    if(cuiUserIdList.contains(new Integer(taskReview.getUserId()))){
                                        continue;
                                    }
                                    cuiUserIdList.add(new Integer(taskReview.getUserId()));
                                    User tempUser = UserDao.getUserById(taskReview.getUserId());
                            %>
                            <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=taskReview.getUserId()%>"><img width="32" src="<%=tempUser.getHeadPhoto()%>"></a>
                            <%
                                }
                            %>
                            <input value="催" type="button" class="minBtn" onclick="cui()">
                        </span>
                    </span>
                </div>

                <div class="reply-wrapper">
                    <div class="d-reply-wrapper clearfix">
                        <span class="d-detail-date grid-r">
                            操作：
                            <%
                                //任务来源和接受任务的人可以修改任务
                                if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
                            %>
                            <input value="修改" type="button" class="minBtn" onclick="beforeUpdateTask()">
                            <%
                                }
                            %>
                            <%
                                //只有创建的人可以删除任务
                                if(task.getFromUserId() == user.getId()){
                            %>
                            <input value="删除" type="button" class="minBtn" onclick="deleteTask()">
                            <%
                                }
                            %>
                            <%
                                //只有
                                if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
                                    if(task.getState() == TaskInterface.STATE_NEW){
                            %>
                            <input value="开始任务" type="button" class="minBtn" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">
                            <%
                                } if(task.getState() == TaskInterface.STATE_ING){
                            %>
                            <input value="完成任务" type="button" class="minBtn" onclick="updateTaskState(<%=TaskInterface.STATE_DONE%>)">
                            <input value="废除任务" type="button" class="minBtn" onclick="updateTaskState(<%=TaskInterface.STATE_DROP%>)">
                            <%
                                } if(task.getState() == TaskInterface.STATE_DONE){
                            %>
                            <input value="重新开始任务" type="button" class="minBtn" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">
                            <%
                                } if(task.getState() == TaskInterface.STATE_DROP){
                            %>
                            <input value="重新开始任务" type="button" class="minBtn" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">
                            <%
                                    }
                                }
                            %>
                        </span>
                    </div>
                    <div class="d-detail-txt" id="showContent" style="overflow: auto;">
                        <%=task.getContent()%>
                    </div>

                    <div id="initContent" style="display: none;">
                        <%=task.getContent()%>
                    </div>

                    <%--<div class="d-detail-opt clearfix">--%>
                    <%--</div>--%>
                </div>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">评论：
                        <span class="qt-name">
                            <input value="评论" type="button" class="minBtn" onclick="beforeReview()">
                        </span>
                    </span>
                </div>

                <div class="d-qtner-wrapper" id="review_div" style="display: none;">
                    <div>
                        <a name="review_a" id="review_a"></a>
                        <span id="review_desc">你的评语：</span>
                        <input class="inputArea inputWidthLong" type="text" id="review_content">
                        <input value="提交" type="button" class="minBtn" onclick="review()">
                        <input value="取消" type="button" class="minBtn" onclick="cancelReview()">
                    </div>
                </div>

                <%
                    for(TaskReview taskReview : notCuiTaskReviews){
                        boolean isReply = false;
                        User repliedUser = null;
                        if(TaskReviewInterface.TYPE_REPLY == taskReview.getType()){
                            isReply = true;
                            repliedUser = UserDao.getUserById(taskReview.getRepliedUserId());
                        }
                        User tempUser = UserDao.getUserById(taskReview.getUserId());
                %>
                <div class="reply-wrapper">
                    <div class="d-reply-wrapper clearfix">
                        <span class="qt-wrapper grid">回答者：
                            <span class="qt-name">
                                <span id="review_desc_<%=taskReview.getId()%>"><a href="user.jsp?id=<%=tempUser.getId()%>" target="_blank"><%=tempUser.getName()%></a></span><%=isReply?"回复" + "<a href=\"user.jsp?id=" + repliedUser.getId() + "\" target=\"_blank\">" + repliedUser.getName() + "</a>" + "：":""%>
                            </span>
                        </span>
                        <span class="d-detail-date grid-r"><%=DateUtil.getCNDateTime(DateUtil.getDateTime(taskReview.getCreateDate(), taskReview.getCreateTime()))%></span>
                        <span class="d-detail-date grid-r">
                            <input value="回复" type="button" class="minBtn" onclick="beforeReplyTaskReview(<%=taskReview.getId()%>)">
                        <%
                            if(tempUser.getId() == user.getId()){
                        %>
                            <input value="修改" type="button" class="minBtn" onclick="beforeUpdateTaskReview(<%=taskReview.getId()%>)">
                            <input value="删除" type="button" class="minBtn" onclick="deleteTaskReview(<%=taskReview.getId()%>)">
                        <%
                            }
                        %>
                        </span>
                    </div>
                    <div class="d-detail-txt answer_content" style="text-align: left;" id="review_content_<%=taskReview.getId()%>"><%=taskReview.getContent()%></div>
                    <div class="d-detail-opt clearfix"></div>
                </div>
                <%
                    }
                %>

            </div>

            <div id="updateDiv" style="display:none;">
                <form name="updateTaskForm" action="<%=baseUrl%>updateTask.do" method="post">
                    <table>
                        <input type="hidden" id="token" name="token" value="<%=token%>">
                        <input type="hidden" id="taskId" name="taskId" value="<%=task.getId()%>">
                        <textarea style="display: none;" id="content" name="content"></textarea>
                        <table cellpadding="0" cellspacing="0" width="100%" class="information">
                            <tr>
                                <td class="table_title">任务指派：</td>
                                <td>
                                    <span id="toUser">
                                       <a href="<%=baseUrl%>user.jsp?id=<%=task.getToUserId()%>" target="_blank">
                                           <img width='32' src="<%=toUser.getHeadPhoto()%>"><%=toUser.getName()%>
                                       </a>
                                    </span>
                                    <input value="选择同事" type="button" class="minBtn" onclick="choose()" />
                                    <input id="toUserId" type="hidden" name="toUserId" value="<%=task.getToUserId()%>">
                                </td>
                            </tr>
                            <tr>
                                <td class="table_title">任务名称：</td>
                                <td>
                                    <input class="inputArea inputWidthLong" type="text" id="title" name="title" value="<%=task.getTitle()%>">
                                </td>
                            </tr>
                            <tr>
                                <td class="table_title">开始日期：</td>
                                <td>
                                    <input class="inputArea inputWidthShort" type="text" id="beginDate" name="beginDate" value="<%=task.getBeginDate()%>">
                                </td>
                            </tr>
                            <tr>
                                <td class="table_title">结束日期：</td>
                                <td>
                                    <input class="inputArea inputWidthShort" type="text" id="endDate" name="endDate" value="<%=task.getEndDate()%>">
                                </td>
                            </tr>
                            <tr>
                                <td class="table_title">任务内容：</td>
                                <td>
                                    <script id="editor" type="text/plain"></script>
                                </td>
                            </tr>
                            <tr>
                                <td class="table_title"></td>
                                <td>
                                    <input name="dosubmit" value="修改" type="button" onclick="updateTask();" class="subBtn"/>
                                    <input name="dosubmit" value="取消" type="button" onclick="cancelUpdateTask();" class="subBtn"/>
                                </td>
                            </tr>
                        </table>
                    </table>
                </form>
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