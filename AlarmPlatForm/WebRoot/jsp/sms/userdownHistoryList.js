	$(document).ready(function() {
			str = "";
			var isFlag=$("#userId").val();
	  		var startTime = getMonthOne();
	  		var endTime = getToday();
	  		$(".startTime").val(startTime);
	  		$(".endTime").val(endTime); //结束时间赋值
	  		var url = "/AlarmPlatForm/smsSendSearch/querySmsInfoList.action?startTime="+startTime+"&endTime="+endTime+"&isFlag="+isFlag+"&pageSize="+pageSize;
			ajaxRequest(url, "get", function(res) {
				var JSONObj = res;
				currentPage = JSONObj[0].currentPage;
				pageCount = JSONObj[0].pageCount;
				recordCount = JSONObj[0].recordCount;
				pageSize = JSONObj[0].pageSize;
				shua(JSONObj[0].list);
				page(JSONObj[0].list);
			});
			$(".seabtn").click(function() {
				var isFlag=$("#userId").val();
				var startTime = getMonthOne();
		  		var endTime = getToday();
				ajaxRequest("/AlarmPlatForm/smsSendSearch/querySmsInfoList.action?startTime="+startTime+"&endTime="+endTime + "&currentPage=" + currentPage+"&isFlag="+isFlag+"&pageSize="+pageSize,"get", function(res) {
					var JSONObj = res;
					currentPage = JSONObj[0].currentPage;
					pageCount = JSONObj[0].pageCount;
					recordCount = JSONObj[0].recordCount;
					pageSize = JSONObj[0].pageSize;
					shua(JSONObj[0].list);
					page(JSONObj[0].list);
				});
			});
	});
	var str = "";
	var recordCount;// 总记录条数
	var currentPage;// 当前页数
	var pageSize;// 每页显示数目
	var pageFlag = "true";
	var pageCount;// 总页数
	function page(data) {
		$(".everypages").empty();
		$("#zongyeshu")[0].innerHTML = pageCount; // 总共页数
		$("#zongshuju")[0].innerHTML = recordCount; // 一共有多少条数据
		// 默认显示的五个界面
		$("<a href='#' onclick='yeshu(this)';>").text(currentPage).appendTo(
				".everypages");
		// 下拉列表
		if (pageFlag == "true") {
			for ( var i = 1; i <= pageCount; i++) {
				$(".optionpages").append(
						"<option value=" + i + ">" + i + "</option>");// 跳转页面
			}
			pageFlag = "false";
		}
		//$(".everypages").children()[0].style.color = "red";
	}
	var flag = 0;
	function shua(data) {
		if (flag != 0) {
			page(data);
		} else {
			flag = 1;
		}
		str = "";
		for ( var key = 1; key <= data.length; key++) {
			// if( key>(currentPage-1)*pageSize && key<= currentPage*pageSize){
			if (key % 2 != 0) {
				str = str + "<tr class='even'>"
				+"	    <td>"+data[key - 1].RN+"</td>"
				+"	    <td>"+data[key - 1].siteName+"</td>"
				+"	    <td>"+data[key - 1].siteNumber+"</td>"
				+"	    <td>"+data[key - 1].zoneName+"</td>"
				+"	    <td>"+data[key - 1].description+"</td>"
				+"	    <td>"+data[key - 1].startTimeStr+"</td>"
				+"	    <td>"+data[key - 1].endTimeStr+"</td>"
				+"	    <td>"+data[key - 1].phone+"</td>"
				+"	    <td>"+data[key - 1].createTimeStr+"</td>"
				+"	    <td><a href='/AlarmPlatForm/jsp/sms/showSMSInfo.jsp?id="+data[key - 1].id+"'>查看</a></td>"
				+ "    </tr>";
			} else {
				str = str + "<tr>" 
				+"	    <td>"+data[key - 1].RN+"</td>"
				+"	    <td>"+data[key - 1].siteName+"</td>"
				+"	    <td>"+data[key - 1].siteNumber+"</td>"
				+"	    <td>"+data[key - 1].zoneName+"</td>"
				+"	    <td>"+data[key - 1].description+"</td>"
				+"	    <td>"+data[key - 1].startTimeStr+"</td>"
				+"	    <td>"+data[key - 1].endTimeStr+"</td>"
				+"	    <td>"+data[key - 1].phone+"</td>"
				+"	    <td>"+data[key - 1].createTimeStr+"</td>"
				+"	    <td><a href='/AlarmPlatForm/jsp/sms/showSMSInfo.jsp?id="+data[key - 1].id+"'>查看</a></td>"
				+ "    </tr>";
			}
			// }
		}
		$("#tbody").html(str);
		$('#iframe', parent.document).css("height", $("#iframeheight").height());
	}
	
	function searchpage(currentPage) {
		var isFlag=$("#userId").val();
		var startTime = getMonthOne();
  		var endTime = getToday();
		ajaxRequest("/AlarmPlatForm/smsSendSearch/querySmsInfoList.action?startTime="+startTime+"&endTime="+endTime + "&currentPage=" + currentPage+"&isFlag="+isFlag+"&pageSize="+pageSize, "get",
				function(res) {
					var JSONObj = res;
					currentPage = JSONObj[0].currentPage;
					pageCount = JSONObj[0].pageCount;
					recordCount = JSONObj[0].recordCount;
					pageSize = JSONObj[0].pageSize;
					shua(JSONObj[0].list);
					page(JSONObj[0].list);
				});
		$(".seabtn").click(function() {
			var isFlag=$("#userId").val();
			var startTime = getMonthOne();
	  		var endTime = getToday();
			ajaxRequest("/AlarmPlatForm/smsSendSearch/querySmsInfoList.action?startTime="+startTime+"&endTime="+endTime + "&currentPage=" + currentPage+"&isFlag="+isFlag+"&pageSize="+pageSize, "get",
					function(res) {
						var JSONObj = res;
						currentPage = JSONObj[0].currentPage;
						pageCount = JSONObj[0].pageCount;
						recordCount = JSONObj[0].recordCount;
						pageSize = JSONObj[0].pageSize;
						shua(JSONObj[0].list);
						page(JSONObj[0].list);
					});
			});
	}
	function yeshu(div) {// 跳转
		if (!isNaN(div)) {
			shub = div;
		} else {
			if (div == 'end') {
				shub = pageCount;
			} else {
				var shub = parseInt(div.innerText);
			}
		}
		currentPage = shub;
		searchpage(currentPage);
	}
	
	function next() {
		currentPage++;
		if (currentPage > pageCount) {
			currentPage = pageCount;
			return;
		}
		searchpage(currentPage);
	}
	function shang() {
		if (currentPage != 1) {
			currentPage--;
		} else {
			currentPage = 1;
			return;
		}
		searchpage(currentPage);
	}