<%@ page import="com.gxx.oa.utils.BaseUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="headerWithOutCheckLogin.jsp" %>
<%
    //判登录
    if(!BaseUtil.isLogin(request))
    {
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp?jumpUrl=" + URLEncoder.encode(BaseUtil.getRequestStr(request)));
        return;
    }
    //外层
    String outLayer = StringUtils.EMPTY;
    //内层
    String inLayer = StringUtils.EMPTY;
%>