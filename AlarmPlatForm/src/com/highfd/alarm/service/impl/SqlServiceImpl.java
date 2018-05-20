package com.highfd.alarm.service.impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.service.AlarmService;
import com.highfd.alarm.service.SqlService;
import com.highfd.siteUser.model.AupsState;
import com.highfd.siteUser.model.DupsState;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.service.impl.SitePing;
import com.jdbc.common.SqlServerCon;

@Service
public class SqlServiceImpl implements SqlService{

	@Autowired
	AlarmDao alarmDao;
	
	@Autowired
	AlarmService alarmService;
	
	/**
	 * 问题站的信息
	 */
	private Map<String, SiteInfo> disconnectSiteMap = new Hashtable<String,SiteInfo>(); 	
	
	public void sqlAlarm() {
		try {
			alarmDao.deleteUserInfoByTime();//删除故障时长大于2小时的报警

			Map<String, SiteInfo> allSiteMap = new Hashtable<String, SiteInfo>();
			List<SiteInfo> siteInfoList = alarmDao.querySiteByCondition("");
			if(null != siteInfoList && siteInfoList.size() > 0){
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					allSiteMap.put(siteInfo.getSiteNumber(),siteInfo);
				}
			}
			
			Set<String> numberSet = new HashSet<String>();
			//sql中存在的情况（路由+接收机都OK）
	        List<String> alarmStationList = SqlServerCon.getAlarmStation();
	        for(String siteNumber:alarmStationList){
	        	SiteInfo disPingDTO = allSiteMap.get(siteNumber);
	        	if(null!=disPingDTO){
	        		disPingDTO.setRouteState(11);//设置状态
		        	numberSet.add(disPingDTO.getSiteNumber());
			        alarmDao.insertInfoSiteState("info",disPingDTO);
			        AlarmController.runtime.sendEvent(disPingDTO);//发送到报警引擎  
	        	}
	        }
	        
	        //sql中没有站点的情况
			if(null != siteInfoList && siteInfoList.size() > 0){
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					if( !numberSet.contains(siteInfo.getSiteNumber())){
						disconnectSiteMap.put(siteInfo.getSiteNumber(), siteInfo);
						new PingThread(siteInfo).start();	//启动一个新的线程进行Ping操作
					}
				}	
			}
			
		
		} catch (Exception e) {
			System.out.println("^^流 报错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			e.printStackTrace();
		}
	}
	class PingThread extends Thread {
		
		private SiteInfo pingDTO;
		long currentTime = System.currentTimeMillis();
		private int timeout = 10000;
		public PingThread(SiteInfo pingDTO){
			this.pingDTO = pingDTO;
		}
		public void run() {
			try {
				SitePing sitePing = new SitePing();
				
				if(sitePing.pingServer(pingDTO.getGnssIp(), timeout)){//先进行接收机判断
					pingDTO.setRouteState(11);
				}else{
					if(sitePing.pingServer(pingDTO.getRouterIp(), timeout)){//路由器正常
						if(sitePing.pingServer(pingDTO.getGnssIp(), timeout)){//接收机都正常
							pingDTO.setRouteState(11);
			        	}else {//接收机不通时
			        		if(sitePing.pingServer(pingDTO.getRouterIp(), timeout)){//再一次访问路由器
			        			pingDTO.setRouteState(12);
			        		}else{
			        			pingDTO.setRouteState(22);
			        		}
			        	}
	        		}else{//路由器不通
	        			pingDTO.setRouteState(22);
	        		}
				}
		        alarmDao.insertInfoSiteState("info",pingDTO);
				AlarmController.runtime.sendEvent(pingDTO);//发送到报警引擎  
				disconnectSiteMap.remove(pingDTO.getSiteNumber());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	
	public static int ThreadSide=1000000;
	public static int timeout =5000;
	public static int aupsNum=0;
	public static int dupsNum=0;
	public static List<SiteInfo> routeGoodStation_Aups = null;
	public void sqlAupsAlarm() {
		try {
			if(routeGoodStation_Aups==null){routeGoodStation_Aups = alarmDao.queryRouteGoodStation();}
			aupsNum=0;
			System.out.println("AUPS线程总数量"+routeGoodStation_Aups.size());
			String[] split = getPingIdList(routeGoodStation_Aups).toString().split(";");
			for(int v=0;v<split.length;v++){
				System.out.println("ping APS 启动线程"+split[v]);
				//new AUPSPingTask(split[v]).start();
				new AUPSPingTask(split[v]).run();
			}
		} catch (Exception e) {
			System.err.println("^^^^^Ping APS 线程错误!!!^^^^^");
			e.printStackTrace();
		}
	}
	class AUPSPingTask {
		private String siteNumberArr;
		public AUPSPingTask(String siteNumberArr){this.siteNumberArr = siteNumberArr;}
		public void run() { 
			try {
				System.out.println("AUPS开始跑的线程"+aupsNum);
				for(int c=0;c<siteNumberArr.split(",").length;c++){//行业数组
					String siteNumber=siteNumberArr.split(",")[c];
					SiteInfo siteInfo=new SiteInfo();
					for(int v=0;v<routeGoodStation_Aups.size();v++){
						siteInfo = routeGoodStation_Aups.get(v);
						if(siteInfo.getSiteNumber().equals(siteNumber)){
							break;
						}
					}
					if(AlarmController.siteMap.containsKey(siteNumber)){
						//continue;
					}else{
						AlarmController.siteMap.put(siteNumber, siteInfo);
					}
					if(null==siteInfo){System.out.println(siteNumberArr.split(",")[c]+"未知台站编号");continue;}
					
					SitePing sitePing = new SitePing();
					if(sitePing.pingServer(siteInfo.getAcupsIp(), timeout)){//先进行接收机判断
						siteInfo.setAupsState(11);
					}else{
						siteInfo.setAupsState(12);
					}
					alarmDao.insertInfoSiteState("aups",siteInfo);
					AupsState as = new AupsState(siteInfo.getSiteNumber(),siteInfo.getAupsState());
					AlarmController.runtime.sendEvent(as); 
					System.out.println("AUPS跑完的线程"+aupsNum++);
				}
			} catch (Exception e) {
				System.err.println("^^执行多线程时   错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static List<SiteInfo> routeGoodStation_Dups = null;
	
	public void sqlDupsAlarm() {
		try {
			if(routeGoodStation_Dups==null){routeGoodStation_Dups = alarmDao.queryRouteGoodStation();}
			dupsNum=0;
			System.out.println("DUPS线程总数量"+routeGoodStation_Dups.size());
			String[] split = getPingIdList(routeGoodStation_Dups).toString().split(";");
			for(int v=0;v<split.length;v++){
				System.out.println("ping DUPS 启动线程"+split[v]);
				new DUPSPingTask(split[v]).start();
			}
		} catch (Exception e) {
			System.err.println("^^^^^Ping DUPS 线程错误!!!^^^^^");
			e.printStackTrace();
		}
	}
	class DUPSPingTask extends Thread {
		private String siteNumberArr;
		public DUPSPingTask(String siteNumberArr){this.siteNumberArr = siteNumberArr;}
		public void run() { 
			try {
				for(int c=0;c<siteNumberArr.split(",").length;c++){//行业数组
					String siteNumber=siteNumberArr.split(",")[c];
					SiteInfo siteInfo=new SiteInfo();
					for(int v=0;v<routeGoodStation_Dups.size();v++){
						siteInfo = routeGoodStation_Dups.get(v);
						if(siteInfo.getSiteNumber().equals(siteNumber)){
							break;
						}
					}
					if(AlarmController.siteMap.containsKey(siteNumber)){
						//continue;
					}else{
						AlarmController.siteMap.put(siteNumber, siteInfo);
					}
					if(null==siteInfo){System.out.println(siteNumberArr.split(",")[c]+"未知台站编号");continue;}
					
					SitePing sitePing = new SitePing();
					if(sitePing.pingServer(siteInfo.getDcupsIp(), timeout)){
						siteInfo.setDupsState(11);
					}else{
						siteInfo.setDupsState(12);
					}
					alarmDao.insertInfoSiteState("dups",siteInfo);
					DupsState ds = new DupsState(siteInfo.getSiteNumber(),siteInfo.getDupsState());
					AlarmController.runtime.sendEvent(ds); 
					System.out.println("DUPS跑完的线程"+dupsNum++);
				}
			} catch (Exception e) {
				System.err.println("^^执行多线程时   错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				e.printStackTrace();
			}
		}
	}
	
	
	public static StringBuffer getPingIdList(List<SiteInfo> SiteList){
		StringBuffer sb=new StringBuffer();
		int g=1;
		for(int o=0;o<SiteList.size();o++){
			int oneThreadIdSide = getOneThreadIdSide(SiteList.size());
			SiteInfo siteInfo = SiteList.get(o);
			if(oneThreadIdSide==0){
				sb.append(siteInfo.getSiteNumber()+",");
			}else if(g<oneThreadIdSide){
				sb.append(siteInfo.getSiteNumber()+",");
				g++;
			}else if(g==oneThreadIdSide){
				sb.append(siteInfo.getSiteNumber()+";");
				g=1;
			}
		}
		return sb;
	}
	public static int getOneThreadIdSide(int numberSide){
		return  numberSide / ThreadSide;
	}
}