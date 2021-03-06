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
    <script type="text/javascript" src="/AlarmPlatForm/jsp/alarm/userdownHistoryList.js"></script>
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

</style>

<body>
	<%@ include file="../../head.jsp" %>
	
   <div class="banner_son">
		<img src="/AlarmPlatForm/img/img_zxjj.jpg" />
    </div>
    
  <div id="iframeheight">
  		<div class="pbox tspbox">
			<div class="ContactUs-header" style="margin: 0px auto 0;width:1200px;">
            <div class="header-tit">报警查询</div>
            <div class="header-nav"><a href="/AlarmPlatForm/skip.action?pageName=1">主页&gt;</a><a href="/AlarmPlatForm/skip.action?pageName=alarmList" class="onactive">报警查询</a></div>
        </div>
  			<div class="clear" style="height:10px;"></div>
  			<div class="sea-box" style="width:1200px;text-align:center;margin:0 auto 10px;height:36px;">
  			
	  			<div class="shengfen shengfen03" style="width:auto;height:36px;">
	  			
					<div class="span pp"> 
                    	<span class="startTitle"  style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 75px;">开始时间</span>   
                    	<input class="startTime" style="width:160px;height:34px;font-size: 14px;line-height: 32px;font-family: '微软雅黑';" type="text"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"/>
                    </div>	 
					<div class="span pp">
                    	<span class="enTitle" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 20px;">结束时间</span>   
                    	<input class="endTime" style="width:160px;height:34px;font-size: 14px;line-height: 32px;font-family: '微软雅黑';" type="text" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"/>
                    </div>  
  
				</div>
				
				<div class="span pp">
		  			<span class="myTip" style="margin-left: 15px;">是否结束</span>
             		<div class="select-container">
	                   <!--select id="userId" style="height:34px;font-size: 14px;line-height: 14px;font-family: '微软雅黑';margin-left: -5px;" class="userId" name="userId">
	                   	  <option value='' style="font-size: 14px; font-family: '微软雅黑';">全部故障</option>
	                   	  <option value='true' style="font-size: 14px; font-family: '微软雅黑';">结束故障</option>
	                   	  <option value='false' style="font-size: 14px; font-family: '微软雅黑';">未结束故障</option>
	                   </select-->
					   <div class="isout">
							<span>全部故障</span>
							<ul style="display:none;">
								<li rel=''>全部故障</li>
								<li rel='true'>结束故障</li>
								<li rel='false'>未结束故障</li>
							</ul>
					   </div>
					   <input type="hidden" value="" id="userId" name="userId">
	                </div>
    			</div>  
    			
  				<input type="button" value="搜索" class="seabtn" id="select" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑';margin-left: 20px;">
  				<div class="span pp">
                <input type="button" value="导出" class="seabtn" id="excel" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 30px;">
  				</div>
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
				.isout span{display:block;border:1px solid #ddd;height:33px;font-size: 14px;line-height:33px;font-family: '微软雅黑';width:120px;padding:0 10px;text-align:left;background:url("/AlarmPlatForm/img/pcgzs/ico2.png") 95% center no-repeat;}
				.isout{position:relative;cursor:pointer;}
				.isout ul{position:absolute;left:0;top:34px;z-index:5;background-color:#fff;border:1px solid #ddd;width:140px;border-bottom:none;}
				.isout ul li{padding:0 10px;border-bottom:1px solid #ddd;height:33px;line-height:33px;font-family: '微软雅黑';text-align:left;}
				.isout ul li.cur{ background-color:#f9f9f9;}
			</style>
            
            <script>
				$(".isout span").click(function(){
					$(".isout ul").show();
				})
				$(".isout ul li").hover(function(){
					$(this).addClass("cur");
				},function(){
					$(this).removeClass("cur");
				})
				$(".isout ul li").click(function(e){
					var val=$(this).attr("rel");
					var name=$(this).text();
					$(".isout span").text(name);
					$("#userId").val(val);
					$(".isout ul").hide();
				})
				$(".isout").mouseleave(function(){
					$(".isout ul").hide();

				})
			</script>
           <div style="min-height: 160px;text-align:center;width:1200px;margin:0 auto;" >
			   <table width="80%" border="0" cellspacing="0" cellpadding="0" class="table01" style="text-align:center;">
				   <tr>
					   <th style="white-space: nowrap;width:80px;">序号</th>
					   <th style="white-space: nowrap;">所属省份</th>
					   <th style="white-space: nowrap;">台站名称</th>
					   <th style="white-space: nowrap;">台站编号</th>
					   <th style="white-space: nowrap;">故障类型</th>
					   <th style="white-space: nowrap;">故障开始时间</th>
					   <th style="white-space: nowrap;">故障结束时间</th>
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
                  <a href="javascript:void(0);" onclick="yeshu(1)">首页</a>
                  <a href="javascript:void(0);" id="previous" onclick="shang();">上一页</a>
                  <span class="everypages"></span>
                  <a href="javascript:void(0);" onclick="next();">下一页</a>
                  <a href="javascript:void(0);" onclick="yeshu('end')">末页</a>
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
    var pageSize = 10;
	//===导出
	$('#excel').click(function() {
		var isFlag=$("#userId").val();
		var startTime=$(".startTime").val();
		var endTime=$(".endTime").val();
		var url = "/AlarmPlatForm/alarmSendSearch/dataExcel.action?isFlag="+isFlag+"&startTime="+startTime+"&endTime="+endTime+"&pageSize=100000";
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
