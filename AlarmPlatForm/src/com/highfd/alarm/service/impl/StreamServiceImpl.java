package com.highfd.alarm.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.service.AlarmService;
import com.highfd.alarm.strom.PMCServerSocket;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.service.impl.SitePing;

@Service
public class StreamServiceImpl{// implements StreamService 

	@Autowired
	AlarmDao alarmDao;
	
	@Autowired
	AlarmService alarmService;
	
	/**
	 * 问题站的信息
	 */
	private Map<String, SiteInfo> disconnectSiteMap = new Hashtable<String,SiteInfo>(); 	
	
	public void StreamAlarm() {
		try {
			alarmDao.deleteUserInfoByTime();

			Map<String, SiteInfo> allSiteMap = new Hashtable<String, SiteInfo>();
			List<SiteInfo> siteInfoList = alarmDao.querySiteByCondition("");
			if(null != siteInfoList && siteInfoList.size() > 0){
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					allSiteMap.put(siteInfo.getSiteNumber(),siteInfo);
				}
			}
			
			
			Set<String> numberSet = new HashSet<String>();
			//Set<Entry<String, String>> set = PMCServerSocket.map.entrySet(); 
			Set<Entry<String, String>> set = PMCServerSocket.sbStream.getRouterMap().entrySet(); 
	        Iterator<Entry<String, String>> it = set.iterator();  
			while(it.hasNext()){
		        Entry<String, String> next = it.next(); 
		        String value = next.getValue();
		        
		        if(value!=null&&value.indexOf("@@")>-1 &&  allSiteMap.keySet().contains(next.getKey())){
			        SiteInfo disPingDTO = allSiteMap.get(next.getKey());
			        String[] split = value.split(" ");
			        
			        //AUPS状态
		            if(null!=split[5]&&split[5].length()>0){
		            	int num = Integer.valueOf(split[5]);
		            	disPingDTO.setAupsState(num);
		        	}
		            
		            //DUPS状态
		            if(null!=split[6]&&split[6].length()>0){
		            	int num = Integer.valueOf(split[6]);
		            	disPingDTO.setDupsState(num);
		        	}
		            
		        	
	        		//判断流是否小于9个小时
			        //if(split[4].indexOf("0")>-1&&split.length>10 && judgeTime(split[2], split[3])){//判断是否正常 
			        if(split[4].indexOf("0")>-1&&split.length>10){//判断是否正常 
			        	disPingDTO.setRouteState(11);
			        }else{
			        	disPingDTO.setRouteState(12);
			        }
			        
			        
			        numberSet.add(disPingDTO.getSiteNumber());
			        
			        if(disPingDTO.getRouteState()==11){
			        	alarmDao.insertInfoSiteState("info",disPingDTO);
			        	AlarmController.runtime.sendEvent(disPingDTO);//发送到报警引擎  
			        }else if(disPingDTO.getRouteState()==12){
			        	disconnectSiteMap.put(disPingDTO.getSiteNumber(), disPingDTO);
			        	new PingThread(disPingDTO).start();	//启动一个新的线程
			        }
	        	}
		    }
			
			//流中没有的站点
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
		
		/**
		 * 当前线程Ping操作对象
		 */
		private SiteInfo pingDTO;
		long currentTime = System.currentTimeMillis();
		
		/**
		 * ping 地址超时时间控制
		 */
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
			        			pingDTO.setAupsState(0);
			        			pingDTO.setDupsState(0);
			        			pingDTO.setRouteState(22);
			        		}
			        	}
	        		}else{//路由器不通
	        			pingDTO.setAupsState(0);
	        			pingDTO.setDupsState(0);
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


	
	
	



	
	
	
	

	public boolean judgeTime(String date,String times) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date liuDate;
		try {
			liuDate = dateFormat.parse(date+" "+times);
			Date nowDate = new Date();
			if( (nowDate.getTime()-liuDate.getTime())>9*60*60*1000      ){
				return false;
			}else{
				return true;
			}
		} catch (ParseException e) {
			System.out.println("流中时间错误！！！第一时间："+date+"     第二时间："+times);
			return false;
		}
	}
}