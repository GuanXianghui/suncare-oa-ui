//短信Json数组
var smsArray = new Array();
//所有员工Json数组
var userArray = new Array();
//所有公司结构Json数组
var structureArray = new Array();

/**
 * 初始化
 */
$(document).ready(function() {
    if(message != EMPTY){
        showInformation(message);
    }
    $("#date").datepicker();
    $( "#date" ).datepicker( "option", "dateFormat", "yymmdd" );
    $( "#date" ).datepicker( "option", "showAnim", "drop" );
    $( "#date" ).datepicker( "option", "onSelect", function(dateText, inst ){
        //根据日期查询短信
        location.href = baseUrl + "sms.jsp?date=" + dateText;
    });
    //把初始smsJsonStr转换成smsArray
    smsArray = transferInitJsonStr2Array(smsJsonStr);

    //处理所有员工json串
    processUserWithJson();
    //处理所有公司结构json串
    processStructureWithJson();
    //处理通讯录
    processContacts();

    //处理短信Json串
    processWithJson();

    back();
});

/**
 * 把初始smsJsonStr转换成smsArray
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
 * 处理短信Json串
 */
function processWithJson(){
    //循环展示
    var html = "<tr class=\"alt-row\"><th width=\"100\">手机号</th><th>内容</th><th width=\"60\">状态</th>"+
        "<th width=\"130\">时间</th><th width=\"70\">操作</th></tr>";
    for(var i=0;i<smsArray.length;i++){
        var user = getUserByMobilePhone(smsArray[i]["phone"]);
        var phoneDisplay = user == null ? smsArray[i]["phone"] : (smsArray[i]["phone"] + "<br>(" + user["name"] + ")");
        html += "<tr><td>" + phoneDisplay + "</td><td>" + changeNewLineBack2(smsArray[i]["content"]) +
            "</td><td>" + smsArray[i]["stateDesc"] + "</td><td>" + getLongDateTime2(smsArray[i]["date"],smsArray[i]["time"]) + "</td><td>" +
            "<input name=\"dosubmit\" value=\"转发\" type=\"button\" class=\"minBtn\" onclick=\"transmit(" + smsArray[i]["id"] + ")\" /></td></tr>";
    }
    if(smsArray.length == 0){
        html += "<tr><td colspan='5'>未发过短信</td></tr>";
    }
    document.getElementById("sms_table").innerHTML = html;
}

/**
 * 根据id查短信
 * @param smsId
 */
function getSMSById(smsId){
    for(var i=0;i<smsArray.length;i++){
        if(smsId == smsArray[i]["id"]){
            return smsArray[i];
        }
    }
    return null;
}

/**
 * 点击发送短信按钮
 */
function beforeSendSMS(){
    document.getElementById("sendSmsDiv").style.display = EMPTY;
}

/**
 * 点击转发短信按钮
 * @param smsId
 */
function transmit(smsId){
    var sms = getSMSById(smsId);
    document.getElementById("content").value = changeNewLineBack2(sms["content"]);
    beforeOperateSMS();
}

/**
 * 发送短信
 */
