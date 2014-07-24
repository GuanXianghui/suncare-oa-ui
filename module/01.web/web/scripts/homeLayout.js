// JavaScript Document Designed by Duanliquan

function resetAllBox(){
	//nav effect js
	var viewH=$(window).height();
	var viewW=$(window).width();
	var docH=$(document).height();
	var docW=$(document).width();
	if($("#SCIM_uList"))
	{
		var iListH=viewH-39;
		$("#SCIM_uList").attr("style","height:"+iListH+"px;");
	}
	if($("#left_Box"))
	{
		var lbW=viewW-522;
		var lbH=docH-120;
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