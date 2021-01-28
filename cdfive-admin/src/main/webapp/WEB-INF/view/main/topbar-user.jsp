<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<li class="dropdown topbar-user" style="width:150px;">
	<a data-hover="dropdown" href="#" class="dropdown-toggle">
		<img src="${webroot}/static/images/user/default-admin.png" alt="" class="img-responsive img-circle" />&nbsp;
 		<span class="hidden-xs"><sec:authentication property="principal.username"/></span>&nbsp;<span class="caret"></span>
 	</a>
	<ul class="dropdown-menu dropdown-user pull-right">
		<!-- <li><a target="main-content" href="extra-profile.html"><i class="fa fa-user"></i>个人中心</a></li>
		<li><a target="main-content" href="email-inbox.html"><i class="fa fa-envelope"></i>我的邮件<span class="badge badge-danger">3</span></a></li>
		<li><a target="main-content" href="extra-profile.html"><i class="fa fa-cog"></i>设置</a></li>
		<li class="divider"></li>
		 -->
		<li><a href="${webroot}/logout"><i class="fa fa-sign-out"></i>退出登录</a></li>
	</ul>
</li>