function operateSMS(){
    var phone = document.getElementById("phone").value;
    var content = document.getElementById("content").value;
    if(phone == EMPTY){
        showAttention("请输入手机号");
        return false;
    }
    var phoneArray = phone.split(SYMBOL_COMMA);
    for(var i=0;i<phoneArray.length;i++){
        if(!isMobilePhone(phoneArray[i])){
            showAttention("手机号[" + phoneArray[i] + "]不合法");
            return false;
        }
    }
    if(content == EMPTY){
        showAttention("请输入内容");
        return false;
    }
    var result = checkStr(content, SYMBOL_ARRAY_1);
    if (result["isSuccess"] == false) {
        showAttention("内容包含非法字符:" + result["symbol"]);
        return false;
    }
    if(content.length > SMS_CONTENT_LENGTH) {
        showAttention("短信内容大于" + SMS_CONTENT_LENGTH + "个字符");
        return false;
    }

    var words = smsDeniedWords.split(SYMBOL_SLASH);
    for(var i=0;i<words.length;i++){
        var word = words[i];
        if(content.indexOf(word) > -1){
            showAttention("你的内容中包括短信运营商屏蔽词汇：[" + word + "]，请修改提醒内容！");
            return;
        }
        if(word.indexOf(SYMBOL_BLANK)){
            word = replaceAll(word, SYMBOL_BLANK, EMPTY);
            if(content.indexOf(word) > -1){
                showAttention("你的内容中包括短信运营商屏蔽词汇：[" + word + "]，请修改提醒内容！");
                return;
            }
        }
    }

    content = "短信来自申成门窗OA系统，内容：" + content;

    if(!confirm("预览短信：\n" + content + "【申成门窗】\n确认发送吗？")){
        return false;
    }

    //ajax请求
    var SUCCESS_STR = "success";//成功编码
    $.ajax({
        type:"post",
        async:false,
        url:baseUrl + "operateSMS.do",
        data:"type=send&phone=" + phone + "&content=" + content + "&token=" + token,
        success:function (data, textStatus) {
            if ((SUCCESS_STR == textStatus) && (null != data)) {
                data = eval("(" + data + ")");
                //判请求是否成功
                if (false == data["isSuccess"]) {
                    showError(data["message"]);
                } else {
                    //请求成功
                    //showSuccess(data["message"]);
                    location.href = baseUrl + "sms.jsp?message=send sms success!";
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
 * 处理所有公司结构json串
 */
function processStructureWithJson() {
    //json串转json数组
    if(structureJsonStr != EMPTY) {
        var array = structureJsonStr.split(SYMBOL_BIT_AND);
        for(var i=0;i<array.length;i++) {
            structureArray[structureArray.length] = eval("(" + array[i] + ")");
        }
    }
}

/**
 * 处理通讯录
 */
function processContacts(){
    /**
     * 通讯录公司架构id数组：公司，部门，职位依次出现的id
     */
    //var contactsIdArray = new Array();
    //var layers = eval("({})");

    /**
     * 算多少个公司
     */
    var companies = new Array();
    for(var i=0;i<structureArray.length;i++) {
        if(structureArray[i]["type"] == STRUCTURE_TYPE_COMPANY){
            companies[companies.length] = structureArray[i]["id"];
        }
    }
    var html = EMPTY;

    /**
     * 循环每个公司
     * 1.先[递归查]找属于该公司，不属于任何部门的职位 -> 如果是公司或者部门则中断
     * 2.[递归查]属于该公司的所有部门 -> 如果是公司则中断
     * 3.循环每个部门，[递归查]找下面的职位 -> 如果是公司或者部门则中断
     */
    for(var i=0;i<companies.length;i++){
        // 遍历公司
        var company = companies[i];
        var structure = getStructureById(company);
        html += "<tr><td class='company' title='com_" + company + "' " +
            "onclick='chooseUser(this, " + structure["id"] + ")'>" + structure["name"] + "</td></tr>";

        //contactsIdArray[contactsIdArray.length] = company;//添加元素

        // 第一步
        var posArray = new Array();
        posArray = getAllPosWithOutDeptByCompany(posArray, company);
        //contactsIdArray = contactsIdArray.concat(posArray);//添加数组
        //layers["company_" + company + "_pos"] = posArray;
        for(var j=0;j<posArray.length;j++){
            structure = getStructureById(posArray[j]);
            html += "<tr><td class='position' title='com_" + company + "_pos_" + structure["id"] + "' " +
                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            var users = queryUsersByPosition(structure["id"]);
            for(var k=0;k<users.length;k++){
                html += "<tr><td class='user' title='com_" + company + "_pos_" + structure["id"] + "_user_" +
                    users[k]["id"] + "' onclick='chooseUser(this, " + users[k]["id"] + ")' style='display: none;'><img src='" + baseUrl + users[k]["headPhoto"] +
                    "' width='27px'>" + users[k]["name"] + "</td></tr>";
            }
        }

        // 第二步
        var deptArray = new Array();
        deptArray = getAllDeptsByCompany(deptArray, company);
        //layers["company_" + company + "_dept"] = deptArray;

        // 第三步
        for(var j=0;j<deptArray.length;j++){
            //contactsIdArray[contactsIdArray.length] = deptArray[j];//添加元素
            structure = getStructureById(deptArray[j]);
            var dept = structure["id"];
            html += "<tr><td class='dept' title='com_" + company + "_dept_" + dept + "' " +
                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            var posArray2 = new Array();
            posArray2 = getAllPosByDept(posArray2, deptArray[j]);
            //contactsIdArray = contactsIdArray.concat(posArray2);//添加数组
            //layers["company_" + company + "_dept_" + deptArray[j]] = posArray2;
            for(var k=0;k<posArray2.length;k++){
                structure = getStructureById(posArray2[k]);
                html += "<tr><td class='position' title='com_" + company + "_dept_" + dept + "_pos_" +
                    structure["id"] + "' onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                var users = queryUsersByPosition(structure["id"]);
                for(var l=0;l<users.length;l++){
                    html += "<tr><td class='user' title='com_" + company + "_dept_" + dept + "_pos_" +
                        structure["id"] + "_user_" + users[l]["id"] + "' onclick='chooseUser(this, " + users[l]["id"] + ")' style='display: none;'><img src='" +
                        baseUrl + users[l]["headPhoto"] + "' width='27px'>" + users[l]["name"] + "</td></tr>";
                }
            }
        }
    }
    $("#friends").html(html);
}

/**
 * 选择好友
 * @param t
 */
function chooseUser(t, id){
    var type = getType(t);
    if(type == TYPE_COM || type == TYPE_DEPT || type == TYPE_POS){
        var nodes =  $("[title^='" + t.title + "_']");//模糊查询
        var isHidden = isAllHidden(nodes);//是否都隐藏
        for(var i=0;i<nodes.length;i++){
            if(isHidden){
                //只展开下一级的元素
                if(nodes[i].title.split("_").length == t.title.split("_").length + 2){
                    nodes[i].style.display = "block";
                }
            } else {
                nodes[i].style.display = "none";
            }
        }
    }
    if(type == TYPE_USER){
        var user = getUserById(id);
        if(user["mobileTel"] == EMPTY){
            showAttention("[" + user["name"] + "]未设置电话！");
            return;
        }
        var phones = $("#phone").val();
        var phoneArray = phones.split(SYMBOL_COMMA);
        for(var i=0;i<phoneArray.length;i++){
            if(phoneArray[i] == user["mobileTel"]){
                showAttention("[" + user["name"] + "," + user["mobileTel"] + "]已添加！");
                return;
            }
        }
        if(phones != EMPTY){
            phones += SYMBOL_COMMA;
        }
        phones += user["mobileTel"];
        $("#phone").val(phones);
    }
}

/**
 * 是否都隐藏
 * @param nodes
 * @return {Boolean}
 */
function isAllHidden(nodes){
    var count = 0;
    for(var i=0;i<nodes.length;i++){
        if(nodes[i].style.display == "none"){
            count ++;
        }
    }
    return count == nodes.length;
}

/**
 * 格子类型
 */
var TYPE_COM = 1;
var TYPE_DEPT = 2;
var TYPE_POS = 3;
var TYPE_USER = 4;

/**
 * 返回格子类型
 * @param t
 * @return {*}
 */
function getType(t){
    if(t.title.indexOf("user") > -1){
        return TYPE_USER;
    }
    if(t.title.indexOf("pos") > -1){
        return TYPE_POS;
    }
    if(t.title.indexOf("dept") > -1){
        return TYPE_DEPT;
    }
    if(t.title.indexOf("com") > -1){
        return TYPE_COM;
    }
    return EMPTY;
}

/**
 * [递归查]找属于该公司，不属于任何部门的职位
 * 如果是公司或者部门则中断
 * @param array
 * @param company
 */
function getAllPosWithOutDeptByCompany(array, company){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == company) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array = getAllPosWithOutDeptByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * [递归查]属于该公司的所有部门
 * 如果是公司则中断
 * @param array
 * @param company
 */
function getAllDeptsByCompany(array, company){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == company) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_DEPT) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]!=STRUCTURE_TYPE_COMPANY) {
                array = getAllDeptsByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * 循环每个部门，[递归查]找下面的职位
 * 如果是公司或者部门则中断
 * @param array
 * @param dept
 */
function getAllPosByDept(array, dept){
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["pid"] == dept) {
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array[array.length] = structureArray[i]["id"];
            }
            if(structureArray[i]["type"]==STRUCTURE_TYPE_POSITION) {
                array = getAllPosWithOutDeptByCompany(array, structureArray[i]["id"]);
            }
        }
    }
    return array;
}

/**
 * 根据id查公司架构
 * @param id
 */
function getStructureById(id) {
    for(var i=0;i<structureArray.length;i++){
        if(structureArray[i]["id"] == id){
            return structureArray[i];
        }
    }
    return null;
}

/**
 * 根据职位来查员工
 * @param position
 */
function queryUsersByPosition(position){
    var users = new Array();
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["position"] == position){
            users[users.length] = userArray[i];
        }
    }
    return users;
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
 * 根据id查用户
 * @param id
 */
