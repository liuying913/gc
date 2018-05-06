package com.highfd.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.highfd.common.PageInfo;
import com.highfd.sms.dao.SmsDao;
import com.highfd.sms.model.SmsInfo;
import com.highfd.sms.service.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	SmsDao dao;

	/**
	 * 插入报警日志信息
	 */
	public void insertSmsInfo(final SmsInfo info) throws Exception {
		dao.insertSmsInfo(info);
	}
	
	//查询短信信息
	public List<SmsInfo>  querySmsInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo){
		return dao.querySmsInfoList(startTime, endTime, isFlag, pageinfo);
	}
}