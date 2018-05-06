package com.highfd.sms.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.highfd.common.TimeUtils;
import com.highfd.sms.model.EventFlag;
import com.highfd.sms.model.EventInfo;
import com.highfd.sms.model.SitePerson;
import com.highfd.sms.model.SmsInfo;
import com.highfd.sms.model.UserPower;
import com.highfd.sms.service.SiteService;
import com.highfd.sms.service.SmsService;
import com.highfd.sms.smsTool.DXCommonUtil;
import com.highfd.sms.smsTool.S2;
import com.highfd.sys.sysTool.GetXMLPath;

/**
 * 短信发送
 */
@Component
@Controller
public class SmsSendController {
	
	@Autowired
	SmsService smsService;
	
	public static String twzxPhone = "18910061019";
	
	@Autowired
	SiteService siteService;
	public static final Logger smsLog = LoggerFactory.getLogger("itemCRUD");
	public static String dateString = "";
	public static Set<String> todayUserSendOverSet = new HashSet<String>();//某天、某人台站的去重String
	public static boolean mondayFlag = false;
	public static String weekNum = "";
	
	/**
	 * 每天晚上11:40 清空缓存
	 */
	public void dayClean() throws Exception {
		System.out.println("*******执行清空操作********");//每天晚上23点 清空  某天、某人台站的去重String
		todayUserSendOverSet = new HashSet<String>();
	}
	
	/**
	 * 每周一 早上8:20 设置true 周一提醒
	 */
	public void mondayFlag() throws Exception {
		System.out.println("*******每周一判定********");
		if(TimeUtils.mondayFlag()){
			mondayFlag=true;
		}
		System.out.println(mondayFlag);
	}
	
	
	/**
	 * 十分钟  遍历一次报警，并发送
	 */
	//@Scheduled(fixedRate = 1000*60) 
	//@RequestMapping("/activeWX")
	public void activeWX() throws Exception {
		Thread.sleep(1000*30);
		System.out.println("*******新一轮开始！********");
		weekNum = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"weekNum");
		//smsLog.error("*******新一轮开始！********");
		Set<SitePerson> allAlarmSet = new HashSet<SitePerson>();
		List<EventInfo> eventInfoList = siteService.queryEventForTwoHours();
		List<UserPower> userPowerList = siteService.queryUserPower(); 
		
		Set<String> eventSendOverSet = new HashSet<String>();//该单个报警 用户去重
		
