package com.highfd.teqc.dao.impl;

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
import com.highfd.teqc.dao.TEQCDao;
import com.highfd.teqc.model.FileInfo;

@Resource
public class TEQCDaoImpl implements TEQCDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//通过文件名称 查询文件是否存在
	public FileInfo  select_30SData_Files(final String year,final FileInfo fileInfo){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from file_info  where FILENAME='"+fileInfo.getFileName()+"'");
		@SuppressWarnings({ "unchecked", "rawtypes"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		    public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setId(Integer.valueOf(rs.getString("id")));
				return info;
		   }
		});
		if(null==eventInfoList || eventInfoList.size()==0){
			return null;
		}else{
			return eventInfoList.get(0);
		}
	}
	
	
	
	/**
	 * 30S日常数据整理入库**************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert_30SData_Files(final String year,final List<FileInfo> fileList) {
		/*BasicDataSource basicDataSource = (BasicDataSource)jdbcTemplate.getDataSource();
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");
		basicDataSource.setDefaultAutoCommit(false);
		System.out.println(basicDataSource.getDefaultAutoCommit()+"  ML");*/
		
		String sql = "INSERT INTO file_info (id,fileYear,fileDayYear,site_number,fileName,filePath,fileSize,fileflag," +
				"systemTime,fileCreateTime,   type,ephem_number,    mp1,mp2,o_slps) VALUES(file_info_SEQ.nextval,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),  ?,?,?,  ?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement prapStmt) throws SQLException, DataAccessException {
				for(int i=0; i<fileList.size(); i++){
					FileInfo info = fileList.get(i);
					prapStmt.setString(1, info.getFileYear());
					prapStmt.setString(2, info.getFileDayYear());
					prapStmt.setString(3, info.getSiteNumber());
					prapStmt.setString(4, info.getFileName());
					prapStmt.setString(5, info.getFilePath());
					prapStmt.setDouble(6, info.getFileSize());
					prapStmt.setInt(7, info.getFileFlag());
					prapStmt.setString(8, info.getSystemStr());
					
					prapStmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
					prapStmt.setString(10, info.getType());
					prapStmt.setString(11, info.getEphem_number());
					
					prapStmt.setString(12, info.getMp1());
					prapStmt.setString(13, info.getMp2());
					prapStmt.setString(14, info.getO_slps());
					
					prapStmt.addBatch(); 
					if(i%10000 == 0){
						prapStmt.executeBatch();
				    }
				}
				return prapStmt.executeBatch().length;
			}
		});
	}
	
	
	
	
	
	/**
	 * 30日常数据补充整理*************************************************************************************************
	 */
	@SuppressWarnings({ "unchecked"})
	public void update_30S_apply(final String year,final FileInfo fileInfo) {
		
		String sql = "update file_info set ephem_number=?, fileflag=?, MP1=?,MP2=?,O_SLPS=?, FILESIZE=? where site_number=? and filedayyear = ?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, fileInfo.getEphem_number());
				pstmt.setInt(2, fileInfo.getFileFlag());
				pstmt.setString(3, fileInfo.getMp1());
				System.out.println(year+"  "+fileInfo.getMp1()+"  "+fileInfo.getFileDayYear()+"  v  "+fileInfo.getSiteNumber());
				pstmt.setString(4, fileInfo.getMp2());
				pstmt.setString(5, fileInfo.getO_slps());
				
				pstmt.setDouble(6, fileInfo.getFileSize());
				pstmt.setString(7, fileInfo.getSiteNumber());
				pstmt.setString(8, fileInfo.getFileDayYear());
				pstmt.execute();
				return null;
			}
		});
	}
	
	
	
	@SuppressWarnings("deprecation")
	public Integer  getListCount(String startTime,String endTime,String fileYear,String fileFlag,final boolean isYear){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from FILE_INFO t,site_info s,zone_info z,dic_info d where s.site_number=t.site_number and s.site_zone=z.zone_code and s.site_department=d.dic_code");
		
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and  TO_DATE( TO_CHAR( t.systemTime,    'YYYY-MM-DD HH24:MI:SS'),  'YYYY-MM-DD HH24:MI:SS')  between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//文件状态
		if(null!=fileFlag && !"".equals(fileFlag)){
			sql.append("and t.fileflag='"+fileFlag+"' ");
		}

		sql.append(" order by t.filecreatetime desc");
		System.out.println(sql);
		return jdbcTemplate.queryForInt(sql.toString());
		
	}
	
	/**
	 * 根据 开始时间 结束时间 台站类别  台站经纬度坐标 进行查询
	 */
	public List<FileInfo>  getFileInfoList(String startTime,String endTime,String fileYear,String fileFlag,final boolean isYear,PageInfo pageinfo){
		
		pageinfo.setRecordCount(getListCount( startTime, endTime, fileYear, fileFlag,  isYear));
		pageinfo.setPageCount(pageinfo.getRecordCount()%pageinfo.getPageSize()==0?pageinfo.getRecordCount()/pageinfo.getPageSize():pageinfo.getRecordCount()/pageinfo.getPageSize()+1);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM  ( SELECT A.*, ROWNUM RN  FROM ( ");
		sql.append("select z.zone_name,d.dic_cn_name,s.site_name,t.fileyear,t.filedayyear,t.site_number,t.filename,t.filepath,t.filesize,"
				+ "t.fileflag,t.filecreatetime,t.systemtime,t.mp1,t.mp2,t.o_slps,t.ephem_number "
				+ "from FILE_INFO t,site_info s,zone_info z,dic_info d where s.site_number=t.site_number and s.site_zone=z.zone_code and s.site_department=d.dic_code");
		
		//时间段
		if(null!=startTime && !"".equals(startTime) && null!=endTime && !"".equals(endTime)){
			sql.append(" and  TO_DATE( TO_CHAR( t.systemTime,    'YYYY-MM-DD HH24:MI:SS'),  'YYYY-MM-DD HH24:MI:SS')  between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss')  and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') ");
		}
		
		//年
		/*if(null!=fileYear && !"".equals(fileYear)){
			sql.append("and t.fileYear='"+fileYear+"' ");
		}*/
		
		//文件状态
		if(null!=fileFlag && !"".equals(fileFlag)){
			sql.append("and t.fileflag='"+fileFlag+"' ");
		}
		
		//年积日
		/*if(null!=fileDayYear && !"".equals(fileDayYear)){
			sql.append("and t.fileDayYear='"+fileDayYear+"' ");
		}*/
		/*//纬度
		if(null!=smallLat && !"".equals(smallLat) && null!=bigLat && !"".equals(bigLat) ){
			sql.append("and s.site_lat>"+smallLat+" and s.site_lat<"+bigLat);
		}
		//经度
		if(null!=smallLon && !"".equals(smallLon) && null!=bigLon && !"".equals(bigLon) ){
			sql.append("and s.SITE_Lon>"+smallLon+" and s.SITE_Lon<"+bigLon);
		}*/
		//台站
		/*StringBuffer siteNumberList = new StringBuffer();
		if(null!=siteNumberArray && !"".equals(siteNumberArray)){
			String[] split = siteNumberArray.split(",");
			for(int i=0;i<split.length;i++){
				if(i==split.length-1){
					siteNumberList.append("'"+split[i]+"'");
				}else{
					siteNumberList.append("'"+split[i]+"',");
				}
			}
			sql.append(" and t.SITE_NUMBER in("+siteNumberList+") ");
		}*/
		
		//文件类型
		/*if(null!=type && !"".equals(type)){
			sql.append("and t.type='"+type+"' ");
		}*/
		sql.append(" order by t.filecreatetime desc");
		sql.append(") A ) WHERE RN between "+((pageinfo.getCurrentPage()-1)*pageinfo.getPageSize() + 1)+" and "+(pageinfo.getCurrentPage()*pageinfo.getPageSize())+"");
		System.out.println(sql);
		@SuppressWarnings({ "unchecked"})
		List<FileInfo> eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		    	FileInfo info = new FileInfo();
		    	info.setRm(rs.getString("rn"));
		    	info.setZoneName(rs.getString("ZONE_NAME"));
		    	info.setDepartmentName(rs.getString("dic_cn_name"));
		    	info.setSiteNumber(rs.getString("site_number"));
		    	info.setSiteName(rs.getString("SITE_NAME"));
		    	
		    	info.setFileYear(rs.getString("fileYear"));
		    	info.setFileDayYear(rs.getString("fileDayYear"));
		    	info.setFileName(rs.getString("fileName"));
		    	info.setFilePath(rs.getString("filePath"));
		    	info.setFileSize(Double.valueOf(rs.getString("fileSize")));
		    	
		    	info.setFileFlag(Integer.valueOf(rs.getString("fileflag")));
		    	if(Integer.valueOf(rs.getString("fileflag"))==1){
		    		info.setFileComp("完整");
		    	}else if(Integer.valueOf(rs.getString("fileflag"))==2){
		    		info.setFileComp("完整");//回补
		    	}else{
		    		info.setFileComp("缺失");
		    	}
		    	
		    	info.setEphemNumber(rs.getString("ephem_number"));//获得历元数量
		    	info.setMp1(rs.getString("mp1"));
		    	info.setMp2(rs.getString("mp2"));
		    	info.setO_slps(rs.getString("o_slps"));
		    	
		    	//优化效率
		    	info.setFileCreateTime(rs.getTimestamp("fileCreateTime"));
		    	if(isYear){
		    		/*Timestamp timestamp = rs.getTimestamp("systemTime");
		    		String dayNumberOfOneYear = DayNumberOfOneYear.getDayNumberOfOneYear(timestamp);*/
		    		String fileDayYear = rs.getString("fileDayYear");
		    		if(fileDayYear.length()==2){
		    			info.setSystemMonthDayNumer("0"+fileDayYear);
		    		}else if(fileDayYear.length()==1){
		    			info.setSystemMonthDayNumer("00"+fileDayYear);
		    		}else{
		    			info.setSystemMonthDayNumer(fileDayYear);
		    		}
		    	}else{
		    		//有用的
		    		//info.setSystemMonthDayNumer(rs.getTimestamp("systemTime").getDate()+"");
		    	}
				return info;
		   }
		});
		return eventInfoList;
	}
}
