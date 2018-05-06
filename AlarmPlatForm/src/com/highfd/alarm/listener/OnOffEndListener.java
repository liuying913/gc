package com.highfd.alarm.listener;

import java.util.Date;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.dao.impl.AlarmDaoImpl;
import com.highfd.alarm.model.AlarmInfo;
import com.highfd.sys.controller.ContextUtils;

public class OnOffEndListener implements UpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {

		if (newEvents != null) {
			if(null==newEvents[0].get("siteNumber").toString() || "".equals(newEvents[0].get("siteNumber").toString())){return;}
			if(null==newEvents[0].get("alarmId").toString() || "".equals(newEvents[0].get("alarmId").toString())){return;}
			
			String site_AlarmType=newEvents[0].get("siteNumber").toString()+"_"+newEvents[0].get("alarmId").toString();
			///当第一次时，进行处理
			if(null==AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
				//System.out.println("结束报警：-end-->" + newEvents[0].get("name"));
				AlarmController.alarmOnOrOffMap.put(site_AlarmType,false);
				return;
			}else{
				if(AlarmController.alarmOnOrOffMap.get(site_AlarmType)!=null && AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
					AlarmController.alarmOnOrOffMap.put(site_AlarmType,false);
					//System.out.println("结束报警：-end-->" + newEvents[0].get("name"));
				}else{
					return;
				}
			}
			
			AlarmInfo ai = new AlarmInfo();
			String alarmId = newEvents[0].get("alarmId").toString().replaceFirst("_E_", "");
			ai.setAlarmId(alarmId);//报警标示
			ai.setSiteNumber(newEvents[0].get("siteNumber").toString());//台站编号
			ai.setName(newEvents[0].get("name").toString());//报警中文名称
			try {
				
				AlarmDao alarmDao=(AlarmDaoImpl)ContextUtils.context.getBean("AlarmDaoImpl");
				String eventStartTime = alarmDao.selectEventStartTime(ai.getSiteNumber().trim(), ai.getAlarmId().trim());
				if(eventStartTime!=null && !"".equals(eventStartTime)){
					ai.setEndtime(new Date().getTime());
					alarmDao.updateEventInfoEndTime(ai);
				}else{
					System.out.println(ai.getSiteNumber()+"结束报警！！！怎么会没有 开始时间的呢！！！！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
