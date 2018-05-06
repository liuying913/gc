package com.highfd.siteUser.dao;

import java.util.List;

import com.highfd.siteUser.model.SiteInfo;

public interface SiteUserDao {
	
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
	
}