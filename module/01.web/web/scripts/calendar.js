//选择日期
var chooseDate = EMPTY;
//提醒数组
var remindArray = new Array();
//新增还是修改 insertRemind/updateRemind
var insertOrUpdate = "insertRemind";
//修改提醒id
var updateRemindId = 0;

/**
 * 初始化
 */
$(document).ready(function() {
    $( "#date" ).datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
        //对选择日期赋值
        chooseDate = dateText;
        var longDate = chooseDate.substr(0,4) + "-" + chooseDate.substr(4,2) + "-" + chooseDate.substr(6,2);
        $("#date_td").html(longDate);
        $("#remind_table_head").html(longDate);
        //根据日期查询提醒
        query();
    });
    $( "#remind_date" ).datepicker();
    $( "#remind_date" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
    $( "#remind_date" ).datepicker( "option", "showAnim", "drop" );
    //页面加载完查询当前日期
    query();
});

/**
 * 根据提醒id得到提醒
 * @param remindId
 */
function getRemindById(remindId){
    var remind = null;
    for(var i=0;i<remindArray.length;i++){
        if(remindArray[i]["id"] == remindId){
            remind = remindArray[i];
            break;
        }
    }
    return remind;
}

/**
 * 把json字符串转换object数组
 * @param jsonStr
 * @return {Array}
 */
function transferJsonStr2Array(jsonStr){
    var jsonArray = new Array();
    if(jsonStr != EMPTY) {
        var array = jsonStr.split(SYMBOL_LOGIC_AND);
        for(var i=0;i<array.length;i++) {
            jsonArray[jsonArray.length] = eval("(" + array[i] + ")");
        }
    }
    return jsonArray;
}

/**
 * 处理提醒数组
 */
function processRemindArray(){
    //循环展示
    var html = "<tr class=\"alt-row\"><th>内容</th><th width=\"80\">是否提醒</th><th width=\"160\">提醒时间</th><th width=\"100\">操作</th></tr>";
    for(var i=0;i<remindArray.length;i++){
        html += "<tr>" +
            "<td>" + remindArray[i]["content"] + "</td>" +
            "<td align='center'>" + remindArray[i]["remindTypeDesc"] + "</td>" +
            "<td>" + getLongDateTime1(remindArray[i]["remindDateTime"]) + "</td>" +
            "<td>" +
            "<input name=\"dosubmit\" value=\"修改\" type=\"submit\" class=\"minBtn\" onclick=\"beforeUpdateRemind(" + remindArray[i]["id"] + ")\" />" +
            "<input name=\"dosubmit\" value=\"取消\" type=\"submit\" class=\"minBtn\" onclick=\"deleteRemind(" + remindArray[i]["id"] + ")\" />" +
            "</td>" +
            "</tr>";
    }
    if(remindArray.length == 0){
        html += "<tr><td colspan=\"4\" align=\"center\">暂无提醒</td></tr>"
    }
    document.getElementById("remind_table").innerHTML = html;
}

/**
 * 根据日期查询提醒
 */
