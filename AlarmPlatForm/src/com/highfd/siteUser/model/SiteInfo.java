package com.highfd.siteUser.model;

public class SiteInfo {
	/**
	 * 序号
	 */
	private String rm;
	/**
	 * 基准站编码
	 */
	private String siteNumber;
	private String siteName;
	
	
	/**
	 * 报警的状态
	 */
	private int routeState;//ping后的状态
	private int aupsState;
	private int dupsState;
	/**
	 * 基准站类别
	 */
	private String siteType;//1：基准站 ，2：其他站
	
	private String smsPhone;//发送的电话号码
	private String smsPerson;//发送的人名
	
	private String zoneCode;
	private String zoneName;
	private String departmentCode;
	private String departmentName;
	
	private String siteCategory;

	private String siteLat;//纬度
	private String siteLng;//经度
	
	private String address;
	
	private String siteUnit;
	private String siteProgect;
	private String siteOldNumber;
	
	private int order;
	private String siteZone;

	/**
	 * 承建部委
	 */
	private String siteDepartment;

	/**
	 * GNSS站点的类型
	 */
	private String siteGnssType;

	/**
	 * GNSS的IP地址
	 */
	private String gnssIp;

	/**
	 * NASS盘的IP地址
	 */
	private String nassIp;

	/**
	 * 路由器的IP地址
	 */
	private String routerIp;

	/**
	 * 交流UPS的IP地址
	 */
	private String acupsIp;

	/**
	 * 支流UPS的IP地址
	 */
	private String dcupsIp;


	public String getSiteZone() {
		return siteZone;
	}
	public void setSiteZone(String siteZone) {
		this.siteZone = siteZone;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	public String getSmsPerson() {
		return smsPerson;
	}
	public void setSmsPerson(String smsPerson) {
		this.smsPerson = smsPerson;
	}
	public String getSiteUnit() {
		return siteUnit;
	}
	public void setSiteUnit(String siteUnit) {
		this.siteUnit = siteUnit;
	}
	public String getSiteProgect() {
		return siteProgect;
	}
	public void setSiteProgect(String siteProgect) {
		this.siteProgect = siteProgect;
	}
	public String getSiteOldNumber() {
		return siteOldNumber;
	}
	public void setSiteOldNumber(String siteOldNumber) {
		this.siteOldNumber = siteOldNumber;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getZoneName() {
		return zoneName;
	}
	public int getRouteState() {
		return routeState;
	}
	public void setRouteState(int routeState) {
		this.routeState = routeState;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getSiteType() {
		return siteType;
	}
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null){  
            return false;  
        }else {           
             if(this.getClass() == obj.getClass()){  
            	 SiteInfo site = (SiteInfo) obj;  
                    if(this.getSiteNumber().equals(site.getSiteNumber())){  
                        return true;  
                    }else{  
                        return false;  
                    }  
            }else{  
                return false;  
            }  
        }        
	}
	public SiteInfo(String siteNumber) {
		super();
		this.siteNumber = siteNumber;
	}
	public SiteInfo() {
		super();
	}
	public String getSmsPhone() {
		return smsPhone;
	}
	public void setSmsPhone(String smsPhone) {
		this.smsPhone = smsPhone;
	}
	public String getSiteDepartment() {
		return siteDepartment;
	}
	public void setSiteDepartment(String siteDepartment) {
		this.siteDepartment = siteDepartment;
	}
	public String getSiteGnssType() {
		return siteGnssType;
	}
	public void setSiteGnssType(String siteGnssType) {
		this.siteGnssType = siteGnssType;
	}
	public String getGnssIp() {
		return gnssIp;
	}
	public void setGnssIp(String gnssIp) {
		this.gnssIp = gnssIp;
	}
	public String getNassIp() {
		return nassIp;
	}
	public void setNassIp(String nassIp) {
		this.nassIp = nassIp;
	}
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	public String getAcupsIp() {
		return acupsIp;
	}
	public void setAcupsIp(String acupsIp) {
		this.acupsIp = acupsIp;
	}
	public String getDcupsIp() {
		return dcupsIp;
	}
	public void setDcupsIp(String dcupsIp) {
		this.dcupsIp = dcupsIp;
	}
	public int getAupsState() {
		return aupsState;
	}
	public void setAupsState(int aupsState) {
		this.aupsState = aupsState;
	}
	public int getDupsState() {
		return dupsState;
	}
	public void setDupsState(int dupsState) {
		this.dupsState = dupsState;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
}
