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
 * 判断DUPS的状态
 */
@Component
@Controller
public class DupsController {
	
	public static int siteNumbers;
	public static Map<String,SiteInfo> siteMap=new HashMap<String,SiteInfo>();;
	public static EPRuntime runtime = null;
	public static Map<String,Boolean> alarmOnOrOffMap = new HashMap<String,Boolean>();
	
	@Autowired
	CrawTimerService crawTimerService;
	
	@Autowired
	SqlService sqlService;
	
	@Scheduled(fixedRate=1000*60*10)
	@RequestMapping("/dupsTask")
	public void dupsTask() throws Exception {
    	if(!OnlyOneController.sys_run_flag){//等待短信猫+报警引擎启动后再执行
    		System.out.println("DUPS 开始启动时间  "+new Date());OnlyOneController.logger.error("DUPS 开始启动时间  ");
    		sqlService.sqlDupsAlarm();
    	}
	}
    
}
