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
import com.highfd.siteUser.model.SiteInfo;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmDao alarmDao;
	
	public void alarmService() throws Exception {
    	System.out.println(new Date());
    	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
        String product = SiteInfo.class.getName();
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
        String epl5 = "select '_E_ACUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '交流UPS报警' as name from pattern[every a=" + product + "(aupsState=12)]  ";
        EPStatement state5 = admin.createEPL(epl5);  
        state5.addListener(new OnOffStartListener());
        
        String epl6 = "select '_E_ACUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '交流UPS报警' as name from pattern[every a=" + product + "(aupsState=11)]  ";
        EPStatement state6 = admin.createEPL(epl6);  
        state6.addListener(new OnOffEndListener()); 
        
        //发生报警
        String epl7 = "select '_E_DCUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '直流UPS报警' as name from pattern[every a=" + product + "(dupsState=12)]  ";
        EPStatement state7 = admin.createEPL(epl7);  
        state7.addListener(new OnOffStartListener());
        
        String epl8 = "select '_E_DCUPS_WORK_ERROR' as alarmId, a.siteNumber as siteNumber ,  '直流UPS报警' as name from pattern[every a=" + product + "(dupsState=11)]  ";
        EPStatement state8 = admin.createEPL(epl8);  
        state8.addListener(new OnOffEndListener()); 
        
        //启动引擎
        AlarmController.runtime = epService.getEPRuntime();  
	}
	
	/*public static List<SiteInfo> PingItemList = null;
	public void PingTask(int itemsCount,String sql) {
		try {
			if(PingItemList==null){PingItemList = getSiteUrl(sql);}
			String[] split = getPingIdList(PingItemList).toString().split(";");
			for(int v=0;v<split.length;v++){
				//System.out.println("ping启动线程"+split[v]);
				new CrawPingTask(split[v]).start();
			}
		} catch (Exception e) {
			System.err.println("^^^^^数据抓取错误^^^^^");
			e.printStackTrace();
		}
	}
	
	
	//不走流的方式
	public static int timeout =5000;
	class CrawPingTask extends Thread {
		private String idArr;
		public CrawPingTask(String idArr){this.idArr = idArr;}
		public void run() {
			try {
				for(int c=0;c<idArr.split(",").length;c++){//行业数组
					String id=idArr.split(",")[c];
					//System.out.println( ++CrawTaskAnnotation.siteNumbers);
					ContextUtils.itemCRUD.error("线程Id："+Thread.currentThread().getId());
					SiteInfo siteInfo=new SiteInfo();
					for(int v=0;v<PingItemList.size();v++){
						siteInfo = PingItemList.get(v);
						if(siteInfo.getSiteNumber().equals(id)){
							break;
						}
					}
					if(AlarmController.siteMap.containsKey(id)){
						ContextUtils.itemCRUD.error("重复的id："+id);
						continue;
					}else{
						AlarmController.siteMap.put(id, siteInfo);
					}
					if(null==siteInfo){System.out.println(idArr.split(",")[c]+"未知id");continue;}
					
					SitePing sitePing = new SitePing();
					
					if(sitePing.pingServer(siteInfo.getGnssIp(), timeout)){//先进行接收机判断
						siteInfo.setRouteState(11);
					}else{
						if(sitePing.pingServer(siteInfo.getRouterIp(), timeout)){//路由器正常
							
							if(sitePing.pingServer(siteInfo.getGnssIp(), timeout)){//接收机都正常
								siteInfo.setRouteState(11);
				        	}else {//接收机不通时
				        		if(sitePing.pingServer(siteInfo.getRouterIp(), timeout)){//再一次访问路由器
				        			siteInfo.setRouteState(12);
				        		}else{
				        			siteInfo.setRouteState(22);
				        		}
				        	}
		        		}else{//路由器不通
		        			siteInfo.setRouteState(22);
		        		}
					}
					insertInfoSiteState(siteInfo);
					AlarmController.runtime.sendEvent(siteInfo);  
				}
			} catch (Exception e) {
				System.err.println("^^执行多线程时   错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				e.printStackTrace();
			}
		}
	}*/
	
	public void insertInfoSiteState(SiteInfo siteInfo) throws Exception{
		alarmDao.insertInfoSiteState(siteInfo);
	}
	
	//获得台站url
	public List<SiteInfo> getSiteUrl(String type){
		return alarmDao.getSiteUrl(type);
	}
	
	public static StringBuffer getPingIdList(List<SiteInfo> SiteList){
		StringBuffer zlzpC=new StringBuffer();
		int g=1;
		for(int o=0;o<SiteList.size();o++){
			int oneThreadIdSide = getOneThreadIdSide(SiteList.size());
			SiteInfo siteInfo = SiteList.get(o);
			if(oneThreadIdSide==0){
				zlzpC.append(siteInfo.getSiteNumber()+",");
			}else if(g<oneThreadIdSide){
				zlzpC.append(siteInfo.getSiteNumber()+",");
				g++;
			}else if(g==oneThreadIdSide){
				zlzpC.append(siteInfo.getSiteNumber()+";");
				g=1;
			}
		}
		return zlzpC;
	}
	
	public static int ThreadSide=10;
	public static int getOneThreadIdSide(int numberSide){
		return  numberSide / ThreadSide;
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
}