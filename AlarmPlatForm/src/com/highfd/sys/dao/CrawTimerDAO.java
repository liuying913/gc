package com.highfd.sys.dao;

import java.util.List;

import com.highfd.alarm.model.AlarmInfo;
import com.highfd.siteUser.model.SiteInfo;

public interface CrawTimerDAO {
	
	 public List<SiteInfo> baiduDemo();

	public void insertInfoSiteState(final int state,final String siteNumber)throws Exception ;
	
	public String selectEventStartTime(String number,String type) throws Exception;
	
	public void insertEventInfo(final AlarmInfo eventInfo) throws Exception;
	
	public void updateEventInfoEndTime(final AlarmInfo eventInfo) throws Exception;
	
	public void insertInfoMonitorState(final int threadNum,final int siteNum) throws Exception;
	
	public List<SiteInfo> getSiteUrl(String type);

}
