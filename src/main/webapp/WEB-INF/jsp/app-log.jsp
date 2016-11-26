<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="云信笔记">
    <meta name="author" content="云信网络科技">

    <title>云信笔记</title>
    
    <!-- css -->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="http://cdn.bootcss.com/animate.css/2.1.1/animate.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/log.css" rel="stylesheet">

    <!-- template skin -->
    <link id="t-colors" href="${pageContext.request.contextPath}/resources/color/default.css" rel="stylesheet"/>
    
    <!-- =======================================================
        Theme Name: Appland
        Theme URL: https://bootstrapmade.com/free-bootstrap-app-landing-page-template/
        Author: BootstrapMade
        Author URL: https://bootstrapmade.com
    ======================================================= -->
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-custom"/>


<div id="wrapper">
    
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
        <div class="container navigation">
        
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="index.html">
                    <img src="${pageContext.request.contextPath}/resources/img/ic_logo_nav.gif" alt="云信笔记" title="云信笔记" />
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
              <ul class="nav navbar-nav">
                <li><div id="cart"></div></li>
                <li><a href="${pageContext.request.contextPath}/">首页</a></li>
                <li class="active" ><a href="#intro">更新日志</a> </li>
              </ul>
            </div>
            <!-- /.navbar-collapse -->

        </div>
        <!-- /.container -->
    </nav>
    
    <!-- Section: intro -->
        <section id="intro" class="content">
          <div class="wrapper">
            <!-- <div class="light"><i></i></div>
            <hr class="line-left">
            <hr class="line-right"> -->
            <c:choose>
                <c:when test="${logList != null && logList.size() > 0 }">
                    <div class="main">
		              <h1 class="title color">云信笔记更新日志</h1>
		              <div class="head-fa"><i class="fa fa-clock-o fa-3x" aria-hidden="true"></i></div>
		              <c:forEach var="vlog" items="${logList }">
		                <div class="year">
		                    <h2><a href="#" class="color" >${vlog.year }年<i class="fa-arrow"></i></a></h2>
		                    <div class="list <c:if test='${vlog.year ne curYear }'>collapse </c:if>">
		                      <ul>
		                        <c:forEach var="version" items="${vlog.versionInfos }">
		                            <!-- The getter should be e.g. isMilestone() and EL should be written as ${version.milestone} -->
		                            <li class="cls <c:if test='${version.milestone }'>highlight </c:if> ">
		                              <p class="date"><fmt:formatDate value="${version.createTime }" pattern="MM月dd日"/></p>
		                              <p class="intro-fa "><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
		                              <p class="intro">${version.title }</p>
		                              <p class="version">V${version.versionName }&nbsp;</p>
		                              <div class="more">
		                                <c:forEach var="logItem" items="${version.subLineList }" varStatus="status">
		                                    <p>${status.index + 1}.${logItem }</p>
		                                </c:forEach>
		                              </div>
		                            </li>
		                        </c:forEach>
		                      </ul>
		                    </div>
		                  </div>
		              </c:forEach>
		            </div>
                </c:when>
                <c:otherwise>
                    <div class="center">
                        <p>暂无更新日志</p>
                    </div>
                </c:otherwise>
            </c:choose>
            
          </div>
        </section>
    
    
    <!-- /Section: content -->
    <footer>
        <div class="sub-footer">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12 col-md-12 col-lg-12">
                        <div class="copyright text-center">
                            Copyright <i class="fa fa-copyright"></i> 2015-2016 
                            <a href="http://www.yunxinlink.com" target="_blank">
                            云信网络科技（深圳）有限公司
                            </a>
                            ALL Rights Reserved. <a target="_blank" href="http://www.miitbeian.gov.cn/">粤ICP备16022995号-1</a>
                        </div>
                    </div>
                </div>  
            </div>
        </div>
    </footer>

</div>
<a href="#intro" class="scrollup" title="返回顶部"><i class="fa fa-angle-up active "></i></a>

    <!-- Core JavaScript Files -->
    <script src="http://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>     
    <script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="http://cdn.bootcss.com/wow/1.1.2/wow.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-scrollTo/2.1.2/jquery.scrollTo.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
    <script type="text/javascript">
        $(function(e) {
            $(".main .year>h2>a").click(function (e) {
                e.preventDefault();
                $(this).parents(".year").children('.list').slideToggle()/*toggleClass("close")*/;
            });
        });
    
    </script>
</body>

</html>
