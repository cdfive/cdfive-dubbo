<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
<script>
function isChrome(){
	var isChrome = window.navigator.userAgent.indexOf("Chrome") !== -1 
	return isChrome;
}
$(function() {
	if (!isChrome()) {
		window.location.href="/browser_not_suppert.html";
	}
	window.location.href="/mp3/index";
});
</script>
</head>
<body>
</body>
</html>
