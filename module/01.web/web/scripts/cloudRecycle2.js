/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
    //把初始jsonStr转换成array
    files = transferJsonStr2Array(filesJsonStr);
    //展示所有文件
    displayFiles();
});

/**
 * 把初始jsonStr转换成array
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
 * 根据id获取申成云
 * @param id
 */
function getCloudById(id){
    for(var i=0;i<files.length;i++) {
        if(files[i]["id"] == id){
            return files[i];
        }
    }
    return null;
}

/**
 * 展示所有文件
 */
function displayFiles(){
    var html = "<ul>";
    if(files.length == 0){
        html = "<li>无文件！ToT</li>";
    }
    for(var i=0;i<files.length;i++){
        html += getDisplayFileByIdAndTypeAndName(files[i]["id"], files[i]["type"], files[i]["name"], files[i]["route"]);
    }
    html += "</ul>";
    $("#files").html(html);
}

/**
 * 展示文件
 * @param type
 * @param name
 */
function getDisplayFileByIdAndTypeAndName(id, type, name, route){
    var file = EMPTY;
    if(CLOUD_TYPE_FILE == type){
        file += "<li onclick=\"chooseCloud(this)\" name=\"" + id + "\"><img src=\"images/ext/png.gif\"/>";
    } else if(CLOUD_TYPE_DIR == type){
        file += "<li onclick=\"chooseCloud(this)\" name=\"" + id + "\"><img src=\"images/ext/dir.gif\"/>";
    }
    file += name + "</li>";
    return file;
}

/**
 * 选择对象
 * @param t
 */
function chooseCloud(t){
    if(isCtrlDown == false){
        for(var i=0;i<chooseClouds.length;i++){
            chooseClouds[i].style.background=''
        }
        chooseClouds = new Array();
        chooseClouds[chooseClouds.length] = t;
        t.style.background='#0A7CA0'
    } else {
        for(var i=0;i<chooseClouds.length;i++){
            if(t == chooseClouds[i]){
                chooseClouds[i].style.background='';
                chooseClouds = removeAllOneFromArray(chooseClouds, chooseClouds[i]);
                return;
            }
        }
        chooseClouds[chooseClouds.length] = t;
        t.style.background='#0A7CA0'
    }
}

/**
 * 按键按下监听
 * @param e
 */
function keyDown(e){
    if(KEY_CODE_CTRL == e.keyCode){
        isCtrlDown = true;
    }
}

/**
 * 按键放开监听
 * @param event
 */
function keyUp(e){
    if(KEY_CODE_ENTER == e.keyCode){
    }
    if(KEY_CODE_CTRL == e.keyCode){
        isCtrlDown = false;
    }
    if(KEY_CODE_DELETE == e.keyCode){
        //触发点击彻底删除按钮
        ctrlDelete();
    }
}

/**
 * 还原
 */
function recover(){
    if(chooseClouds.length == 0){
        showAttention("请选择文件或者文件夹！");
        return;
    }
    var recoverIds = EMPTY;
    for(var i=0;i<chooseClouds.length;i++){
        if(recoverIds != EMPTY){
            recoverIds += SYMBOL_COMMA;
        }
        recoverIds += ($(chooseClouds[i]).attr("name"));
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudRecover.do",
        data:"recoverIds=" + recoverIds + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //清空选择对象
                    chooseClouds = new Array();
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
function ctrlDelete(){
    if(chooseClouds.length == 0){
        showAttention("请选择文件或者文件夹！");
        return;
    }
    //确认是否彻底删除
    var result = confirm("确定彻底删除选中的" + chooseClouds.length + "个文件吗？");
    if(result == false){
        return;
    }
    var ctrlDeleteIds = EMPTY;
    for(var i=0;i<chooseClouds.length;i++){
        if(ctrlDeleteIds != EMPTY){
            ctrlDeleteIds += SYMBOL_COMMA;
        }
        ctrlDeleteIds += ($(chooseClouds[i]).attr("name"));
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudCtrlDelete.do",
        data:"ctrlDeleteIds=" + ctrlDeleteIds + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //清空选择对象
                    chooseClouds = new Array();
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
 * 清空回收站
 */
function clearRecycle(){
    //确认是否清空回收站
    var result = confirm("确定清空回收站吗？");
    if(result == false){
        return;
    }
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudClearRecycle.do",
        data:"token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);

                    //把初始taskJsonStr转换成taskArray
                    files = transferJsonStr2Array(data["filesJsonStr"]);
                    //展示所有文件
                    displayFiles();
                    //清空选择对象
                    chooseClouds = new Array();
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