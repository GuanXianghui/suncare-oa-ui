<%@ page import="com.gxx.oa.interfaces.UserRightInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul id="main-nav">
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0001_USER_OPERATE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0002_ORG_STRUCTURE_MANAGE)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("用户模块")?" current":""%>"> 用户模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0001_USER_OPERATE)){
            %>
            <li><a href="<%=baseUrl%>userOperate.jsp"<%=inLayer.equals("后台用户管理")?" class=\"current\"":""%>>后台用户管理</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0002_ORG_STRUCTURE_MANAGE)){
            %>
            <li><a href="<%=baseUrl%>orgStructureManage.jsp"<%=inLayer.equals("组织架构管理")?" class=\"current\"":""%>>组织架构管理</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0003_CONFIG_NOTICE)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("消息模块")?" current":""%>"> 消息模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0003_CONFIG_NOTICE)){
            %>
            <li><a href="<%=baseUrl%>configNotice.jsp"<%=inLayer.equals("公告管理")?" class=\"current\"":""%>>公告管理</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0004_DEFAULT_RIGHT) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0005_USER_RIGHT)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("权限模块")?" current":""%>"> 权限模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0004_DEFAULT_RIGHT)){
            %>
            <li><a href="<%=baseUrl%>defaultRight.jsp"<%=inLayer.equals("默认权限")?" class=\"current\"":""%>>默认权限</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0005_USER_RIGHT)){
            %>
            <li><a href="<%=baseUrl%>userRight.jsp"<%=inLayer.equals("权限管理")?" class=\"current\"":""%>>权限管理</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
</ul>