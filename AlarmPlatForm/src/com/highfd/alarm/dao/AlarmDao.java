package com.highfd.alarm.dao;

import java.util.List;

import com.highfd.alarm.model.AlarmInfo;
import com.highfd.common.PageInfo;
import com.highfd.siteUser.model.SiteInfo;

public interface AlarmDao {

	public List<SiteInfo> getSiteUrl(String type);
	
	public void insertInfoSiteState(final String type,final SiteInfo siteInfo) throws Exception;
	
	public String selectEventStartTime(String number,String type) throws Exception;
	
	/**
	 * 添加事件信息
	 */
	public void insertEventInfo(final AlarmInfo ai) throws Exception;
	
	public void updateEventInfoEndTime(final AlarmInfo eventInfo) throws Exception;
	
	public void insertInfoMonitorState(final int threadNum,final int siteNum) throws Exception;
	
	/**
	 * 根据条件查询站点列表
	 */
	public List<SiteInfo> querySiteByCondition(String condition) throws Exception;
	
	/**
	 * 删除某段时间范围内的报警信息
	 */
	public void deleteUserInfoByTime() throws Exception;
	
	/**
	 * 查询报警信息
	 */
	public List<AlarmInfo>  queryAlarmInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo);
	
	/**
	 * 地图界面实时查询报警信息
	 */
	public List<AlarmInfo> getMapState();
	/**
	 * 界面DUPS实时查询报警信息
	 */
	public List<AlarmInfo> getMapDupsState();
	/**
	 * 界面AUPS实时查询报警信息
	 */
	public List<AlarmInfo> getMapAupsState();
	/**
	 * 实时查询路由器通信状态良好的站点
	 */
	public List<SiteInfo> queryRouteGoodStation() throws Exception;
}
