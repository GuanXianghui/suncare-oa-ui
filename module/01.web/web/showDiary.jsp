<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@ page import="com.gxx.oa.entities.Diary" %>
    <%@ page import="com.gxx.oa.dao.DiaryDao" %>
    <%@ page import="com.gxx.oa.dao.UserDao" %>
    <%@ page import="com.gxx.oa.entities.DiaryReview" %>
    <%@ page import="java.util.List" %>
    <%@ page import="com.gxx.oa.interfaces.DiaryReviewInterface" %>
    <%@ page import="com.gxx.oa.dao.DiaryReviewDao" %>
    <%@ page import="com.gxx.oa.utils.DateUtil" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="header.jsp" %>
    <%
        //判入参id合法性
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e){
            response.sendRedirect(baseUrl + "diary.jsp");
            return;
        }
        Diary diary = DiaryDao.getDiaryById(id);
        if(null == diary){
            response.sendRedirect(baseUrl + "diary.jsp");
            return;
        }
        //工作日志所属用户
        User diaryUser = UserDao.getUserById(diary.getUserId());
        //工作日志回复 包括点赞和留言
        List<DiaryReview> diaryReviews = DiaryReviewDao.queryDiaryReviews(diary.getId());
    %>
    <title>Suncare-OA</title>
    <%@ include file="ueditor_base.jsp" %>
    <%@ include file="datepicker_base.jsp" %>
    <script language="javascript" type="text/javascript" src="scripts/im.js"></script>
    <script type="text/javascript" charset="utf-8" src="scripts/showDiary.js"></script>
    <script type="text/javascript">
        //工作日志id
        var diaryId = <%=id%>;
        //日志日期
        var date = "<%=diary.getDate()%>";
        //当前日期
        var nowDate = "<%=DateUtil.getNowDate()%>";
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
    <div class="normalTitle">查看日志</div>
    <div id="wikiArea">
        <div class="wikiDetail">
            <div class="d-detail" id="showDiv">
                <h2><span class="d-detail-title"><%=DateUtil.getLongDate(DateUtil.getDate(diary.getDate()))%></span></h2>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">发布者：
                        <span class="qt-name">
                            <a href="<%=baseUrl%>user.jsp?id=<%=diaryUser.getId()%>" target="_blank"><%=diaryUser.getName()%></a>
                        </span>
                    </span>
                </div>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">创建时间：
                        <span class="qt-name">
                            <%=DateUtil.getCNDateTime(DateUtil.getDateTime(diary.getCreateDate(), diary.getCreateTime()))%>
                        </span>
                    </span>
                </div>

                <%
                    if(StringUtils.isNotBlank(diary.getUpdateDate())){
                %>
                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">修改时间：
                        <span class="qt-name">
                            <%=DateUtil.getCNDateTime(DateUtil.getDateTime(diary.getUpdateDate(), diary.getUpdateTime()))%>
                        </span>
                    </span>
                </div>
                <%
                    }
                %>

                <div class="d-qtner-wrapper">
                    <span class="qt-wrapper">点赞：
                        <span class="qt-name">
                            <%
                                int countZan = 0;//赞数量
                                boolean hasZan = false;//没有点赞过
                                for(DiaryReview diaryReview : diaryReviews){
                                    //点赞
                                    if(com.gxx.oa.interfaces.DiaryReviewInterface.TYPE_ZAN != diaryReview.getType()){
                                        continue;
                                    }
                                    countZan ++;
                                    User tempUser = UserDao.getUserById(diaryReview.getUserId());
                                    if(tempUser.getId() == user.getId()){
                                        hasZan = true;
                                    }
                            %>
                            <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=diaryReview.getUserId()%>">
                                <img width="32" src="<%=tempUser.getHeadPhoto()%>"></a>
                            <%
                                }
                            %>
                            <%
                                if(hasZan){//点过赞
                            %>
                            <input value="取消赞" type="button" class="minBtn" onclick="cancelZan()">
                            <%
                            } else {//没有点过赞
                            %>
                            <input value="赞" type="button" class="minBtn" onclick="clickZan()">
                            <%
                                }
                            %>
                        </span>
                    </span>
                </div>

                <div class="reply-wrapper">
                    <%
                        if(diary.getUserId() == user.getId()){
                    %>
                    <div class="d-reply-wrapper clearfix">
                        <span class="d-detail-date grid-r">
                            操作：
                            <input value="修改" type="button" class="minBtn" onclick="beforeUpdateDiary()">
                            <input value="删除" type="button" class="minBtn" onclick="deleteDiary()">
                        </span>
                    </div>
                    <%
                        }
                    %>
                    <div class="d-detail-txt" id="showContent" style="overflow: auto;">
                        <%=diary.getContent()%>
                    </div>

                    <div id="initContent" style="display: none;">
                        <%=diary.getContent()%>
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
                    for(DiaryReview diaryReview : diaryReviews){
                        //不是点赞，而是留言或者回复
                        if(com.gxx.oa.interfaces.DiaryReviewInterface.TYPE_ZAN == diaryReview.getType()){
                            continue;
                        }
                        boolean isReply = false;
                        User repliedUser = null;
                        if(DiaryReviewInterface.TYPE_REPLY == diaryReview.getType()){
                            isReply = true;
                            repliedUser = UserDao.getUserById(diaryReview.getRepliedUserId());
                        }
                        User tempUser = UserDao.getUserById(diaryReview.getUserId());
                %>
                <div class="reply-wrapper">
                    <div class="d-reply-wrapper clearfix">
                        <span class="qt-wrapper grid">回答者：
                            <span class="qt-name">
                                <span id="review_desc_<%=diaryReview.getId()%>"><a href="user.jsp?id=<%=tempUser.getId()%>" target="_blank"><%=tempUser.getName()%></a></span><%=isReply?"回复" + "<a href=\"user.jsp?id=" + repliedUser.getId() + "\" target=\"_blank\">" + repliedUser.getName() + "</a>" + "：":""%>
                            </span>
                        </span>
                        <span class="d-detail-date grid-r"><%=DateUtil.getCNDateTime(DateUtil.getDateTime(diaryReview.getCreateDate(), diaryReview.getCreateTime()))%></span>
                        <span class="d-detail-date grid-r">
                            <input value="回复" type="button" class="minBtn" onclick="beforeReplyDiaryReview(<%=diaryReview.getId()%>)">
                        <%
                            if(tempUser.getId() == user.getId()){
                        %>
                            <input value="修改" type="button" class="minBtn" onclick="beforeUpdateDiaryReview(<%=diaryReview.getId()%>)">
                            <input value="删除" type="button" class="minBtn" onclick="deleteDiaryReview(<%=diaryReview.getId()%>)">
                        <%
                            }
                        %>
                        </span>
                    </div>
                    <div class="d-detail-txt answer_content" style="text-align: left;" id="review_content_<%=diaryReview.getId()%>"><%=diaryReview.getContent()%></div>
                    <div class="d-detail-opt clearfix"></div>
                </div>
                <%
                    }
                %>
            </div>

            <div id="updateDiv" style="display:none;">
                <form name="updateDiaryForm" action="<%=baseUrl%>updateDiary.do" method="post">
                    <input type="hidden" id="token" name="token" value="<%=token%>">
                    <input type="hidden" id="diaryId" name="diaryId" value="<%=diary.getId()%>">
                    <table cellpadding="0" cellspacing="0" width="100%" class="information">
                        <tr>
                            <td class="table_title">日期：</td>
                            <td colspan="3">
                                <input class="inputArea inputWidthShort" type="text" id="date" name="date">
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
                            <td colspan="3">
                                <input name="dosubmit" value="修改" type="button" onclick="updateDiary();" class="subBtn"/>
                                <input name="dosubmit" value="取消" type="button" onclick="cancelUpdateDiary();" class="subBtn"/>
                            </td>
                        </tr>
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