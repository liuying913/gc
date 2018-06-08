package com.highfd.alarm.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highfd.alarm.controller.AlarmController;
import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.service.AlarmService;
import com.highfd.alarm.service.StreamService;
import com.highfd.alarm.strom.PMCServerSocket;
import com.highfd.siteUser.model.SiteInfo;

@Service
public class StreamFourServiceImpl implements StreamService {

	@Autowired
	AlarmDao alarmDao;
	
	@Autowired
	AlarmService alarmService;
	
	public void StreamAlarm() {
		try {
			alarmDao.deleteUserInfoByTime();
			
			boolean timeFlag = PMCServerSocket.sbStream.isTimeFlag();
			if(!timeFlag){return;}//时间不对 不走了
			Map<String, String> routerMap = PMCServerSocket.sbStream.getRouterMap();
			Map<String, String> receiverMap = PMCServerSocket.sbStream.getReceiverMap();
			Map<String, String> acupsMap = PMCServerSocket.sbStream.getAcupsMap();
			Map<String, String> dcupsMap = PMCServerSocket.sbStream.getDcupsMap();
			
			List<SiteInfo> siteInfoList = alarmDao.querySiteByCondition("");
			if(null != siteInfoList && siteInfoList.size() > 0){
				for(int i=0;i<siteInfoList.size();i++){
					SiteInfo siteInfo = siteInfoList.get(i);
					String siteNumber = siteInfo.getSiteNumber();
					
					if(null!=routerMap && null!=routerMap.get(siteNumber)){
						if(routerMap.get(siteNumber).indexOf("0")>-1){//--> 0:connectd,1:disconnected
							
							//接收机设备
							if(null!=receiverMap && null!=receiverMap.get(siteNumber)){
								if(receiverMap.get(siteNumber).indexOf("0")>-1){
									siteInfo.setRouteState(11);//接收机正常
								}else{
									siteInfo.setRouteState(12);//接收机 不 正常
								}
							}else{
								System.out.println(siteNumber+" 接收机设备缺失");
							}
							
							//交流UPS设备
							if(null!=acupsMap && null!=acupsMap.get(siteNumber)){
								if(acupsMap.get(siteNumber).indexOf("0")>-1){
									siteInfo.setAupsState(11);//交流UPS正常
								}else{
									siteInfo.setAupsState(12);//交流UPS 不 正常
								}
							}else{
								System.out.println(siteNumber+" 交流UPS设备缺失");
							}
							
							//直流UPS设备
							if(null!=dcupsMap && null!=dcupsMap.get(siteNumber)){
								if(dcupsMap.get(siteNumber).indexOf("0")>-1){
									siteInfo.setDupsState(11);//直流UPS正常
								}else{
									siteInfo.setDupsState(12);//直流UPS 不 正常
								}
							}else{
								System.out.println(siteNumber+" 直流UPS设备缺失");
							}
							
						}else{
							siteInfo.setRouteState(22);//路由不正常
						}
						
						AlarmController.runtime.sendEvent(siteInfo);//发送到报警引擎  
					}else{
						System.out.println(siteNumber+" 路由器设备缺失");
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("^^流 报错误^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			e.printStackTrace();
		}
	}


}