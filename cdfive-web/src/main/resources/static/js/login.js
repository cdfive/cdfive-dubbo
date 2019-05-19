$(function() {
	$('.contain').css("height", $(window).height());
	$('.js-icon-btn').click(function() {
		if ($('.js-icon-btn').hasClass('cur')) {
			$(this).removeClass('cur')
		} else {
			$(this).addClass('cur')
		}
	});
	$('input.submit').hover(function() {
		$(this).toggleClass('cur')
	});
	$('.register_btn').hover(function() {
		$(this).toggleClass('cur')
	});

	$("#btnSubmit").click(function() {
		login_submit();
	});
});

// 登录 确定
function login_submit() {
	// 禁用登录的按钮
	var option = {
		beforeSubmit : catalog_valid,
		url : LOGIN + "?r="+Math.random(),
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		type : 'POST',
		dataType : "text",
		success : function(responseText) {
			var res = eval('(' + responseText + ')');
			if (res.code != 0) {
				$('#btnSubmit').removeAttr("disabled").removeClass('btnDisplay');
				layer.alert(res.msg);
			} else {
				window.location.href = "/admin/index";
			}
		}
	};
	$("#fromsubmit").ajaxSubmit(option);
}

// 提交前验证
function catalog_valid() {
	var _fromBox = $('#fromsubmit');
	_fromBox.validate({
		rules : {
			firstname : "required",
			lastname : "required",
			username : {
				required : true,
				minlength : 2
			},
			password : {
				required : true
			},
			confirm_password : {
				required : true,
				minlength : 6,
				maxlength : 16,
				equalTo : "#password"
			}
		},
		messages: {
			password : {
				required: "请输入密码",
		        minlength: "密码长度不能小于 6 个字母",
				maxlength : "密码长度不能大于 16 个字母"
			}
		}
	});
	var _validStatus = _fromBox.valid();
	if (_validStatus) {
		$('#btnSubmit').prop('disabled', true).addClass('btnDisplay');
	}
	return _validStatus;
}

