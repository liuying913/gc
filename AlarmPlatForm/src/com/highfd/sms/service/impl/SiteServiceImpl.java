package com.highfd.sms.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.sms.dao.SiteDAO;
import com.highfd.sms.model.EventFlag;
import com.highfd.sms.model.EventInfo;
import com.highfd.sms.model.GroupAuth;
import com.highfd.sms.model.SiteSMSInfo;
import com.highfd.sms.model.UserPower;
import com.highfd.sms.service.SiteService;



@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	SiteDAO dao;

	//查询用户权限
    public GroupAuth queryGroup(String id) throws Exception{
    	return dao.queryGroup(id);
    }
    
	//获得台站列表
	public List<SiteSMSInfo> querySiteInfoList(String siteNumber ,String zoneCode,String dicCode) throws Exception{
    	return dao.querySiteInfoList(siteNumber, zoneCode, dicCode);
    }
    
	//获得报警列表
	public List<EventInfo> queryEventInfoList(String siteNumber ,String siteNumberStr,String eventTypeStr) throws Exception{
    	return dao.queryEventInfoList(siteNumber, siteNumberStr, eventTypeStr);
    }
	
	//将台站列表 转换成 siteNumber字符串
	public String SiteInfoListToStr(List<SiteSMSInfo> siteInfoList) throws Exception{
		StringBuffer sb = new StringBuffer("");
    	if(null!=siteInfoList && siteInfoList.size()>0){
    		for(int i=0;i<siteInfoList.size();i++){
    			SiteSMSInfo siteInfo = siteInfoList.get(i);
    			sb.append(siteInfo.getSiteNumber()+",");
    		}
    		sb.deleteCharAt(sb.length()-1);
        	return sb.toString();
    	}else{
    		return null;
    	}
    }
	
	//将台站列表 转换成 siteName字符串
	public String SiteNameListToStr(List<SiteSMSInfo> siteInfoList) throws Exception{
		StringBuffer sb = new StringBuffer("");
    	if(null!=siteInfoList && siteInfoList.size()>0){
    		for(int i=0;i<siteInfoList.size();i++){
    			SiteSMSInfo siteInfo = siteInfoList.get(i);
    			sb.append(siteInfo.getSiteName()+",");
    		}
    		sb.deleteCharAt(sb.length()-1);
        	return sb.toString();
    	}else{
    		return null;
    	}
    }
	
	//获得符合报警时长的报警信息 2个小时
	public List<EventInfo> queryEventForTwoHours() throws Exception{
    	return dao.queryEventForTwoHours();
    }
    
  //将发生完的报警修改设置
	public void updateEventInfo(final EventFlag eventFlag) throws Exception {
		dao.updateEventInfo(eventFlag);
	}
	
	
	//将发生完的报警修改设置
	public void updateEventInfoEndTime(final EventInfo info) throws Exception {
		dao.updateEventInfoEndTime(info);
	}
	/**
	 * 跟外网对接，获得未完成报警的id集合
	 */
	public String queryEventInfoIdList(String params) throws Exception {
		return dao.queryEventInfoIdList(params);
	}
	public void insertEventInfo(final List<EventInfo> list) throws Exception {
		dao.insertEventInfo(list);
	}
	
	//删除 时间短的报警
	public void deleteUserInfo(final int id) throws Exception {
		dao.deleteUserInfo(id);
	}
	
	//查询所有用户的权限
	public List<UserPower> queryUserPower() throws Exception{
		return dao.queryUserPower();
	}
	
	//查询 台站列表（部位、省份）
	public void queryDepartZoneSiteList() throws Exception{
		List<SiteSMSInfo> siteList = dao.queryDepartZoneSiteList();
		for(int i=0;i<siteList.size();i++){
			/*SiteInfo siteInfo = siteList.get(i);
			
			Set<String> dicSet = AlarmEmailAnnotation.dicSiteMap.get(siteInfo.getSiteDic());
			if(null==dicSet){dicSet = new HashSet<String>();}
			dicSet.add(siteInfo.getSiteNumber());
			AlarmEmailAnnotation.dicSiteMap.put(siteInfo.getSiteDic(), dicSet);
			
			Set<String> zoneSet = AlarmEmailAnnotation.zoneSiteMap.get(siteInfo.getSiteZone());
			if(null==zoneSet){zoneSet = new HashSet<String>();}
			zoneSet.add(siteInfo.getSiteNumber());
			AlarmEmailAnnotation.zoneSiteMap.put(siteInfo.getSiteZone(), zoneSet);*/
		}
	}
}