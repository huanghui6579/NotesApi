<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户基本API测试</title>
</head>
<body>
	<span>登录测试</span>
	<form action="login" method="post">
		手机号：<input type="text" name="user.mobile"><br>
		密 码：<input type="password" name="user.password"><br>
		<button type="submit">登录</button>
	</form>
	<hr>
	<span>注册测试</span>
	<form action="register" method="post">
		手机号：<input type="text" name="user.mobile"><br>
		密 码：<input type="password" name="user.password"><br>
		<button type="submit">注册</button>
	</form>
	<hr>
	<span>用户信息修改测试</span>
	<form action="modify/1561164709011390464" method="post" enctype="multipart/form-data">
		选择头像：<input type="file" name="avatarFile"><br>
		昵称：<input type="text" name="user.nickname"><br>
		<button type="submit">修改</button>
	</form>
</body>
</html>