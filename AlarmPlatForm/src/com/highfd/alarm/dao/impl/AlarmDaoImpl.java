package com.highfd.alarm.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.alarm.dao.AlarmDao;
import com.highfd.alarm.model.AlarmInfo;
import com.highfd.common.PageInfo;
import com.highfd.common.TimeUtils;
import com.highfd.siteUser.model.SiteInfo;

	public class AlarmDaoImpl implements AlarmDao{
		
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
			@SuppressWarnings({ "unchecked" })
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

		@SuppressWarnings({ "unchecked", })
		public List<SiteInfo> getSiteUrl(String type){
			
			String sql = "select SITE_NAME,SITE_NUMBER,SITE_GNSS_IP,SITE_ROUTER_IP from SITE_INFO t order by site_number ";
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
		public void insertInfoSiteState(final String type,final SiteInfo siteInfo) throws Exception {//状态入库
			String sql = "INSERT INTO SITE_"+type+"_STATE (SITE_NUMBER,SITE_STATE,SITE_DATE)"
					+ "VALUES(?,?,?)";
			//String sql2 = "select recruit_data_SEQ.nextval from dual";
			//data.setId(getNextID(jdbcTemplate, sql2)+"");
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
					pstmt.setString(1, siteInfo.getSiteNumber());
					if(type.indexOf("info")>-1){
						pstmt.setInt(2, siteInfo.getRouteState());
					}else if(type.indexOf("aups")>-1){
						pstmt.setInt(2, siteInfo.getAupsState());
					}else if(type.indexOf("dups")>-1){
						pstmt.setInt(2, siteInfo.getDupsState());
					}
					pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					pstmt.execute();
					return null;
				}
			});
		}

		@SuppressWarnings({ "unchecked" })
		public void insertInfoSiteState2(final SiteInfo siteInfo) throws Exception {//状态入库
			String sql = "INSERT INTO SITE_INFO_STATE (SITE_NUMBER,ROUTE_STATE,aups_state,dups_state,SITE_DATE)"
					+ "VALUES(?,?,?,?,?)";
			//String sql2 = "select recruit_data_SEQ.nextval from dual";
			//data.setId(getNextID(jdbcTemplate, sql2)+"");
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
					pstmt.setString(1, siteInfo.getSiteNumber());
					pstmt.setInt(2, siteInfo.getRouteState());
					
					pstmt.setInt(3, siteInfo.getAupsState());
					pstmt.setInt(4, siteInfo.getDupsState());
					pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
					pstmt.execute();
					return null;
				}
			});
		}
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String selectEventStartTime(String number,String type) throws Exception {
			String sql = "select t.starttime from EVENT_INFO t where t.endtime is null and t.sitenumber = ? and t.eventtype = ?";
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
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void insertEventInfo(final AlarmInfo ai) throws Exception {
			String sql = "INSERT INTO EVENT_INFO (id,sitenumber,description,eventtype,starttime)"
					+ "VALUES(EVENT_INFO_SEQ.nextval,?,?,?,?)";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.setString(1, ai.getSiteNumber());
					pstmt.setString(2, ai.getName());
					pstmt.setString(3, ai.getAlarmId());
					pstmt.setTimestamp(4, new java.sql.Timestamp(ai.getStarttime()));
					pstmt.execute();
					return null;
				}
			});
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void updateEventInfoEndTime(final AlarmInfo eventInfo) throws Exception{
			String sql = "UPDATE EVENT_INFO set endtime=? WHERE sitenumber=? AND eventtype=? AND endtime is null ";
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
		
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void insertInfoMonitorState(final int threadNum,final int siteNum) throws Exception {//状态入库
			String sql = "INSERT INTO MONITOR_STATE (id,THREADNUMBER,SITENUMBER,SITE_DATE)"
					+ "VALUES(MONITOR_STATE_SEQ.nextval,?,?,?)";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
					pstmt.setInt(1, threadNum);
					pstmt.setInt(2, siteNum);
					pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					pstmt.execute();
					return null;
				}
			});
		}
		
		
		/**
		 * 根据条件查询站点列表
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<SiteInfo> querySiteByCondition(String condition) throws Exception {
			String sql = "select * from site_info where 1=1";
			if (condition != null && !"".equals(condition)) {
				sql = sql + condition;
			}
			return (List<SiteInfo>) jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SiteInfo siteInfo = new SiteInfo();
					siteInfo.setSiteNumber(rs.getString("site_number"));
					siteInfo.setSiteName(rs.getString("site_name"));
					
					siteInfo.setSiteCategory(rs.getString("site_category"));
					siteInfo.setSiteZone(rs.getString("site_zone"));
					
					siteInfo.setGnssIp(rs.getString("site_gnss_ip"));
					siteInfo.setNassIp(rs.getString("site_nass_ip"));
					siteInfo.setRouterIp(rs.getString("site_router_ip"));
					siteInfo.setAcupsIp(rs.getString("site_acups_ip"));
					siteInfo.setDcupsIp(rs.getString("site_dcups_ip"));
					return siteInfo;
				}
			});
		}
		
		
		
		
		/**
		 * 删除某段时间范围内的报警信息
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void deleteUserInfoByTime() throws Exception {
			String sql = "delete from event_info  where  ceil((To_date(to_char(endtime,'yyyy-mm-dd hh24-mi-ss') , 'yyyy-mm-dd hh24-mi-ss') - To_date(to_char(starttime,'yyyy-mm-dd hh24-mi-ss')  , 'yyyy-mm-dd hh24-mi-ss')) * 24 * 60 * 60)<181";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.execute();
					return null;
				}
			});
		}

		@SuppressWarnings("deprecation")
		public Integer  getListCount(String startTime,String endTime,String isFlag){
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from event_info e,site_info s,zone_info z where s.site_number=e.sitenumber and z.zone_code=s.site_zone ");
			
			//时间段
			if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
				
				sql.append(" and ((e.starttime>=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss') AND e.starttime<=to_date('"+endTime+"','yyyy-MM-dd hh24:mi:ss')) "
						+ "or (e.endtime>=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss') AND e.endtime<=to_date('"+endTime+"','yyyy-MM-dd hh24:mi:ss')) "
						+ "or (e.endtime is null AND e.starttime<=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss')))");
			}
			
			if(null!=isFlag && !"".equals(isFlag)){
				if(isFlag.equals("true")){
					sql.append(" and e.endtime is not null");
				}else if(isFlag.equals("false")){
					sql.append(" and e.endtime is null");
				}
			}
			sql.append(" order by e.starttime desc");
	System.out.println(sql);
			return jdbcTemplate.queryForInt(sql.toString());
			
		}
		/**
		 * 查询报警信息
		 */
		public List<AlarmInfo>  queryAlarmInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo){
			pageinfo.setRecordCount(getListCount( startTime, endTime, isFlag));
			pageinfo.setPageCount(pageinfo.getRecordCount()%pageinfo.getPageSize()==0?pageinfo.getRecordCount()/pageinfo.getPageSize():pageinfo.getRecordCount()/pageinfo.getPageSize()+1);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM  ( SELECT A.*, ROWNUM RN  FROM ( ");
			sql.append("select z.zone_name,s.site_name,s.site_number,e.description,e.starttime,e.endtime from event_info e,site_info s,zone_info z where s.site_number=e.sitenumber and z.zone_code=s.site_zone ");
			
			//时间段
			if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
				
				sql.append(" and ((e.starttime>=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss') AND e.starttime<=to_date('"+endTime+"','yyyy-MM-dd hh24:mi:ss')) "
						+ "or (e.endtime>=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss') AND e.endtime<=to_date('"+endTime+"','yyyy-MM-dd hh24:mi:ss')) "
						+ "or (e.endtime is null AND e.starttime<=to_date('"+startTime+"','yyyy-MM-dd hh24:mi:ss')))");
			}
			
			if(null!=isFlag && !"".equals(isFlag)){
				if(isFlag.equals("true")){
					sql.append(" and e.endtime is not null");
				}else if(isFlag.equals("false")){
					sql.append(" and e.endtime is null");
				}
			}
			sql.append(" order by e.starttime desc");
			sql.append(") A ) WHERE RN between "+((pageinfo.getCurrentPage()-1)*pageinfo.getPageSize() + 1)+" and "+(pageinfo.getCurrentPage()*pageinfo.getPageSize())+"");
	System.out.println(sql);
			@SuppressWarnings({ "unchecked"})
			List<AlarmInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					AlarmInfo info = new AlarmInfo();
					info.setRm(rs.getString("rn"));
					info.setZoneName(rs.getString("zone_name"));
					info.setSiteName(rs.getString("site_name"));
			    	info.setSiteNumber(rs.getString("site_number"));
			    	info.setName(rs.getString("description"));
			    	
			    	info.setStartTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("starttime")));
			    	Timestamp endTime = rs.getTimestamp("endtime");
			    	if(null!=endTime){
			    		info.setEndTimeStr(TimeUtils.TimestampToString(endTime));
			    	}else{
			    		info.setEndTimeStr("");
			    	}
					return info;
			   }
			});
			return eventInfoList;
		}
		
		

		/**
		 * 界面实时查询报警信息
		 */
		@SuppressWarnings("unchecked")
		public List<AlarmInfo> getMapState() {
			String sql = "select c.site_name,a.site_state from site_info_state a ,(select s.site_number as siteNum,max(s.site_date) as dates from site_info_state s group by s.site_number) b, site_info c where a.site_number=b.siteNum and a.site_date=b.dates and a.site_number=c.site_number and  a.site_number not like '%-ZL'";
			List<AlarmInfo> list = jdbcTemplate.query(sql,new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					AlarmInfo info = new AlarmInfo();
					info.setName(rs.getString("site_name"));
					info.setSiteState(rs.getString("site_state"));
					return info;
				}
			});
			return list;
		}
		
		
		/**
		 * 界面AUPS实时查询报警信息
		 */
		@SuppressWarnings("unchecked")
		public List<AlarmInfo> getMapAupsState() {
			String sql = "select s.site_name,d.site_state from site_info s left join (select a.site_number,a.site_state from site_aups_state a ,(select s.site_number as siteNum,max(s.site_date) as dates from site_aups_state s group by s.site_number) b where a.site_number=b.siteNum and a.site_date=b.dates ) d on s.site_number=d.site_number";
			List<AlarmInfo> list = jdbcTemplate.query(sql,new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					AlarmInfo info = new AlarmInfo();
					info.setName(rs.getString("site_name"));
					if(null==rs.getString("site_state")){
						info.setSiteState("11");
					}else{
						info.setSiteState(rs.getString("site_state"));
					}
					
					return info;
				}
			});
			return list;
		}
		/**
		 * 界面DUPS实时查询报警信息
		 */
		@SuppressWarnings("unchecked")
		public List<AlarmInfo> getMapDupsState() {
			String sql = "select s.site_name,d.site_state from site_info s left join (select a.site_number,a.site_state from site_dups_state a ,(select s.site_number as siteNum,max(s.site_date) as dates from site_dups_state s group by s.site_number) b where a.site_number=b.siteNum and a.site_date=b.dates ) d on s.site_number=d.site_number";
			List<AlarmInfo> list = jdbcTemplate.query(sql,new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					AlarmInfo info = new AlarmInfo();
					info.setName(rs.getString("site_name"));
					if(null==rs.getString("site_state")){
						info.setSiteState("11");
					}else{
						info.setSiteState(rs.getString("site_state"));
					}
					
					return info;
				}
			});
			return list;
		}
		
		/**
		 * 实时查询路由器通信状态良好的站点
		 */
		@SuppressWarnings("unchecked")
		public List<SiteInfo> queryRouteGoodStation() throws Exception {
			String sql = "select s.site_acups_ip,s.site_dcups_ip,d.site_number from SITE_INFO_STATE d,site_info s,(select t.site_number,max(t.site_date) as maxDate from SITE_INFO_STATE t where sysdate-t.site_date<=60/24  group by t.site_number) m where m.site_number=s.site_number and d.site_number=m.site_number and d.site_date=m.maxDate and d.site_state!=22";
			return (List<SiteInfo>) jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SiteInfo siteInfo = new SiteInfo();
					siteInfo.setSiteNumber(rs.getString("site_number"));
					siteInfo.setAcupsIp(rs.getString("site_acups_ip"));
					siteInfo.setDcupsIp(rs.getString("site_dcups_ip"));
					return siteInfo;
				}
			});
		}

	}
