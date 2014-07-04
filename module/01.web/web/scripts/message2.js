//ueditor编辑器
var editor;
//消息Json数组
var messageArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');

    //处理所有员工json串
    processUserWithJson();

    //把初始messageJsonStr转换成messageArray
    messageArray = transferInitJsonStr2Array(messageJsonStr);

    //处理消息Json串
    processWithJson();

    $("#mailDetail").css("display", "none");
});

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

/**
 * 把初始messageJsonStr转换成messageArray
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
 * 处理消息Json串
 */
function processWithJson(){
    //循环展示
    var html = EMPTY;
    for(var i=0;i<messageArray.length;i++){
        var isReaded = messageArray[i]["state"] == MESSAGE_STATE_READED;
        html += "<li onclick='showMessageDetail(this, " + messageArray[i]["id"] + ")'";
        if(isReaded == false){
            html += "style='font-weight: bold;'";
        }
        html += ">"
            + "<img src=\"" + messageArray[i]["headPhoto"] + "\" alt=\"" + messageArray[i]["fromUserName"] + "\"/>";
        html += "<a href=\"#\">来自[" + messageArray[i]["fromUserName"] + "]的消息</a>";
        html += "<span>" + messageArray[i]["date"] + "</span>"
            + "<p>" + getShortContent(messageArray[i]) + "</p>"
            + "<div class=\"clearBoth\"></div>"
            + "</li>";
    }
    //是否加载下一页
    if(messageCount > messageArray.length){
        html += "<li style='text-align: center; cursor: pointer;' onclick='showNextPageMessages()'>加载更多</li>";
    }
    //判是否为空
    if(EMPTY == html){
        html += "<li style='text-align: center; cursor: pointer;'>暂无</li>";
    }
    $("#mailList").html(html);
}

/**
 * 得到站内信的内容缩略信息
 * @param letter
 */
function getShortContent(letter){
    $("#initMailTxt").html(letter["content"]);
    var shortContent = $("#initMailTxt").text();
    shortContent = replaceAll(shortContent, " ", "");
    if(shortContent.length > 30){
        shortContent = shortContent.substring(0, 30) + "...";
    }
    return shortContent;
}

/**
 * 根据id查消息
 * @param messageId
 */
function getMessageById(messageId){
    for(var i=0;i<messageArray.length;i++){
        if(messageId == messageArray[i]["id"]){
            return messageArray[i];
        }
    }
    return null;
}

/**
 * 根据id删除消息
 * @param messageId
 */
function deleteMessageById(messageId){
    for(var i=0;i<messageArray.length;i++){
        if(messageId == messageArray[i]["id"]){
            messageArray.splice(i, 1);
        }
    }
    return null;
}

/**
 * 删除消息
 * @param messageId
 */
function deleteMessage(messageId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=delete&messageId=" + messageId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    //根据id删除消息
                    deleteMessageById(messageId);
                    //总共消息的量要减一
                    messageCount--;
                    //处理消息Json串
                    processWithJson();

                    $("#mailDetail").css("display", "none");
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
 * 查看消息
 * @param messageId
 */
function showMessageDetail(t, messageId){
    $("#mailDetail").css("display", "block");
    var message = getMessageById(messageId);
    //抬头
    $("#mailTitle").html("<b>来自[" + message["fromUserName"] + "]的消息</b>");

    //内容 解析
    $("#mailTxt").html(changeNewLineBack(message["content"]));
    uParse("#mailTxt", {rootPath: baseUrl + '/ueditor/'});

    //操作 解析
    $("#mailOperate").html("<input class=\"minBtn\" type=\"button\" onclick=\"deleteMessage(" + messageId + ");\" value=\"删除\" />");


    $(t).css("font-weight", "");

    //判是否已读
    var isReaded = message["state"] == MESSAGE_STATE_READED;

    //如果已读返回
    if(isReaded == true){
        return;
    }

    //如果未读调ajax请求置成已读
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=read&messageId=" + messageId + "&token=" + token,
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
                    //修改已读
                    getMessageById(messageId)["state"] = MESSAGE_STATE_READED
                    //处理消息Json串
                    processWithJson();
                    $('tbody tr:even').addClass("alt-row");
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
 * 加载下一页消息
 */
function showNextPageMessages(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateMessage.do",
        data:"type=nextPage&countNow=" + messageArray.length + "&token=" + token,
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
                        //把初始messageJsonStr转换成messageArray
                        var array = transferInitJsonStr2Array(nextPageJson);
                        messageArray = messageArray.concat(array);
                    }
                    //处理消息Json串
                    processWithJson();
                    $('tbody tr:even').addClass("alt-row");
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