/**
 * 初始化
 */
$(document).ready(function() {
    //如果message非空则显示
    if(EMPTY != message){
        showInformation(message);
    }

    $( "#birthday_input" ).datepicker();
    $( "#birthday_input" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
    $( "#birthday_input" ).datepicker( "option", "showAnim", "drop" );
    $( "#birthday_input" ).datepicker( "option", "onSelect", function(dateText, inst ){
    });
    if(birthday != EMPTY){
        birthday = getLongDate(birthday);
    }
    $( "#birthday_input").val(birthday);
});

//点击修改密码
function beforeUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = "none";
    document.getElementById("update_password_td").style.display = EMPTY;
    document.getElementById("password").value = EMPTY;
}

//取消修改密码
function cancelUpdatePassword(){
    document.getElementById("before_update_password_td").style.display = EMPTY;
    document.getElementById("update_password_td").style.display = "none";
}

//点击修改密码
function beforeUploadHeadPhoto(){
    document.getElementById("before_upload_head_photo_td").style.display = "none";
    document.getElementById("upload_head_photo_td").style.display = EMPTY;
}

//取消修改密码
function cancelUploadHeadPhoto(){
    document.getElementById("before_upload_head_photo_td").style.display = EMPTY;
    document.getElementById("upload_head_photo_td").style.display = "none";
}

//上传图片
function uploadHeadPhoto(){
    document.forms["uploadHeadPhotoForm"].action = baseUrl + "uploadHeadPhoto.do?token=" + token;
    document.forms["uploadHeadPhotoForm"].submit();
}

/**
 * 点击修改密码
 * 用到checkStr()，依赖base.js
 */
function updatePassword(){
    //密码md5签名
    var password = $("#facebox #password");
    if (password.val() == EMPTY) {
        showAttention("请输入密码!");
        return;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(password.val(), SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("密码包含非法字符:" + result["symbol"]);
        return;
    }

    var md5Pwd = MD5(password.val() + md5Key);
    password.val(md5Pwd);
    //ajax修改密码
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updatePassword.do",
        data:"password=" + md5Pwd + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    password.val(EMPTY);
                    return;
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
                }
            } else {
                showAttention("服务器连接异常，请稍后再试！");
            }
        },
        error:function (data, textStatus) {
            showAttention("服务器连接异常，请稍后再试！");
        }
    });
}

//用户信息输入项
var infoArray = new Array("sex","birthday","office_tel","mobile_tel","desk","email","qq","msn","address","website");

//点击修改信息
function beforeUpdateInfo(){
    document.getElementById("before_update_info_td").style.display = "none";
    document.getElementById("update_info_td").style.display = EMPTY;
    for(var i=0;i<infoArray.length;i++) {
        document.getElementById(infoArray[i] + "_td_1").style.display = "none";
        document.getElementById(infoArray[i] + "_td_2").style.display = EMPTY;
    }
}

//取消修改信息
function cancelUpdateInfo(){
    document.getElementById("before_update_info_td").style.display = EMPTY;
    document.getElementById("update_info_td").style.display = "none";
    for(var i=0;i<infoArray.length;i++) {
        document.getElementById(infoArray[i] + "_td_1").style.display = EMPTY;
        document.getElementById(infoArray[i] + "_td_2").style.display = "none";
    }
}

/**
 * 修改信息
 * password varchar(32) not null comment '密码 md5签名 不为空',
 * desk varchar(50) comment '工位 可为空',
 * #3
 * sex int not null comment '性别 1 男 0 女 不为空',
 * birthday varchar(8) comment '生日 可为空',
 * office_tel varchar(20) comment '办公电话 可为空',
 * mobile_tel varchar(20) comment '移动电话 可为空',
 * email varchar(100) comment '邮件 可为空',
 * qq varchar(20) comment 'qq 可为空',
 * msn varchar(50) comment 'msn 可为空',
 * address varchar(100) comment '地址 可为空',
 * #4
 * head_photo varchar(500) not null comment '头像地址 相对路径 不为空',
 * website varchar(100) comment '个人网站 可为空',
 */
function updateInfo(){
    //格式校验
    var sex = document.getElementById("sex_select").value;
    var birthday = replaceAll(document.getElementById("birthday_input").value, "-", EMPTY);
    if(EMPTY != birthday) {
        if(!isNum(birthday) || birthday.length != 8) {
            showAttention("生日格式不对!");
            return;
        }
    }
    //判断字符串是否含有非法字符
    var result = checkStr(birthday, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("生日包含非法字符:" + result["symbol"]);
        return;
    }

    var officeTel = document.getElementById("office_tel_input").value;
    result = checkStr(officeTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("办公电话包含非法字符:" + result["symbol"]);
        return;
    }
    if(officeTel.length > 20){
        showAttention("办公电话长度不能大于20");
        return;
    }
    var mobileTel = document.getElementById("mobile_tel_input").value;
    result = checkStr(mobileTel, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("移动电话包含非法字符:" + result["symbol"]);
        return;
    }
    if(mobileTel.length > 20){
        showAttention("移动电话长度不能大于20");
        return;
    }
    var desk = document.getElementById("desk_input").value;
    result = checkStr(desk, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("工位包含非法字符:" + result["symbol"]);
        return;
    }
    if(desk.length > 25){
        showAttention("工位长度不能大于25");
        return;
    }
    var email = document.getElementById("email_input").value;
    if(EMPTY != email && !isEmail(email)) {
        showAttention("email格式不对!");
        return;
    }
    result = checkStr(email, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("邮件包含非法字符:" + result["symbol"]);
        return;
    }
    if(email.length > 50){
        showAttention("邮件长度不能大于50");
        return;
    }
    var qq = document.getElementById("qq_input").value;
    if(EMPTY != qq && !isNum(qq)) {
        showAttention("qq格式不对!");
        return;
    }
    result = checkStr(qq, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("qq包含非法字符:" + result["symbol"]);
        return;
    }
    if(qq.length > 20){
        showAttention("qq长度不能大于20");
        return;
    }
    var msn = document.getElementById("msn_input").value;
    if(EMPTY != msn && !isEmail(msn)) {
        showAttention("msn格式不对!");
        return;
    }
    result = checkStr(msn, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("msn包含非法字符:" + result["symbol"]);
        return;
    }
    if(msn.length > 50){
        showAttention("msn长度不能大于50");
        return;
    }
    var address = document.getElementById("address_input").value;
    result = checkStr(address, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("地址包含非法字符:" + result["symbol"]);
        return;
    }
    if(address.length > 50){
        showAttention("地址长度不能大于50");
        return;
    }
    var website = document.getElementById("website_input").value;
    result = checkStr(website, SYMBOL_ARRAY_2_CHECK_URL);
    if (result["isSuccess"] == false) {
        showAttention("个人网站包含非法字符:" + result["symbol"]);
        return;
    }
    if(website.length > 100){
        showAttention("个人网站长度不能大于100");
        return;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "updateInfo.do",
        data:"sex=" + filterStr(sex) + "&birthday=" + filterStr(birthday) + "&officeTel=" + filterStr(officeTel) +
            "&mobileTel=" + filterStr(mobileTel) + "&desk=" + filterStr(desk) + "&email=" + filterStr(email) +
            "&qq=" + filterStr(qq) + "&msn=" + filterStr(msn) + "&address=" + filterStr(address) + "&website=" +
            filterStr(website) + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判修改密码是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //修改密码成功
                    showSuccess(data["message"]);
                }
                //判是否有新token
                if (data["hasNewToken"]) {
                    token = data["token"];
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
