package com.highfd.sms.dao;

import java.util.List;

import com.highfd.common.PageInfo;
import com.highfd.sms.model.SmsInfo;

public interface SmsDao {
	
	/**
	 * 插入报警日志信息
	 */
	public void insertSmsInfo(final SmsInfo info) throws Exception;
	
	//查询短信信息
	public List<SmsInfo>  querySmsInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo);
	
	/**
	 * 根据ID获得短信详情
	 */
	public SmsInfo getSMSInfoById(final String id) throws Exception;
	
}