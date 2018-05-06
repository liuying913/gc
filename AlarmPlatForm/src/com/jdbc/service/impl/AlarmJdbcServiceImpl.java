package com.jdbc.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.highfd.alarm.model.AlarmInfo;
import com.jdbc.dao.AlarmJdbcDao;
import com.jdbc.dao.impl.AlarmJdbcDaoImpl;
import com.jdbc.service.AlarmJdbcService;

public class AlarmJdbcServiceImpl implements AlarmJdbcService {

	private AlarmJdbcDao alarmJdbcDao = new AlarmJdbcDaoImpl();
	public static Connection getAlarmConnection() {

		Connection conn = null;
		try {
			String driver = "oracle.jdbc.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "gc","gc");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}
	
	public boolean updateEventInfoEndTime(AlarmInfo eventInfo,Connection conn) throws Exception{
		return alarmJdbcDao.updateEventInfoEndTime(eventInfo, conn);
	}
	public boolean insertEventInfo(final AlarmInfo eventInfo,Connection conn) throws Exception {
		return alarmJdbcDao.insertEventInfo(eventInfo, conn);
	}
	public String selectEventStartTime(String number,String type,Connection conn) throws Exception {
		return alarmJdbcDao.selectEventStartTime(number,type, conn);
	}
	public int getNextSiteInfoEventId(Connection conn) throws Exception{
		return alarmJdbcDao.getNextSiteInfoEventId(conn);
	}

}
