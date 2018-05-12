<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<title>国家基础地理信息中心智能报警平台</title>
	<link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/css.css"/>
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/AlarmPlatForm/css/qd.css"/>
  </head>
 <body>
 <div class="bg"></div>
 <div class="tck">
 	<div class="close"> <img src="/AlarmPlatForm/img/searchclose.png"></div>
 	<h3>用户登录</h3>
    <div class="line"></div>
    <ul>
    	<li><span>用户名：</span><input type="text" id="userName" style="border:1px solid #ccc;width:200px;"></li>
    	<li><span>密　码：</span><input type="password" id="userPwd" style="border:1px solid #ccc;width:200px;"></li>
    </ul>
    <p class="cuowu" id="msgid">用户名或密码错误！</p>
    <button id = "submit" type="submit">登 录</button>
 </div>
		<!--  ==顶部==  -->
		<jsp:include page="head.jsp" flush="true" />
		
		<!--  ==页面主体==  -->
		<div class="index-main">
			<div class="index-banner">
				<div class="mian-01 antialias"><img src="/AlarmPlatForm/img/001.jpg"/></div>
				<!--<div class="antialias"><img src="/AlarmPlatForm/img/000.jpg"/></div>
				<div class="antialias"><img src="/AlarmPlatForm/img/img-01.png"/></div>
				--><div class="main-warp">
					<!--<div class="mian-container-left"><img src="/AlarmPlatForm/img/index-left.png"/></div>
					<div class="mian-container-right"><img src="/AlarmPlatForm/img/index-right.png"/></div>
				--></div>
			</div>
                <!--banner下边的大导航-->
              <div class="link_two">
                <ul>
                  <li><a href="/AlarmPlatForm/skip.action?pageName=smsList"><img src="/AlarmPlatForm/img/index-img-16.png"/>短信查询</a></li>
                  <li><a href="/AlarmPlatForm/skip.action?pageName=alarmList"><img src="/AlarmPlatForm/img/index-img-17.png"/>报警查询</a></li>
                  <li><a href="/AlarmPlatForm/skip.action?pageName=teqcList"><img src="/AlarmPlatForm/img/index-img-18.png"/>文件查询</a></li>
                  <li><a href="/AlarmPlatForm/skip.action?pageName=siteList"><img src="/AlarmPlatForm/img/index-img-19.png"/>台站管理</a></li>
                  <li><a href="/AlarmPlatForm/skip.action?pageName=5"><img src="/AlarmPlatForm/img/index-img-20.png"/>联系我们</a></li>
                  <li style="width:1px; padding:0px;"><a href="#">&nbsp;</a></li>
                </ul>
              </div>
            
            
            <!--正文-->
            <div class="zhongjian">
               <div class="content">
                  <div class="zxdt">
                    <h3>短信报警<a href="/AlarmPlatForm/skip.action?pageName=3"><img src="/AlarmPlatForm/img/more.png" /></a></h3>
                    <ul id="newswenzi"></ul>
                  </div>
                  
                  <div class="zxjj">
                     <h3>平台简介<a href="/AlarmPlatForm/skip.action?pageName=2"><img src="/AlarmPlatForm/img/more.png" /></a></h3>
                     <div class="zxjjwz">
                     　　<div class="zxjjwzimg"><img src="/AlarmPlatForm/img/index-img-08.png"/></div>
                         <div class="zxjjfont">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该平台实现通过对基准站各设备运行状态数据的采集，根据平台报警规则，实时智能的产生报警，并将报警以短信的形式通知相关负责人，确保在设备运行出现异常后能够得到快速及时的处理。
							<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;具体功能包括：基准站各设备运行状态数据采集、实时报警、短信发送、观测数据质量分析、综合管理等功能。
						</div>


                     </div>
                  </div>
               </div>
            </div>
            <!--end 中间-->
		</div>
		<!--  = 底部 =  -->
		<jsp:include page="foot.jsp" flush="true" />
		<script src="/AlarmPlatForm/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
		<script src="/AlarmPlatForm/js/new_file.js" type="text/javascript" charset="utf-8"></script>
        <script src="/AlarmPlatForm/js/sjsq.js" type="text/javascript" ></script>
        <script src="/AlarmPlatForm/js/dengl.js" type="text/javascript" ></script>
        <script type="text/javascript" src="/AlarmPlatForm/js/extra.js"></script>
        <script type="text/javascript" src="/AlarmPlatForm/js/common/popup/commonPopup.js"></script>

		 <script>
		 	 $.ajaxSetup({ cache: false });//全局禁用缓存
		     $(document).ready(function(){
		         ajaxRequest("/AlarmPlatForm/news/getNews30DayFlag.action?type=1&json","get",function(res) {
		             data = res;
		             var News = data.newsList[0].News;
		             var str = "";
		             
		             var newNumbers = News.length;
		             if(News.length>=7){newNumbers=7;}
		             
		             for( var i=0;i<newNumbers;i++){
		            	 
		            	 var title = News[i].title;
		                 if(title.length>22){
		                	 title= title.substring(0,22)+"...";
		                 }
		                 
		                 if(News[i].flags.indexOf('in30')>-1){
		                 	str +=" <li><span>"+News[i].time+"</span><a href='/AlarmPlatForm/news/newsDetailToJsp.action?id="+News[i].id+"'>"+title+"<img src='/AlarmPlatForm/img/new.jpg' /></a></li>";
		                 }else{
		                 	str +=" <li><span>"+News[i].time+"</span><a href='/AlarmPlatForm/news/newsDetailToJsp.action?id="+News[i].id+"'>"+title+"</a></li>";
		                 }
		             }
		             $("#newswenzi").append( str );
		         });
		     });
		 </script>
	</body>
</html>
