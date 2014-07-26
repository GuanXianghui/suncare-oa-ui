<%@ page import="com.gxx.oa.interfaces.BaseInterface"
        %><%@ page import="com.gxx.oa.utils.BaseUtil"
        %><%@ page import="com.gxx.oa.entities.User"
        %><%@ page import="com.gxx.oa.interfaces.OperateLogInterface"
        %><%@ page import="com.gxx.oa.utils.DateUtil"
        %><%@ page import="com.gxx.oa.utils.IPAddressUtil"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    User user = (User)request.getSession().getAttribute(BaseInterface.USER_KEY);
    if(user != null){
        BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_LOG_OUT, "退出成功",
                DateUtil.getNowDate(), DateUtil.getNowTime(), IPAddressUtil.getIPAddress(request));
    }
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    request.getSession().setAttribute(BaseInterface.USER_KEY, null);
    String resp = "{isSuccess:true,message:'退出成功！',isRedirect:true,redirectUrl:'" + baseUrl + "index.jsp'}";
    response.getWriter().write(resp);
%>