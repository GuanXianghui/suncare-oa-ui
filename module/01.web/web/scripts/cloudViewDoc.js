/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
    $("#title").html(initTitle);
    if(initDescription != EMPTY){
        $("#description").html(initDescription);
    }
    if(initTags != EMPTY){
        $("#tags").html(initTags);
    }
    //初始化申成文库分类
    initCloudDocType("show_type", "type", initType);
});

/**
 * 查询文档
 */
function queryDoc(doc){
    $("#cloudQueryDocName").val(doc);
    document.forms["cloudQueryDocForm"].submit();
}

/**
 * 下载文档
 * @param docId
 */
function cloudDownLoadDoc(docId, downloadUrl){
    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "cloudDownLoadDoc.do",
        data:"docId=" + docId + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    showSuccess(data["message"]);
                    //判是否有新token
                    if (data["hasNewToken"]) {
                        token = data["token"];
                    }
                    //下载文件
                    window.open(downloadUrl);
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