package com.highfd.vo;

public class DimSource {
	
	private String sourceid;
	private String sourcename;
	private String url;
	private String remark;
	public DimSource() {
		super();
	}
	
	public DimSource(String sourceid, String sourcename) {
		super();
		this.sourceid = sourceid;
		this.sourcename = sourcename;
	}

	public DimSource(String sourceid, String sourcename, String url) {
		super();
		this.sourceid = sourceid;
		this.sourcename = sourcename;
		this.url = url;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getSourcename() {
		return sourcename;
	}
	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
