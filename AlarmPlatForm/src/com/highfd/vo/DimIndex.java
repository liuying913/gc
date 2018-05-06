package com.highfd.vo;
/**
 * 指标bean
 * @author Administrator
 *
 */
public class DimIndex {
	private String indexId;
	private String indexName;
	private String baseIndexId;
	private String remark;
	public DimIndex() {
		super();
	}
	
	public DimIndex(String indexId, String indexName) {
		super();
		this.indexId = indexId;
		this.indexName = indexName;
	}

	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getBaseIndexId() {
		return baseIndexId;
	}
	public void setBaseIndexId(String baseIndexId) {
		this.baseIndexId = baseIndexId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