function query(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=query&date=" + chooseDate + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    //第一次查询，如果提醒内容非空，则
                    if(remindContent != EMPTY){
                        showInformation("申成OA提醒系统提醒您：" + remindContent);
                        remindContent = EMPTY;
                    }
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
                    //返回查看提醒页面
                    back();
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

/**
* 点击新增提醒按钮
*/
function beforeCreateRemind(){
    document.getElementById("create_content").value = EMPTY;
    document.getElementById("create_remind_type").value = "1";
    document.getElementById("remind_date").value = EMPTY;
    document.getElementById("remind_date_hour").value = "00";
    document.getElementById("remind_date_minute").value = "00";
    insertOrUpdate = "insertRemind";

    $("#view_remind_div").css("display", "none");
    $("#operate_remind_table").css("display", "block");
}

/**
 * 新增提醒
 */
function createRemind(){
    var content = document.getElementById("create_content").value;
    if(content == EMPTY){
        showAttention("请输入内容");
        return false;
    }
    //判断字符串是否含有非法字符
    var result = checkStr(content, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("内容包含非法字符:" + result["symbol"]);
        return;
    }
    if(content.length > REMIND_CONTENT_LENGTH) {
        showAttention("内容大于" + REMIND_CONTENT_LENGTH + "个字符");
        return false;
    }
    var remindType = document.getElementById("create_remind_type").value;
    if(remindType == EMPTY){
        showAttention("请选择提醒类型");
        return false;
    }
    var remindDate = document.getElementById("remind_date").value;
    if(remindType != REMIND_TYPE_NO_REMIND && remindDate == EMPTY){
        showAttention("请输入提醒时间");
        return false;
    }

    var remindDateTime = EMPTY;
    if(remindDate == EMPTY){
        remindDateTime = EMPTY;
    } else {
        remindDateTime = remindDate.substr(0, 4) + remindDate.substr(5, 2) + remindDate.substr(8, 2) +
            $("#remind_date_hour").val() + $("#remind_date_minute").val() + "00";
    }

    if(remindDateTime != EMPTY && remindDateTime <= nowDateTime){
        showAttention("提醒时间选择有误，请选择在之后的某个时间提醒你");
        return false;
    }

    if(remindType == REMIND_TYPE_SMS){
        var words = smsDeniedWords.split(SYMBOL_SLASH);
        for(var i=0;i<words.length;i++){
            var word = words[i];
            if(content.indexOf(word) > -1){
                showAttention("由于你选择短信提醒，你的内容中包括短信运营商屏蔽词汇：[" + word + "]，请修改提醒内容！");
                return;
            }
            if(word.indexOf(SYMBOL_BLANK)){
                word = replaceAll(word, SYMBOL_BLANK, EMPTY);
                if(content.indexOf(word) > -1){
                    showAttention("由于你选择短信提醒，你的内容中包括短信运营商屏蔽词汇：[" + word + "]，请修改提醒内容！");
                    return;
                }
            }
        }
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=" + insertOrUpdate + "&updateRemindId=" + updateRemindId + "&date=" + chooseDate + "&content=" +
            content + "&remindType=" + remindType + "&remindDateTime=" + remindDateTime + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
                    //返回查看提醒页面
                    back();
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

/**
 * 查看提醒
 * @param remindId
 */
function showRemind(remindId){
    var remind = getRemindById(remindId);
    document.getElementById("show_date").innerHTML = remind["date"];
    document.getElementById("show_content").innerHTML = remind["content"];
    document.getElementById("show_remind_type").innerHTML = remind["remindTypeDesc"];
    document.getElementById("show_remind_date_time").innerHTML = remind["remindDateTime"];
    document.getElementById("create_remind_table").style.display = "none";
    document.getElementById("show_remind_table").style.display = EMPTY;
}

/**
 * 点击修改提醒按钮
 * @param remindId
 */
function beforeUpdateRemind(remindId){
    insertOrUpdate = "updateRemind";
    updateRemindId = remindId;
    var remind = getRemindById(remindId);
    document.getElementById("create_content").value = remind["content"];
    document.getElementById("create_remind_type").value = remind["remindType"];
    if(remind["remindDateTime"] != EMPTY){
        document.getElementById("remind_date").value = remind["remindDateTime"].substr(0, 4) + "-" + remind["remindDateTime"].substr(4, 2) + "-" + remind["remindDateTime"].substr(6, 2);
        document.getElementById("remind_date_hour").value = remind["remindDateTime"].substr(8, 2);
        document.getElementById("remind_date_minute").value = remind["remindDateTime"].substr(10, 2);
    } else {
        document.getElementById("remind_date").value = EMPTY;
        document.getElementById("remind_date_hour").value = "00";
        document.getElementById("remind_date_minute").value = "00";
    }
    $("#view_remind_div").css("display", "none");
    $("#operate_remind_table").css("display", "block");
}

/**
 * 取消提醒
 * @param remindId
 */
function deleteRemind(remindId){
    if(!confirm("你确认取消提醒吗？")){
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateRemind.do",
        data:"type=deleteRemind&deleteRemindId=" + remindId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    var remindsJson = data["remindsJson"];
                    //把json字符串转换object数组
                    remindArray = transferJsonStr2Array(remindsJson);
                    //处理提醒数组
                    processRemindArray();
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

/**
 * 切换模式
 */
function changeMode(){
    var className = $("#content-box1").attr("class");
    if("content-box" == className){
        $("#content-box1").attr("class", "content-box column-left");
        $("#content-box2").attr("class", "content-box column-right");
    } else {
        $("#content-box1").attr("class", "content-box");
        $("#content-box2").attr("class", "content-box");
    }
    return false;
}

/**
 * 返回查看提醒页面
 */
function back(){
    $("#view_remind_div").css("display", "block");
    $("#operate_remind_table").css("display", "none");
}