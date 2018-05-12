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
import com.highfd.siteUser.model.ZoneInfo;

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
		sql.append("select row_number() over( order by z.zone_name ) as rm,s.SITE_SELECT_UNIT,s.site_number,s.site_name,s.site_phone,s.site_person,"
				+ "s.site_gnss_ip,s.site_router_ip,s.site_dcups_ip,s.site_acups_ip,"
				+ "s.site_lat,s.SITE_Lon,s.SITE_ADDRESS,z.zone_code,z.zone_name,d.dic_cn_name,d.dic_code "
				+ "from site_info s,zone_info z ,dic_info d where s.site_zone = z.zone_code and s.SITE_DEPARTMENT =d.dic_code ");
		
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
		sql.append(" order by s.site_zone ");
		System.out.println(sql.toString());
		List<SiteInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	SiteInfo info = new SiteInfo();
		    	info.setRm(rs.getString("rm"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("site_name"));
		    	info.setSite_phone(rs.getString("site_phone"));
		    	info.setSite_person(rs.getString("site_person"));
		    	
		    	info.setGnssIp(rs.getString("site_gnss_ip"));
		    	info.setRouterIp(rs.getString("site_router_ip"));
		    	info.setAcupsIp(rs.getString("site_acups_ip"));
		    	info.setDcupsIp(rs.getString("site_dcups_ip"));
		    	
		    	info.setSiteLat(rs.getString("site_lat"));
		    	info.setSiteLng(rs.getString("SITE_Lon"));
		    	info.setAddress(rs.getString("SITE_ADDRESS"));
		    	info.setSiteUnit(rs.getString("SITE_SELECT_UNIT"));
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
	@SuppressWarnings({ "unchecked"})
	public SiteInfo getBaseSiteInfoById(final String siteNumber) throws Exception {
		String sql = "select s.*,z.zone_id,z.zone_code, z.zone_name from site_info s,zone_info z where s.site_zone=z.zone_code and site_number = ?";
		String[] code = new String[] { siteNumber };
		SiteInfo obj = (SiteInfo) jdbcTemplate.query(sql, code,
				new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							SiteInfo info = new SiteInfo();
					    	info.setSiteNumber(rs.getString("site_number"));
					    	info.setSiteName(rs.getString("site_name"));
					    	
					    	info.setSite_phone(rs.getString("site_phone"));
					    	info.setSite_person(rs.getString("site_person"));
					    	
					    	info.setZoneCode(rs.getString("zone_code"));//所属省份
					    	info.setZoneName(rs.getString("zone_name"));
					    	
					    	info.setDepartmentCode(rs.getString("SITE_DEPARTMENT"));//所属部委
					    	info.setDepartmentName(rs.getString("SITE_DEPARTMENT"));
					    	
					    	//info.setDepartmentCode(rs.getString("dic_en_name"));
					    	//info.setDepartmentName(rs.getString("dic_cn_name"));
					    	
					    	
					    	info.setGnssIp(rs.getString("site_gnss_ip"));
					    	info.setRouterIp(rs.getString("site_router_ip"));
					    	info.setAcupsIp(rs.getString("site_acups_ip"));
					    	info.setDcupsIp(rs.getString("site_dcups_ip"));
					    	
					    	info.setSiteUnit(rs.getString("SITE_SELECT_UNIT"));
					    	info.setSiteLat(rs.getString("site_lat"));
					    	info.setSiteLng(rs.getString("SITE_Lon"));
					    	info.setAddress(rs.getString("SITE_ADDRESS"));
					    	
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
	@SuppressWarnings({ "unchecked" })
	public void updateBaseSiteInfo(final SiteInfo siteInfo) throws Exception {
		String sql = "update site_info set SITE_NAME=?,SITE_ZONE=?,site_phone=?,site_person=?" +
				",site_router_ip=?,site_gnss_ip=?,site_acups_ip=?,site_dcups_ip=?,SITE_SELECT_UNIT=? where SITE_NUMBER=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, siteInfo.getSiteName());
				pstmt.setString(2, siteInfo.getZoneCode());
				pstmt.setString(3, siteInfo.getSite_phone());
				pstmt.setString(4, siteInfo.getSite_person());
				
				pstmt.setString(5, siteInfo.getRouterIp());
				pstmt.setString(6, siteInfo.getGnssIp());
				pstmt.setString(7, siteInfo.getAcupsIp());
				pstmt.setString(8, siteInfo.getDcupsIp());
				pstmt.setString(9, siteInfo.getSiteUnit());
				
				pstmt.setString(10, siteInfo.getSiteNumber());
				pstmt.execute();
				return null;
			}
		});
	}

	 //获得  省份  列表
    public List<ZoneInfo>  getZoneInfoList(){
		StringBuffer sql = new StringBuffer();
		sql.append("select t.zone_id,t.zone_code,t.zone_name from zone_info t where 1=1 and t.zone_code like '%0000'");
		@SuppressWarnings({ "unchecked"})
		List<ZoneInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	ZoneInfo info = new ZoneInfo();
		    	info.setZoneId(Integer.valueOf(rs.getString("zone_id")));
		    	info.setZoneCode(rs.getString("zone_code"));
		    	info.setZoneName(rs.getString("zone_name"));
				return info;
		   }
		});
		return eventInfoList;
	}
}
