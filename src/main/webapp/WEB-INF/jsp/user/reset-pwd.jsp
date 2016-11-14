<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>设置新密码</title>

    <!-- Bootstrap -->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/reset-pwd.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  	<nav class="navbar navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          
          <a class="navbar-brand" href="http://www.yunxinlink.com">
          	<img src="${pageContext.request.contextPath}/resources/img/ic_logo_nav.gif" alt="云信笔记" title="云信笔记" />
          </a>
        </div>
        
      </div>
    </nav>
    <div class="container content-body ">
    	<div class="panel panel-default">
    		<h2 class="pannel-header">输入您的新密码</h2>
    		<div id="alert-info" class="alert alert-info" role="alert" <c:if test='${isSuccess }'>style="display: none;"</c:if>>
    			<c:choose>
    				<c:when test="${actionResult.resultCode == 202 }">
    					该用户被禁用了，请联系客服：<a href="mailto:service@yunxinlink.com">service@yunxinlink.com</a>
    				</c:when>
    				<c:otherwise>链接已失效了，请访问&nbsp;<a href="${errorTipUrl }">这里</a>&nbsp;重新发送邮件</c:otherwise>
    			</c:choose>
    			
    		</div>
          	<c:if test="${isSuccess }">
	          	<form id="reset-form" class="form-submit" role="form" method="post">
	          		<input type="hidden" name="userSid" value="${userSid }">
	  				<div class="form-group">
	  					<label for="password">新密码</label>
	  					<input id="password" name="password" class="form-control input-lg" placeholder="新密码" required="" autofocus="" type="password">    
	  					<span for="password" class="tip-block">密码不能小于6个字符</span>
	  					<!-- <span class="glyphicon glyphicon-ok form-control-feedback"></span>-->
	  				</div>
	  				<div class="form-group vertical-space">
	  					<label for="confirmPassword">确认新密码</label>
	  					<input id="confirmPassword" name="confirmPassword" class="form-control input-lg" placeholder="确认新密码" required="" type="password">    
	  					<span for="confirmPassword" class="tip-block">密码不能小于6个字符</span>
	  					<!-- <span class="glyphicon glyphicon-ok form-control-feedback"></span>-->
	  				</div>
		    		<div class="form-group">
		    			<button id="reset-button" class="btn btn-lg btn-primary btn-block vertical-space" data-loading-text="提交中..." autocomplete="off" type="submit">重置</button>
		    		</div>
	
		    	</form>
          	</c:if>
			
		</div>
    	
    </div>
	<footer>
    	<p class="coryright">Copyright &copy; 2015-2016 云信网络科技（深圳）有限公司 ALL Rights Reserved. 粤ICP备16022995号-1</p>
    </footer>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-validate/1.15.1/jquery.validate.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/form.js"></script>

  </body>
</html>