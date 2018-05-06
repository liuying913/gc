package com.highfd.sms.dao.impl;

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
import org.springframework.jdbc.core.RowMapper;

import com.highfd.common.PageInfo;
import com.highfd.common.TimeUtils;
import com.highfd.sms.dao.SmsDao;
import com.highfd.sms.model.SmsInfo;


@Resource
public class SmsDaoImpl implements SmsDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 插入报警日志信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insertSmsInfo(final SmsInfo info) {
		try{
			String sql = "INSERT INTO sms_INFO2 (id,sitenumber,eventId,phone,smsContent,isFlag,createTime)VALUES(SMS_INFO_SEQ.nextval,?,?,?,?,?,?)";
			jdbcTemplate.execute(sql, new PreparedStatementCallback(){
				public Object doInPreparedStatement(PreparedStatement pstmt)throws SQLException, DataAccessException {
					pstmt.setString(1, info.getSiteNumber());
					pstmt.setString(2, info.getEventId());
					pstmt.setString(3, info.getPhone());
					pstmt.setString(4, info.getSmsContent());
					pstmt.setString(5, info.getIsFlag());
					pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
					pstmt.execute();
					return null;
				}
			});
		}catch(Exception e){
			 e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public Integer  getListCount(String startTime,String endTime,String isFlag){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from "
				+ "SMS_INFO2 t,event_info e,site_info s,ZONE_INFO z,DIC_INFO d "
				+ "where t.eventid=e.id and e.sitenumber=s.site_number and s.site_zone=z.zone_code and s.site_department=d.dic_code ");
		
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.createTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		if(null!=isFlag && !"".equals(isFlag)){
			sql.append(" and t.isflag='"+isFlag+"'");
		}
		sql.append(" order by t.createtime desc");
System.out.println(sql);
		return jdbcTemplate.queryForInt(sql.toString());
		
	}
	//查询短信信息
	public List<SmsInfo>  querySmsInfoList(String startTime,String endTime,String isFlag,PageInfo pageinfo){
		pageinfo.setRecordCount(getListCount( startTime, endTime, isFlag));
		pageinfo.setPageCount(pageinfo.getRecordCount()%pageinfo.getPageSize()==0?pageinfo.getRecordCount()/pageinfo.getPageSize():pageinfo.getRecordCount()/pageinfo.getPageSize()+1);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM  ( SELECT A.*, ROWNUM RN  FROM ( ");
		sql.append("select s.site_name,s.site_number,z.zone_name,d.dic_cn_name,e.starttime,e.endtime,e.description,t.createtime,t.phone,t.isflag from "
				+ "SMS_INFO2 t,event_info e,site_info s,ZONE_INFO z,DIC_INFO d "
				+ "where t.eventid=e.id and e.sitenumber=s.site_number and s.site_zone=z.zone_code and s.site_department=d.dic_code ");
		
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and t.createTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		if(null!=isFlag && !"".equals(isFlag)){
			sql.append(" and t.isflag='"+isFlag+"'");
		}
		sql.append(" order by t.createtime desc");
		sql.append(") A ) WHERE RN between "+((pageinfo.getCurrentPage()-1)*pageinfo.getPageSize() + 1)+" and "+(pageinfo.getCurrentPage()*pageinfo.getPageSize())+"");
		System.out.println(sql);
		@SuppressWarnings({ "unchecked", "rawtypes"})
		List<SmsInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsInfo info = new SmsInfo();
				info.setId(rs.getString("RN"));
				info.setSiteName(rs.getString("site_name"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartMent(rs.getString("dic_cn_name"));
		    	info.setStartTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("starttime")));
		    	Timestamp endTime = rs.getTimestamp("endtime");
		    	if(null!=endTime){
		    		info.setEndTimeStr(TimeUtils.TimestampToString(endTime));
		    	}else{
		    		info.setEndTimeStr("");
		    	}
		    	
		    	info.setDescription(rs.getString("description"));
		    	info.setCreateTimeStr(TimeUtils.TimestampToString(rs.getTimestamp("createtime")));
		    	info.setPhone(rs.getString("phone"));
		    	
		    	String isFlag = rs.getString("isflag");
		    	if(null!=isFlag){
		    		if(isFlag.equals("true")){
		    			info.setIsFlag("发送成功");
		    		}else{
		    			info.setIsFlag("发送失败");
		    		}
		    	}else{
		    		info.setIsFlag("发送失败");
		    	}
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
}
