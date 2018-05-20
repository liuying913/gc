package com.highfd.alarm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.listener.OnOffEndListener;
import com.highfd.alarm.listener.OnOffStartListener;
import com.highfd.alarm.model.AlarmInfo;
import com.highfd.alarm.service.AlarmService;
import com.highfd.common.PageInfo;
import com.highfd.siteUser.model.AupsState;
import com.highfd.siteUser.model.DupsState;
import com.highfd.siteUser.model.SiteInfo;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmDao alarmDao;
	
	public void alarmService() throws Exception {
    	System.out.println(new Date());
    	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
        String product = SiteInfo.class.getName();
        String dupsProduct = DupsState.class.getName();
        String aupsProduct = AupsState.class.getName();
        EPAdministrator admin = epService.getEPAdministrator(); 
        //select '_E_ROUTE_LINK_ERROR' as eventId,a.ID as varId,a.value,a.timestamp,-6 from pattern[every a=ria.runtime.common.message.RIAMessage(ID like '%_DevNS' and Integer.valueOf(value.toString()) in (22,21))]
        //String epl2 = "select '_E_Route_error' as alarmId,A.siteNumber as siteNumber,   '路由器报警' as name from " + product + "(siteState=2).win:length(1) as  A ";
        String epl = "select '_E_ROUTE_LINK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '通信异常' as name from pattern[every a=" + product + "(routeState=22)]  ";
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new OnOffStartListener());
        
        String epl2 = "select '_E_ROUTE_LINK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '通信异常' as name from pattern[every a=" + product + "(routeState=11)]  ";
        EPStatement state2 = admin.createEPL(epl2);  
        state2.addListener(new OnOffEndListener());  
        
        //发生报警
        String epl3 = "select '_E_GNSS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '接收机中断' as name from pattern[every a=" + product + "(routeState=12)]  ";
        EPStatement state3 = admin.createEPL(epl3);  
        state3.addListener(new OnOffStartListener());
        
        String epl4 = "select '_E_GNSS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '接收机中断' as name from pattern[every a=" + product + "(routeState=11)]  ";
        EPStatement state4 = admin.createEPL(epl4);  
        state4.addListener(new OnOffEndListener()); 
        
        //发生报警
        String   epl5 = "select '_E_ACUPS_WORK_ERROR' as alarmId, A.siteNumber as siteNumber, '交流UPS报警' as name from " + product + ".win:length(1) as  A ," + aupsProduct + ".win:length(1) as B where A.siteNumber=B.siteNumber and A.routeState<=12 and B.aupsState=12 ";
        //String epl5 = "select '_E_ACUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber, '交流UPS报警' as name from pattern[every a=" + product + "(aupsState=12)]  ";
        EPStatement state5 = admin.createEPL(epl5);  
        state5.addListener(new OnOffStartListener());
        
        String   epl6 = "select '_E_ACUPS_WORK_ERROR' as alarmId, A.siteNumber as siteNumber,  '交流UPS报警' as name from " + product + ".win:length(1) as  A ," + aupsProduct + ".win:length(1) as B where A.siteNumber=B.siteNumber and A.routeState<=12 and B.aupsState=11 ";
        //String epl6 = "select '_E_ACUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber , '交流UPS报警' as name from pattern[every a=" + product + "(aupsState=11)]  ";
        EPStatement state6 = admin.createEPL(epl6);  
        state6.addListener(new OnOffEndListener()); 
        
        //发生报警
        String   epl7 = "select '_E_DCUPS_WORK_ERROR' as alarmId, A.siteNumber as siteNumber, '直流UPS报警' as name from " + product + ".win:length(1) as  A ," + dupsProduct + ".win:length(1) as B where A.siteNumber=B.siteNumber and A.routeState<=12 and B.dupsState=12 ";
        //String epl7 = "select '_E_DCUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,'直流UPS报警' as name from pattern[every a=" + product + "(dupsState=12)]  ";
        EPStatement state7 = admin.createEPL(epl7);  
        state7.addListener(new OnOffStartListener());
        
        String   epl8 = "select '_E_DCUPS_WORK_ERROR' as alarmId, A.siteNumber as siteNumber, '直流UPS报警' as name from " + product + ".win:length(1) as  A ," + dupsProduct + ".win:length(1) as B where A.siteNumber=B.siteNumber and A.routeState<=12 and B.dupsState=11 ";
        //String epl8 = "select '_E_DCUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,'直流UPS报警' as name from pattern[every a=" + product + "(dupsState=11)]  ";
        EPStatement state8 = admin.createEPL(epl8);  
        state8.addListener(new OnOffEndListener()); 
        
        //启动引擎
        AlarmController.runtime = epService.getEPRuntime();  
	}
	
	//获得台站url
	public List<SiteInfo> getSiteUrl(String type){
		return alarmDao.getSiteUrl(type);
	}
	/**
	 * 根据条件查询站点列表
	 */
	public List<SiteInfo> querySiteByCondition(String condition) throws Exception{
		return alarmDao.querySiteByCondition(condition);
	}
	
	/**
	 * 查询报警信息
	 */
	public List<AlarmInfo>  queryAlarmInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo){
		return alarmDao.queryAlarmInfoList(startTime, endTime, isFlag, pageinfo);
	}
	/**
	 * 地图界面实时查询报警信息
	 */
	public List<AlarmInfo> getMapState(){
		return alarmDao.getMapState();
	}
	/**
	 * 界面DUPS实时查询报警信息
	 */
	public List<AlarmInfo> getMapDupsState() {
		return alarmDao.getMapDupsState();
	}
	/**
	 * 界面AUPS实时查询报警信息
	 */
	public List<AlarmInfo> getMapAupsState() {
		return alarmDao.getMapDupsState();
	}
}