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
		<img src="/AlarmPlatForm/img/img_zxjj.jpg" />
    </div>
  <div id="iframeheight">
  		<div class="pbox tspbox">
		<div class="ContactUs-header" style="margin: 0px auto 0;width:1200px;">
            <div class="header-tit">短信明细</div>
            <div class="header-nav"><a href="/AlarmPlatForm/skip.action?pageName=1">主页&gt;</a><a href="/AlarmPlatForm/skip.action?pageName=smsList" class="onactive">短信查询</a></div>
        </div>
  			<div class="clear" style="height:10px;"></div>
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
			   
			   
			  <div id="iframeheight">
		  		<div class="pbox">
		            <div class="base-info baseInfo ">
		                <div class="base-info-input">
		                    <ul class="clearfix">
		                    	<li>
		                        	<span>台站编号</span>
		                            <label><input name="siteNumber" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        
		                        <li>
		                        	<span>台站名称</span>
		                            <label><input name="siteName" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        
		                        <li>
		                        	<span>所属地区</span>
		                            <label><input name="zoneName" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        <li>
		                        	<span>故障类型</span>
		                            <label><input name="zoneName" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        <li>
		                        	<span>故障开始时间</span>
		                            <label><input name="zoneName" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        
		                        <li>
		                        	<span>故障结束时间</span>
		                            <label><input name="site_person" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        <li>
		                        	<span>发送号码</span>
		                            <label><input name="site_person" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        
		                        <li>
		                        	<span>发送时间</span>
		                            <label><input name="site_phone" type="text" value="" class="input01 require" ></label>
		                        </li>
		                      
								<li>
		                        	<span>发送内容</span>
		                            <label><input name="acupsIp" type="text" value="" class="input01 require" ></label>
		                        </li>
		                        
		                    </ul>
		                </div>
		            </div>
		            <div>
		              <button type="button" id="back" class="shengfen-button" style="margin:25px auto 0;float:none;">返回</button>
		            </div>
		        </div>
		        </div>
        
        
        
		   </div>
            <!--分页-->
            
        </div>
  </div>
  <script>
    $.ajaxSetup({ cache: false });//全局禁用缓存

	var id =  "<%=request.getParameter("id")%>";  
   	$(document).ready(function(){
   		var url=window.location.href;
		$(".base-info").addClass("base-info-back");
		$(".input01").attr("disabled","disabled");
		ajaxRequest("/AlarmPlatForm/smsSendSearch/getSMSInfoById.action?id="+id,"get",function(res){
			$(".input01").eq(0).val(res[0].siteNumber);
			$(".input01").eq(1).val(res[0].siteName);
			$(".input01").eq(2).val(res[0].zoneName);
			$(".input01").eq(3).val(res[0].description);
			$(".input01").eq(4).val(res[0].startTimeStr);
			$(".input01").eq(5).val(res[0].endTimeStr);
			$(".input01").eq(6).val(res[0].phone);
			$(".input01").eq(7).val(res[0].createTimeStr);
			$(".input01").eq(8).val(res[0].smsContent);
			$(".input01").eq(9).val(res[0].acupsIp);
		})
		
		//返回
		$("#back").click(function(){
			window.location.href="/AlarmPlatForm/jsp/sms/smsList.jsp";	
		})
   	})
  </script>
  <%@ include file="../../foot.jsp" %>
</body>
</html>
