// JavaScript Document Designed by Duanliquan

$(document).ready(function(){
	//nav effect js
	$("div#menu ul li").each(function(){		
		$(this).mouseenter(function(){
            //$(this).children(".menu_sub").fadeIn();
            //$(this).children(".menu_sub").show();
            $(this).children(".menu_sub").fadeIn(200);
		});
		$(this).mouseleave(function(){
            //$(this).children(".menu_sub").fadeOut();
            //$(this).children(".menu_sub").hide();
            $(this).children(".menu_sub").fadeOut(200);
		});
	});	
});
