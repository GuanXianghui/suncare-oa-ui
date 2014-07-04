$(document).ready(function(){
    $("#i_username").focus();
    $("#i_password").hide();
    $("#i_username").focus(function(){
        $('#login_codeImg').hide();
        if($(this).val()=='用户名')
        {
            $(this).val('');
        }
    });
    $("#i_username").blur(function(){
        if($(this).val()=='')
        {
            $(this).val('用户名');
        }
    });
    $("#i_password_text").focus(function(){
        $(this).hide();
        $('#i_password').show();
        $('#i_password').focus();
    });
    $("#i_password").focus(function(){
        $('#login_codeImg').hide();
        $("#i_password_text").hide();
    });
    $("#i_password").blur(function(){
        if($(this).val()=='')
        {
            $("#i_password").hide();
            $("#i_password_text").show();
        }
    });
});

/**
 * 按钮监听
 */
function keyPress(e){
    if( 13 == e.keyCode){
        login();
    }
}

//登陆
function login() {
    var name = $("#i_username").val();
    var password = $("#i_password").val();
    //判非空
    if (name == EMPTY) {
        showAttention("请输入用户名!");
        return;
    }
    if (password == EMPTY) {
        showAttention("请输入密码!");
        return;
    }
    // 判断字符串是否含有非法字符
    var result = checkStr(name, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("用户名包含非法字符:" + result["symbol"]);
        return;
    }
    result = checkStr(password, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("密码包含非法字符:" + result["symbol"]);
        return;
    }

    //md5签名
    var md5Pwd = MD5(password + md5Key);
    $("#i_password").val(md5Pwd)

    //ajax登陆
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "ajax/login.jsp",
        data:"name=" + filterStr(name) + "&password=" + md5Pwd + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判登陆是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    $("#i_password").val('')
                    //判是否有新token
                    if (data["hasNewToken"]) {
                        token = data["token"];
                    }
                    return;
                } else {
                    //登陆成功
                    showSuccess(data["message"]);
                }
                //是否跳转页面
                if (data["isRedirect"]) {
                    var redirectUrl = data["redirectUrl"];
                    location.href = redirectUrl;
                }
            } else {
                showError("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showError("服务器连接异常，请稍后再试！");
        }
    });
}