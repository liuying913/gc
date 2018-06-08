package com.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.highfd.alarm.model.AlarmInfo;
import com.jdbc.common.DBUtil;
import com.jdbc.dao.AlarmJdbcDao;

public class AlarmJdbcDaoImpl implements AlarmJdbcDao{

		public String selectEventStartTime(String number,String type,Connection conn) throws Exception {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select t.starttime from event_info t where t.endtime is null and t.sitenumber = ? and t.eventtype = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, number);
				stmt.setString(2, type);
				rs = stmt.executeQuery();
				while (rs.next()) {
					return rs.getString("starttime");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
			}
			return "";
		}
		
		//获得事件表的 序列id
		public int getNextSiteInfoEventId(Connection conn) throws Exception {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select event_info_SEQ.nextval as isd from dual";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
			}
			return -1;
		}
		
		
		public boolean insertEventInfo(final AlarmInfo ai,Connection conn) throws Exception {
			boolean flag = false;
			PreparedStatement stmt = null;
			try {
				String sql = "INSERT INTO event_info (id,sitenumber,deviceid,eventtype,starttime) VALUES(?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, ai.getId());
				stmt.setString(2, ai.getSiteNumber());
				stmt.setString(3, "");
				stmt.setString(4, ai.getAlarmId());
				stmt.setTimestamp(5, new java.sql.Timestamp(ai.getStarttime()));
				flag = stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
			}
			return flag;
		}
		
		public boolean updateEventInfoEndTime(final AlarmInfo eventInfo,Connection conn) throws Exception{
			PreparedStatement stmt = null;
			boolean flag = false;
			try {
				String sql = "UPDATE event_info set endtime=? WHERE sitenumber=? AND eventtype=? AND endtime is null ";
				stmt = conn.prepareStatement(sql);
				stmt.setTimestamp(1, new java.sql.Timestamp(eventInfo.getEndtime()));
				stmt.setString(2, eventInfo.getSiteNumber());
				stmt.setString(3, eventInfo.getAlarmId());
				flag = stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.closePsmt(stmt);
			}
			return flag;
		}
}
