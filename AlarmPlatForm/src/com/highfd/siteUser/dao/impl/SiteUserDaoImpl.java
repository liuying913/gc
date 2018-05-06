package com.highfd.siteUser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.siteUser.dao.SiteUserDao;
import com.highfd.siteUser.model.SiteInfo;

@Resource
public class SiteUserDaoImpl implements SiteUserDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 搜索   省份  台站  部位  查询 台站信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SiteInfo>  getSiteInfoList(String siteParam){
		StringBuffer sql = new StringBuffer();
		sql.append("select row_number() over( order by z.zone_name ) as rm,s.site_number,s.site_name,s.site_phone,s.site_person,"
				+ "s.site_gnss_ip,s.site_router_ip,s.site_dcups_ip,s.site_acups_ip,"
				+ "s.site_lat,s.SITE_Lon,s.SITE_ADDRESS,z.zone_code,z.zone_name,d.dic_cn_name,d.dic_code "
				+ "from site_info s,zone_info z ,dic_info d where s.site_zone = z.zone_code and s.SITE_DEPARTMENT =d.dic_code");
		
		if(null!=siteParam && !"".equals(siteParam)){
			sql.append(" and (s.site_number like '%"+siteParam+"%' "
					+ "or s.site_name like '%"+siteParam+"%' "
					+ "or s.site_phone like '%"+siteParam+"%' "
					+ "or s.site_person like '%"+siteParam+"%' "
					+ "or s.site_gnss_ip like '%"+siteParam+"%' "
					+ "or s.site_router_ip like '%"+siteParam+"%' "
					+ "or s.site_dcups_ip like '%"+siteParam+"%' "
					+ "or s.site_acups_ip like '%"+siteParam+"%' "
					+ "or z.zone_code like '%"+siteParam+"%' "
					+ "or z.zone_name like '%"+siteParam+"%' "
					+ "or d.dic_cn_name like '%"+siteParam+"%' "
				+ "or d.dic_code like '%"+siteParam+"%')");
		}
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setRm(rs.getString("rm"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSmsPhone(rs.getString("site_phone"));
		    	info.setSmsPerson(rs.getString("site_person"));
		    	
		    	info.setGnssIp(rs.getString("site_gnss_ip"));
		    	info.setRouterIp(rs.getString("site_router_ip"));
		    	info.setAcupsIp(rs.getString("site_acups_ip"));
		    	info.setDcupsIp(rs.getString("site_dcups_ip"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
		    	info.setDepartmentCode(rs.getString("dic_code"));
		    	info.setDepartmentName(rs.getString("dic_cn_name"));
		    	
				return info;
		   }
		});
		return eventInfoList;
	}
	
	
	/**
	 * 根据ID获得台站信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SiteInfo getBaseSiteInfoById(final String siteNumber) throws Exception {
		String sql = "select * from site_info where site_number = ?";
		String[] code = new String[] { siteNumber };
		SiteInfo obj = (SiteInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							SiteInfo info = new SiteInfo();
					    	info.setSiteNumber(rs.getString("site_number"));
					    	info.setSiteName(rs.getString("site_name"));
					    	
					    	info.setSmsPhone(rs.getString("SMS_PHONE"));
					    	
					    	info.setZoneCode(rs.getString("zone_code"));//所属省份
					    	info.setZoneName(rs.getString("zone_code"));
					    	
					    	info.setDepartmentCode(rs.getString("SITE_DEPARTMENT"));//所属部委
					    	info.setDepartmentName(rs.getString("SITE_DEPARTMENT"));
					    	
					    	//info.setDepartmentCode(rs.getString("dic_en_name"));
					    	//info.setDepartmentName(rs.getString("dic_cn_name"));
					    	
					    	info.setSiteLat(rs.getString("site_lat"));
					    	info.setSiteLng(rs.getString("SITE_Lon"));
					    	info.setAddress(rs.getString("SITE_ADDRESS"));
					    	
					    	info.setOrder(Integer.valueOf(rs.getString("user_order")));
							return info;
						}
						return null;
					}
				});
		return obj;
	}
	
	
	
	/**
	 * 更新台站信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception {
		String sql = "update site_info set SITE_NAME=?,SITE_ZONE=?,SITE_DEPARTMENT=?,site_lat=?,SITE_Lon=?,SITE_ADDRESS=? where SITE_NUMBER=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, siteInfo.getSiteName());
				pstmt.setString(2, siteInfo.getZoneCode());
				pstmt.setString(3, siteInfo.getDepartmentCode());
				pstmt.setString(4, siteInfo.getSiteLat());
				pstmt.setString(5, siteInfo.getSiteLng());
				pstmt.setString(6, siteInfo.getAddress());
				pstmt.setString(7, siteInfo.getSiteNumber());
				pstmt.execute();
				return null;
			}
		});

	}
}
