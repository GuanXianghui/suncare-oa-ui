<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Properties"%>
<%@ page import="ueditor.Uploader" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="com.gxx.oa.entities.User" %>
<%@ page import="com.gxx.oa.interfaces.BaseInterface" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    request.setCharacterEncoding( Uploader.ENCODEING );
    response.setCharacterEncoding( Uploader.ENCODEING );

    String currentPath = request.getRequestURI().replace( request.getContextPath(), "" );

    File currentFile = new File( currentPath );

    currentPath = currentFile.getParent() + File.separator;

    //加载配置文件
    String propertiesPath = request.getSession().getServletContext().getRealPath( currentPath + "config.properties" );
    Properties properties = new Properties();
    try {
        properties.load( new FileInputStream( propertiesPath ) );
    } catch ( Exception e ) {
        //加载失败的处理
        e.printStackTrace();
    }

    Uploader up = new Uploader(request);

    /**
     * 保存路径
     */
    User user = (User)request.getSession().getAttribute(BaseInterface.USER_KEY);//用户
    String savePathStr = "upload";//原始的
    savePathStr = (user == null ? 0 : user.getId()) + StringUtils.EMPTY;//新的：每个用户用id分开

    up.setSavePath(savePathStr); //保存路径
    String[] fileType = {".rar" , ".doc" , ".docx" , ".xls" , ".xlsx" , ".zip" , ".pdf" , ".txt" , ".swf", ".wmv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogg", ".mov", ".wmv", ".mp4", ".xml", ".XML"};  //允许的文件类型
    up.setAllowFiles(fileType);
    up.setMaxSize(500 * 1024);        //允许的文件最大尺寸，单位KB
    up.upload();
    response.getWriter().print("{'url':'"+up.getUrl()+"','fileType':'"+up.getType()+"','state':'"+up.getState()+"','original':'"+up.getOriginalName()+"'}");
%>
