//# sourceURL=change_password.js

$(function() {
	$("#js_txt_oldPassword").focus();
	
	$("#js_btn_submit").click(function() {
		var param = form.getData($("#form"));
		if (!param.oldPassword) {
			msg.error("原密码不能为空");
			return;
		}
		if (!param.newPassword) {
			msg.error("新密码不能为空");
			return;
		}
		if (!param.confirmPassword) {
			msg.error("确认密码不能为空");
			return;
		}
		
		sendAjax(USER_CHANGE_PASSWORD, param, function(data) {
			parent.msg.success("修改密码成功");
			parent.layer.closeAll('iframe');
		});
	});
	
	$("#js_btn_cancel").click(function() {
		parent.layer.closeAll('iframe');
	});
});