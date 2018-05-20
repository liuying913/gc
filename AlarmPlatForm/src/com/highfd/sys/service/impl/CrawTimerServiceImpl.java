package com.highfd.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.model.AlarmInfo;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.dao.CrawTimerDAO;
import com.highfd.sys.service.CrawTimerService;

@Service
public class CrawTimerServiceImpl implements CrawTimerService{
	
	@Autowired
	CrawTimerDAO dao;
	
	public static final Logger itemCRUD = LoggerFactory.getLogger("itemCRUD");  


	public static int getOneThreadIdSide(int numberSide){
		return  numberSide / ThreadSide;
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
	public void updateEventInfoEndTime(AlarmInfo eventInfo) throws Exception{
		dao.updateEventInfoEndTime(eventInfo);
	}
	/**
	 * 添加事件信息
	 */
	public void insertEventInfo(final AlarmInfo eventInfo) throws Exception {
		dao.insertEventInfo(eventInfo);
	}
	public String selectEventStartTime(String number,String type) throws Exception {
		return dao.selectEventStartTime(number,type);
	}
	//***********************Ping*****************************************************************************************
	public static int ThreadSide=10;
	public static List<SiteInfo> PingItemList = null;
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
	
	public static int timeout =5000;
	class CrawPingTask extends Thread {
		private String idArr;
		public CrawPingTask(String idArr){this.idArr = idArr;}
		public void run() {
			try {
				for(int c=0;c<idArr.split(",").length;c++){//行业数组
					String id=idArr.split(",")[c];
					//System.out.println( ++CrawTaskAnnotation.siteNumbers);
					itemCRUD.error("线程Id："+Thread.currentThread().getId());
					SiteInfo siteInfo=new SiteInfo();
					for(int v=0;v<PingItemList.size();v++){
						siteInfo = PingItemList.get(v);
						if(siteInfo.getSiteNumber().equals(id)){
							break;
						}
					}
					if(AlarmController.siteMap.containsKey(id)){
						itemCRUD.error("重复的id："+id);
						continue;
					}else{
						AlarmController.siteMap.put(id, siteInfo);
					}
					if(null==siteInfo){System.out.println(idArr.split(",")[c]+"未知id");continue;}
					
					SitePing sitePing = new SitePing();
					
					if(sitePing.pingServer(siteInfo.getGnssIp(), timeout)){//先进行接收机判断
						siteInfo.setRouteState(11);
						insertInfoSiteState(11,siteInfo.getSiteNumber());
					}else{
						if(sitePing.pingServer(siteInfo.getRouterIp(), timeout)){//路由器正常
							
							if(sitePing.pingServer(siteInfo.getGnssIp(), timeout)){//接收机都正常
								siteInfo.setRouteState(11);
								insertInfoSiteState(11,siteInfo.getSiteNumber());
				        	}else {//接收机不通时
				        		if(sitePing.pingServer(siteInfo.getRouterIp(), timeout)){//再一次访问路由器
				        			siteInfo.setRouteState(12);
				        			insertInfoSiteState(12,siteInfo.getSiteNumber());
				        		}else{
				        			siteInfo.setRouteState(22);
				        			insertInfoSiteState(22,siteInfo.getSiteNumber());
				        		}
				        	}
		        		}else{//路由器不通
		        			siteInfo.setRouteState(22);
		        			insertInfoSiteState(22,siteInfo.getSiteNumber());
		        		}
					}
					
					AlarmController.runtime.sendEvent(siteInfo);  
				}
			} catch (Exception e) {
				System.err.println("^^执行多线程时   错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				e.printStackTrace();
			}
		}
	}
	
	//获得台站url
	public List<SiteInfo> getSiteUrl(String type){
		return dao.getSiteUrl(type);
	}
	
	public void insertInfoSiteState(int state,String siteNumber) throws Exception{
		dao.insertInfoSiteState(state,siteNumber);
	}

	

}
