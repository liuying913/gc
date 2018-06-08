package com.highfd.alarm.controller.sql;

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
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.controller.OnlyOneController;
import com.highfd.sys.service.CrawTimerService;

/**
 * 判断GNSS+路由器的状态
 */
@Component
@Controller
public class RouteController {
	
	public static int siteNumbers;
	public static Map<String,SiteInfo> siteMap=new HashMap<String,SiteInfo>();;
	public static EPRuntime runtime = null;
	public static Map<String,Boolean> alarmOnOrOffMap = new HashMap<String,Boolean>();
	
	@Autowired
	CrawTimerService crawTimerService;
	
	@Autowired
	SqlService sqlService;
	
    //通过sql的方式定时获取数据  上次执行完2秒后，继续执行
	@Scheduled(fixedDelay=1000*120)
	@RequestMapping("/sqlTask")
	public void sqlTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		//crawTimerService.insertInfoMonitorState(AlarmController.siteNumbers,siteMap.size());
        	//CrawTimerServiceImpl.itemCRUD.error("线程数量"+ AlarmController.siteNumbers+"真正运行线程"+siteMap.size());
    		System.out.println("定时通过SQL的方式获得数据"+new Date());
        	siteMap = new HashMap<String,SiteInfo>();
        	RouteController.siteNumbers=0;
        	sqlService.sqlAlarm();
    	}
	}
    
}
