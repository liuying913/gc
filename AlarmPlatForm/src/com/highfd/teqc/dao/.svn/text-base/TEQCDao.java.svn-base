package com.highfd.teqc.dao;

import java.util.List;

import com.highfd.common.PageInfo;
import com.highfd.teqc.model.FileInfo;

public interface TEQCDao {
	
	/**
	 * 通过文件名称 查询文件是否存在
	 */
	public FileInfo  select_30SData_Files(final String year,final FileInfo fileInfo);
		
	/**
	 * 30S日常数据整理入库**************************************************************************************************
	 */
	public void insert_30SData_Files(final String year,final List<FileInfo> fileList);
		
		
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	public void update_30S_apply(final String year,final FileInfo fileInfo);
	
	
	/**
	 * 根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	 */
	public List<FileInfo>  getFileInfoList(String startTime,String endTime,String fileYear,String fileFlag,final boolean isYear,PageInfo pageinfo);
	
}