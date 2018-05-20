package com.highfd.siteUser.model;

public class AupsState {
	
	private String siteNumber;
	private String siteName;
	private int aupsState;
	
	public AupsState() {
		super();
	}
	
	public AupsState(String siteNumber, int aupsState) {
		super();
		this.siteNumber = siteNumber;
		this.aupsState = aupsState;
	}

	public String getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getAupsState() {
		return aupsState;
	}
	public void setAupsState(int aupsState) {
		this.aupsState = aupsState;
	}
	
}
