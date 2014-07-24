//工作日志Json数组
var diaryArray = new Array();
////所有员工Json数组
//var userArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }

    //把初始diaryJsonStr转换成diaryArray
    diaryArray = transferInitJsonStr2Array(diaryJsonStr);

    //检查是否还有下一页
    //checkHasNextPage();

    //处理所有员工json串
//    processUserWithJson();

    //初始化有权限查看的用户
    initRightUsers();

    //处理工作日志Json串
    processWithJson();

    $("#date").datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
    });

    if(date == EMPTY){
        $("#date").val("全部时间");
    } else {
        $("#date").val(date);
    }
});

/**
 * 初始化有权限查看的用户
 */
function initRightUsers(){
    if(rightUserWithComma != EMPTY){
        //用户id数组
        var userIdArray = rightUserWithComma.split(SYMBOL_COMMA);
        for(var i=0;i<userIdArray.length;i++){
            var id = userIdArray[i];
            var user = getUserById(id);
            document.getElementById("userId").innerHTML += "<option value='" + user["id"] + "'" + ((userId==id)?" " +
                "selected":"") + ">" + user["name"] + "</option>"
        }
    }
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
function processWithJson(){
    //循环展示
    var html = EMPTY;
    if(diaryArray.length == 0){
        html = "<li>无相关日志</li>";
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
    $('tbody tr:even').addClass("alt-row");
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
 * 检查是否还有下一页
 */
function checkHasNextPage(){
    if(diaryCount > diaryArray.length){
        document.getElementById("nextPageDiv").style.display = EMPTY;
    } else {
        document.getElementById("nextPageDiv").style.display = "none";
    }
}

/**
 * 塞选工作日志
 */
function selectDiary(){
    var userId = document.getElementById("userId").value;
    var date = document.getElementById("date").value;
    if(date == "全部时间"){
        date = EMPTY;
    }
    if(date != EMPTY && (isNum(date) == false || date.length != 8)){
        showAttention("日期输入有误！");
        return;
    }
    var condition = EMPTY;
    if(userId != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "userId=" + userId;
    }
    if(date != EMPTY){
        if(condition == EMPTY){
            condition += "?";
        } else {
            condition += "&";
        }
        condition += "date=" + date;
    }

    location.href = baseUrl + "diary.jsp" + condition;
}

/**
 * 加载下一页
 */
function showNextPageDiaries(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateDiary.do",
        data:"type=nextPage&countNow=" + diaryArray.length + "&userId=" + userId + "&date=" + date + "&token=" + token,
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
                    var nextPageJson = data["nextPageJson"];
                    if(EMPTY != nextPageJson) {
                        //把初始letterJsonStr转换成letterArray
                        var array = transferInitJsonStr2Array(nextPageJson);
                        diaryArray = diaryArray.concat(array);
                    }
                    //处理站内信Json串
                    processWithJson();
                    //检查是否还有下一页
                    checkHasNextPage();
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