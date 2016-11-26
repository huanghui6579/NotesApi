$(document).ready(function() {

	$("#reset-form").validate({
		errorElement : 'span',
		errorClass : 'help-block',

		rules : {
			password : {
				required : true,
				minlength : 6
			},
			confirmPassword : {
				required : true,
				minlength : 6,
				equalTo : "#password"
			}
		},
		messages : {
			password : {
				required : "请输入密码",
				minlength : $.validator.format("密码不能小于{0}个字符")
			},
			confirmPassword : {
				required : "请输入确认密码",
				minlength : "确认密码不能小于6个字符",
				equalTo : "两次输入密码不一致不一致"
			}
		},
		errorPlacement : function(error, element) {
			element.next().remove();
			element.after('<span class="glyphicon glyphicon-remove-circle form-control-feedback" aria-hidden="true"></span>');
			element.closest('.form-group').append(error);
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error has-feedback');
		},
		success : function(label) {
			var el=label.closest('.form-group').find("input");
			el.next().remove();
			el.after('<span class="glyphicon glyphicon-ok-circle form-control-feedback" aria-hidden="true"></span>');
			label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler: function(form) {
			resetPassword(form);
		}

	})

	$("#send-form").validate({
		errorElement : 'span',
		errorClass : 'help-block',

		rules : {
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			email : {
				required : "请输入Email地址",
				email : "请输入正确的Email地址"
			},
		},
		errorPlacement : function(error, element) {
			element.next().remove();
			element.after('<span class="glyphicon glyphicon-remove-circle form-control-feedback" aria-hidden="true"></span>');
			element.closest('.form-group').append(error);
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error has-feedback');
		},
		success : function(label) {
			var el=label.closest('.form-group').find("input");
			el.next().remove();
			el.after('<span class="glyphicon glyphicon-ok-circle form-control-feedback" aria-hidden="true"></span>');
			label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler: function(form) { 
			var emailAddrss = $('#email').val();
			sendMail(emailAddrss);
		}

	})

	/**
	 * 重置密码
	 * @param  {[type]} userSid  [description]
	 * @param  {[type]} password [description]
	 * @return {[type]}          [description]
	 */
	function resetPassword(form) {
		var $btn = $('#reset-button').button('loading');
		var paramData = $(form).serialize();
		var isSuccess = false;
		var msg = "";
		$.ajax({
			url: 'resetPassword',
			type: 'POST',
			dataType: 'json',
			data: paramData
		})
		.done(function(data) {
			if (data) {
				var code = data.resultCode;
				switch (code) {
					case 100:	//成功
						isSuccess = true;
						msg = "密码重置成功。";
					break;
					case 203://用户不存在 
						msg = "用户不存在";
					break;
					case 202://用户被禁用
						msg = "用户被禁用";
					break;
					case 205: //两次输入的密码不一致
						msg = "两次输入的密码不一致";
					break;
					default:
						msg = "重置密码失败";
					break;

				}
			} else {
				msg = "重置密码失败";
			}
			showResetPwdResult(isSuccess, msg);
		})
		.fail(function() {
			showResetPwdResult(false, "重置密码失败");
		})
		.always(function() {
			$btn.button('reset');
		});
		
	}

	/**
	 * 显示重置密码结果的提示语
	 * @param  {Boolean} isSucces [description]
	 * @param  {[type]}  msg      [description]
	 * @return {[type]}           [description]
	 */
	function showResetPwdResult(isSuccess, msg) {
		var alertInfo = $('#alert-info');
		alertInfo.show();
		if (isSuccess) {
			$('#reset-form').remove();
			alertInfo.removeClass('alert-info').removeClass('alert-danger').addClass('alert-success');
		} else {
			alertInfo.removeClass('alert-info').addClass('alert-danger');
		}
		alertInfo.html(msg);
	}

	/**
	 * 发送邮件
	 * @param  {[type]} emailAddrss [description]
	 * @return {[type]}             [description]
	 */
	function sendMail(emailAddrss) {

		var $btn = $('#send-button').button('loading');
		var url = emailAddrss + '/forget';
		var isSuccess = false;
		var msg = "";
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json'
		})
		.done(function(data) {
			
			if (data) {
				var code = data.resultCode;
				switch (code) {
					case 100:	//成功
						isSuccess = true;
						msg = "发送成功,可前往登录邮箱。";
						var mailUrl = gotoEmail(emailAddrss);
						if (mailUrl && mailUrl.length > 0) {
							mailUrl = "http://" + mailUrl;
							msg += "<a href='" + mailUrl + "' target='_blank'>立即去登录</a>";
						}
						
					break;
					case 203://用户不存在 
						msg = "用户不存在";
					break;
					case 202://用户被禁用
						msg = "用户被禁用";
					break;
					default:
						msg = "发送失败";
					break;

				}
			} else {
				msg = "发送失败";
			}
			showSendMailResult(isSuccess, msg);
		})
		.fail(function() {
			showSendMailResult(false, "服务器请求失败");
		})
		.always(function() {
			$btn.button('reset');
		});
	}

	/**
	 * 显示发送邮件的结果
	 * @param  {Boolean} isSuccess [description]
	 * @param  {[type]}  msg       [description]
	 * @return {[type]}            [description]
	 */
	function showSendMailResult(isSuccess, msg) {
		var alertInfo = $('#alert-info');
		alertInfo.show();
		if (isSuccess) {
			$('#content-row').remove();
			alertInfo.removeClass('alert-info').removeClass('alert-danger').addClass('alert-success');
		} else {
			alertInfo.removeClass('alert-info').addClass('alert-danger');
		}
		alertInfo.html(msg);
	}
});

//功能：根据用户输入的Email跳转到相应的电子邮箱首页
function gotoEmail($mail) {
    $t = $mail.split('@')[1];
    $t = $t.toLowerCase();
    if ($t == '163.com') {
        return 'mail.163.com';
    } else if ($t == 'vip.163.com') {
        return 'vip.163.com';
    } else if ($t == '126.com') {
        return 'mail.126.com';
    } else if ($t == 'qq.com' || $t == 'vip.qq.com' || $t == 'foxmail.com') {
        return 'mail.qq.com';
    } else if ($t == 'gmail.com') {
        return 'mail.google.com';
    } else if ($t == 'sohu.com') {
        return 'mail.sohu.com';
    } else if ($t == 'tom.com') {
        return 'mail.tom.com';
    } else if ($t == 'vip.sina.com') {
        return 'vip.sina.com';
    } else if ($t == 'sina.com.cn' || $t == 'sina.com') {
        return 'mail.sina.com.cn';
    } else if ($t == 'tom.com') {
        return 'mail.tom.com';
    } else if ($t == 'yahoo.com.cn' || $t == 'yahoo.cn') {
        return 'mail.cn.yahoo.com';
    } else if ($t == 'tom.com') {
        return 'mail.tom.com';
    } else if ($t == 'yeah.net') {
        return 'www.yeah.net';
    } else if ($t == '21cn.com') {
        return 'mail.21cn.com';
    } else if ($t == 'hotmail.com') {
        return 'www.hotmail.com';
    } else if ($t == 'sogou.com') {
        return 'mail.sogou.com';
    } else if ($t == '188.com') {
        return 'www.188.com';
    } else if ($t == '139.com') {
        return 'mail.10086.cn';
    } else if ($t == '189.cn') {
        return 'webmail15.189.cn/webmail';
    } else if ($t == 'wo.com.cn') {
        return 'mail.wo.com.cn/smsmail';
    } else if ($t == '139.com') {
        return 'mail.10086.cn';
    } else {
        return '';
    }
};