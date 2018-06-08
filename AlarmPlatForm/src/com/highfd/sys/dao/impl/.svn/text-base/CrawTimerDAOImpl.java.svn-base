package com.highfd.sys.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.alarm.model.AlarmInfo;
import com.highfd.siteUser.model.SiteInfo;
import com.highfd.sys.dao.CrawTimerDAO;

@Resource
public class CrawTimerDAOImpl implements CrawTimerDAO{
	
	
	@SuppressWarnings("unchecked")
	public List<SiteInfo> baiduDemo(){
		String sql = "select t.site_name,t.site_lng,t.site_lat from SITE_INFO t ";
		List<SiteInfo> itemList = (List<SiteInfo>) jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SiteInfo siteInfo = new SiteInfo();
					siteInfo.setSiteName(rs.getString("site_name"));
					siteInfo.setSiteLng(rs.getString("site_lng"));
					siteInfo.setSiteLat(rs.getString("site_lat"));
					System.out.println(siteInfo.getSiteName());
					return siteInfo;
				}
			});
		return itemList;
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


 
	 //创建记录时，用的id
	public int getNextID(JdbcTemplate jdbcTemplate, String sql){
		@SuppressWarnings("unchecked")
		Integer obj = (Integer) jdbcTemplate.query(sql,new ResultSetExtractor() {
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getInt(1)+1;
						}
						return null;
					}
				});
		return obj;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SiteInfo> getSiteUrl(String type){
		
		String sql = "select * from SITE_INFO t  " +
				"order by site_number ";
		
		List<SiteInfo> itemList = (List<SiteInfo>) jdbcTemplate.query(sql,new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SiteInfo siteInfo = new SiteInfo();
					siteInfo.setSiteName(rs.getString("SITE_NAME"));
					siteInfo.setSiteNumber(rs.getString("SITE_NUMBER"));
					//siteInfo.setGnssUrl(rs.getString(""));
					siteInfo.setGnssIp(rs.getString("SITE_GNSS_IP"));
					siteInfo.setRouterIp(rs.getString("SITE_ROUTER_IP"));
					return siteInfo;
				}
			});
		return itemList;
	}
	
	
	@SuppressWarnings("unchecked")
	public void insertInfoSiteState(final int siteState,final String siteNumber) throws Exception {//状态入库
		String sql = "INSERT INTO SITE_INFO_STATE (site_number,SITE_STATE,SITE_DATE)"
				+ "VALUES(?,?,?)";
		//String sql2 = "select recruit_data_SEQ.nextval from dual";
		//data.setId(getNextID(jdbcTemplate, sql2)+"");
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, siteNumber);
				pstmt.setInt(2, siteState);
				pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	public String selectEventStartTime(String number,String type) throws Exception {
		String sql = "select t.starttime from site_info_event t where t.endtime is null and t.sitenumber = ? and t.eventtype = ?";
		String[] param = new String[] { number,type };
		return (String) jdbcTemplate.query(sql, param,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							String starttime = rs.getString("starttime");
							return starttime;
						}
						return null;
					}
				});
	}
	

	/**
	 * 添加事件信息
	 */

	@SuppressWarnings("unchecked")
	public void insertEventInfo(final AlarmInfo ai) throws Exception {
		String sql = "INSERT INTO site_info_event (id,sitenumber,deviceid,eventtype,starttime)"
				+ "VALUES(?,?,?,?,?,?,?)";
		String sql2 = "select event_info_seq.nextval from dual";
		ai.setId(getNextID(jdbcTemplate, sql2));
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setInt(1, ai.getId());
				pstmt.setString(2, ai.getSiteNumber());
				pstmt.setString(3, "");
				pstmt.setString(4, ai.getAlarmId());
				pstmt.setTimestamp(5, new java.sql.Timestamp(ai.getStarttime()));
				pstmt.execute();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void updateEventInfoEndTime(final AlarmInfo eventInfo) throws Exception{
		String sql = "UPDATE site_info_event set endtime=? WHERE sitenumber=? AND eventtype=? AND endtime is null ";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setTimestamp(1, new java.sql.Timestamp(eventInfo.getEndtime()));
				pstmt.setString(2, eventInfo.getSiteNumber());
				pstmt.setString(3, eventInfo.getAlarmId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void insertInfoMonitorState(final int threadNum,final int siteNum) throws Exception {//状态入库
		String sql = "INSERT INTO MONITOR_STATE (id,THREADNUMBER,SITENUMBER,SITE_DATE)"
				+ "VALUES(?,?,?,?)";
		String sql2 = "select MONITOR_STATE_SEQ.nextval from dual";
		final String id = getNextID(jdbcTemplate, sql2)+"";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, id);
				pstmt.setInt(2, threadNum);
				pstmt.setInt(3, siteNum);
				pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
				return null;
			}
		});
	}
}
