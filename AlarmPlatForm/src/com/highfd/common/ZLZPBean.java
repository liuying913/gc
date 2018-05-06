package com.highfd.common;

public class ZLZPBean {
	
	public String city;
	public String utfName;
	public String realName;
	
	
	public ZLZPBean(String city, String utfName, String realName) {
		super();
		this.city = city;
		this.utfName = utfName;
		this.realName = realName;
	}
	public ZLZPBean(String city, String utfName) {
		super();
		this.city = city;
		this.utfName = utfName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUtfName() {
		return utfName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setUtfName(String utfName) {
		this.utfName = utfName;
	}
	

}
