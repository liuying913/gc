<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>短信发布平台</title>
	<link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/css.css" />
        <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/drop-down.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/qd.css"/>
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/mypcgzs.css" />
    <script type="text/javascript" src="/AlarmPlatForm/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/extra.js"></script>
	<script type="text/javascript" src="/AlarmPlatForm/js/pageJsonList.js"></script>
	<script src="/AlarmPlatForm/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/AlarmPlatForm/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/AlarmPlatForm/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/select/select-widget-min.js"></script>
</head>

<style>
.sea-box .userId {
    float: left;
    color: #333;
    margin-right: 15px;
    width: 100px;
    height: 34px;
    line-height: 34px;

    text-indent: 0.5em;
    padding: 8px 0\9;
    box-shadow:none;
}
.userId {
    float: left;
    color: #333;
    margin-right: 15px;
    width: 100px;
    height: 22px;
    line-height: 22px;
  
    text-indent: 0.5em;
    padding: 8px 0\9;
    box-shadow: none;
}
#userId{ 
    border:1px solid #ddd !important;
	}
.sea-box select {
    -webkit-appearance: none !important;
    -moz-appearance: none !important;
    -webkit-border-radius: 0;
    background: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeD0iMTJweCIgeT0iMHB4IiB3aWR0aD0iMjRweCIgaGVpZ2h0PSIzcHgiIHZpZXdCb3g9IjAgMCA2IDMiIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDYgMyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PHBvbHlnb24gcG9pbnRzPSI1Ljk5MiwwIDIuOTkyLDMgLTAuMDA4LDAgIi8+PC9zdmc+) 100% center no-repeat #fff;
}
.sea-box .seabtn {
    width: 100px;
    height: 34px;
    float: none; 
    border: none; 
    cursor: pointer; 
    background-color: #306ab5; 
    color: #fff; 
}

</style>

<body>
	<%@ include file="../../head.jsp" %>
	
    <div class="banner_son">
		<img src="/AlarmPlatForm/img/img_zxjj.jpg" style="margin:-350px 0px;"/>
    </div>
    
  <div id="iframeheight">
  		<div class="pbox tspbox">
		<div class="ContactUs-header" style="margin: 20px auto 0;width:1200px;">
            <div class="header-tit">台站管理</div>
            <div class="header-nav"><a href="/AlarmPlatForm/skip.action?pageName=1">主页&gt;</a><a href="/AlarmPlatForm/skip.action?pageName=siteList" class="onactive">台站管理</a></div>
        </div>
  			<div class="clear" style="height:10px;"></div>
  			<div class="sea-box" style="text-align:center;margin:0px auto 10px;height:36px;">
    			<input class="searchParam" style="width:350px;height:34px;font-size: 14px;line-height: 32px;font-family: '微软雅黑';border:1px solid #ddd;" type="text"/>
  				<input type="button" value="搜索" class="seabtn" id="select" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑';margin-left: 20px;">
                <input type="button" value="导出" class="seabtn" id="excel" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 20px;">
  				
  			</div>  
			<style> 
            	.sea-box{ box-shadow:none;height:auto;width: 95%;margin-left: 28%;}
				.tspbox .shengfen03{ background-color:#fff;padding-left:0;float:left;margin-top:0;padding:0;}
				.span span{float:left;}
				.sea-box .span{width:auto;margin-top:0;}
				.shengfen03 input{height:34px;line-height:34px;margin-left:5px;margin-top:0;width:220px;}
				.sea-box .input01{width:250px;height:34px;line-height:34px;border: 1px solid #b0b0b0 !important;}
				.seabtn{height:34px;}
				.startTitle{height:34px;}
				.enTitle{height:34px;}
            </style>
            
            
           <div style="min-height: 160px;text-align:center;width: 1200px;margin: 0 auto;" >
			   <table width="80%" border="0" cellspacing="0" cellpadding="0" class="table01" style="text-align:center;">
				   <tr>
					   <th style="white-space: nowrap;">序号</th>
					   <th style="white-space: nowrap;">所属省份</th>
					   <th style="white-space: nowrap;">台站名称</th>
					   <th style="white-space: nowrap;">台站编号</th>
					   <th style="white-space: nowrap;">联系人</th>
					   <th style="white-space: nowrap;">联系方式</th>
				   </tr>
				   <tbody id="tbody">
				   </tbody>
			   </table>
		   </div>
            <!--分页-->
            <div class="newstable-content">
	            <div class="newstable-fy">
	                <div class="newstable-fy-pages">
	                    <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
	                    <a href="#" onclick="yeshu(1)">首页</a>
	                    <a href="#" id="prevpage" onclick="shang();">上一页</a>
	                    <span class="everypages">
	                    </span>
	                    <a href="#" onclick="next();">下一页</a>
	                    <a href="#" onclick="yeshu('end')">末页</a>
	                    <span>跳转至
	                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages">
	                        </select>
	                    </span>
	                </div>
	            </div>
	        </div>
        </div>
  </div>
  <script>
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var searchParam = $(".searchParam").val();
  		var url = "/AlarmPlatForm/siteUser/getSiteInfoList.action?searchParam="+searchParam;
  		ajaxRequest(url,'get',function(res){
			data = res;
			shua();
			page();
			
			
			/*修改select的默认方式*/
            $("#userId").selectWidget({
                change  : function (changes) {
                  return changes;
                },
                effect       : "slide",
                keyControl   : true,
                speed        : 200,
                scrollHeight : 250
            });
  		})
  	});
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value){
    			if(key%2!=0){
    				str=str+"<tr class='even'>"
    				+"	    <td>"+value.rm+"</td>"
    				+"	    <td>"+value.zoneName+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.smsPerson+"</td>"
    				+"	    <td>"+value.smsPhone+"</td>"
    				+"    </tr>";
    			}else{
    				str=str+"<tr>"
    				+"	    <td>"+value.rm+"</td>"
    				+"	    <td>"+value.zoneName+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.smsPerson+"</td>"
					+"	    <td>"+value.smsPhone+"</td>"
    				+"    </tr>";
    			}
		  	}
		});
		$("#tbody").html(str); 
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
		
	
	}
  	
  	
	$("#select").click(function(){
		var searchParam = $(".searchParam").val();
		ajaxRequest("/AlarmPlatForm/siteUser/getSiteInfoList.action?searchParam="+searchParam,"get",function(res){
		data = res;
          shua();
		  page();
		});
	});
	
	//===导出
	$('#excel').click(function() {
		var isFlag=$("#userId").val();
		var startTime=$(".startTime").val();
		var endTime=$(".endTime").val();
		var url = "/AlarmPlatForm/siteUser/dataExcel.action?isFlag="+isFlag+"&startTime="+startTime+"&endTime="+endTime;
		window.location.href=url;
	});
	
	
	//获得当月第一天
	function getMonthOne(){//获得今天
		var today=new Date();
		year = today.getFullYear();
		month = today.getMonth()+1;
		if(month<10){month="0"+month;}
		var iniEndTime = year+"-"+month+"-01"+" 00:00:00";
		return iniEndTime;
	}

	function getToday(){//获得今天
		var today=new Date();
		year = today.getFullYear();
		month = today.getMonth()+1;
		if(month<10){month="0"+month;}
		date = today.getDate();
		if(date<10){date="0"+date;}
		var iniEndTime = year+"-"+month+"-"+date+" 23:59:59";
		return iniEndTime;
	}
  </script>
  <%@ include file="../../foot.jsp" %>
</body>
</html>
