package com.highfd.teqc.service;

import java.io.File;
import java.util.List;

import com.highfd.common.PageInfo;
import com.highfd.teqc.model.FileInfo;

public interface TEQCService {
	
	/**
	 * 查询所有的o文件，并进行计算入库
	 */
	public void analysisAll_o(final File dir) throws Exception;
	
	/**
	 * 根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	 */
	public List<FileInfo>  getFileInfoList(String startTime,String endTime,String fileYear,String fileFlag,final boolean isYear,PageInfo pageinfo);
	
}