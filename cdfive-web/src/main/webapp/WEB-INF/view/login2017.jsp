<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统登录</title>
<%@include file="/WEB-INF/view/taglib.jsp"%>
 <link rel="stylesheet" href="/static/css/login.css"/>
<script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
<script type="text/javascript" src="/static/plugin/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/static/plugin/jqueryForm/jquery.form.js"></script>
<script type="text/javascript" src="/static/plugin/layer/layer.js"></script>
<script type="text/javascript" src="/static/js/public/api.js"></script>
<script type="text/javascript" src="/static/js/login.js"></script>
</head>
<body>
	<div class="contain">
		<div class="contain-wp" id="js-contain-wp" >
			<!-- <img src="/static/images/login/bg.jpg" /> -->
		</div>
		<div class="register-box">
			<%-- <img src="/static/image/login/logo-new.png"/>  --%>
			<form id="fromsubmit" class="register_btn" target="_self" name="regist" action="" novalidate="novalidate">
				<fieldset>
					<div style="position: relative;">
						<input placeholder="账号" name="username" type="text" required />
					</div>
					<div style="position: relative;">
						<input placeholder="密码" id="password" name="password" type="password" required />
					</div>
					<input class="submit" type="button" id="btnSubmit" value="登录" />
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>