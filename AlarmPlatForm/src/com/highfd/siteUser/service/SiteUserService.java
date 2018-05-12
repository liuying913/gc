package com.highfd.siteUser.service;

import java.util.List;

import com.highfd.siteUser.model.SiteInfo;
import com.highfd.siteUser.model.ZoneInfo;

public interface SiteUserService {
	
	/**
	 * 搜索   省份  台站  部位  查询 台站信息
	 */
	public List<SiteInfo>  getSiteInfoList(String siteParam);
	
	/**
	 * 根据ID获得台站信息
	 */
	public SiteInfo getBaseSiteInfoById(final String siteNumber) throws Exception;
	
	/**
	 * 更新台站信息
	 */
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception;
	
	 //获得  省份  列表
    public List<ZoneInfo>  getZoneInfoList();
	
}