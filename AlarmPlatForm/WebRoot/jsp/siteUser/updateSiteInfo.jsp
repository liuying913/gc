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
        
	<script type="text/javascript" src="/AlarmPlatForm/js/layer/layer.js"></script>
	
    <script type="text/javascript" src="/AlarmPlatForm/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/extra.js"></script>
	<script type="text/javascript" src="/AlarmPlatForm/js/pageJsonList.js"></script>
	<script src="/AlarmPlatForm/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/AlarmPlatForm/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/AlarmPlatForm/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/select/select-widget-min.js"></script>
    <script type="text/javascript" src="/AlarmPlatForm/js/commonPopup.js"></script>
    <style type="text/css">
    	.myInfo ul li {width: 500px;;}
    	.myInfo ul li span {width: 60px;}
    	.myInfo ul {margin: 10px 0 0 290px;}
		.tsfun-box a:hover {
			color: #fff;
		}
    </style>
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
			            <div class="base-info baseInfo">
			                <div class="base-info-input">
			                    <ul  class="clearfix">
			                    
			                        <li>
				                    	<span><b class="requireFlag">*</b>台站编号</span>
				                        <label><input id="siteNumber2" name="siteNumber2" type="text" value="" class="input01 require"  disabled="disabled"></label>
										<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写用户名</p></div>
				                    </li>
                    
                    				<li>
				                    	<span><b class="requireFlag">*</b>台站名称</span>
				                        <label><input id="siteName" name="siteName" type="text" value="" class="input01 require" ></label>
										<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写台站名称</p></div>
				                    </li>
				                    
			                       
			                       <li>
			                        	<span><b class="requireFlag">*</b>所属地区</span>
			                            <label>
											<select id="zoneCode" name="zoneCode" class="input01 require" err="请选择所属地区">
											</select>
										</label>
			                        </li>
			                        
									<li>
			                        	<span><b class="requireFlag">*</b>联系人</span>
			                            <label><input name="site_person" type="text" value="" class="input01 require"></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写联系人</p></div>
			                        </li>
									
								
			                        <li>
			                        	<span><b class="requireFlag">*</b>联系方式</span>
			                             <label><input name="site_phone" type="text" value="" class="input01 require"></label>
			                             <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写联系方式</p></div>
			                        </li>
			                        
			                        <li>
			                        	<span><b class="requireFlag">*</b>联系人单位</span>
			                            <label><input name="siteUnit" type="text" value="" class="input01 require"></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写联系人单位</p></div>
			                        </li>
									<li>
			                        	<span><b class="requireFlag">*</b>路由器IP</span>
			                            <label><input name="routerIp" type="text" value="" class="input01 require"></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写路由器IP</p></div>
			                        </li>
			                        
			                        <li>
			                        	<span><b class="requireFlag">*</b>接收机IP</span>
			                            <label><input name="gnssIp" type="text" value="" class="input01 require"></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写GNSS接收机IP</p></div>
			                        </li>
			                        
			                        <li>
			                        	<span><b class="requireFlag">*</b>直流UPS IP</span>
			                            <label><input name="dcupsIp" type="text" value="" class="input01 require" ></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写直流UPS IP</p></div>
			                        </li>
			                        
			                        <li>
			                        	<span><b class="requireFlag">*</b>交流UPS IP</span>
			                            <label><input name="acupsIp" type="text" value="" class="input01 require" ></label>
			                            <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/AlarmPlatForm/img/pcgzs/success.png"></p></div>
										<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/AlarmPlatForm/img/pcgzs/error.png">请填写交流UPS IP</p></div>
			                        </li>
			                        
			                    </ul>
			                </div>
			            </div>
						<div class="warp-button" style="text-align: center">
							<br><br>
							<button type="button" id="save" class="shengfen-button" style="width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/AlarmPlatForm/img/s1.png"/>-->保存</button>
							<button type="button" id="back" class="shengfen-button" style="margin-left: 15px;width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/AlarmPlatForm/img/s2.png"/>-->返回</button>
						</div>
			        </div>
			    </div>
		        </div>
        
        
        
		   </div>
            <!--分页-->
            
        </div>

  <script>
		var siteNumber =  "<%=request.getParameter("siteNumber")%>";  
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
    	$("#save").click(function(){
    		i=0;
			str="1=1";
			$(".require").each(function(e){
				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					i=1;
				}else{
					if(e==rlen-1&&i==0){
						$(".input01").each(function(a) {
							$(this).parents("li").find(".errState").hide();
						    $(this).parents("li").find(".sucuState").show();
							str=str+"&"+$(this).prop("name")+"="+$(this).val();//str=str+"/"+$(this).name+':'+$(this).attr("name")+" "+$(this).prop("name");
						  	if(a==len-1){
						  	    str = encodeURI(str);
								var URL="/AlarmPlatForm/siteUser/updateSiteInfo.action?siteNumber="+siteNumber+"&"+str;
								ajaxRequest(URL,"get",function(res){
									if(res.date[0].code==1){
										 init("保存成功",0);
										 $(".confirm",window.parent.document).click( function(){
											window.location.href="/AlarmPlatForm/jsp/siteUser/siteUserList.jsp";	
 		                                 })
										window.location.href="/AlarmPlatForm/jsp/siteUser/siteUserList.jsp";	
									}else{
										init( res.date[0].msg ,1 );
										//alert(res.date[0].msg);	
									}
								})
								
							}  
						});	
					}
				}
			})	
			
		})
		
		
		
		
		$(document).ready(function(){
		
			//地区下拉列表
			ajaxRequest("/AlarmPlatForm/siteUser/getZoneInfoList.action","get",function(res){
				 $.each(res, function(key, value) {
					 $("#zoneCode").append("<option value='"+value.zoneCode+"'>"+value.zoneName+"</option>");
				 });
			})
			
			
			$(".userName").attr("disabled","disabled");
			ajaxRequest("/AlarmPlatForm/siteUser/getBaseSiteInfoById.action?siteNumber="+siteNumber,"get",function(res){
				$(".input01").eq(0).val(res[0].siteNumber);
				$(".input01").eq(1).val(res[0].siteName);
				$(".input01").eq(2).val(res[0].zoneCode);
				$(".input01").eq(3).val(res[0].site_person);
				$(".input01").eq(4).val(res[0].site_phone);
				$(".input01").eq(5).val(res[0].siteUnit);
				$(".input01").eq(6).val(res[0].routerIp);
				$(".input01").eq(7).val(res[0].gnssIp);
				$(".input01").eq(8).val(res[0].dcupsIp);
				$(".input01").eq(9).val(res[0].acupsIp);
			})
		
			//返回
			$("#back").click(function(){
				var url="/AlarmPlatForm/jsp/siteUser/siteUserList.jsp";
				window.location.href=url;	
			})
		
		
    	})
    	
  </script>
  <%@ include file="../../foot.jsp" %>
</body>
</html>
