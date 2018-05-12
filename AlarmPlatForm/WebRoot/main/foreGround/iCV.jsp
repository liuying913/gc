<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>GNSS数据资源共享与信息发布平台</title>
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/qd.css"/>
</head>
<body>
	<!--  ==顶部==  -->
	<%@ include file="../../head.jsp" %>
	
	
	<div class="banner_son"></div>
    <!--  ==========  -->
    <!--  ==页面主体==  -->
    <!--  ==========  -->
    <div class="CenterP-main">
    
         <div class="leftbar">
       <div class="leftbar_top"><h3>平台简介</h3></div>
        <!--左侧弹性竖菜单-->
        <div id="menu">
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_10"><a href="/AlarmPlatForm/skip.action?pageName=2" class="active">平台简介</a></h1>
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_11"><a href="/AlarmPlatForm/skip.action?pageName=21">中心简介</a></h1>
	        <!--<h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_12"><a href="/AlarmPlatForm/skip.action?pageName=22">共享理念</a></h1>-->
        </div>
		
     </div>
    
        
        <!--右边内容-->
        <div class="CenterP-right">
            <div class="CenterP-right-header">
                <div class="header-tit">平台简介</div>
                <div class="header-nav"><a href="/AlarmPlatForm/skip.action?pageName=1">主页></a><a href="/AlarmPlatForm/skip.action?pageName=2">平台简介></a><a href="/AlarmPlatForm/skip.action?pageName=2" class="onactive">平台简介</a></div>
            </div>
            <div class="CenterP-right-content">
              			国家基础地理信息中心智能报警平台:该平台实现通过对基准站各设备运行状态数据的采集，根据平台报警规则，实时智能的产生报警，并将报警以短信的形式通知相关负责人，确保在设备运行出现异常后能够得到快速及时的处理。
						<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;具体功能包括：基准站各设备运行状态数据采集、实时报警、短信发送、观测数据质量分析、综合管理等功能。
            </div>
            <img style="margin-top: 20px; width: 645px;" src="/AlarmPlatForm/img/zxjj-pic.png" />
        </div>
    </div>
    
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/AlarmPlatForm/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/AlarmPlatForm/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $("#c_"+10).addClass("on");
        $("#c_"+10).parent().css('display','block');
        $("#c_"+10).parent().parent().css('display','block');
        </script>
        <!--左侧弹性菜单结束-->
		<script>
            $("#c_"+10).addClass("on");
        </script>
</body>
</html>
