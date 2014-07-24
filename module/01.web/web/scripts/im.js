// JavaScript Document Designed by Duanliquan

$(document).ready(function(){
    //处理所有员工json串
    processUserWithJson();
    //处理所有公司结构json串
    processStructureWithJson();
    //处理通讯录
    processContacts();

	//nav effect js
	var viewH=$(window).height();
	$("div#SCIM_uList ul li").each(function(){
		$(this).mouseenter(function(){
			//$("#user_Card").fadeOut();
			//To do here
			//change the content of #user_Card

            if(this.id.indexOf("group") > -1){
                $("#user_Card").fadeOut();
                return;
            }

            var userId = this.id.split("_")[2];
            var user = getUserById(userId);

			$("#user_Card_Head img").attr("src",user["headPhoto"]);
			$("#uc_name").text(user["name"]);
			$("#uc_post").text(user["positionName"]);
			$("#uc_phone").text(user["officeTel"]==EMPTY?"暂无":user["officeTel"]);
			$("#uc_mobile").text(user["mobileTel"]==EMPTY?"暂无":user["mobileTel"]);
			
			//end
			var position = $(this).offset();
			var scrollH= $(this).scrollTop();
			var topH=position.top-scrollH;
			if(viewH-topH>160)
			{
				$("#user_Card").attr("style","top:"+(topH-100)+"px;");
			}else{
				topH-=106;
				$("#user_Card").attr("style","top:"+(topH-100)+"px;");
			}
			$("#user_Card").fadeIn();
		});
        $(this).click(function(){
            if(this.id.indexOf("group") == -1){
                return;
            }
            var html = $(this).html();
            if(html.indexOf("+") == 0){
                jQuery("li[id^='user_" + this.id.split("_")[1] + "_']").css("display", "block");
                $(this).html("-" + html.substring(1));
            } else {
                jQuery("li[id^='user_" + this.id.split("_")[1] + "_']").css("display", "none");
                $(this).html("+" + html.substring(1));
            }
            $("#SCIM_uList").mCustomScrollbar("update");//更新滚动条
        });
	});
	$("#sc_IM").mouseleave(function(){
		$("#user_Card").fadeOut();
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
            // 遍历公司
            var company = companies[i];
            var structure = getStructureById(company);
            //公司不显示
            //html += "<tr><td class='company' title='com_" + company + "' " +
            //        "onclick='chooseUser(this, " + structure["id"] + ")'>" + structure["name"] + "</td></tr>";

            //contactsIdArray[contactsIdArray.length] = company;//添加元素

            // 第一步
            var posArray = new Array();
            posArray = getAllPosWithOutDeptByCompany(posArray, company);
            //contactsIdArray = contactsIdArray.concat(posArray);//添加数组
            //layers["company_" + company + "_pos"] = posArray;
            for(var j=0;j<posArray.length;j++){
                structure = getStructureById(posArray[j]);
                //html += "<tr><td class='position' title='com_" + company + "_pos_" + structure["id"] + "' " +
                //        "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                html += "<li class=\"group\" id=\"group_" + structure["id"] + "\">+" + structure["name"] + "</li>";
                var users = queryUsersByPosition(structure["id"]);
                for(var k=0;k<users.length;k++){
                    //html += "<tr><td class='user' title='com_" + company + "_pos_" + structure["id"] + "_user_" +
                    //        users[k]["id"] + "' onclick='chooseUser(this, " + users[k]["id"] + ")' style='display: none;'><img src='" + baseUrl + users[k]["headPhoto"] +
                    //        "' width='27px'>" + users[k]["name"] + "</td></tr>";
                    html += "<li id=\"user_" + structure["id"] + "_" + users[k]["id"] + "\" style=\"display:none\">" +
                        "<a href=\"user.jsp?id=" + users[k]["id"] + "\" target=\"_blank\">" +
                        "<img src=\"" + users[k]["headPhoto"] + "\" /></a><span>" + users[k]["name"] + "</span></li>";
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
                //html += "<tr><td class='dept' title='com_" + company + "_dept_" + dept + "' " +
                //        "onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                html += "<li class=\"group\" id=\"group_" + structure["id"] + "\">+" + structure["name"] + "</li>";
                var posArray2 = new Array();
                posArray2 = getAllPosByDept(posArray2, deptArray[j]);
                //contactsIdArray = contactsIdArray.concat(posArray2);//添加数组
                //layers["company_" + company + "_dept_" + deptArray[j]] = posArray2;
                for(var k=0;k<posArray2.length;k++){
                    structure = getStructureById(posArray2[k]);
                    //部门下面直接显示人员，不现实岗位
                    //html += "<tr><td class='position' title='com_" + company + "_dept_" + dept + "_pos_" +
                    //        structure["id"] + "' onclick='chooseUser(this, " + structure["id"] + ")' style='display: none;'>" + structure["name"] + "</td></tr>";
                    var users = queryUsersByPosition(structure["id"]);
                    for(var l=0;l<users.length;l++){
                        //html += "<tr><td class='user' title='com_" + company + "_dept_" + dept + "_pos_" +
                        //        structure["id"] + "_user_" + users[l]["id"] + "' onclick='chooseUser(this, " + users[l]["id"] + ")' style='display: none;'><img src='" +
                        //        baseUrl + users[l]["headPhoto"] + "' width='27px'>" + users[l]["name"] + "</td></tr>";
                        html += "<li id=\"user_" + dept + "_" + users[l]["id"] + "\" style=\"display:none\">" +
                            "<a href=\"user.jsp?id=" + users[l]["id"] + "\" target=\"_blank\">" +
                            "<img src=\"" + users[l]["headPhoto"] + "\" /></a><span>" + users[l]["name"] + "</span></li>";
                    }
                }
            }
        }
        html += "</ul>";
        $("#SCIM_uList").html(html);
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
});
