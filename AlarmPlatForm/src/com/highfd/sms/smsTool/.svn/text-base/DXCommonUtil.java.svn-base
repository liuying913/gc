package com.highfd.sms.smsTool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.highfd.common.TimeUtils;
import com.highfd.sms.controller.SmsSendController;
import com.highfd.sms.model.SitePerson;
import com.highfd.sms.model.UserPower;


public class DXCommonUtil {
	
	//判断号码是否是 管理员的号码
	public static boolean getManagerPhone(List<UserPower> userPowerList,String phoneNumber){
		Set<String> phoneSet = new HashSet<String>();
		if(null!=userPowerList && userPowerList.size()>0){
			for(int i=0;i<userPowerList.size();i++){
				UserPower userPower = userPowerList.get(i);
				String openid = userPower.getOpenid();//收机号码
				phoneSet.add(openid);
			}
		}
		if(phoneSet.contains(phoneNumber)){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	
	//去掉重复的
	public static void duplicateRemoval(String eventId,String phoneStrSplit,String siteM,String siteNumber,String eventStr,Set<String> eventSendOverSet,Set<SitePerson> allAlarmSet){
		int day = TimeUtils.getDay();//获得当天日期
		if(null!=phoneStrSplit && !"".equals(phoneStrSplit)){
			String[] phoneSplit = phoneStrSplit.split(",");
			for(int s=0;s<phoneSplit.length;s++){
				SitePerson sp = new SitePerson();
				sp.setSmsMessage(siteM);
				sp.setSiteNumber(siteNumber);
				sp.setSitePhone(phoneSplit[s]);
				sp.setSmsMessage(siteM);
				sp.setEventId(eventId);
				
				String todayUserSendOver = day+"_"+phoneSplit[s]+"_"+siteNumber;// 某天、某人、某台站的去重String
				if(todayUserSiteNumberFlag(todayUserSendOver)){//单个报警 人员去重  &&  当天某人某台站去重
					allAlarmSet.add(sp); 
				}else{
					//System.out.println("重复的  "+eventSendOver);
				}
			}
		}
	}
	
	
	/*//当天报警  去掉重复的人
	public static boolean eventUserFlag(Set<String> eventSendOverSet,String todayUserSendOver){
		if(!eventSendOverSet.contains(todayUserSendOver)){
			eventSendOverSet.add(todayUserSendOver);
			return true;
		}
		return false;
	}*/
	
	//单个报警 人员去重  &&  当天某人台站去重
	public static boolean todayUserSiteNumberFlag(String todayUserSendOver){
		if(!SmsSendController.todayUserSendOverSet.contains(todayUserSendOver)){
			SmsSendController.todayUserSendOverSet.add(todayUserSendOver);
			return true;
		}
		return false;
	}

}
