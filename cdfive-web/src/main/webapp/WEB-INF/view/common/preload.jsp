<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="/static/plugin/queryloader2/queryloader2.js"></script>
<script type="text/javascript">
  window.addEventListener('DOMContentLoaded', function() {
	new QueryLoader2(document.querySelector("body"), {
		barColor: "#efefef",
		backgroundColor: "#111",
		percentage: true,
		barHeight: 1,
		minimumTime: 200,
		fadeOutTime: 1000
	});
});			
</script>