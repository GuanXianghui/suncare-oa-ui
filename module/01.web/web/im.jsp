<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--右侧IM 开始-->
<div id="sc_IM">
    <div id="user_Card">
        <div id="user_Card_Head"><img src="" /></div>
        <div id="user_Card_Info">
            <span id="uc_name"></span>
            <span id="uc_post"></span>
            <span id="uc_phone"></span>
            <span id="uc_mobile"></span>
        </div>
    </div>
    <div id="SCIM_groupSel">联系人</div>
    <div id="SCIM_uList" class="content">
        <ul>
            <li class="group" id="group_01">+技术部</li>
            <li id="user_124"><a href="#"><img src="images/header.jpg" /></a><span>关关</span></li>
        </ul>
    </div>
</div>
<!--右侧IM 结束-->
<script language="javascript" type="text/javascript" src="scripts/jquery.mCustomScrollbar.concat.min.js"></script>
<script>
    //所有员工json串
    var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
    //所有公司结构json串
    var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
    //所有员工Json数组
    var userArray = new Array();
    //所有公司结构Json数组
    var structureArray = new Array();

    (function($){
        $(window).load(function(){
            $("#SCIM_uList").mCustomScrollbar();
        });
    })(jQuery);
</script>