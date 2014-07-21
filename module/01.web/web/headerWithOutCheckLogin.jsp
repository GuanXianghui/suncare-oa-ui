<%@ page import="com.gxx.oa.interfaces.BaseInterface" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.gxx.oa.utils.TokenUtil" %>
<%@ page import="com.gxx.oa.utils.PropertyUtil" %>
<%@ page import="com.gxx.oa.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //域名链接
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    //md5 key
    String md5Key = PropertyUtil.getInstance().getProperty(BaseInterface.MD5_KEY);
    //token串
    String token = TokenUtil.createToken(request);
    //session中获取user
    User user = (User)request.getSession().getAttribute(BaseInterface.USER_KEY);
    //是否已经登录
    boolean isLogin = user != null;
    //session中获取userRight
    String userRight = (String)request.getSession().getAttribute(BaseInterface.USER_RIGHT_KEY);
    //消息
    String message = StringUtils.trimToEmpty((String)request.getAttribute("message"));
    if(StringUtils.isBlank(message)){
        message = StringUtils.trimToEmpty(request.getParameter("message"));
    }
    //跳转地址
    String jumpUrl = StringUtils.trimToEmpty(request.getParameter("jumpUrl"));
%>
<script type="text/javascript">
    //域名链接
    var baseUrl = "<%=baseUrl%>";
    //md5 key
    var md5Key = "<%=md5Key%>";
    //token穿
    var token = "<%=token%>";
    //是否已经登录
    var isLogin = <%=isLogin%>;
    //弹出消息框
    var message = '<%=message%>';
    //跳转地址
    var jumpUrl = '<%=jumpUrl%>';
</script>
<!-- 图标 -->
<link rel="shortcut icon" type="image/x-icon" href="images/suncare-file-little-logo.png" />
<!-- css基础依赖 -->
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/imessage.css" rel="stylesheet" type="text/css" />
<!-- js基础依赖 -->
<script language="javascript" type="text/javascript" src="scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="scripts/base.js"></script>
<script language="javascript" type="text/javascript" src="scripts/menu.js"></script>
<script language="javascript" type="text/javascript" src="scripts/mailLayout.js"></script>
<!-- facebox控件 -->
<script type="text/javascript" src="scripts/facebox.js"></script>
<link href="css/facebox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    jQuery(document).ready(function($) {
        $('a[rel*=facebox]').facebox({
            loadingImage : 'images/loading.gif',
            closeImage : 'images/closelabel.png'
        })
        $("#facebox").css("margin-top", "150px")
    })
</script>