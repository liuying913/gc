package com.highfd.alarm.service.impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.service.AlarmService;
import com.highfd.alarm.service.SqlService;
import com.highfd.common.TimeUtils;
import com.highfd.siteUser.model.AupsState;
import com.highfd.siteUser.model.DupsState;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.controller.OnlyOneController;
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
			routeNum=0;
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
				int threadNum=0;
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					if( !numberSet.contains(siteInfo.getSiteNumber())){
						threadNum++;
					}
				}
				CountDownLatch countDownLatch = new CountDownLatch(threadNum);
				System.out.println("  PP   "+threadNum);OnlyOneController.logger.error("  PP   "+threadNum);
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					if( !numberSet.contains(siteInfo.getSiteNumber())){
						disconnectSiteMap.put(siteInfo.getSiteNumber(), siteInfo);
						new PingThread(siteInfo,countDownLatch).start();	//启动一个新的线程进行Ping操作
					}
				}
				//等待所有的子线程结束
		        boolean await = countDownLatch.await(110, TimeUnit.SECONDS);
		        if(!await){
		        	System.out.println("超时了！！");OnlyOneController.logger.error("超时了！！");
		        }else{
		        	 System.out.println("全部结束");OnlyOneController.logger.error("全部结束");
		        }
			}
		
		} catch (Exception e) {
			System.out.println("^^流 报错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			e.printStackTrace();
		}
	}
	class PingThread  extends Thread{
		
		private SiteInfo pingDTO;
		private CountDownLatch countDownLatch;
		private int timeout = 10000;
		public PingThread(SiteInfo pingDTO,CountDownLatch countDownLatch){
			this.pingDTO = pingDTO;
			this.countDownLatch = countDownLatch;
		}
		public void run() {
			try {
				//SitePing sitePing = new SitePing();
				if(new SitePing().ping_GC(pingDTO.getGnssIp(), timeout)){//先进行接收机判断
					pingDTO.setRouteState(11);
				}else{
					if(new SitePing().ping_GC(pingDTO.getRouterIp(), timeout)){//路由器正常
						if(new SitePing().ping_GC(pingDTO.getGnssIp(), timeout)){//接收机都正常
							pingDTO.setRouteState(11);
			        	}else {//接收机不通时
			        		if(new SitePing().ping_GC(pingDTO.getRouterIp(), timeout)){//再一次访问路由器
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
				System.out.println(TimeUtils.getNowTime() +"  "+pingDTO.getSiteNumber()+"-"+pingDTO.getRouteState()+"   路由器跑完的线程"+routeNum++);
				OnlyOneController.logger.error(TimeUtils.getNowTime() +"  "+pingDTO.getSiteNumber()+"-"+pingDTO.getRouteState()+"   路由器跑完的线程"+routeNum++);
			} catch (Exception e) {
				e.printStackTrace();
			}  finally {
                //线程结束时，将计时器减一
                countDownLatch.countDown();
            }
		}
	}

	
	
	
	
	public static int ThreadSide=5;
	public static int timeout =5000;
	public static int routeNum=0;
	public static int aupsNum=0;
	public static int dupsNum=0;
	public static List<SiteInfo> routeGoodStation_Aups = null;
	
	public static int aupsThreadNum=0;
	public static int dupsThreadNum=0;
	/**
	 ********************AUPS**********************
	 */
	public void sqlAupsAlarm() {
		try {
			routeGoodStation_Aups = alarmDao.queryRouteGoodStation();
			aupsNum=0;
			String[] split = getPingIdList(routeGoodStation_Aups).toString().split(";");
			aupsThreadNum++;
			System.out.println("AUPS  第"+aupsThreadNum+"轮*******************************线程总数量"+routeGoodStation_Aups.size());
			OnlyOneController.logger.error("AUPS  第"+aupsThreadNum+"轮*******************************线程总数量"+routeGoodStation_Aups.size());
			for(int v=0;v<split.length;v++){
				//System.out.println("ping APS 启动线程"+split[v]);
				//new AUPSPingTask(split[v]).start();
				new AUPSPingTask(split[v],aupsThreadNum).start();
			}
		} catch (Exception e) {
			System.err.println("^^^^^Ping APS 线程错误!!!^^^^^");
			e.printStackTrace();
		}
	}
	class AUPSPingTask extends Thread {
		private String siteNumberArr;
		private int aupsThreadNum;
		public AUPSPingTask(String siteNumberArr,int aupsThreadNum){this.siteNumberArr = siteNumberArr;this.aupsThreadNum=aupsThreadNum;}
		public void run() { 
			try {
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
					
					if(new SitePing().ping_GC(siteInfo.getAcupsIp(), timeout)){//AUPS通断判断
						siteInfo.setAupsState(11);
					}else{
						siteInfo.setAupsState(12);
					}
					alarmDao.insertInfoSiteState("aups",siteInfo);
					AupsState as = new AupsState(siteInfo.getSiteNumber(),siteInfo.getAupsState());
					AlarmController.runtime.sendEvent(as); 
					System.out.println("AUPS  第"+aupsThreadNum+"轮： "+siteInfo.getSiteNumber()+"-"+siteInfo.getAupsState()+"   AUPS跑完的线程"+aupsNum++);
					OnlyOneController.logger.error("AUPS  第"+aupsThreadNum+"轮："+aupsThreadNum+" "+siteInfo.getSiteNumber()+"-"+siteInfo.getAupsState()+"   AUPS跑完的线程"+aupsNum);
				}
			} catch (Exception e) {
				System.err.println("^^执行多线程时   错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 ********************DUPS**********************
	 */
	public static List<SiteInfo> routeGoodStation_Dups = null;
	public void sqlDupsAlarm() {
		try {
			routeGoodStation_Dups = alarmDao.queryRouteGoodStation();
			dupsNum=0;
			String[] split = getPingIdList(routeGoodStation_Dups).toString().split(";");
			dupsThreadNum++;
			System.out.println("DUPS               "+" 第"+dupsThreadNum+"轮******************************************************线程总数量"+routeGoodStation_Dups.size());
			OnlyOneController.logger.error("DUPS               "+" 第"+dupsThreadNum+"轮******************************************************线程总数量"+routeGoodStation_Dups.size());
			for(int v=0;v<split.length;v++){
				//System.out.println("ping DUPS 启动线程"+split[v]);
				new DUPSPingTask(split[v],dupsThreadNum).start();
			}
		} catch (Exception e) {
			System.err.println("^^^^^Ping DUPS 线程错误!!!^^^^^");
			e.printStackTrace();
		}
	}
	class DUPSPingTask extends Thread {
		private String siteNumberArr;
		private int dupsThreadNum;
		public DUPSPingTask(String siteNumberArr,int dupsThreadNum){this.siteNumberArr = siteNumberArr;this.dupsThreadNum=dupsThreadNum;}
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
					
					if(new SitePing().ping_GC(siteInfo.getDcupsIp(), timeout)){//DUPS通断判断
						siteInfo.setDupsState(11);
					}else{
						siteInfo.setDupsState(12);
					}
					alarmDao.insertInfoSiteState("dups",siteInfo);
					DupsState ds = new DupsState(siteInfo.getSiteNumber(),siteInfo.getDupsState());
					AlarmController.runtime.sendEvent(ds); 
					System.out.println("DUPS               "+" 第"+dupsThreadNum+"轮：  "+siteInfo.getSiteNumber()+"-"+siteInfo.getDupsState()+"   DUPS跑完的线程"+dupsNum++);
					OnlyOneController.logger.error("DUPS               "+" 第"+dupsThreadNum+"轮：  "+siteInfo.getSiteNumber()+"-"+siteInfo.getDupsState()+"   DUPS跑完的线程"+dupsNum);
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