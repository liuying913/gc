package com.highfd.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.highfd.alarm.service.AlarmService;
import com.highfd.sms.controller.SmsSendController;
import com.highfd.sys.sysTool.GetXMLPath;

/**
 * 启动短信猫服务
 */
@Component
@Controller
public class OnlyOneController {
	
	/**
	 * 初始化只运行一次的开关
	 */
	public static boolean sys_run_flag = true;
	
	/**
	 * 短信猫是否正常启动
	 */
	public static String smsRunFlag = "";
	
	
	
	/**
	 * aups、dups查询接收机状态时候， 查询的时间长度
	 */
	public static String adup_dups_time = "";
	
	/**
	 * 读取Trimble数据库中数据 ， 查询的时间长度
	 */
	public static String trimble_time = "";
	
	/**
	 * 天宝数据库配置信息
	 */
	public static String SqlserverDriverClassName="";
	public static String SqlserverUrl="";
	public static String SqlserverUsername="";
	public static String SqlserverPassword="";
	public static final Logger logger = LoggerFactory.getLogger("AlarmPlatForm");//日志
	@Autowired
	AlarmService alarmService;

	@Scheduled(fixedRate = 1000*60) 
	@RequestMapping("/startService")
	public void startService() throws Exception {
		if(sys_run_flag){
			System.out.println("启动服务，只执行一次！");
			String dbpath = GetXMLPath.getXMLPath(getClass());
			adup_dups_time = GetXMLPath.getProperties(dbpath,"adup_dups_time");//aups 查询的最近时间端
			trimble_time = GetXMLPath.getProperties(dbpath,"trimble_time");    //天宝数据库查询的最近时间端
			smsRunFlag = GetXMLPath.getProperties(dbpath,"smsRunFlag");        //短信猫是否正常启动
			
			SqlserverDriverClassName = GetXMLPath.getProperties(dbpath,"SqlserverDriverClassName");        //短信猫是否正常启动
			SqlserverUrl = GetXMLPath.getProperties(dbpath,"SqlserverUrl");        //短信猫是否正常启动
			SqlserverUsername = GetXMLPath.getProperties(dbpath,"SqlserverUsername");        //短信猫是否正常启动
			SqlserverPassword = GetXMLPath.getProperties(dbpath,"SqlserverPassword");        //短信猫是否正常启动
			
			SmsSendController.smsLog.error("启动服务，只执行一次！");
			//S2.restartService();//短信服务
			alarmService.alarmService();//报警引擎
			sys_run_flag = false;
		}
	}
	
	
	
}