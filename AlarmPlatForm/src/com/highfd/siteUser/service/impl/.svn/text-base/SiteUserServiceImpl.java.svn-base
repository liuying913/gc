package com.highfd.siteUser.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.highfd.siteUser.dao.SiteUserDao;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.siteUser.model.ZoneInfo;
import com.highfd.siteUser.service.SiteUserService;

@Service
public class SiteUserServiceImpl implements SiteUserService {

	@Autowired
	SiteUserDao dao;
	
	/**
	 * 搜索   省份  台站  部位  查询 台站信息
	 */
	public List<SiteInfo>  getSiteInfoList(String siteParam){
		return dao.getSiteInfoList(siteParam);
	}
	
	/**
	 * 根据站点编号 获得台站信息
	 */
	public SiteInfo getBaseSiteInfoById(final String siteNumber) throws Exception{
		return dao.getBaseSiteInfoById(siteNumber);
	}
	
	/**
	 * 更新台站信息
	 */
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception{
		dao.updateBaseSiteInfo(siteInfo);
	}
	
	 //获得  省份  列表
    public List<ZoneInfo>  getZoneInfoList(){
    	return dao.getZoneInfoList();
    }
}