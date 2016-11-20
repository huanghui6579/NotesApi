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
            <div class="main">
              <h1 class="title color">云信笔记更新日志</h1>
              <div class="head-fa"><i class="fa fa-clock-o fa-3x" aria-hidden="true"></i></div>
              <div  class="year">
                <h2><a href="#" class="color" >2014年<i class="fa-arrow"></i></a></h2>
                <div class="list">
                  <ul>
                    <li class="cls highlight">
                      <p class="date">3月26日</p>
                      <p class="intro-fa highlight-text"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚硬件微美图正式推出</p>
                      <p class="version">V1.2&nbsp;</p>
                      <div class="more">
                        <p>首创微信集商务、娱乐一体化多媒体智能终端机微美图打印机</p>
                        <p>营销推广活动支持导入SN码</p>
                        <p>微商城模板，支持分类列表</p>
                        <p>微商城完整支持微信支付</p>
                        <p>微商城数据统计显示顺序更正</p>
                        <p>微商城支持商品预览</p>
                        <p>微商城订单成功后，网页将收到新订单通知</p>
                        <p>幸运机活动时间延长至三个月等6个改进及Bug</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">3月19日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">全新会员卡3.0、微官网3.0、微商城2.0上线，渠道统计模块上线！</p>
                      <p class="version">v1.1&nbsp;</p>
                      <div class="more">
                        <p>会员卡手机端会员卡页面全部改版，给用户更时尚大方的体验</p>
                        <p>会员卡完全自定义会员用户信息</p>
                        <p>会员卡支持领卡手机短信验证</p>
                        <p>会员卡支持快捷充值，余额支付</p>
                        <p>会员卡支持领取优惠券，代金券，礼品券</p>
                        <p>会员卡支持开卡即送功能，包括开卡送优惠券，送积分，送代金券等等</p>
                        <p>会员卡积分支持兑换礼品券</p>
                        <p>会员卡支持给用户发送通知</p>
                        <p>微商城中添加搜索框，增加商品的模糊匹配</p>
                        <p>微商城中不同的商品详情，支持不同的配置</p>
                        <p>微商城订单文档说明文字更详尽，并同时同步到订单说明里面</p>
                        <p>微商城商品自动审核优化, 防止过滤过严</p>
                        <p>微商城列表展现页的图片支持自适应大小</p>
                        <p>微商城将图文与商品整合，提供统一的商品配置</p>
                        <p>微官网提供多套底部导航菜单</p>
                        <p>微官网的分类支持高级分类，高级分类同时也可以设置模板</p>
                        <p>微官网支持背景动画</p>
                        <p>微官网支持背景音乐</p>
                        <p>渠道统计功能上线，用户可以通过生成二维码统计各渠道新增的粉丝</p>
                        <p>美化门店的手机UI界面，并提供外链访问</p>
                        <p>修复微活动的活动封面无法更换等5个Bug</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date ">3月12日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">首家支持微信支付，多客服系统上线！</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>为公众账号的微信支付提供技术接入，通过简单配置即可使用微信支付</p>
                        <p>微信公众平台高级接口支持，通过认证的服务号可以使用多客服系统回复粉丝</p>
                        <p>微商城首页添加多套模板，详情页支持模板自定义，商城购买商品支持微信支付；商城首页图片自适应等等十多个细节改进</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">3月5日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">360全景隆重上线！</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>全新360全景模块上线，3D看房、3D看车、3D看实景，一网打尽</p>
                        <p>微官网新增24套一级模板</p>
                        <p>微官网提供全局颜色调色器</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">2月26日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微婚庆行业应用上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>提供更加友好的套餐价格</p>
                        <p>提供经典案例，并可以二维码扫描</p>
                        <p>提供更新日志，让您每周有惊喜</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">2月19日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信墙(微信大屏幕)上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>支持上墙、抽奖、投票等等功能的微信墙上线</p>
                        <p>修正一战到底等多个模块的13个Bug, 并提供7个新改进</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">2月12日</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微贺卡上线</p>
                      <p class="version"></p>
                    </li>
                    <li class="cls highlight">
                      <p class="date">1月</p>
                      <p class="intro-fa highlight-text"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚V6.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>确立每周迭代、周三发布，用户参与推动模块开发的小米模式</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="year">
                <h2><a href="#"  >2013年<i class="fa-arrow"></i></a></h2>
                <div class="list collapse">
                  <ul>
                    <li class="cls">
                      <p class="date">12月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚V5.4上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>形成完整的基础服务+互动推广+业务管理+行业应用+应用商店的服务架构</p>
                        <p>注册用户突破10万，荣获“创业之星”大赛八强</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">11月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚v5.3上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>全新渠道代理管理后台上线，更便捷更强大</p>
                        <p>荣获2013年微应用最佳服务商</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">10月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚V5.2上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>与众多行业建立合作，推出多个行业解决方案</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">9月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微俱聚V5.1上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>发力行业O2O应用，为商家提供量身定制的行业方案</p>
                      </div>
                    </li>
                    <li class="cls highlight">
                      <p class="date">8月</p>
                      <p class="intro-fa highlight-text"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">全新微俱聚平台V5.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>推出微信应用商店，为商家提供更丰富的应用选择</p>
                        <p>整合众多优秀微信应用，打造微应用分发平台</p>
                      </div>
                    </li>
                    <li class="cls highlight">
                      <p class="date">7月</p>
                      <p class="intro-fa highlight-text"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信加更名为微俱聚</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>出于品牌和商标保护的考虑，微信加平台更名</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">6月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信加V4.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>微信在线预约、更多微信互动模块和微官网模板，商家业务展现日渐丰富</p>
                        <p>签约《中国好声音》，成为其微信独家战略合作伙伴</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">5月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信加V3.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>推出微信会员卡、微信团购等业务模块，打通更多商家业务</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">4月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信加V2.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>推出微官网等微信业务模块，实现微信与商家业务对接</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">3月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">微信加V1.0上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>微信基础服务模块+微信互动推广模块上线</p>
                        <p>创造微信公众号一周涨5万粉的奇迹</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">1月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">全网第一个微信刮刮卡、大转盘上线</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>微信互动推广模块上线，用实际行动证明微信鸡汤有毒</p>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="year">
                <h2><a href="#">2012年<i class="fa-arrow"></i></a></h2>
                <div class="list collapse">
                  <ul>
                    <li class="cls">
                      <p class="date">8月</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">顿悟</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>顿悟，微信公众平台是企业移动互联网化的入口</p>
                      </div>
                    </li>
                    <li class="cls">
                      <p class="date">8月以前</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">实现一个NB的社会化APP</p>
                      <p class="version">&nbsp;</p>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="year">
                <h2><a href="#">2012年以前<i class="fa-arrow"></i></a></h2>
                <div class="list collapse">
                  <ul>
                    <li class="cls">
                      <p class="date">&nbsp;</p>
                      <p class="intro-fa"><i class="fa  fa-dot-circle-o" aria-hidden="true"></i></p>
                      <p class="intro">在企业管理市场和SAP、Oracle厮杀</p>
                      <p class="version">&nbsp;</p>
                      <div class="more">
                        <p>我不会告诉你，我们是参与全球J2EE标准制定组织成员</p>
                      </div>
                    </li>
                  </ul>
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
