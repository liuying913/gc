package com.highfd.vo;

public class JsonBean{
	private String name;
	private String id;
	private String nameCount;
	public JsonBean() {
		super();
	}
	public JsonBean(String name) {
		super();
		this.name = name;
	}

	public JsonBean(String name, String id, String nameCount) {
		super();
		this.name = name;
		this.id = id;
		this.nameCount = nameCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameCount() {
		return nameCount;
	}
	public void setNameCount(String nameCount) {
		this.nameCount = nameCount;
	}
}