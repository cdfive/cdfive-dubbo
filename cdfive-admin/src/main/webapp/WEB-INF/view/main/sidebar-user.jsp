<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<li class="user-panel">
	<div class="thumb">
		<img src="${webroot}/static/images/user/default-admin.png" alt="" class="img-circle" />
	</div>
	<div class="info">
		<p><sec:authentication property="principal.username"/></p>
		<ul class="list-inline list-unstyled">
			<!-- <li><a target="main-content" href="extra-profile.html" data-hover="tooltip" title="个人中心"><i class="fa fa-user"></i></a></li>
			<li><a target="main-content" href="email-inbox.html" data-hover="tooltip" title="我的邮件"><i class="fa fa-envelope"></i></a></li>
			<li><a target="main-content" href="settings.html" data-hover="tooltip" title="设置"><i class="fa fa-cog"></i></a></li> -->
			<li><a href="${webroot}/logout" data-hover="tooltip" title="退出登录"><i class="fa fa-sign-out"></i></a></li>
		</ul>
	</div>
	<div class="clearfix"></div>
</li>