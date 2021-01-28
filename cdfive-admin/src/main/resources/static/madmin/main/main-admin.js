$(function() {
	pageLoaded();
	// makeSessionNeverExpired();
});

function pageLoaded()
{
	var url = window.location.href;
	var idx = url.lastIndexOf("#");
	
	var pageUrl;
	if(idx < 0) pageUrl = "welcome";
	else
	{
		var suffix = url.substring(idx+1, url.length);
		if(suffix.length <= 0) pageUrl = "welcome";
		// else pageUrl = suffix+".do";
		else pageUrl = suffix;
	}
	pageUrl = pageUrl.split("_").join("/");
	menuopen(pageUrl);
	
	if($(window).width() >= 768)
	{
		var a = $("#side-menu a[href=\"javascript:menuopen('"+pageUrl+"')\"]");
		var buff = a.parent().parent();
		while(buff.hasClass("nav"))
		{
			buff.addClass("in");
			buff = buff.parent().parent();
		}
	}
}

function menuopen(url)
{
	// var suffix = url.substring(0, url.lastIndexOf("."))
	var suffix = url;
	suffix = suffix.split("/").join("_");
	window.location.href = "#"+suffix;
	
	var a = $("#side-menu a[href=\"javascript:menuopen('"+url+"')\"]");
	$("#side-menu li.active").removeClass("active");
	a.parent("li").addClass("active");
	
	$(".page-header .page-title").text(a.text());
	
	var ul = a.parent("li").parent("ul");
	if(ul.hasClass("nav-third-level"))
	{
		menuBreadcrumb(a, 6);
		menuBreadcrumb(ul.prev("a"), 4);
		menuBreadcrumb(ul.parent("li").parent("ul").prev("a"), 2);
	}
	else if(ul.hasClass("nav-second-level"))
	{
		var breadcrumb = $(".breadcrumb");
		breadcrumb.children("li:eq(6)").hide();
		breadcrumb.children("li:eq(5)").hide();
		
		menuBreadcrumb(a, 4);
		menuBreadcrumb(ul.prev("a"), 2);
	}
	else
	{
		var breadcrumb = $(".breadcrumb");
		breadcrumb.children("li:eq(6)").hide();
		breadcrumb.children("li:eq(5)").hide();
		breadcrumb.children("li:eq(4)").hide();
		breadcrumb.children("li:eq(3)").hide();
		
		menuBreadcrumb(a, 2);
	}
	
	if($(window).width() < 768 && $("#sidebar .sidebar-collapse").hasClass("in"))
	{
		$("#topbar .navbar-toggle").click();
	}
	
	menuLoad(url);
}

function menuLoad(url)
{
	var tempUrl = url;
	if(url == "welcome"){
		tempUrl = webroot + "/" + url;
	} else {
		if(url.indexOf("https:") == 0 || url.indexOf("http:") == 0){//包含有
			tempUrl = url;
		} else if (url.indexOf("https:") == -1 || url.indexOf("http:") == -1){//不包含
			tempUrl = webroot + url;
		}
	}
	$.ajax({
		type: "GET",
		url: tempUrl,
		dataType: 'html',
		success: function(data)
		{
			$("#page-wrapper .page-content").html(data);
		},
		error:function (xmlHttp, textStatus, errorThrown) 
		{
			if(xmlHttp.status == 0)
			{
			}
			if(xmlHttp.status == 401)
			{
				window.location.href = webroot + "/";
			}
			else
			{ 
				$("#page-wrapper .page-content").html("<div id='error-page'>"+ xmlHttp.responseText+ "</div>");
			}
		}
	});
}

function menuBreadcrumb(a, idx)
{
	var breadcrumb = $(".breadcrumb");
	var bli = breadcrumb.children("li:eq("+idx+")");
	var blia = bli.children("a");
	blia.text(a.children("span:eq(0)").text());
	blia.attr("href", a.attr("href"));
	bli.show();
	breadcrumb.children("li:eq("+(idx-1)+")").show();
}
// function makeSessionNeverExpired(){
// 	$.post("/logout?j_username=admin&j_password=admin&_" + new Date().getTime());
// 	setTimeout(makeSessionNeverExpired, 1000*60);
// }
