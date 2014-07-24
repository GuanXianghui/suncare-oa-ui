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
	if($("#mailList"))
	{
		var lbH=viewH-177;
		$("#mailList").attr("style","width:380px; height:"+lbH+"px");
	}
	if($("#mail_Box"))
	{
		var lbH=viewH-100;
		$("#mail_Box").attr("style","width:380px; height:"+lbH+"px");
	}
	if($("#right_Box"))
	{
		var lbW=viewW-502;
		$("#mailDetail").attr("style","width:"+lbW+"px");
	}
}

$(document).ready(function(){
	resetAllBox();
});

$(window).resize(function() {
	resetAllBox();
});