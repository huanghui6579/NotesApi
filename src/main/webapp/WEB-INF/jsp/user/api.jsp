<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户基本API测试</title>
</head>
<body>
	<form action="login" method="post">
		手机号：<input type="text" name="user.mobile"><br>
		密 码：<input type="password" name="user.password"><br>
		<button type="submit">登录</button>
	</form>
	<hr>
	<form action="register" method="post">
		手机号：<input type="text" name="user.mobile"><br>
		密 码：<input type="password" name="user.password"><br>
		<button type="submit">注册</button>
	</form>
</body>
</html>