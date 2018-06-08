package com.highfd.alarm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.espertech.esper.client.EPRuntime;
import com.highfd.alarm.service.StreamService;
import com.highfd.alarm.strom.PMCServerSocket;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.controller.OnlyOneController;
import com.highfd.sys.service.CrawTimerService;

/**
 * 通过流的方式 定时获取数据
 */
@Component
@Controller
public class AlarmController {
	
	public static int siteNumbers;
	public static Map<String,SiteInfo> siteMap=new HashMap<String,SiteInfo>();
	public static EPRuntime runtime = null;
	public static Map<String,Boolean> alarmOnOrOffMap = new HashMap<String,Boolean>();
	
	@Autowired
	CrawTimerService crawTimerService;
	
	@Autowired
	StreamService streamService;
	
    //通过流的方式 定时获取数据
    //@Scheduled(fixedRate = 1000*10*2) 
	@RequestMapping("/PingTask")
	public void PingTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		System.out.println("定时去 获得数据"+new Date());
    		PMCServerSocket.getSS(10010);
    		//crawTimerService.insertInfoMonitorState(AlarmController.siteNumbers,siteMap.size());
        	//CrawTimerServiceImpl.itemCRUD.error("线程数量"+ AlarmController.siteNumbers+"真正运行线程"+siteMap.size());
        	siteMap = new HashMap<String,SiteInfo>();
        	AlarmController.siteNumbers=0;
    		//crawTimerService.PingTask(1,"4");//不走流的方式
        	streamService.StreamAlarm();
    	}
	}
    
}
