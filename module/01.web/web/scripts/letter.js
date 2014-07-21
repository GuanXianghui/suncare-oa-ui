//站内信Json数组
var letterArray = new Array();
//当前点击对象索引
var nowFocusIndex = -1;

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showAttention(message);
    }
    //处理所有员工json串
    processUserWithJson();

    //把初始letterJsonStr转换成letterArray
    letterArray = transferInitJsonStr2Array(letterJsonStr);

    //处理站内信Json串
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
 * 根据id获取letter
 * @param id
 * @return {*}
 */
function getLetterById(id){
    for(var i=0;i<letterArray.length;i++){
        if(letterArray[i]["id"] == id){
            return letterArray[i];
        }
    }
    return null;
}

/**
 * 把初始letterJsonStr转换成letterArray
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
 * 处理站内信Json串
 */
function processWithJson(){
    //循环展示
    var html = EMPTY;
    for(var i=0;i<letterArray.length;i++){
        var isReaded = letterArray[i]["readState"] == LETTER_READ_STATE_READED;
        html += "<li onclick='showLetter(" + i + ", " + letterArray[i]["id"] + ")'>";
        html += "<img src=\"" + letterArray[i]["headPhoto"] + "\" alt=\"" + letterArray[i]["fromUserName"] + "\"/>";
        html += "<a href=\"#\"";
        if(isReaded == false){
            html += " style='font-weight: bold;'";
        } else {
            html += " style='font-weight: normal;'";
        }
        html += ">" + letterArray[i]["title"] + "</a>";
        html += "<span>" + getLongDate(letterArray[i]["createDate"]) + "</span>"
            + "<p>" + getShortContent(letterArray[i]) + "</p>"
            + "<div class=\"clearBoth\"></div>"
            + "</li>";
    }
    //是否加载下一页
    if(letterCount > letterArray.length){
        html += "<li style='text-align: center; cursor: pointer;' onclick='showNextPageLetters()'>加载更多</li>";
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
 * 删除
 */
function deleteLetter(){
    if(chooseLetterIds == EMPTY){
        showAttention("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=delete&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;

                    $("#mailDetail").css("display", "none");
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
 * 彻底删除
 */
function ctrlDeleteLetter(){
    if(chooseLetterIds == EMPTY){
        showAttention("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=ctrlDelete&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 转发
 */
function transmit(){
    if(chooseLetterIds == EMPTY){
        showAttention("请选择站内信");
        return;
    }
    if(chooseLetterIds.split(SYMBOL_COMMA).length > 1){
        showAttention("你只能选择一封站内信进行转发");
        return;
    }
    location.href = baseUrl + "writeLetter.jsp?type=transmit&id=" + chooseLetterIds;
}

/**
 * 标记成已读
 */
function setReaded(){
    if(chooseLetterIds == EMPTY){
        showAttention("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=setReaded&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray[j]["readState"] = LETTER_READ_STATE_READED;
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 还原
 */
function restore(){
    if(chooseLetterIds == EMPTY){
        showAttention("请选择站内信");
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=restore&ids=" + chooseLetterIds + "&token=" + token,
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
                    var idArray = chooseLetterIds.split(SYMBOL_COMMA);
                    for(var i=0;i<idArray.length;i++){
                        var id = idArray[i];
                        for(var j=letterArray.length-1;j>=0;j--){
                            if(id == letterArray[j]["id"]){
                                letterArray.splice(j, 1);
                            }
                        }
                    }
                    //处理站内信Json串
                    processWithJson();
                    //总共站内信的量减去删除的量
                    letterCount -= idArray.length;
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 加载下一页站内信
 */
function showNextPageLetters(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=nextPage&countNow=" + letterArray.length + "&box=" + box + "&token=" + token,
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
                        letterArray = letterArray.concat(array);
                    }
                    //处理站内信Json串
                    processWithJson();
                    //清空选择站内信ids
                    chooseLetterIds = EMPTY;
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
 * 显示信
 * @param id
 */
function showLetter(index, id){
    //处理当前焦点对象
    processFocusIndex(index);
    $("#mailDetail").css("display", "block");
    letterId = id;
    var letter = getLetterById(id);
    //抬头
    $("#mailTitle").html("<b>" + letter["title"] + "</b>");
    //发件人
    $("#mailFrom").html("发件人：<a href=\"" + baseUrl + "user.jsp?id=" + letter["fromUserId"] + "\" target=\"_blank\">" +
        "<img src=\"" + getUserById(letter["fromUserId"])["headPhoto"] + "\">" + letter["fromUserName"] + "</a>" +
        "<span>[" + getLongDateTime2(letter["createDate"], letter["createTime"]) + "]</span>");
    //收件人
    var toUserIds = letter["toUserIds"];
    if(EMPTY == toUserIds){
        $("#mailTo").css("display", "none");
    } else{
        $("#mailTo").css("display", "block");
        var html = "收件人：";
        var userIdArray = toUserIds.split(SYMBOL_COMMA);
        for(var i=0;i<userIdArray.length;i++){
            if(i > 0){
                html += SYMBOL_COMMA;
            }
            var user = getUserById(userIdArray[i]);
            html += "<a href=\"" + baseUrl + "user.jsp?id=" + user["id"] + "\" target=\"_blank\">" +
                "<img src=\"" + user["headPhoto"] + "\">" + user["name"] + "</a>";
        }
        $("#mailTo").html(html);
    }
    //抄送人
    var ccUserIds = letter["ccUserIds"];
    if(EMPTY == ccUserIds){
        $("#mailCc").css("display", "none");
    } else{
        $("#mailCc").css("display", "block");
        var html = "抄送人：";
        var userIdArray = ccUserIds.split(SYMBOL_COMMA);
        for(var i=0;i<userIdArray.length;i++){
            if(i > 0){
                html += SYMBOL_COMMA;
            }
            var user = getUserById(userIdArray[i]);
            html += "<a href=\"" + baseUrl + "user.jsp?id=" + user["id"] + "\" target=\"_blank\">" +
                "<img src=\"" + user["headPhoto"] + "\">" + user["name"] + "</a>";
        }
        $("#mailCc").html(html);
    }

    //内容 解析
    $("#mailTxt").html(changeNewLineBack(letter["content"]));
    uParse("#mailTxt", {rootPath: baseUrl + '/ueditor/'});

    //操作 解析
    $("#mailOperate").css("display", "block");


    //设置成已读
    if(letter["readState"] == LETTER_READ_STATE_NOT_READED){
        letter["readState"] = LETTER_READ_STATE_READED;
        //标记成已读
        readLetter(id);
        //处理站内信Json串
        processWithJson();
    }
}

/**
 * 标记成已读
 */
function readLetter(id){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=setReaded&ids=" + id + "&token=" + token,
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
 * 回复
 */
function reply(){
    location.href = baseUrl + "writeLetter.jsp?type=reply&id=" + letterId;
}

/**
 * 回复全部
 */
function replyAll(){
    location.href = baseUrl + "writeLetter.jsp?type=replyAll&id=" + letterId;
}

/**
 * 转发
 */
function transmit(){
    location.href = baseUrl + "writeLetter.jsp?type=transmit&id=" + letterId;
}

/**
 * 删除
 */
function deleteLetter(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=delete&ids=" + letterId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    location.href = baseUrl + "letter.jsp?message=delete success!";
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
 * 彻底删除
 */
function ctrlDeleteLetter(){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateLetter.do",
        data:"type=ctrlDelete&ids=" + letterId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    location.href = baseUrl + "letter.jsp?message=delete success!";
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