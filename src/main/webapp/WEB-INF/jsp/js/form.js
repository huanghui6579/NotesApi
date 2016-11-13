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
			alert("submitted!");
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
			alert("submitted!");
		}

	})
});