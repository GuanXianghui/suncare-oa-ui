//空
var EMPTY = "";

//符号集合
var SYMBOL_BLANK = " ";
var SYMBOL_COMMA = ",";
var SYMBOL_EQUAL = "=";
var SYMBOL_BIT_AND = "&";
var SYMBOL_SINGLE_QUOT = "'";
var SYMBOL_DOUBLE_QUOT = "\"";
var SYMBOL_LOGIC_AND = "&&";
var SYMBOL_WAVE = "~";
var SYMBOL_EXCLAMATION = "!";
var SYMBOL_MOUSE = "@";
var SYMBOL_WELL = "#";
var SYMBOL_DOLLAR = "$";
var SYMBOL_PERCENT = "%";
var SYMBOL_BIT_DIFF = "^";
var SYMBOL_STAR = "*";
var SYMBOL_SLASH = "/";
var SYMBOL_DOT = ".";
var SYMBOL_COLON = ":";
var SYMBOL_NEW_LINE = "\r\n";
var SYMBOL_NEW_LINE2 = "\n";
var SYMBOL_ARRAY_1 = new Array(SYMBOL_BIT_AND,SYMBOL_SINGLE_QUOT,SYMBOL_DOUBLE_QUOT,SYMBOL_SLASH);
var SYMBOL_ARRAY_2_CHECK_URL = new Array(SYMBOL_SINGLE_QUOT,SYMBOL_DOUBLE_QUOT);

//按键值
var KEY_CODE_ENTER = 13;
var KEY_CODE_CTRL = 17;
var KEY_CODE_DELETE = 46;

//默认信息提示框ID
var DEFAULT_MESSAGE_ID = "message_id";

/**
 * UE的代码功能，每行都是换行的，写到json里会有问题，所以把换行先转换成一个uuid串，展示之前再转回来
 * 即：\r\n<->uuid
 * 注意：该标志要与服务端config.properties配置一致
 */
var GXX_OA_NEW_LINE_UUID = "0e8e1794-9ff3-411b-9e4f-f364f663b839";

/**
 * 性别: 1男，0女
 */
var USER_SEX_X = 1;
var USER_SEX_O = 0;

/**
 * 组织架构类型
 */
var STRUCTURE_TYPE_COMPANY = 1;
var STRUCTURE_TYPE_DEPT = 2;
var STRUCTURE_TYPE_POSITION = 3;

/**
 * 字段长度
 */
var NOTICE_TITLE_LENGTH = 40;//公告标题长度
var NOTICE_CONTENT_LENGTH = 10000;//公告内容长度

/**
 * 公告管理类型:add:新增 update:修改 delete:删除
 */
var NOTICE_TYPE_ADD = "add";
var NOTICE_TYPE_UPDATE = "update";
var NOTICE_TYPE_DELETE = "delete";

/**
 * 消息用户类型 1:普通用户 2:公众账号
 */
var MESSAGE_USER_TYPE_NORMAL = 1;
var MESSAGE_USER_TYPE_PUBLIC = 2;

/**
 * 消息状态 1:未读 2:已读 删就直接删掉记录了
 */
var MESSAGE_STATE_NOT_READED = 1;
var MESSAGE_STATE_READED = 2;

/**
 * 站内信信箱 received:收件箱 sent:已发送 deleted:已删除
 */
var LETTER_BOX_RECEIVED = "received";
var LETTER_BOX_SENT = "sent";
var LETTER_BOX_DELETED = "deleted";

/**
 * 未读还是已读 1:未读 2:已读
 */
var LETTER_READ_STATE_NOT_READED = 1;
var LETTER_READ_STATE_READED = 2;

/**
 * 字段长度
 */
var LETTER_CONTENT_LENGTH = 10000;//站内信内容长度

/**
 * 字段长度
 */
var DIARY_CONTENT_LENGTH = 1000;//工作日志内容长度

/**
 * 字段长度
 */
var DIARY_REVIEW_CONTENT_LENGTH = 250;//工作日志评论内容长度

/**
 * 字段长度
 */
var REMIND_CONTENT_LENGTH = 250;//提醒内容长度
/**
 * 提醒类型 1:不提醒 2:消息提醒 3:短信提醒 4:邮件提醒(目前只能1或者2)
 */
var REMIND_TYPE_NO_REMIND = 1;
var REMIND_TYPE_MESSAGE = 2;
var REMIND_TYPE_SMS = 3;
var REMIND_TYPE_MAIL = 4;

/**
 * 字段长度
 */
var TASK_TITLE_LENGTH = 50;//任务名称长度
var TASK_CONTENT_LENGTH = 10000;//任务内容长度

/**
 * 字段长度
 */
var TASK_REVIEW_CONTENT_LENGTH = 250;//任务评论内容长度

/**
 * 字段长度
 */
var SMS_CONTENT_LENGTH = 65;//短信内容长度

/**
 * 状态 1 正常 2 删除 3 彻底删除
 */
