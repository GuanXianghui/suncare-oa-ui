//ueditor编辑器
var editor;
//公告Json数组
var noticeArray = new Array();
//当前点击对象索引
var nowFocusIndex = -1;

/**
 * 初始化
 */
$(document).ready(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    editor = UE.getEditor('editor');

    //处理公告Json串
    processWithJson();

    $("#mailDetail").css("display", "none");
});

/**
 * 处理公告Json串
 */
function processWithJson(){
    //json串转json数组
    if(noticeJsonStr != EMPTY) {
        noticeArray = new Array();
        var array = noticeJsonStr.split(SYMBOL_LOGIC_AND);
        for(var i=0;i<array.length;i++) {
            noticeArray[noticeArray.length] = eval("(" + array[i] + ")");
        }
        //因为加载下一页会把结果拼接到json串后面再生成一次json数组重新加载一次，所以得过滤掉已删除的部分
        for(var i=noticeArray.length-1;i>=0;i--){
            //判已删除，删掉该元素
            if((SYMBOL_COMMA + deletedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeArray[i]["id"] + SYMBOL_COMMA) > -1){
                noticeArray.splice(i, 1);
            }
        }
    }

    //循环展示
    var html = EMPTY;
    for(var i=0;i<noticeArray.length;i++){
        var isReaded = false;
        if((SYMBOL_COMMA + readedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeArray[i]["id"] + SYMBOL_COMMA) > -1){
            isReaded = true;
        }
        html += "<li onclick='showNotice(" + i + ", " + noticeArray[i]["id"] + ")'>";
        html += "<a href=\"#\"";
        if(isReaded == false){
            html += " style='font-weight: bold;'";
        } else {
            html += " style='font-weight: normal;'";
        }
        html += ">" + noticeArray[i]["title"] + "</a>";
        html += "<span>" + getLongDate(noticeArray[i]["createDate"]) + "</span>"
            + "<p>" + getShortContent(noticeArray[i]) + "</p>"
            + "<div class=\"clearBoth\"></div>"
            + "</li>";
    }
    //是否加载下一页
    if(notDeletedNoticeCount > noticeArray.length+deleteCount){
        html += "<li style='text-align: center; cursor: pointer;' onclick='showNextPageNotices()'>加载更多</li>";
    }
    //判是否为空
    if(EMPTY == html){
        html += "<li style='text-align: center; cursor: pointer;'>暂无</li>";
    }
    $("#mailList").html(html);
    //刷新后处理
    focusNowIndex();
}

/**
 * 根据id查公告
 * @param noticeId
 */
function getNoticeById(noticeId){
    for(var i=0;i<noticeArray.length;i++){
        if(noticeId == noticeArray[i]["id"]){
            return noticeArray[i];
        }
    }
    return null;
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
 * 删除公告
 * @param noticeId
 */
function deleteNotice(noticeId){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateUserNotice.do",
        data:"type=delete&noticeId=" + noticeId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    $("#mailDetail").css("display", "none");
                    //请求成功
                    showSuccess(data["message"]);
                    if(deletedIds != EMPTY){
                        deletedIds += SYMBOL_COMMA;
                    }
                    deletedIds += noticeId;
                    //在本页面中删除的个数累加
                    deleteCount++;

                    //当前点击对象索引置成-1
                    nowFocusIndex = -1;

                    //处理公告Json串
                    processWithJson();
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
 * 处理当前焦点对象
 * @param index
 */
function processFocusIndex(index){
    var liObj = null;
    if(nowFocusIndex >= 0){
        liObj = $("#mailList li")[nowFocusIndex];
        liObj.style.background = "";
        liObj.style.borderBottom = "";
    }
    liObj = $("#mailList li")[index];
    liObj.style.background = "#f0efef";
    liObj.style.borderBottom = "solid 1px #e5e5e5";
    nowFocusIndex = index;
}

/**
 * 刷新后处理
 */
function focusNowIndex(){
    if(nowFocusIndex < 0){
        return;
    }
    var liObj = $("#mailList li")[nowFocusIndex];
    liObj.style.background = "#f0efef";
    liObj.style.borderBottom = "solid 1px #e5e5e5";
}

/**
 * 查看公告
 * @param noticeId
 */
function showNotice(index, noticeId){
    //处理当前焦点对象
    processFocusIndex(index);
    $("#mailDetail").css("display", "block");
    var notice = getNoticeById(noticeId);
    //抬头
    $("#mailTitle").html("<b>" + notice["title"] + "</b>");
    var content = notice["content"];
    //将uuid->\r\n
    content = changeNewLineBack(content);
    //内容 解析
    $("#mailTxt").html(content);
    uParse("#mailTxt", {rootPath: baseUrl + '/ueditor/'});
    //操作 解析
    $("#mailOperate").html("<input class=\"minBtn\" type=\"button\" onclick=\"deleteNotice(" + noticeId + ");\" value=\"删除\" />");

    //判是否已读
    var isReaded = false;
    if((SYMBOL_COMMA + readedIds + SYMBOL_COMMA).indexOf(SYMBOL_COMMA + noticeId + SYMBOL_COMMA) > -1){
        isReaded = true;
    }

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
        url:baseUrl + "operateUserNotice.do",
        data:"type=read&noticeId=" + noticeId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    //return;
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    if(readedIds != EMPTY){
                        readedIds += SYMBOL_COMMA;
                    }
                    readedIds += noticeId;
                    //处理公告Json串
                    processWithJson();
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
 * 加载下一页公告
 */
function showNextPageNotices(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateUserNotice.do",
        data:"type=nextPage&countNow=" + noticeArray.length + "&token=" + token,
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
                        noticeJsonStr += SYMBOL_LOGIC_AND + nextPageJson;
                    }
                    //处理公告Json串
                    processWithJson();
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