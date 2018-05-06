package com.highfd.alarm.service;

import java.util.List;

import com.highfd.alarm.model.AlarmInfo;
import com.highfd.common.PageInfo;
import com.highfd.siteUser.model.SiteInfo;

public interface AlarmService {
	
	/**
	 * 报警服务
	 */
	public void alarmService() throws Exception;
	
	public void insertInfoSiteState(final SiteInfo siteInfo) throws Exception;
	
	/**
	 * 根据条件查询站点列表
	 */
	public List<SiteInfo> querySiteByCondition(String condition) throws Exception;
	
	/**
	 * 查询报警信息
	 */
	public List<AlarmInfo>  queryAlarmInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo);
}