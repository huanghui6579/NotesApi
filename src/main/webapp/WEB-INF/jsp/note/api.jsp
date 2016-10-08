<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>笔记接口</title>
</head>
<body>
	<span>添加笔记</span>
	<form action="create" method="post">
		用户sid：<input type="text" name="userSid"><br>
		笔记sid：<input type="text" name="noteInfos[0].sid"><br>
		笔记内容：<textarea name="noteInfos[0].content"></textarea><br>
		笔记类型：
		<select name="noteInfos[0].kind">
			<option value="0">文本</option>
			<option value="1">清单</option>
		</select><br>
		笔记状态：
		<select name="noteInfos[0].deleteState">
			<option value="0">正常</option>
			<option value="1">回收站</option>
			<option value="2">隐藏</option>
			<option value="3">彻底删除</option>
		</select><br>
		<button type="button">添加</button>
	</form>
</body>
</html>