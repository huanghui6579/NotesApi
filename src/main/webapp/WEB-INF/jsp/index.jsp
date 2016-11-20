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
    <link href="http://cdn.bootcss.com/nivo-lightbox/1.3.1/nivo-lightbox.min.css" rel="stylesheet" />
    <link href="http://cdn.bootcss.com/nivo-lightbox/1.3.1/themes/default/default.min.css" rel="stylesheet" type="text/css" />
    <link href="http://cdn.bootcss.com/animate.css/2.1.1/animate.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- template skin -->
    <link id="t-colors" href="${pageContext.request.contextPath}/resources/color/default.css" rel="stylesheet"/>
    
    <!-- =======================================================
        Theme Name: Appland
        Theme URL: https://bootstrapmade.com/free-bootstrap-app-landing-page-template/
        Author: BootstrapMade
        Author URL: https://bootstrapmade.com
    ======================================================= -->
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-custom">


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
                <li class="active"><a href="#intro">首页</a></li>
                <li><a href="updateLog">更新日志</a> </li>
              </ul>
            </div>
            <!-- /.navbar-collapse -->

        </div>
        <!-- /.container -->
    </nav>
    
    <!-- Section: intro -->
    <section id="intro" class="intro">
        <div class="intro-content">
            <div class="container">
                
                <div class="row">
                    <div class="col-sm-6 col-md-6 col-lg-6">
                    <div class="wow fadeInDown" data-wow-offset="0" data-wow-delay="0.1s">
                        <img src="${pageContext.request.contextPath}/resources/img/iphone.png" class="img-responsive" alt="" />

                    </div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-6 slogan">
                        <div class="wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.2s">
                        <h2>云信笔记</h2>
                        <p class="lead">
                        图文混排、大附件添加、笔记本归档、有条的清单、云同步、大容量存储空间
                        </p>
                        <p class="lead">
                        身边的知识管理小助手，记录生活、工作的点滴
                        </p>
                        </div>
                        <div class="row">
                            <c:set var="hasVersion" value="${versionInfo != null }"></c:set>
                            <div class="buttons col-md-6">
                                <c:choose>
                                    <c:when test="${hasVersion }">
                                        <a href="${androidUrl }" class="btn btn-success  btn-lg btn-download wow fadeInLeft" data-wow-duration="1s" data-wow-delay="0.2s"><i class="fa fa-btn fa-android fa-2x"></i> 立即下载<br /> </a>
		                                    <p class="btn-desc color wow fadeInLeft" data-wow-duration="1s"  data-wow-delay="0.2s">
		                                    版本 ：<span>Ｖ<c:if test="${hasVersion }">${versionInfo.versionName }</c:if></span><br>
		                                    更新时间：<span><c:if test="${hasVersion }"><fmt:formatDate value="${versionInfo.createTime }" pattern="yyyy-MM-dd"/></c:if></span><br>
		                                    <span>适用：Android4.0.3或者更高</span>
		                                    </p>
                                    </c:when>
                                    <c:otherwise>
                                        <span  class="btn btn-success  btn-lg btn-download wow fadeInLeft" disabled="disabled" data-wow-duration="1s" data-wow-delay="0.2s"><i class="fa fa-btn fa-android fa-2x"></i> 敬请期待<br /> </span>
                                    </c:otherwise>
                                </c:choose>
                        </div>
                        <c:if test="${hasVersion }">
                            <div class="center wow fadeInRight">
                                <img class="img-responsive "  src="${pageContext.request.contextPath}/resources/img/qrcode-download.png" alt="扫描下载">
                            </div>
                        </c:if>
                    </div>                  
                </div>      
            </div>
        </div>      
        </div>
    </section>
    
    <!-- /Section: intro -->
    <div class="divider-short"></div>
    
    <section id="content1" class="home-section">
    
        <div class="container">
            <div class="row text-center heading">
                <h3>丰富的图文编辑</h3>
            </div>
            
            
            <div class="row">
                <div class="col-md-6">
                    <div class="wow fadeInLeft" data-wow-delay="0.2s">
                        <img src="${pageContext.request.contextPath}/resources/img/img-1.png" alt="" class="img-responsive" />
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="wow fadeInRight" data-wow-delay="0.3s">
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>图文混排</h5>
                            <p>
                            所见即所得的图文编排，还支持音频、视频等多种格式的编排，告别单调的文记录工作
                            </p>
                        </div>
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>多彩涂鸦</h5>
                            <p>
                            各种颜色、粗细的画笔，丰富了涂鸦的体验，享受指尖上的画画乐趣
                            </p>
                        </div>
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>便捷清单</h5>
                            <p>
                            有效的清单能提高办事效率，有条不紊的完成各项代办任务
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>
    <!-- /Section: content -->
    
    <div class="divider-short"></div>
    
    <section id="content2" class="home-section">
    
        <div class="container">
            <div class="row text-center heading">
                <h3>放心的笔记</h3>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="wow fadeInLeft" data-wow-delay="0.2s">
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>随时记录</h5>
                            <p>
                            可随时随地记录那一刻的灵感、心得，打造自己的知识宝库，并且可及时同步到云端，永久保存。
                            </p>
                        </div>
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>安心使用</h5>
                            <p>
                            可为自己的笔记加锁，有效的保护自己的隐私内容。
                            </p>
                        </div>
                        <div class="features">                          
                            <i class="fa fa-check fa-2x circled bg-skin float-left"></i>
                            <h5>快捷分享</h5>
                            <p>
                            分享是一种乐趣，可无私的将自己的一些知识记录下来，一键分享给微信好友、朋友圈、微博！
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="wow fadeInRight" data-wow-delay="0.3s">
                        <img src="${pageContext.request.contextPath}/resources/img/img-2.png" alt="" class="img-responsive" />
                    </div>
                </div>

            </div>
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
    <script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
    <script src="http://cdn.bootcss.com/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="http://cdn.bootcss.com/wow/1.1.2/wow.min.js"></script>
    <script src="http://cdn.bootcss.com/jquery-scrollTo/2.1.2/jquery.scrollTo.min.js"></script>
    <script src="http://cdn.bootcss.com/nivo-lightbox/1.3.1/nivo-lightbox.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
    
</body>

</html>
