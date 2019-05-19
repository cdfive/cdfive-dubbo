<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<link rel="stylesheet" href="/static/plugin/bootstrap/3.3.5/css/bootstrap.min.css" />
<style>
.password-info{
	width: 80%;
	height: auto;
	margin: 20px auto;
}
.form-group lable{
	line-height: 30px;
}
.r{
	float:right;
	width: 70%;
}
#newPassword-error,#rePassword-error{
	color: red;
	font-size: 12px;
	font-weight: normal;
}

.form-group .col-sm-6{
	    padding-left: 39%;
}
</style>
</head>
<body>
<div class="password-info">
	<form id="form" class="form-horizontal" role="form">
		<div class="form-group fix">
			<label for="oldPassword " class="control-label l">原密码</label>
			<div class=" r">
				<input id="js_txt_oldPassword" name="oldPassword" type="password" class="form-control"  placeholder="请输入原密码" required>
			</div>
		</div>
		<div class="form-group fix">
			<label for="newPassword " class="control-label l">新密码</label>
			<div class="r">
				<input id="js_txt_newPassword" name="newPassword" type="password" class="form-control" placeholder="请输入新密码" required>		
			</div>
		</div>
		<div class="form-group fix">
			<label for="rePassword" class="control-label l">请确认新密码</label>
			<div class="r">
				<input id="js_txt_confirmPassword" name="confirmPassword" type="password" class="form-control" placeholder="请确认新密码">	
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-6">
				<a id="js_btn_submit" class="btn btn-success">提交</a>
				<a id="js_btn_cancel" class="btn">取消</a>
			</div>
		</div>
	</form>
</div>	
<script type="text/javascript" src="/static/plugin/jquery/jquery.1.10.2.js"></script>
<script type="text/javascript" src="/static/plugin/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/plugin/artTemplate/template.js"></script>
<script type="text/javascript" src="/static/plugin/layer/layer.js"></script>
<script type="text/javascript" src="/static/js/public/api.js"></script>
<script type="text/javascript" src="/static/js/public/common.js"></script>
<script type="text/javascript" src="/static/js/admin/change_password.js"></script>
</body>
</html>