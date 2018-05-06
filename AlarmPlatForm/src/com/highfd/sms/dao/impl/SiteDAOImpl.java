package com.highfd.sms.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import com.highfd.common.TimeUtils;
import com.highfd.sms.dao.SiteDAO;
import com.highfd.sms.model.EventFlag;
import com.highfd.sms.model.EventInfo;
import com.highfd.sms.model.GroupAuth;
import com.highfd.sms.model.SiteSMSInfo;
import com.highfd.sms.model.UserPower;

@Resource
public class SiteDAOImpl implements SiteDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//查询用户权限
    public GroupAuth queryGroup(String id) throws Exception{
		String sql = "select a.grounp_id,a.zone_id,a.dic_id,a.site_id from USERLIST u ,AUTH_GROUP_ACCESS a where u.id=a.cid and u.openid=?";
		String[] code = { id };
		@SuppressWarnings("unchecked")
		GroupAuth obj = (GroupAuth)this.jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				  if (rs.next()){
					  GroupAuth note = new GroupAuth();
					  note.setGrounpId(rs.getString("grounp_id"));
					  note.setZone_id(rs.getString("zone_id"));
					  note.setDic_id(rs.getString("dic_id"));
					  note.setSiteNumberStr(rs.getString("site_id"));
					  return note;
				 }
				 return null;
		    }
		});
		return obj;
    }
    
    
	//获得台站列表
    @SuppressWarnings("unchecked")
	public List<SiteSMSInfo> querySiteInfoList(String siteNumber ,String zoneCode,String dicCode) throws Exception{
		StringBuffer sql = new StringBuffer("select t.site_number from SITE_INFO t where 1=1 ");
		if(null!=siteNumber && !"".equals(siteNumber)){
			sql.append(" and (t.site_number= '"+siteNumber+"'  or t.site_name ='"+siteNumber+"' )");
		}
		if(null!=zoneCode && !"".equals(zoneCode)){
			sql.append(" and t.site_zone= '"+zoneCode+"' ");
		}
		if(null!=dicCode && !"".equals(dicCode)){
			sql.append(" and t.site_department= '"+dicCode+"' ");
		}
		
		List<SiteSMSInfo> siteInfoList = (List<SiteSMSInfo>) jdbcTemplate.query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 SiteSMSInfo note = new SiteSMSInfo();
				  note.setSiteNumber(rs.getString("site_number"));
				  return note;
			}
		});
		return siteInfoList;
    }
    
	//获得报警列表
    @SuppressWarnings("unchecked")
	public List<EventInfo> queryEventInfoList(String siteNumber ,String siteNumberStr,String eventTypeStr) throws Exception{
		StringBuffer sql = new StringBuffer("select e.sitenumber,s.site_name,e.starttime,e.endtime,e.eventtype,d.dic_cn_name  from site_info s, EVENT_INFO e,DIC_INFO d  where 1=1 and s.site_number=e.sitenumber and e.eventtype=d.dic_en_name ");
		if(null!=siteNumber && !"".equals(siteNumber)){
			sql.append(" and e.sitenumber= '"+siteNumber+"' ");
		}
		
		//台站列表的话
		StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberStr && !"".equals(siteNumberStr)){
			String[] split = siteNumberStr.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and e.sitenumber in("+siteNumberList+") ");
		}
		
		//报警类型
		StringBuffer eventTypeStrList = new StringBuffer();
		if(null!=eventTypeStr && !"".equals(eventTypeStr)){
			String[] split = eventTypeStr.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					eventTypeStrList.append("'"+split[i]+"'");
				}else{
					eventTypeStrList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and e.eventtype in("+eventTypeStrList+") ");
		}
		
		List<EventInfo> eventInfoList = (List<EventInfo>) jdbcTemplate.query(sql.toString(), new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				EventInfo note = new EventInfo();
				note.setSiteName(rs.getString("site_name"));
				note.setSiteNumber(rs.getString("sitenumber"));
				note.setStartTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("starttime")));  
				if(null==rs.getTimestamp("endtime")){
					note.setEndTimeStr("");
				}else{
					note.setEndTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("endtime")));
				}
				note.setEvnetType(rs.getString("eventtype"));
				note.setEventTypeName(rs.getString("dic_cn_name"));
				  return note;
			}
		});
		return eventInfoList;
    }
    
    
    
    
    
    
    
    //获得符合报警时长的报警信息 2个小时
    @SuppressWarnings("unchecked")
	public List<EventInfo> queryEventForTwoHours() throws Exception{
    	String sql = "select e.*, s.site_number,s.site_name,s.site_pro_phone,s.site_cha_phone,s.site_phone  from event_info e, site_info s " +
		"where e.sitenumber = s.site_number   " +
		"   and (ceil((To_date(to_char(e.endtime, 'yyyy-mm-dd hh24-mi-ss'), 'yyyy-mm-dd hh24-mi-ss') - To_date(to_char(e.starttime, 'yyyy-mm-dd hh24-mi-ss'), 'yyyy-mm-dd hh24-mi-ss')) * 24 * 60 * 60) > 7200 or (e.endtime is null and ceil((To_date(to_char(sysdate, 'yyyy-mm-dd hh24-mi-ss'), 'yyyy-mm-dd hh24-mi-ss') - To_date(to_char(e.starttime, 'yyyy-mm-dd hh24-mi-ss'),'yyyy-mm-dd hh24-mi-ss')) * 24 * 60 * 60) > 7200)) " +
		"and (e.deviceid is null or e.deviceid =1) ";
		
		List<EventInfo> siteInfoList = (List<EventInfo>) jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 EventInfo note = new EventInfo();
				 note.setId(rs.getString("id"));
				 note.setSiteNumber(rs.getString("site_number"));
				 note.setEvnetType(rs.getString("eventtype"));
				 note.setSiteNumber(rs.getString("site_number"));
				 if(note.getSiteNumber().indexOf("-ZL")>-1){
				 	 note.setSiteName(rs.getString("site_name")+"重力站");
				 }else{
					 note.setSiteName(rs.getString("site_name")+"站");
				 }
				 note.setStartTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("starttime")));  
				 if(null==rs.getTimestamp("endtime")){
					 note.setEndTimeStr(null);
				 }else{
					 note.setEndTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("endtime")));
				 }
				 note.setDescription(rs.getString("description"));
				 if(note.getDescription().indexOf("通信异常")>-1){
					 note.setDescription("通信异常（台站断电或线路故障）");
				 }else if(note.getDescription().indexOf("接收机中断")>-1){
					 note.setDescription(note.getDescription()+"（接收机故障）");
				 }
				 note.setSite_phone(rs.getString("site_phone"));
				 note.setSite_cha_phone(rs.getString("site_cha_phone"));
				 note.setSite_pro_phone(rs.getString("site_pro_phone"));
				 note.setDeviceid(rs.getString("deviceid"));
				 return note;
			}
		});
		return siteInfoList;
    }
    
    
    
    
    
	//将发生完的报警修改设置
	@SuppressWarnings("unchecked")
	public void updateEventInfo(final EventFlag eventFlag) throws Exception {
		String sql = "UPDATE event_info set deviceid=? WHERE id =? ";
		
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, eventFlag.getTag());
				pstmt.setString(2, eventFlag.getId());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	
	
	/**
	 * 跟外网对接，获得未完成报警的id集合
	 */
	@SuppressWarnings("unchecked")
	public String queryEventInfoIdList(String params) throws Exception {
		String sql = "select t.id from EVENT_INFO t where t.endtime is null ";
		final StringBuffer sb = new StringBuffer("");
		List<EventInfo> infoList = (List<EventInfo>) jdbcTemplate.query(sql,new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						EventInfo info = new EventInfo();
						info.setId(rs.getString("id"));
						sb.append(info.getId()+",");
						return info;
					}
				});
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
		//插入从  外网获得的 监控报警信息
		@SuppressWarnings("unchecked")
		public void insertEventInfo(final List<EventInfo> list) throws Exception {
			String sql = "INSERT INTO EVENT_INFO (id,sitenumber,starttime,endtime,eventtype,description)VALUES(?,?,?,?,?,?)";
			Object execute = jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
					for(int i=0; i<list.size(); i++){
						EventInfo info = list.get(i);
						prapStmt.setString(1, info.getId());
						prapStmt.setString(2, info.getSiteNumber());
						try {
							prapStmt.setTimestamp(3, TimeUtils.getTempByAllTime(info.getStartTimeStr()));
							
							if(null!=info.getEndTimeStr() && !"".equals(info.getEndTimeStr())){
								prapStmt.setTimestamp(4, TimeUtils.getTempByAllTime(info.getEndTimeStr()));
							}else{
								prapStmt.setTimestamp(4, null);
							}
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						prapStmt.setString(5, info.getEvnetType());
						prapStmt.setString(6,info.getDescription());
						
						prapStmt.addBatch(); 
						if(i%1000 == 0){
							prapStmt.executeBatch();
					    }
					}
					return prapStmt.executeBatch().length;
				}
			});
		}
		
		
		
		//将发生完的报警修改设置
		@SuppressWarnings("unchecked")
		public void updateEventInfoEndTime(final EventInfo info) throws Exception {
			String sql = "UPDATE event_info set endtime=? WHERE id =? ";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					try {
						pstmt.setTimestamp(1, TimeUtils.getTempByAllTime(info.getEndTimeStr()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pstmt.setString(2, info.getId());
					pstmt.execute();
					return null;
				}
			});
		}
		
		
		//删除 时间短的报警
		@SuppressWarnings("unchecked")
		public void deleteUserInfo(final int id) throws Exception {
			String sql = "delete from event_info  where  ceil((To_date(to_char(endtime,'yyyy-mm-dd hh24-mi-ss') , 'yyyy-mm-dd hh24-mi-ss') - To_date(to_char(starttime,'yyyy-mm-dd hh24-mi-ss')  , 'yyyy-mm-dd hh24-mi-ss')) * 24 * 60 * 60)<181";
			jdbcTemplate.execute(sql, new PreparedStatementCallback() {
				public Object doInPreparedStatement(PreparedStatement pstmt)
						throws SQLException, DataAccessException {
					pstmt.execute();
					return null;
				}
			});
		}

	    
		//查询所有用户的权限
		@SuppressWarnings("unchecked")
		public List<UserPower> queryUserPower() throws Exception{
			StringBuffer sql = new StringBuffer("select t.intro,t.openid,a.grounp_id,a.dic_id,a.zone_id,a.site_id,t.openid from USERLIST t,AUTH_GROUP_ACCESS a where t.id= a.cid ");
			List<UserPower> siteInfoList = (List<UserPower>) jdbcTemplate.query(sql.toString(), new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					UserPower note = new UserPower();
					note.setGrounpId(rs.getString("grounp_id"));
					note.setZone_id(rs.getString("zone_id"));
					note.setDic_id(rs.getString("dic_id"));
					note.setSite_Array(rs.getString("site_id"));
					note.setOpenid(rs.getString("openid"));
					note.setUserName(rs.getString("intro"));
					return note;
				}
			});
			return siteInfoList;
	    }
		
		
		
		//查询 台站列表（部位、省份）
		@SuppressWarnings("unchecked")
		public List<SiteSMSInfo> queryDepartZoneSiteList() throws Exception{
			StringBuffer sql = new StringBuffer("select s.site_zone, s.site_department,s.site_number from DIC_INFO t,site_info s where s.site_department=t.dic_code and t.dic_type_code='dic-001'");
			List<SiteSMSInfo> siteInfoList = (List<SiteSMSInfo>) jdbcTemplate.query(sql.toString(), new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					SiteSMSInfo note = new SiteSMSInfo();
					note.setSiteDic(rs.getString("site_department"));
				    note.setSiteZone(rs.getString("site_zone"));
				    note.setSiteNumber(rs.getString("site_number"));
					return note;
				}
			});
			return siteInfoList;
	    }
}
