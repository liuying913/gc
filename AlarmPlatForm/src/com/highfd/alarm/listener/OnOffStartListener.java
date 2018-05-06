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
			//����һ�η��͹���ʱ ������
			if(null==AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
				//System.out.println("��ʼ������-->" + newEvents[0].get("name"));	
				AlarmController.alarmOnOrOffMap.put(site_AlarmType,true);
			}else{
				//���ǵ�һ�ε�ʱ��
				if(AlarmController.alarmOnOrOffMap.get(site_AlarmType)!=null && !AlarmController.alarmOnOrOffMap.get(site_AlarmType)){
					//System.out.println("��ʼ������-->" + newEvents[0].get("siteNumber"));		
					AlarmController.alarmOnOrOffMap.put(site_AlarmType, true);
				}else{
					return;
				}
			}
			
			AlarmInfo ai = new AlarmInfo();
			String alarmId = newEvents[0].get("alarmId").toString().replaceFirst("_E_", "");
			ai.setAlarmId(alarmId);//������ʾ
			ai.setSiteNumber(newEvents[0].get("siteNumber").toString());//̨վ���
			ai.setName(newEvents[0].get("name").toString());//������������
			//System.out.println(ai.getSiteNumber().trim()+"))))))))))"+ ai.getAlarmId().trim());
			
			try {
				AlarmDao alarmDao=(AlarmDaoImpl)ContextUtils.context.getBean("AlarmDaoImpl");
				
				String eventStartTime = alarmDao.selectEventStartTime(ai.getSiteNumber().trim(), ai.getAlarmId().trim());//�жϿ�����û�п�ʼ�ı���
				if(eventStartTime!=null || !"".equals(eventStartTime)){
					ai.setStarttime(new Date().getTime());
					alarmDao.insertEventInfo(ai);
				}else{
					System.out.println("��ʼ����!!!!��ô����   û�н����ı�����");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
