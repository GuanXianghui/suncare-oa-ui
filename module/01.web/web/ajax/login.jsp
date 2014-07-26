<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.oa.dao.UserDao"
        %><%@ page import="com.gxx.oa.entities.User"
        %><%@ page import="com.gxx.oa.entities.UserRight"
        %><%@ page import="com.gxx.oa.dao.UserRightDao"
        %><%@ page import="com.gxx.oa.dao.OperateLogDao"
        %><%@ page import="com.gxx.oa.interfaces.*"
        %><%@ page import="com.gxx.oa.utils.*"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    String resp;
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    String name = StringUtils.trimToEmpty(request.getParameter("name"));
    String password = StringUtils.trimToEmpty(request.getParameter("password"));
    String token = StringUtils.trimToEmpty(request.getParameter("token"));
    System.out.println("name=" + name + ",password=" + password + ",token=" + token);
    if(!TokenUtil.checkToken(request, token)){
        resp = "{isSuccess:false,message:'您的提交失败，token已失效！',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
    } else {
        if(!UserDao.isNameExist(name)){
            resp = "{isSuccess:false,message:'你输入的用户名不存在！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            User user = UserDao.getUserByName(name);
            if(!user.getPassword().equals(password)){
                resp = "{isSuccess:false,message:'你输入的密码错误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                user.setVisitDate(DateUtil.getNowDate());
                user.setVisitTime(DateUtil.getNowTime());
                user.setVisitIp(IPAddressUtil.getIPAddress(request));
                UserDao.updateUserVisitInfo(user);
                //创建操作日志
                BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_LOG_IN, "登陆成功",
                        DateUtil.getNowDate(), DateUtil.getNowTime(), IPAddressUtil.getIPAddress(request));
                //查用户当天登陆次数
                int count = OperateLogDao.countOperateLogsByLike(user.getId(), OperateLogInterface.TYPE_LOG_IN, DateUtil.getNowDate());
                //登陆 申成币+1(一天只有一次)
                if(count == 1){
                    UserDao.updateUserMoney(user.getId(), MoneyInterface.ACT_LOG_IN);
                    user = UserDao.getUserById(user.getId());
                    //创建申成币变动日志
                    BaseUtil.createOperateLog(user.getId(), OperateLogInterface.TYPE_SUNCARE_MONEY_CHANGE, "申成币变动 登陆" + MoneyInterface.ACT_LOG_IN,
                            DateUtil.getNowDate(), DateUtil.getNowTime(), IPAddressUtil.getIPAddress(request));
                    //公众账号给用户发一条消息
                    BaseUtil.createPublicMessage(PublicUserInterface.SUNCARE_OA_MESSAGE, user.getId(),
                            "您今天第一次登陆成功，申成币" + MoneyInterface.ACT_LOG_IN + "！", IPAddressUtil.getIPAddress(request));
                }
                /**
                 * 将用户和用户权限放入缓存
                 */
                UserRight userRight = UserRightDao.getUserRightByUserId(user.getId());
                request.getSession().setAttribute(BaseInterface.USER_KEY, user);
                request.getSession().setAttribute(BaseInterface.USER_RIGHT_KEY, userRight.getUserRight());
                resp = "{isSuccess:true,message:'登陆成功！',isRedirect:true,redirectUrl:'" + baseUrl + "home.jsp'}";
            }
        }
    }
    response.getWriter().write(resp);
%>