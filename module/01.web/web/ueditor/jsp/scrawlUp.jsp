<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="ueditor.Uploader" %>
<%@ page import="com.gxx.oa.entities.User" %>
<%@ page import="com.gxx.oa.interfaces.BaseInterface" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");

    String param = request.getParameter("action");
    Uploader up = new Uploader(request);

    /**
     * 路径
     */
    User user = (User)request.getSession().getAttribute(BaseInterface.USER_KEY);//用户
    String savePathStr = "upload";//原始的
    savePathStr = (user == null ? 0 : user.getId()) + StringUtils.EMPTY;//新的：每个用户用id分开

    String path = savePathStr;
    up.setSavePath(path);
    String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
    up.setAllowFiles(fileType);
    up.setMaxSize(10000); //单位KB

    if(param!=null && param.equals("tmpImg")){
        up.upload();
        out.print("<script>parent.ue_callback('" + up.getUrl() + "','" + up.getState() + "')</script>");
    }else{
        up.uploadBase64("content");
        response.getWriter().print("{'url':'" + up.getUrl()+"',state:'"+up.getState()+"'}");
    }
%>
