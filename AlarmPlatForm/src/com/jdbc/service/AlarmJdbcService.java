package com.jdbc.service;

import java.sql.Connection;

import com.highfd.alarm.model.AlarmInfo;

public interface AlarmJdbcService {
	
	//public static Connection getAlarmConnection();
	
    public String selectEventStartTime(String number,String type,Connection conn) throws Exception;

	public boolean insertEventInfo(final AlarmInfo eventInfo,Connection conn) throws Exception;
	
	public boolean updateEventInfoEndTime(AlarmInfo eventInfo,Connection conn) throws Exception;
	
	public int getNextSiteInfoEventId(Connection conn) throws Exception;

}