function getUserByMobilePhone(phone) {
    for(var i=0;i<userArray.length;i++){
        if(userArray[i]["mobileTel"] == phone){
            return userArray[i];
        }
    }
    return null;
}

/**
 * 点击发送短信按钮
 */
function beforeOperateSMS(){
    $("#view_sms_div").css("display", "none");
    $("#operate_sms_table").css("display", "block");
}

/**
 * 返回查看短信页面
 */
function back(){
    $("#view_sms_div").css("display", "block");
    $("#operate_sms_table").css("display", "none");
    $("#date").datepicker();
}

/**
 * 从通讯录选择
 */
function choose(){
    //设置窗口的一些状态值
    var windowStatus = "left=380,top=200,width=260,height=200,resizable=0,scrollbars=0,menubar=no,status=0,fullscreen=1";
    //在窗口中打开的页面
    var url = "chooseMultiplePhoneContact.jsp";
    var userIds = showModalDialog(url,"",windowStatus);
    if(userIds == EMPTY || userIds == undefined){
        return;
    }
    var userIdArray = userIds.split(SYMBOL_COMMA);
    var phones = EMPTY;
    for(var i=0;i<userIdArray.length;i++){
        var user = getUserById(userIdArray[i]);
        if(user["mobileTel"] == EMPTY){
            continue;
        }
        if(phones != EMPTY){
            phones += SYMBOL_COMMA;
        }
        phones += user["mobileTel"];
    }
    $("#phone").val(phones);
}