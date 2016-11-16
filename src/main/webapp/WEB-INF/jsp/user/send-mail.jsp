<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>重置密码</title>

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
        <h2 class="pannel-header">忘记密码？</h2>
        <div id="alert-info" class="alert alert-info" role="alert" style="display: none;">
   			的话就是记得发货的师傅
   		</div>
        <div id="content-row" class="row">
          <div class="col-md-4">
            <div class="fun-tip">
              <p>请输入您注册的电子邮箱。</p>
              <p>
              	您将收到一封重置密码的邮件，可以重新设置密码。
              </p>
            </div>
          </div>
          <div class="col-md-6">
            <form id="send-form" class="form-submit" role="form" method="post">
              <div class="form-group">
                <label for="email">电子邮箱</label>
                <input id="email" name="email" value="${account }" class="form-control input-lg" placeholder="电子邮箱" required="" autofocus="" type="email">
                <!-- <span class="glyphicon glyphicon-ok form-control-feedback"></span>-->
              </div>
              <div class="form-group">
                <button id="send-button" class="btn btn-lg btn-primary btn-block vertical-space" data-loading-text="发送中..." autocomplete="off" type="submit">发送邮件</button>
              </div>

            </form>
          </div>
          
        </div>
        
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