<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a id="totop" href="#"><i class="fa fa-angle-up"></i></a>
<div id="header-topbar-option-demo" class="page-header-topbar">
	<nav id="topbar" role="navigation" style="margin-bottom: 0; z-index: 2;"
		class="navbar navbar-default navbar-static-top">
		<%@ include file="topbar-header.jsp" %>
		<div class="topbar-main">
			<a id="menu-toggle" href="#" class="hidden-xs"><i class="fa fa-bars"></i></a>
			<%@ include file="topbar-search.jsp" %>
			<ul class="nav navbar navbar-top-links navbar-right mbn">
				<%@ include file="topbar-user.jsp" %>
				<%@ include file="topbar-fullscreen.jsp" %>
				<%@ include file="topbar-theme-setting.jsp" %>		
			</ul>
		</div>
	</nav>
</div>