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
import com.highfd.alarm.service.SqlService;
import com.highfd.common.TimeUtils;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.controller.OnlyOneController;
import com.highfd.sys.service.CrawTimerService;

/**
 * 通过sql的方式定时获取数据
 */
@Component
@Controller
public class AlarmSqlController {
	
	public static int siteNumbers;
	public static Map<String,SiteInfo> siteMap=new HashMap<String,SiteInfo>();;
	public static EPRuntime runtime = null;
	public static Map<String,Boolean> alarmOnOrOffMap = new HashMap<String,Boolean>();
	
	@Autowired
	CrawTimerService crawTimerService;
	
	@Autowired
	SqlService sqlService;
	
	
	//@Scheduled(fixedRate=2000)：   上一次开始执行时间点后2秒再次执行；  
	//@Scheduled(fixedDelay=2000)：上一次执行完毕时间点后2秒再次执行； 
	
	
    //通过sql的方式定时获取数据
    //@Scheduled(fixedRate = 1000*10*2) 
	//@RequestMapping("/sqlTask")
	public void sqlTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		//crawTimerService.insertInfoMonitorState(AlarmController.siteNumbers,siteMap.size());
        	//CrawTimerServiceImpl.itemCRUD.error("线程数量"+ AlarmController.siteNumbers+"真正运行线程"+siteMap.size());
    		System.out.println("定时通过SQL的方式获得数据"+new Date());OnlyOneController.logger.error("Route定时通过SQL的方式获得数据");
        	siteMap = new HashMap<String,SiteInfo>();
        	AlarmSqlController.siteNumbers=0;
        	sqlService.sqlAlarm();
    	}
	}
    
    //@Scheduled(fixedRate = 1000*50) 
	//@RequestMapping("/aupsTask")
	public void aupsTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		System.out.println("AUPS 开始启动时间  "+TimeUtils.getNowTime());
        	sqlService.sqlAupsAlarm();
    	}
	}
    
    //@Scheduled(fixedRate = 1000*50) 
	//@RequestMapping("/dupsTask")
	public void dupsTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		System.out.println("DUPS 开始启动时间  "+new Date());
    		sqlService.sqlDupsAlarm();
    	}
	}
    
}
