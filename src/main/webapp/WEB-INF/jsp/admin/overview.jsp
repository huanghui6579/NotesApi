<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="zh-CN">
<head>
	<title>概况</title>
	<%@ include file="/WEB-INF/jsp/admin/common-head.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">

	<!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
		今日数据
        <small>用户量</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href=""><i class="fa fa-dashboard"></i> 主页</a></li>
        <li class="active">概况</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <!-- Your Page Content Here -->
        <!-- 今日基本数据 -->
        <div class="row">
          <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-aqua">
              <div class="inner">
                <h3>150</h3>

                <p>New Orders</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-green">
              <div class="inner">
                <h3>53<sup style="font-size: 20px">%</sup></h3>

                <p>Bounce Rate</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-yellow">
              <div class="inner">
                <h3>44</h3>

                <p>用户注册人数</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="#" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-xs-6">
            <!-- small box -->
            <div class="small-box bg-red">
              <div class="inner">
                <h3>65</h3>

                <p>APP使用人数</p>
              </div>
              <div class="icon">
                <i class="ion ion-pie-graph"></i>
              </div>
              <a href="#" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
        </div>
        <!-- 今日基本数据结束 -->

        <!-- 手机品牌报表 -->
        <h2 class="page-header">手机品牌使用情况</h2>
        <div class="row">
          <div class="col-md-6">
            <div class="box  box-default">
              <div class="box-header with-border">
                <h3 class="box-title">Browser Usage</h3>

                <div class="box-tools pull-right">
                  <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body">
                <div class="row">
                  <div class="col-md-8">
                    <div class="chart-responsive">
                      <canvas id="pieChart" height="160" style="width: 257px; height: 160px;" width="257"></canvas>
                    </div>
                    <!-- ./chart-responsive -->
                  </div>
                  <!-- /.col -->
                  <div class="col-md-4">
                    <ul class="chart-legend clearfix">
                      <li><i class="fa fa-circle-o text-red"></i> Chrome</li>
                      <li><i class="fa fa-circle-o text-green"></i> IE</li>
                      <li><i class="fa fa-circle-o text-yellow"></i> FireFox</li>
                      <li><i class="fa fa-circle-o text-aqua"></i> Safari</li>
                      <li><i class="fa fa-circle-o text-light-blue"></i> Opera</li>
                      <li><i class="fa fa-circle-o text-gray"></i> Navigator</li>
                    </ul>
                  </div>
                  <!-- /.col -->
                </div>
                <!-- /.row -->
              </div>
            </div>
          </div>
        </div>
    </section>

<!-- REQUIRED JS SCRIPTS -->

<%@ include file="/WEB-INF/jsp/admin/common-body.jsp" %>
<script src="http://cdn.bootcss.com/Chart.js/2.4.0/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/main.js"></script>

<script type="text/javascript">
	$(function() {
		//显示圆饼形图
		showPieChat($("#pieChart"));
	});
	
</script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>