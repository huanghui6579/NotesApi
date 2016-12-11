function gopage(url, obj) {
	if(!url) {
		console.info("暂时无法使用，该功能正在开发中..." + url)
		// toastMsg("暂时无法使用，该功能正在开发中...");
		return;
	}
	$(obj).siblings().removeClass('active');
	$(obj).addClass('active');
	$.ajax({
		url: url,
		type: 'GET',
		dataType: 'html',
		contentType : "application/x-www-form-urlencoded ; charset=UTF-8",// 解决传递中文乱码的问题
	})
	.done(function(data) {
		$('#main_content').html(data);
	})
	.fail(function() {
		console.log("error");
	});
}

/**
 * 显示圆饼形图
 * @param  {[type]} pieChat [description]
 * @return {[type]}         [description]
 */
function showPieChat(pieChat) {
	//-------------
	//- PIE CHART -
	//-------------
	// Get context with jQuery - using jQuery's .get() method.
	var pieChartCanvas = pieChat.get(0).getContext("2d");
	var pieOptions = {
	  //Boolean - Whether we should show a stroke on each segment
	  segmentShowStroke: true,
	  //String - The colour of each segment stroke
	  segmentStrokeColor: "#fff",
	  //Number - The width of each segment stroke
	  segmentStrokeWidth: 2,
	  //Number - The percentage of the chart that we cut out of the middle
	  percentageInnerCutout: 50, // This is 0 for Pie charts
	  //Number - Amount of animation steps
	  // animationSteps: 100,
	  //String - Animation easing effect
	  // animationEasing: "easeOutBounce",
	  //Boolean - Whether we animate the rotation of the Doughnut
	  // animateRotate: true,
	  //Boolean - Whether we animate scaling the Doughnut from the centre
	  // animateScale: false,
	  //Boolean - whether to make the chart responsive to window resizing
	  responsive: true,
	  // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
	  maintainAspectRatio: true,
      legend: {
    	  display:false,
      },
      animation: {
			// Boolean - Whether we animate the rotation of the Doughnut
			animateRotate: true,
			// Boolean - Whether we animate scaling the Doughnut from the centre
			animateScale: false,
			easing: 'easeOutBounce',
		},
	  //String - A legend template
	  legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"
	};
	//Create pie or douhnut chart
	// You can switch between pie and douhnut using the method below.
	var data = {
    labels: [
        "Chrome",
        "IE",
        "FireFox",
        "Safari",
        "Opera",
        "Navigator"
    ],
    datasets: [
        {
            data: [700, 500, 400, 600, 300, 100],
            backgroundColor: [
                "#f56954",
                "#00a65a",
                "#f39c12",
                "#00c0ef",
                "#3c8dbc",
                "#d2d6de"
            ],
            hoverBackgroundColor: [
                "#f56954",
                "#00a65a",
                "#f39c12",
                "#00c0ef",
                "#3c8dbc",
                "#d2d6de"
            ]
        }]
	};
	//Create pie or douhnut chart
	// You can switch between pie and douhnut using the method below.
	// pieChart.Doughnut(PieData, pieOptions);
	var mDoughnutChart = new Chart(pieChartCanvas, {
	    type: 'doughnut',
	    data: data,
	    options: pieOptions
	});
}