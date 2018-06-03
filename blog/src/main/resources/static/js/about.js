layui.use([ 'element', 'jquery', 'form', 'layedit' ], function() {
	var element = layui.element;
	var $ = layui.jquery;
	var layedit = layui.layedit;
	// Hash地址的定位
	var layid = location.hash.replace(/^#tabIndex=/, '');
	if (layid == "") {
		element.tabChange('tabAbout', 1);
	}
	element.tabChange('tabAbout', layid);

	element.on('tab(tabAbout)', function(elem) {
		location.hash = 'tabIndex=' + $(this).attr('lay-id');
		if (elem.index == 1) { //只对第二个点击生效
			var w = $('.aboutinfo-figure').width();// 设置最大宽度,也可根据img的外部容器
			if (w<100){
					w=100;
			} 
			else {
				$("img").each(function() {// 如果有很多图片,使用each()遍历
				var img_w = $(this).width();// 图片宽度
				var img_h = $(this).height();// 图片高度
				if (img_w > w) {// 如果图片宽度超出指定最大宽度
					var height = (w * img_h) / img_w; // 高度等比缩放
					$(this).css({
						"width" : w,
						"height" : height
					});// 设置缩放后的宽度和高度
				}

			});
		}
		}
	});
});
systemTime();

function systemTime() {
	// 获取系统时间。
	var dateTime = new Date();
	var year = dateTime.getFullYear();
	var month = dateTime.getMonth() + 1;
	var day = dateTime.getDate();
	var hh = dateTime.getHours();
	var mm = dateTime.getMinutes();
	var ss = dateTime.getSeconds();

	// 分秒时间是一位数字，在数字前补0。
	mm = extra(mm);
	ss = extra(ss);

	// 将时间显示到ID为time的位置，时间格式形如：19:18:02
	document.getElementById("time").innerHTML = year + "-" + month + "-" + day
			+ " " + hh + ":" + mm + ":" + ss;
	// 每隔1000ms执行方法systemTime()。
	setTimeout("systemTime()", 1000);
}

// 补位函数。
function extra(x) {
	// 如果传入数字小于10，数字前补一位0。
	if (x < 10) {
		return "0" + x;
	} else {
		return x;
	}
}