<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云信笔记-首页</title>
</head>
<body>
	<form action="regist" method="post">
		手机号：<input type="text" name="mobile"><br>
		密 码：<input type="password" name="password"><br>
		<button type="submit">注册</button>
	</form>
	<hr>
	<form action="login" method="post">
		手机号：<input type="text" name="mobile"><br>
		密 码：<input type="password" name="password"><br>
		<button type="submit">登录</button>
	</form>
	<hr>
	<form action="device/registerDevice" method="post">
		手机厂商：<input type="text" name="brand"><br>
		手机型号：<input type="text" name="phoneModel"><br>
		OS：<input type="text" name="os"><br>
		OS 版本：<input type="text" name="osVersion"><br>
		设备IMEI：<input type="text" name="imei"><br>
		<button type="submit">添加设备</button>
	</form>
</body>
</html>