		for(int i=0;i<eventInfoList.size();i++){
			
			EventInfo eventInfo = eventInfoList.get(i);
			
			//发给台站中心的短信内容
			String eventId = eventInfo.getId();
			String eventType = eventInfo.getEvnetType();
			String siteNumber = eventInfo.getSiteNumber();
			String startTime = eventInfo.getStartTimeStr();
			String sitePhone = eventInfo.getSite_phone();
			String siteChaPhone = eventInfo.getSite_cha_phone();//信道负责人
			String siteProPhone = eventInfo.getSite_pro_phone();//部委负责人
			String eventStr = siteNumber+"_"+eventType+"_"+startTime;
			
			//组装报警信息     +  管理员发送情况
			String siteM = SmsSendController.getAlarmStr(smsService,siteService, eventInfo,eventStr, userPowerList,eventSendOverSet);
			if("".equals(siteM)){continue;}
			
			//台站负责人
			if(eventType.indexOf("ROUTE_LINK_ERROR")>-1 || eventType.indexOf("GNSS_WORK_ERROR")>-1 ||eventType.indexOf("GRAVITY_WORK_ERROR")>-1 ||eventType.indexOf("GNSS_Satellite_ERROR")>-1){
				DXCommonUtil.duplicateRemoval(eventId,sitePhone,siteM,siteNumber,eventStr,eventSendOverSet,allAlarmSet);
			}
			
			//信道负责人
			if(eventType.indexOf("ROUTE_LINK_ERROR")>-1){
				DXCommonUtil.duplicateRemoval(eventId,siteChaPhone,siteM,siteNumber,eventStr,eventSendOverSet,allAlarmSet);
			}
			
			//部委负责人
			if(eventType.indexOf("ROUTE_LINK_ERROR")>-1 || eventType.indexOf("GNSS_WORK_ERROR")>-1 ||eventType.indexOf("GRAVITY_WORK_ERROR")>-1 ||eventType.indexOf("GNSS_Satellite_ERROR")>-1){
				DXCommonUtil.duplicateRemoval(eventId,siteProPhone,siteM,siteNumber,eventStr,eventSendOverSet,allAlarmSet);
			}
		}
		//向站点发送时的情况
		Iterator<SitePerson> allAlarmIt = allAlarmSet.iterator();  
	    while (allAlarmIt.hasNext()){
	        SitePerson sitePerson = allAlarmIt.next();
	        if(!DXCommonUtil.getManagerPhone(userPowerList,sitePerson.getSitePhone())){
	           System.out.println("用户发送  ："+sitePerson.getSitePhone()+sitePerson.getSmsMessage());
	 	       smsLog.error("用户发送  ："+sitePerson.getSitePhone()+sitePerson.getSmsMessage());
	 	       boolean sendSMS = S2.sendSMS(sitePerson.getSitePhone(), sitePerson.getSmsMessage());
	 	       smsService.insertSmsInfo(new SmsInfo(sitePerson.getSiteNumber(), sitePerson.getEventId(), sitePerson.getSitePhone(),sitePerson.getSmsMessage(), sendSMS+""));
	        }
	    }
	    mondayFlag = false;//周一发送结束了
	}
	
	
	public static String getAlarmStr(SmsService smsService,SiteService siteService,EventInfo eventInfo,String eventStr,List<UserPower> userPowerList ,Set<String> eventSendOverSet) throws Exception{
		
		//报警记录修改为已经发送短信
		EventFlag ef = new EventFlag();
		List<EventFlag> EventFlagList = new ArrayList<EventFlag>(); 
		String id = eventInfo.getId();
		
		//发给台站中心的短信内容
		String eventType = eventInfo.getEvnetType();
		String siteNumber = eventInfo.getSiteNumber();
		String siteName = eventInfo.getSiteName();
		String startTime = eventInfo.getStartTimeStr();
		String endTime = eventInfo.getEndTimeStr();
		String description = eventInfo.getDescription();
		
		String fgAlarmStr = "";
		if(startTime.indexOf(".")>-1){
			startTime = " 发生时间为"+startTime.substring(0,startTime.indexOf("."));
		}else{
			startTime = " 发生时间为"+startTime;
		}
		
		String siteM  = "";//单条报警记录的内容
		if(null==endTime){
			
			if(null == eventInfo.getDeviceid()){//新推送出来的，未结束报警
				//修改短信发送标志位
				ef.setId(id);
				ef.setTag("1");
				EventFlagList.add(ef);
				siteM = siteName+description+startTime;
			}else{//旧的未结束的报警
				//continue;
				
				if(mondayFlag && new TimeUtils().getBetweenTwoDayNumbers(startTime,weekNum)){//每周一发送
					siteM = "(周一提醒：故障时长已超过"+weekNum+"周，仍未处理的报警)"+siteName+description+startTime;
				}
			}
		}else{//结束的报警
			
			//修改短信发送标志位
			ef.setId(id);
			ef.setTag("2");
			EventFlagList.add(ef);
			
			if(endTime.indexOf(".")>-1){
				endTime = " 结束时间为："+endTime.substring(0,endTime.indexOf("."));
			}else{
				endTime = " 结束时间为"+endTime;
			}
			
			if(null==eventInfo.getDeviceid()){//开始时间没有发送报警的
				//这种情况只给中心发
				//System.out.println("                   覆盖全过程的报警："+siteName+description+startTime+endTime+"   ");
				fgAlarmStr = siteName+description+startTime+endTime;
			}else if(eventInfo.getDeviceid().indexOf("1")>-1){//发送了一次开始时间的报警
				siteM = siteName+description+startTime+endTime;
			}else{
				//continue;
			}
		}
		
		//报警记录修改为已经发送短信
		for(int u=0;u<EventFlagList.size();u++){
			siteService.updateEventInfo(EventFlagList.get(u));
		}
		
		//向管理员发送报警
		if(null!=userPowerList && userPowerList.size()>0){
			for(int i=0;i<userPowerList.size();i++){
				UserPower userPower = userPowerList.get(i);
				String openid = userPower.getOpenid();//收机号码
				String userName = userPower.getUserName();
				//超级管理员  全部的报警都发送
				if(userPower.grounpId.equals("1")){
					//向管理员发送全部的报警
					if(siteM!=null && siteM.length()>0){
						System.out.println("管理员("+userName+")发送："+openid+"  "+siteM);
						smsLog.error("管理员("+userName+")发送："+openid+"  "+siteM);
						
						boolean sendSMS = S2.sendSMS(openid, siteM);
						smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
					}
					
					if(fgAlarmStr!=null && fgAlarmStr.length()>0){
						System.out.println("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
						smsLog.error("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
						boolean sendSMS = S2.sendSMS(openid, fgAlarmStr);
						smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
					}
				}
				
				//5:四种主要的报警
				if(userPower.grounpId.equals("5")){
					if(eventType.indexOf("ROUTE_LINK_ERROR")>-1 || eventType.indexOf("GNSS_WORK_ERROR")>-1 ||eventType.indexOf("GRAVITY_WORK_ERROR")>-1 ||eventType.indexOf("GNSS_Satellite_ERROR")>-1){
						if(siteM!=null && siteM.length()>0){
							System.out.println("管理员("+userName+")发送："+openid+"  "+siteM);
							smsLog.error("管理员("+userName+")发送："+openid+"  "+siteM);
							boolean sendSMS = S2.sendSMS(openid, siteM);
							smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
						}
						
						if(fgAlarmStr!=null && fgAlarmStr.length()>0){
							System.out.println("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
							smsLog.error("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
							boolean sendSMS = S2.sendSMS(openid, fgAlarmStr);
							smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
						}
					}
				}
				
				//6:通信报警
				if(userPower.grounpId.equals("6") &&  eventType.indexOf("ROUTE_LINK_ERROR")>-1){
					//向管理员发送全部的报警
					if(siteM!=null && siteM.length()>0){
						System.out.println("管理员("+userName+")发送："+openid+"  "+siteM);
						smsLog.error("管理员("+userName+")发送："+openid+"  "+siteM);
						boolean sendSMS = S2.sendSMS(openid, siteM);
						smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
					}
					
					if(fgAlarmStr!=null && fgAlarmStr.length()>0){
						System.out.println("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
						smsLog.error("管理员("+userName+")发送：覆盖全过程"+openid+"  "+fgAlarmStr);
						boolean sendSMS = S2.sendSMS(openid, fgAlarmStr);
						smsService.insertSmsInfo(new SmsInfo(siteNumber, id, openid,siteM, sendSMS+""));
					}
				}
			}
		}
		return siteM;
	}
	

	public String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}