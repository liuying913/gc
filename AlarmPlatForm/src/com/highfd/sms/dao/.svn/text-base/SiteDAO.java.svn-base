package com.highfd.sms.dao;

import java.util.List;

import com.highfd.sms.model.EventFlag;
import com.highfd.sms.model.EventInfo;
import com.highfd.sms.model.GroupAuth;
import com.highfd.sms.model.SiteSMSInfo;
import com.highfd.sms.model.UserPower;


public interface SiteDAO {
	
	//查询用户权限
    public GroupAuth queryGroup(String id) throws Exception;
    
	//获得台站列表
	public List<SiteSMSInfo> querySiteInfoList(String siteNumber ,String zoneCode,String dicCode) throws Exception;
	
	//获得报警列表
	public List<EventInfo> queryEventInfoList(String siteNumber ,String siteNumberStr,String eventTypeStr) throws Exception;
	
	//获得符合报警时长的报警信息 2个小时
	public List<EventInfo> queryEventForTwoHours() throws Exception;
	
	//将发生完的报警修改设置
	public void updateEventInfo(final EventFlag eventFlag) throws Exception;
	
	
	//将发生完的报警修改设置
	public void updateEventInfoEndTime(final EventInfo info) throws Exception;
	/**
	 * 跟外网对接，获得未完成报警的id集合
	 */
	public String queryEventInfoIdList(String params) throws Exception;
	//插入从  外网获得的 监控报警信息
	public void insertEventInfo(final List<EventInfo> list) throws Exception;
	
	//删除 时间短的报警
	public void deleteUserInfo(final int id) throws Exception;
	
	//查询所有用户的权限
	public List<UserPower> queryUserPower() throws Exception;
	
	//查询 台站列表（部位、省份）
	public List<SiteSMSInfo> queryDepartZoneSiteList() throws Exception;
}