package com.highfd.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.highfd.alarm.service.AlarmService;
import com.highfd.sms.controller.SmsSendController;
import com.highfd.sms.smsTool.S2;
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
	
	@Autowired
	AlarmService alarmService;

	@Scheduled(fixedRate = 1000*60) 
	@RequestMapping("/startService")
	public void startService() throws Exception {
		if(sys_run_flag){
			System.out.println("启动服务，只执行一次！");
			
			smsRunFlag = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"smsRunFlag");//短信猫是否正常启动
			SmsSendController.smsLog.error("启动服务，只执行一次！");
			//S2.restartService();//短信服务
			alarmService.alarmService();//报警引擎
			sys_run_flag = false;
		}
	}
	
	
	
}