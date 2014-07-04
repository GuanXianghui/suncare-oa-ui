// JavaScript Document Designed by Duanliquan

function resetAllBox(){
	//nav effect js
	var viewH=$(window).height();
	var viewW=$(window).width();
	var docH=$(document).height();
	var docW=$(document).width();
	if($("#SCIM_uList"))
	{
		var iListH=viewH-180;
		$("#SCIM_uList").attr("style","height:"+iListH+"px;");
	}
	if($("#left_Box"))
	{
		var lbW=viewW-502;
		var lbH=docH-100;
		$("#left_Box").attr("style","width:"+lbW+"px; height:"+lbH+"px");
	}
	if($("#right_Box"))
	{
		$("#right_Box").attr("style","width:400px");
	}
}

$(document).ready(function(){
	resetAllBox();
});

$(window).resize(function() {
	resetAllBox();
});