var CLOUD_STATE_NORMAL = 1;
var CLOUD_STATE_DELETE = 2;
var CLOUD_STATE_CTRL_DELETE = 3;

/**
 * 类型 1 文件 2 文件夹 3 系统文件
 */
var CLOUD_TYPE_FILE = 1;
var CLOUD_TYPE_DIR = 2;
var CLOUD_TYPE_SYSTEM_FILE = 3;

/**
 * 申成文库相关字段
 */
var CLOUD_UPLOAD_DOC_EMPTY_TITLE = "请输入标题";
var CLOUD_UPLOAD_DOC_EMPTY_DESCRIPTION = "填写文档简介，能帮助文档传播更广~";
var CLOUD_UPLOAD_DOC_EMPTY_TAGS = "多个标签请用逗号隔开";

/**
 * 申成知道相关字段
 */
var CLOUD_KNOW_ASK_EMPTY_QUESTION = "请输入问题";

/**
 * 计算str1中还有几个str2
 * @param str1
 * @param str2
 */
function containCount(str1, str2) {
    var count = 0;
    while(str1.indexOf(str2) > -1) {
         count ++;
        str1 = str1.substr(str1.indexOf(str2) + str2.length);
    }
    return count;
}

/**
 * 判断字符串是否含有非法字符
 * 校验通过返回result["isSuccess"]=true
 * 校验失败返回result["isSuccess"]=false,result["symbol"]=包含的非法字符
 * @param value
 * @param symbolArray
 */
function checkStr(value, symbolArray) {
    var result = EMPTY;
    for(var i=0;i<symbolArray.length;i++){
        if(value.indexOf(symbolArray[i]) > -1) {
            if("'" == symbolArray[i]){
                //对单引号特殊处理
                result = "{isSuccess:false,symbol:\"\'\"}";
            } else {
                result = "{isSuccess:false,symbol:'" + symbolArray[i] + "'}";
            }
            break;
        }
    }
    if(result == EMPTY){
        result = "{isSuccess:true}";
    }
    result = eval("(" + result + ")");
    return result;
}

/**
 * 跳到用户主页
 */
function jump2Main(){
    location.href = baseUrl + "main.jsp";
}

/**
 * 退出
 * 注意：用到$.ajax，所以得依赖jquery-min.js
 * 注意：用到变量baseUrl，所以得依赖header.jsp
 */
