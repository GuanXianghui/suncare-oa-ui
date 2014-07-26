//所有员工Json数组
var userArray = new Array();

/**
 * 加载页面后初始化
 */
$(document).ready(function(){
    $( "#date" ).datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
    });
    $( "#date" ).val(date);

    //处理所有员工json串
    processUserWithJson();

    //初始化用户
    if(initUserId > 0){
        var user = getUserById(initUserId);
        $("#userId").val(initUserId);
        $("#userName").val(user["name"]);
    }
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
 * 选择全部用户
 */
function chooseAll(){
    $("#userId").val(0);
    $("#userName").val("全部用户");
}

/**
 * 从通讯录选择
 */
function choose(){
    //设置窗口的一些状态值
    var windowStatus = "left=380,top=200,width=260,height=200,resizable=0,scrollbars=0,menubar=no,status=0,fullscreen=1";
    //在窗口中打开的页面
    var url = "chooseSingleContact.jsp";
    var userId = showModalDialog(url,"",windowStatus);
    if(userId == EMPTY || userId == undefined){
        return;
    }
    var user = getUserById(userId);
    $("#userId").val(userId);
    $("#userName").val(user["name"]);
}

/**
 * 查询操作日志
 */
function queryOperateLogs() {
    var userId = $("#userId").val();
    var type = $("#type").val();
    var date = $("#date").val();

    //判userId为空置成0，这样就不会带入作为条件
    if(userId == EMPTY){
        userId = parseInt("0");
    } else {
        //判type是否数字
        if(!isNum(userId)){
            showAttention("type不是整数");
            return;
        }
    }

    //判type为空置成0，这样就不会带入作为条件
    if(type == EMPTY){
        type = parseInt("0");
    } else {
        //判type是否数字
        if(!isNum(type)){
            showAttention("type不是整数");
            return;
        }
    }

    // 判断字符串是否含有非法字符
    result = checkStr(date, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("日期包含非法字符:" + result["symbol"]);
        return;
    }

    $("#operateLogUserId").val(userId);
    $("#operateLogType").val(type);
    $("#operateLogDate").val(date);
    document.forms["operateLogForm"].submit();
}

/**
 * 跳到某一页
 *
 * @param pageNum
 */
function jump2page(pageNum) {
    $("#pageNum").val(pageNum);
    document.forms["operateLogForm"].submit();
}