//工作日志Json数组
var diaryArray = new Array();
//任务Json数组
var taskArray = new Array();
//提醒数组
var remindArray = new Array();
//所有员工Json数组
var userArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    //把初始diaryJsonStr转换成diaryArray
    diaryArray = transferInitJsonStr2Array(diaryJsonStr);

    //把初始taskJsonStr转换成taskArray
    taskArray = transferInitJsonStr2Array(taskJsonStr);

    //把初始remindJsonStr转换成remindArray
    remindArray = transferInitJsonStr2Array(remindJsonStr);

    //处理所有员工json串
    processUserWithJson();

    //处理工作日志Json串
    processWithDiaryJson();

    //处理任务Json串
    processWithTaskJson();

    //处理提醒Json串
    processWithRemindJson();
});

/**
 * 处理提醒Json串
 */
function processWithRemindJson(){
    var html = "<div class=\"normalTitle\">提醒" +
        "<input class=\"minBtn\" type=\"button\" onclick=\"location.href='calendar2.jsp'\" value=\"新增提醒\" />" +
        "<span class=\"titleSelect\">" +
        "<a href=\"calendar2.jsp\">全部提醒</a>" +
        "</span>" +
        "</div>";
    for(var i=0;i<remindArray.length;i++){
        html += "<div class=\"notice\">" +
            "<div class=\"notice_box\">" +
            "<div class=\"notice_time\">今天 ";
        if(remindArray[i]["remindDateTime"] != EMPTY){
            var dateTime = remindArray[i]["remindDateTime"].substr(8);
            html += dateTime.substr(0, 2) + " : " + dateTime.substr(2, 2);
        }
        html += "[" + remindArray[i]["remindTypeDesc"] + "]</div>" +
            "<div class=\"notice_info\">" + remindArray[i]["content"] + "</div>" +
            "</div>" +
            "</div>";
    }
    if(0 == remindArray.length){
        html += "<div style=\"text-align: center;\">今日暂无提醒</div>";
    }
    $("#noticeBoxDiv").html(html);
}

/**
 * 处理任务Json串
 */
function processWithTaskJson(){
    var html = "<div class=\"normalTitle\">任务" +
        "<input class=\"minBtn\" type=\"button\" onclick=\"location.href='writeTask2.jsp'\" value=\"分配任务\" />" +
        "<span class=\"titleSelect\">" +
        "<a href=\"task2.jsp\">全部任务</a>" +
        "</span>" +
        "</div>";

    for(var i=0;i<taskArray.length;i++){
        html += "<div class=\"task\">" +
            "<div class=\"task_status\">" + taskArray[i]["stateDesc"] + "</div>" +
            "<div class=\"task_title\">" +
            "<a href=\"" + baseUrl + "showTask.jsp?id=" + taskArray[i]["id"] + "\">" + taskArray[i]["title"] + "</a>" +
            "</div>" +
            "<div class=\"task_from\">" +
            "<a href=\"" + baseUrl +"user.jsp?id=" + taskArray[i]["fromUserId"] + "\" target=\"_blank\">" + taskArray[i]["fromUserName"] + "</a>" +
            "指派给" +
            "<a href=\"" + baseUrl +"user.jsp?id=" + taskArray[i]["toUserId"] + "\" " + "target=\"_blank\">" + taskArray[i]["toUserName"] + "</a>" +
            "</div>" +
            "<div class=\"task_time\">开始日期： " + taskArray[i]["beginDate"] + "</div>" +
            "<div class=\"task_time\">结束日期： " + taskArray[i]["endDate"] + "</div>" +
            "<div class=\"clearBoth\"></div>" +
            "</div>";
    }

    if(0 == taskArray.length){
        html += "<div class=\"task\" style=\"text-align: center;\">暂无任务</div>";
    }

    $("#taskBoxDiv").html(html);
}

/**
 * 把初始diaryJsonStr转换成diaryArray
 */
function transferInitJsonStr2Array(jsonStr){
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
 * 处理工作日志Json串
 */
function processWithDiaryJson(){
    //循环展示
    var html = EMPTY;
    if(diaryArray.length == 0){
        html = "<li style=\"text-align: center;\">暂无日志</li>";
    } else {
        for(var i=0;i<diaryArray.length;i++){
            var user = getUserById(diaryArray[i]["userId"]);
            html += "<li>" +
                "<img src=\"" + user["headPhoto"] + "\" alt=\"" + user["name"] + "\" width=\"80\"/>" +
                "<a href=\"" + baseUrl + "showDiary.jsp?id=" + diaryArray[i]["id"] + "\">" +
                diaryArray[i]["date"].substr(0, 4) + "-" + diaryArray[i]["date"].substr(4, 2) + "-" +
                diaryArray[i]["date"].substr(6, 2) + "</a><span>" + user["name"] + "</span>" +
                "<p>" + getShortContent(diaryArray[i]["content"]) + "</p>" +
                "<div class=\"clearBoth\"></div>" +
                "</li>";
        }
    }
    document.getElementById("diaryList").innerHTML = html;
}

/**
 * 得到内容缩略信息
 * @param content
 */
function getShortContent(content){
    $("#initDiaryTxt").html(content);
    var shortContent = $("#initDiaryTxt").text();
    shortContent = replaceAll(shortContent, " ", "");
    if(shortContent.length > 500){
        shortContent = shortContent.substring(0, 500) + "...";
    }
    return shortContent;
}

/**
 * 处理所有员工json串
 */
function processUserWithJson() {
    //json串转json数组
    if(userJsonStr != EMPTY) {
        var array = userJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            userArray[userArray.length] = eval("(" + array[i] + ")");
        }
    }
}

/**
 * 根据id查用户
 * @param id
 */
function getUserById(id) {
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["id"] == id){
            return userArray[i];
        }
    }
    return null;
}