//所有员工Json数组
var userArray = new Array();
//所有公司结构Json数组
var structureArray = new Array();

/**
 * 初始化
 */
$(document).ready(function(){
    //处理所有员工json串
    processUserWithJson();
    //处理所有公司结构json串
    processStructureWithJson();
    //处理通讯录
    processContacts();

    $("div#userListArea ul li a").each(function(){
        $(this).click(function(){
            if($(this).attr("class")=="userSelected")
            {
                $(this).removeClass();
            }else{
                $(this).addClass("userSelected");
            }
        });
    });
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
    var html = "<ul>";

    /**
     * 循环每个公司
     * 1.先[递归查]找属于该公司，不属于任何部门的职位 -> 如果是公司或者部门则中断
     * 2.[递归查]属于该公司的所有部门 -> 如果是公司则中断
     * 3.循环每个部门，[递归查]找下面的职位 -> 如果是公司或者部门则中断
     */
    for(var i=0;i<companies.length;i++){
        //默认只取第一个公司：目前只支持申成，而且不显示申成，直接显示部门和岗位 todo
        if(i > 0){
            continue;
        }
        // 遍历公司
        var company = companies[i];
        var structure = getStructureById(company);

        //contactsIdArray[contactsIdArray.length] = company;//添加元素

        // 第一步
        var posArray = new Array();
        posArray = getAllPosWithOutDeptByCompany(posArray, company);
        //contactsIdArray = contactsIdArray.concat(posArray);//添加数组
        //layers["company_" + company + "_pos"] = posArray;
        for(var j=0;j<posArray.length;j++){
            structure = getStructureById(posArray[j]);
//            html += "<tr><td class='position' title='com_" + company + "_pos_" + structure["id"] + "' " +
//                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            html += "<li><p>" + structure["name"] + "</p>";
            var users = queryUsersByPosition(structure["id"]);
            for(var k=0;k<users.length;k++){
//                html += "<tr><td class='user' title='com_" + company + "_pos_" + structure["id"] + "_user_" +
//                    users[k]["id"] + "' onclick='chooseUser(this, " + users[k]["id"] + ")' style='display: none;'><img src='" + baseUrl + users[k]["headPhoto"] +
//                    "' width='27px'>" + users[k]["name"] + "</td></tr>";
                html += " <a href=\"javascript:chooseThis(this, " + users[k]["id"] + ");return false;\" id=\"userId" + users[k]["id"] + "\">" + users[k]["name"] + "</a>";
            }
            html += "</li>";
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
//            html += "<tr><td class='dept' title='com_" + company + "_dept_" + dept + "' " +
//                "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
            html += "<li><p>" + structure["name"] + "</p>";
            var posArray2 = new Array();
            posArray2 = getAllPosByDept(posArray2, deptArray[j]);
            //contactsIdArray = contactsIdArray.concat(posArray2);//添加数组
            //layers["company_" + company + "_dept_" + deptArray[j]] = posArray2;
            for(var k=0;k<posArray2.length;k++){
                structure = getStructureById(posArray2[k]);
//                html += "<tr><td class='position' title='com_" + company + "_dept_" + dept + "_pos_" +
//                    structure["id"] + "' onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                var users = queryUsersByPosition(structure["id"]);
                for(var l=0;l<users.length;l++){
//                    html += "<tr><td class='user' title='com_" + company + "_dept_" + dept + "_pos_" +
//                        structure["id"] + "_user_" + users[l]["id"] + "' onclick='chooseUser(this, " + users[l]["id"] + ")' style='display: none;'><img src='" +
//                        baseUrl + users[l]["headPhoto"] + "' width='27px'>" + users[l]["name"] + "</td></tr>";
                    html += " <a href=\"javascript:chooseThis(this, " + users[l]["id"] + ");return false;\" id=\"userId" + users[l]["id"] + "\">" + users[l]["name"] + "<span>" + structure["name"] + "</span></a>";
                }
            }
            html += "</li>";
        }
    }
    html += "</ul>";
    $("#userListArea").html(html);
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
 * 选择员工
 */
function chooseThis(t, userId){
    window.returnValue = userId;
    window.close();
}