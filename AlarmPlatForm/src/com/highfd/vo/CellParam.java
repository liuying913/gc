package com.highfd.vo;


public class CellParam{
	public CellParam(){}
	String itemId = "";
    String charset = "";//编码格式
	
    String prefixDate = "";
    String prefixValue="";
    int type=0;
	//具体数据的抓取
	String daterule = "";//日期dom
	String dateregex = "";//日期格式
	String valuerule = "";//数值dom
	String valueregex = "";//数值格式
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getDaterule() {
		return daterule;
	}
	public void setDaterule(String daterule) {
		this.daterule = daterule;
	}
	public String getDateregex() {
		return dateregex;
	}
	public void setDateregex(String dateregex) {
		this.dateregex = dateregex;
	}
	public String getValuerule() {
		return valuerule;
	}
	public void setValuerule(String valuerule) {
		this.valuerule = valuerule;
	}
	public String getValueregex() {
		return valueregex;
	}
	public void setValueregex(String valueregex) {
		this.valueregex = valueregex;
	}
	public String getPrefixDate() {
		return prefixDate;
	}
	public void setPrefixDate(String prefixDate) {
		this.prefixDate = prefixDate;
	}
	public String getPrefixValue() {
		return prefixValue;
	}
	public void setPrefixValue(String prefixValue) {
		this.prefixValue = prefixValue;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	
}