function logOut(){
    //ajax退出
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "ajax/logOut.jsp",
        data:EMPTY,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判退出是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                    return;
                } else {
                    //退出成功
                    //showSuccess(data["message"]);
                }
                //是否跳转页面
                if (data["isRedirect"]) {
                    var redirectUrl = data["redirectUrl"];
                    location.href = redirectUrl;
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
 * email格式校验
 * @param email
 */
function isEmail(email){
    var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    return re.test(email);
}

/**
 * num格式校验
 * @param num
 */
function isNum(num){
    var re = /^[\d]+$/
    return re.test(num);
}

/**
 * 判是否是移动电话
 * @param num
 */
function isMobilePhone(str){
    if(str.length != 11 || isNum(str) == false){
        return false;
    }
    return true;
}

/**
 * 将\r\n->uuid
 * @param content
 */
function changeNewLine(content){
    while(content.indexOf(SYMBOL_NEW_LINE) > -1) {
        content = content.replace(SYMBOL_NEW_LINE, GXX_OA_NEW_LINE_UUID);
    }
    return content;
}

/**
 * 将\n->uuid
 * @param content
 */
function changeNewLine2(content){
    while(content.indexOf(SYMBOL_NEW_LINE2) > -1) {
        content = content.replace(SYMBOL_NEW_LINE2, GXX_OA_NEW_LINE_UUID);
    }
    return content;
}

/**
 * 将uuid->\r\n
 * @param content
 */
function changeNewLineBack(content){
    while(content.indexOf(GXX_OA_NEW_LINE_UUID) > -1) {
        content = content.replace(GXX_OA_NEW_LINE_UUID, SYMBOL_NEW_LINE);
    }
    return content;
}

/**
 * 将uuid->\n
 * @param content
 */
function changeNewLineBack2(content){
    while(content.indexOf(GXX_OA_NEW_LINE_UUID) > -1) {
        content = content.replace(GXX_OA_NEW_LINE_UUID, SYMBOL_NEW_LINE2);
    }
    return content;
}

/**
 * 显示信息
 * @param messageId
 * @param message
 */
function showMessage(messageId, message){
    $("#" + messageId + "_content").html(message);
    $("#" + messageId).css("opacity", 1);
    $("#" + messageId).show(300);
}

/**
 * 显示警告消息
 * @param message
 */
function showAttention(message){
    alert(message);
//    showAttentionMessage(DEFAULT_MESSAGE_ID, message);
}

/**
 * 显示警告消息
 * @param messageId
 * @param message
 */
function showAttentionMessage(messageId, message){
    $("#" + messageId).attr("class", "notification attention png_bg");
    showMessage(messageId, message);
}

/**
 * 显示消息
 * @param message
 */
function showInformation(message){
    alert(message);
//    showInformationMessage(DEFAULT_MESSAGE_ID, message);
}

/**
 * 显示消息
 * @param messageId
 * @param message
 */
function showInformationMessage(messageId, message){
    $("#" + messageId).attr("class", "notification information png_bg");
    showMessage(messageId, message);
}

/**
 * 显示成功消息
 * @param message
 */
function showSuccess(message){
    alert(message);
//    showSuccessMessage(DEFAULT_MESSAGE_ID, message);
}

/**
 * 显示成功消息
 * @param messageId
 * @param message
 */
function showSuccessMessage(messageId, message){
    $("#" + messageId).attr("class", "notification success png_bg");
    showMessage(messageId, message);
}

/**
 * 显示错误消息
 * @param message
 */
function showError(message){
    alert(message);
//    showErrorMessage(DEFAULT_MESSAGE_ID, message);
}

/**
 * 显示错误消息
 * @param messageId
 * @param message
 */
function showErrorMessage(messageId, message){
    $("#" + messageId).attr("class", "notification error png_bg");
    showMessage(messageId, message);
}

/**
 * 字符串编码
 * 采用Ajax传递参数加号(+)和与符号(&)时候，服务端获取到的参数并不如意！
 * 解决办法：在传到服务端之前先将参数中的"+"和"&"符号都编码一下
 * @param str
 */
function filterStr(str){
    str = filePlus(str);
    str = fileBitAnd(str);
    return str;
}

/**
 * 编码+号
 * @param str
 * @return {*}
 */
function filePlus(str){
    str = str.replace(/\+/g,"%2B");
    return str;
}

/**
 * 编码&号
 * @param str
 * @return {*}
 */
function fileBitAnd(str){
    str = str.replace(/\&/g,"%26");
    return str;
}

/**
 * 得到one在数组array中的index
 * 未校验是否可用！
 * @param array
 * @param one
 */
function getIndexOfArray(array, one){
    var index = -1;
    for(var i=0;i<array.length;i++){
        if(array[i] == one){
            index = i;
            break;
        }
    }
    return index;
}

/**
 * 从array数组中把所有等于one的删掉
 * @param array
 * @param one
 */
function removeAllOneFromArray(array, one){
    var newArray = new Array();
    for(var i=0;i<array.length;i++){
        if(array[i] != one){
            newArray[newArray.length] = array[i];
        }
    }
    return newArray;
}

/**
 * 从array数组中把第index位置的删掉
 * 未校验是否可用！
 * @param array
 * @param index
 */
function removeIndexFromArray(array, index){
    var newArray = new Array();
    for(var i=0;i<array.length;i++){
        if(i != index){
            newArray[newArray.length] = array[i];
        }
    }
    return newArray;
}

//图片类型
var IMG_TYPE_ARRAY = new Array("JPG","jpg","JPEG","jpeg","GIF","gif","BMP","bmp","PNG","png");
/**
 * 根据文件名判是否图片类型
 * @param name
 * @return {Boolean}
 */
function isImg(name){
    if(name == null || name == EMPTY){
        return false;
    }
    var index = name.lastIndexOf(SYMBOL_DOT);
    if(index == -1 || index+1==name.length){
        return false;
    }
    var type = name.substr(index + 1);
    for(var i=0;i<IMG_TYPE_ARRAY.length;i++){
        if(IMG_TYPE_ARRAY[i] == type){
            return true;
        }
    }
    return false;
}

/**
 * 去掉前后空格
 * @param s
 * @return {*}
 */
function trim(s){
    return trimRight(trimLeft(s));
}
//去掉左边的空格
function trimLeft(s){
    if(s == null) {
        return "";
    }
    var whitespace = new String(" \t\n\r");
    var str = new String(s);
    if (whitespace.indexOf(str.charAt(0)) != -1) {
        var j=0, i = str.length;
        while (j < i && whitespace.indexOf(str.charAt(j)) != -1){
            j++;
        }
        str = str.substring(j, i);
    }
    return str;
}
//去掉右边的空格 www.2cto.com
function trimRight(s){
    if(s == null) return "";
    var whitespace = new String(" \t\n\r");
    var str = new String(s);
    if (whitespace.indexOf(str.charAt(str.length-1)) != -1){
        var i = str.length - 1;
        while (i >= 0 && whitespace.indexOf(str.charAt(i)) != -1){
            i--;
        }
        str = str.substring(0, i+1);
    }
    return str;
}

/**
 * 替换字符串
 * @param initStr
 * @param beforeStr
 * @param afterStr
 */
function replaceAll(initStr, beforeStr, afterStr){
    if(initStr == null || initStr == undefined){
        return initStr;
    }
    while(initStr.indexOf(beforeStr) > -1){
        initStr = initStr.replace(beforeStr, afterStr);
    }
    return initStr;
}