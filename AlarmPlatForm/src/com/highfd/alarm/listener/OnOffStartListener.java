package com.highfd.alarm.listener;

import java.util.Date;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.dao.impl.AlarmDaoImpl;
import com.highfd.alarm.model.AlarmInfo;
import com.highfd.sys.controller.ContextUtils;

public class OnOffStartListener implements UpdateListener {
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {

		if (newEvents != null) {
			
			if(null==newEvents[0].get("siteNumber").toString() || "".equals(newEvents[0].get("siteNumber").toString())){return;}
			if(null==newEvents[0].get("alarmId").toString() || "".equals(newEvents[0].get("alarmId").toString())){return;}
			
			String site_AlarmType=newEvents[0].get("siteNumber").toString()+"_"+newEvents[0].get("alarmId").toString();
			//当第一次发送过来时 做处理
			if(null==AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
				//System.out.println("开始报警：-->" + newEvents[0].get("name"));	
				AlarmController.alarmOnOrOffMap.put(site_AlarmType,true);
			}else{
				//不是第一次的时候
				if(AlarmController.alarmOnOrOffMap.get(site_AlarmType)!=null && !AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
					//System.out.println("开始报警：-->" + newEvents[0].get("siteNumber"));		
					AlarmController.alarmOnOrOffMap.put(site_AlarmType, true);
				}else{
					return;
				}
			}
			
			AlarmInfo ai = new AlarmInfo();
			String alarmId = newEvents[0].get("alarmId").toString().replaceFirst("_E_", "");
			ai.setAlarmId(alarmId);//报警标示
			ai.setSiteNumber(newEvents[0].get("siteNumber").toString());//台站编号
			ai.setName(newEvents[0].get("name").toString());//报警中文名称
			//System.out.println(ai.getSiteNumber().trim()+"))))))))))"+ ai.getAlarmId().trim());
			
			try {
				AlarmDao alarmDao=(AlarmDaoImpl)ContextUtils.context.getBean("AlarmDaoImpl");
				
				String eventStartTime = alarmDao.selectEventStartTime(ai.getSiteNumber().trim(), ai.getAlarmId().trim());//判断库中有没有开始的报警
				if(eventStartTime!=null || !"".equals(eventStartTime)){
					ai.setStarttime(new Date().getTime());
					alarmDao.insertEventInfo(ai);
				}else{
					System.out.println("开始报警!!!!怎么会有   没有结束的报警呢");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
