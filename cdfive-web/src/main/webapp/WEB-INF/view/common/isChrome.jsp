<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var isChrome = true;
try {
	isChrome = window.navigator.userAgent.indexOf("Chrome") != -1;
} catch (e) {
	isChrome = false;
}
if (!isChrome) {
	window.location.href="/browser_not_support";
}
</script>