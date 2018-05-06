package com.highfd.common;



public class SiteInfo {
	
	
	/**
	 * 基准站编码
	 */
	private String siteNumber;

	/**
	 * 基准站名称
	 */
	private String siteName;

	/**
	 * 基准站类别
	 */
	private String siteCategory;
	
	/**
	 * 路由器的IP地址
	 */
	private String routerIp;

	/**
	 * 所属区域
	 */
	private String siteZone;
	private String gnssUrl;
	private String gnssIp;
	
	
	private String siteCode;
	private String siteLng;
	private String siteLat;
	private int siteState;
	
	public int getSiteState() {
		return siteState;
	}
	public void setSiteState(int siteState) {
		this.siteState = siteState;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getSiteLng() {
		return siteLng;
	}
	public void setSiteLng(String siteLng) {
		this.siteLng = siteLng;
	}
	public String getSiteLat() {
		return siteLat;
	}
	public void setSiteLat(String siteLat) {
		this.siteLat = siteLat;
	}
	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getSiteCategory() {
		return siteCategory;
	}
	public void setSiteCategory(String siteCategory) {
		this.siteCategory = siteCategory;
	}
	public String getSiteZone() {
		return siteZone;
	}
	public void setSiteZone(String siteZone) {
		this.siteZone = siteZone;
	}
	public String getGnssUrl() {
		return gnssUrl;
	}
	public void setGnssUrl(String gnssUrl) {
		this.gnssUrl = gnssUrl;
	}
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	public String getGnssIp() {
		return gnssIp;
	}
	public void setGnssIp(String gnssIp) {
		this.gnssIp = gnssIp;
	}
}
