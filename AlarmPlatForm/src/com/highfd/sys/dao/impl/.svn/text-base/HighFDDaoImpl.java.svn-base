package com.highfd.sys.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.common.PageInfo;
import com.highfd.sys.dao.HighFDDao;
import com.highfd.vo.Item;
import com.highfd.vo.ItemListParam;
/**
 * DAO层
 * @author Douban73
 *
 */
@Resource
public class HighFDDaoImpl implements HighFDDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * 查询项目
	 * @param item
	 * @return
	 */
	public String getItem(Item item){
		StringBuffer sql = new StringBuffer();
//		sql.append("select * from (select 0 as flag,itemid,itemname,url,indexid,indexname,industryid,"
//				+ "industryname,companyid,companyname,frequencyid,frequencyname,regionid,regionname,unitid,unitname,sourceid,sourcename "
//				+ "from highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1) "
//				+ "union select 1 as flag,itemid,itemname,url,indexid,indexname,industryid,"
//				+ "industryname,companyid,companyname,frequencyid,frequencyname,regionid,regionname,unitid,unitname,sourceid,sourcename "
//				+ "from (select rownum r,t2.* from highfd_item_jjmx t2 order by itemid) t3 where r between 147 and 216 "
//				+ "and t3.itemid in (select distinct t4.collect_item_id from bd_caculatevalue@dblink t4) "
//				+ "union select 2 as flag,itemid,itemname,url,indexid,indexname,industryid,"
//				+ "industryname,companyid,companyname,frequencyid,frequencyname,regionid,regionname,unitid,unitname,sourceid,sourcename "
//				+ "from (select * from (select rownum r,t5.* from highfd_item_jjmx t5 order by itemid)where r>0 and r<147 "
//				+ "union select * from (select rownum r,t6.* from highfd_item_jjmx t6 order by itemid) where r>216 and r<=370) t7 "
//				+ "where t7.itemid in (select distinct t8.collect_item_id from bd_collect@dblink t8)) where itemid is not null "
//				+ "");
		sql.append("select * from (select 0 as flag,itemid,itemname,url,indexid,indexname,industryid,industryname,companyid,");
		sql.append("companyname,frequencyid,frequencyname,regionid,regionname,unitid,unitname,sourceid,sourcename,remark from ");
		sql.append("highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1)");
		if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
			sql.append(" and contains(itemname,'");
			sql.append(item.getKeywords());
			sql.append("')>0");
		}
		sql.append(" union all select flag,itemid,");
		sql.append("itemname,url,indexid,indexname,industryid,industryname,companyid,companyname,frequencyid,frequencyname,");
		sql.append("regionid,regionname,unitid,unitname,sourceid,sourcename,remark from highfd_item_jjmx t");
		if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
			sql.append(" where contains(itemname,'");
			sql.append(item.getKeywords());
			sql.append("')>0");
		}
		sql.append(") where itemid is not null ");
		if(item.getIndexId()!=null&&!item.getIndexId().equals("")){
			sql.append("and indexid in (");
			sql.append(item.getIndexId());
			sql.append(")");
		}
		if(item.getIndustryId()!=null&&!item.getIndustryId().equals("")){
			sql.append("and industryid in (");
			sql.append(item.getIndustryId());
			sql.append(")");
		}
		if(item.getFrequencyId()!=null&&!item.getFrequencyId().equals("")){
			sql.append("and frequencyid in (");
			sql.append(item.getFrequencyId());
			sql.append(")");
		}
		if(item.getRegionId()!=null&&!item.getRegionId().equals("")){
			sql.append("and regionid in (");
			sql.append(item.getRegionId());
			sql.append(")");
		}
		sql.append(" order by itemname");
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			   for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   valuebuf.append("{");
				   valuebuf.append("\"itemid\":");
					valuebuf.append(rs.get("itemid"));
					
					valuebuf.append(",\"flag\":");
						valuebuf.append(rs.get("flag"));
					
						valuebuf.append(",\"itemname\":\"");
					if(rs.get("itemname")!=null){
						valuebuf.append(rs.get("itemname").toString().replaceAll("\"", ""));
					}else{
						valuebuf.append(rs.get("itemname"));
					}
					valuebuf.append("\",\"sourcename\":\"");
					if(rs.get("sourcename") != null){
						valuebuf.append(rs.get("sourcename"));
					}
					valuebuf.append("\",\"frequencyname\":\"");
					if(rs.get("frequencyname")!=null){
						valuebuf.append(rs.get("frequencyname"));
					}
					valuebuf.append("\",\"unitname\":\"");
					if(rs.get("unitname") != null){
						valuebuf.append(rs.get("unitname"));
					}
					valuebuf.append("\"");
					valuebuf.append("},");
			   }
			   if(valuebuf.length() > 10){
					valuebuf = valuebuf.deleteCharAt(valuebuf.length()-1);
			   }
				valuebuf.append("]}");
		}
		return valuebuf.toString();
	}

	
	/**
	 * 查询page
	 * @param item
	 * @return
	 */
	public List<String> getItemListByPage(Item item,PageInfo info){
		
		info.setRecordCount(this.getItemCountByPage(item));
		info.setPageCount(info.getRecordCount()%info.getPageSize()==0?info.getRecordCount()/info.getPageSize():info.getRecordCount()/info.getPageSize()+1);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select rownum r,t.* from (select 0 as flag,itemid,itemname,url,indexid,indexname,industryid,industryname,companyid,");
		sql.append("companyname,frequencyid,frequencyname,regionid,regionname,unitid,unitname,sourceid,sourcename,remark from ");
		sql.append("highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1)");
		if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
			sql.append(" and contains(itemname,'");
			sql.append(item.getKeywords());
			sql.append("')>0");
		}
		sql.append(" and IS_VALID=1");
		sql.append(" union all select flag,itemid,");
		sql.append("itemname,url,indexid,indexname,industryid,industryname,companyid,companyname,frequencyid,frequencyname,");
		sql.append("regionid,regionname,unitid,unitname,sourceid,sourcename,remark from highfd_item_jjmx t ") ;
		if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
			sql.append(" where contains(itemname,'");
			sql.append(item.getKeywords());
			sql.append("')>0");
		}
		sql.append(")t where itemid is not null ");
		if(item.getIndexId()!=null&&!item.getIndexId().equals("")){
			sql.append("and indexid in (");
			sql.append(item.getIndexId());
			sql.append(")");
		}
		if(item.getIndustryId()!=null&&!item.getIndustryId().equals("")){
			sql.append("and industryid in (");
			sql.append(item.getIndustryId());
			sql.append(")");
		}
		if(item.getFrequencyId()!=null&&!item.getFrequencyId().equals("")){
			sql.append("and frequencyid in (");
			sql.append(item.getFrequencyId());
			sql.append(")");
		}
		if(item.getRegionId()!=null&&!item.getRegionId().equals("")){
			sql.append("and regionid in (");
			sql.append(item.getRegionId());
			sql.append(")");
		}
		sql.append(" )where r between "+((info.getCurrentPage()-1)*info.getPageSize()+1)+" and " + (info.getCurrentPage()*info.getPageSize())+"");
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			   for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   valuebuf.append("{");
				   valuebuf.append("\"itemid\":");
					valuebuf.append(rs.get("itemid"));
					
					valuebuf.append(",\"flag\":");
						valuebuf.append(rs.get("flag"));
					
						valuebuf.append(",\"itemname\":\"");
					if(rs.get("itemname")!=null){
						valuebuf.append(rs.get("itemname").toString().replaceAll("\"", ""));
					}else{
						valuebuf.append(rs.get("itemname"));
					}
					valuebuf.append("\",\"sourcename\":\"");
					if(rs.get("sourcename") != null){
						valuebuf.append(rs.get("sourcename"));
					}
					valuebuf.append("\",\"frequencyname\":\"");
					if(rs.get("frequencyname")!=null){
						valuebuf.append(rs.get("frequencyname"));
					}
					valuebuf.append("\",\"unitname\":\"");
					if(rs.get("unitname") != null){
						valuebuf.append(rs.get("unitname"));
					}
					valuebuf.append("\"");
					valuebuf.append("},");
			   }
			   if(valuebuf.length() > 10){
					valuebuf = valuebuf.deleteCharAt(valuebuf.length()-1);
			   }
				valuebuf.append("]}");
		}
		List<String> list1 = new ArrayList<String>();
		list1.add(valuebuf.toString());
		return list1;
	}
	
	
	public int getItemCountByPage(Item item){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(itemid) from (select 0 as flag,itemid,itemname,indexid,industryid,frequencyid,regionid,unitid,sourceid,remark ");
		sql.append("from highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1)"); 
				if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
					sql.append(" and contains(itemname,'");
					sql.append(item.getKeywords());
					sql.append("')>0");
				}
		sql.append(" and IS_VALID=1");
		sql.append("union all select flag,itemid,itemname,indexid,industryid,frequencyid,regionid");
		sql.append(",unitid,sourceid,remark from highfd_item_jjmx t");
		if(item.getKeywords()!=null&&!item.getKeywords().equals("")){
			sql.append(" where contains(itemname,'");
			sql.append(item.getKeywords());
			sql.append("')>0");
		}
		sql.append(") where itemid is not null ");
		if(item.getIndexId()!=null&&!item.getIndexId().equals("")){
			sql.append("and indexid in (");
			sql.append(item.getIndexId());
			sql.append(")");
		}
		if(item.getIndustryId()!=null&&!item.getIndustryId().equals("")){
			sql.append("and industryid in (");
			sql.append(item.getIndustryId());
			sql.append(")");
		}
		if(item.getFrequencyId()!=null&&!item.getFrequencyId().equals("")){
			sql.append("and frequencyid in (");
			sql.append(item.getFrequencyId());
			sql.append(")");
		}
		if(item.getRegionId()!=null&&!item.getRegionId().equals("")){
			sql.append("and regionid in (");
			sql.append(item.getRegionId());
			sql.append(")");
		}
		sql.append(" order by itemname");
		return jdbcTemplate.queryForInt(sql.toString());
	}
	
	/**
	 * 查询全部项目
	 * @param item
	 * @return
	   @Override
	 */
	public List<String>  getItemAll(ItemListParam param, PageInfo info){
		info.setRecordCount( this.getItemCount(param));
		info.setPageCount(info.getRecordCount()%info.getPageSize()==0?info.getRecordCount()/info.getPageSize():info.getRecordCount()/info.getPageSize()+1);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT itemname,status,itemid,indexname,industryname,sourcename,regionname,frequencyname,unitname from (select rownum r,h.* from ");
		sql.append("(select h1.itemid,h1.itemname,h1.indexid,h2.indexname,h1.industryid,h4.industryname,h1.frequencyid,"
				+ "h6.frequencyname,h1.regionid,h5.regionname,h1.sourceid,h7.sourcename,h1.unitid,h3.unitname,h1.datatypeid,"
				+ "h1.datatypename,h1.keywords,h1.status from highfd_item  h1,highfd_dim_index h2,highfd_dim_unit h3,highfd_dim_industry h4,"
				+ "highfd_dim_region h5,highfd_dim_frequency h6,highfd_dim_source h7 "
				+ "where h1.indexid=h2.indexid and h1.unitid = h3.unitid and h1.industryid=h4.industryid and h1.regionid=h5.regionid "
				+ "and h1.frequencyid=h6.frequencyid and h1.sourceid=h7.sourceid");
		sql.append(" order by h1.itemid)h where itemid is not null");
		if(param.getIndex()!=null){
			sql.append(" and indexid in (");
			sql.append(param.getIndex());
			sql.append(")");
		}
		if(param.getSource()!=null){
			sql.append(" and sourceid in (");
			sql.append(param.getSource());
			sql.append(")");
		}
		if(param.getIndus()!=null){
			sql.append(" and industryid in (");
			sql.append(param.getIndus());
			sql.append(")");
		}
		if(param.getFrequency()!=null){
			sql.append(" and frequencyid in (");
			sql.append(param.getFrequency());
			sql.append(")");
		}
		if(!param.getRegion().equals("")){
			sql.append(" and regionid in (");
			if(!param.getSubregion().equals("")&&param.getSubregion()!=null){
				sql.append(param.getSubregion());
			}else{
				sql.append(param.getRegion());
			}
			sql.append(")");
		}
		if(!param.getKeywords().equals("")){
			sql.append(" and keywords like '%");
			sql.append(param.getKeywords());
			sql.append("%'");
		}
		if(!param.getItemname().equals("")){
			sql.append(" and itemname like '%");
			sql.append(param.getItemname());
			sql.append("%'");
		}
		sql.append(") t where t.r between "+((info.getCurrentPage()-1)*info.getPageSize()+1)+" and "+(info.getCurrentPage()*info.getPageSize())+"");
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			   for (int i=0; i<list.size(); i++) {
				   rs = list.get(i);
				   valuebuf.append("{");
					valuebuf.append("\"itemid\":\"");
					valuebuf.append(rs.get("itemid"));
					valuebuf.append("\",\"itemname\":\"");
					if(rs.get("itemname")!=null){
						valuebuf.append(rs.get("itemname").toString().replaceAll("\"",""));
					}
					valuebuf.append("\",\"indexname\":\"");
					if(rs.get("indexname")!=null){
						valuebuf.append(rs.get("indexname").toString().replaceAll("\"",""));
					}
					valuebuf.append("\",\"indusname\":\"");
					if(rs.get("industryname")!=null){
						valuebuf.append(rs.get("industryname")).toString().replace("\"","");
					}
					valuebuf.append("\",\"regionname\":\"");
					if(rs.get("regionname")!=null){
						valuebuf.append(rs.get("regionname"));
					}
					valuebuf.append("\",\"unitname\":\"");
					if(rs.get("unitname") != null){
						valuebuf.append(rs.get("unitname"));
					}
					valuebuf.append("\",\"frequencyname\":\"");
					valuebuf.append(rs.get("frequencyname"));
					valuebuf.append("\",\"sourcename\":\"");
					if(rs.get("sourcename") != null){
						valuebuf.append(rs.get("sourcename"));
					}
					valuebuf.append("\",\"status\":");
						BigDecimal bd = (BigDecimal) rs.get("status");
						if(bd.intValue()==0){
							valuebuf.append("\"停用");
						}else{
							valuebuf.append("\"启用");
						}
					valuebuf.append("\"},");
			   }
			   if(valuebuf.length() > 10){
					valuebuf = valuebuf.deleteCharAt(valuebuf.length()-1);
				}
				valuebuf.append("]}");
		}
		List<String> list1 = new ArrayList<String>();
		list1.add(valuebuf.toString());
		return list1;
	}
	/**
	 * 分页查询总记录数
	 */
	public Integer getItemCount(ItemListParam param) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(h1.itemid) from highfd_item  h1,"
				+ "highfd_dim_index h2,highfd_dim_unit h3,highfd_dim_industry h4,highfd_dim_region h5,highfd_dim_frequency h6,highfd_dim_source h7 "
				+ "where h1.indexid=h2.indexid and h1.unitid = h3.unitid and h1.industryid=h4.industryid and h1.regionid=h5.regionid "
				+ "and h1.frequencyid=h6.frequencyid and h1.sourceid=h7.sourceid");
		if(param.getIndex()!=null){
			sql.append(" and h1.indexid in (");
			sql.append(param.getIndex());
			sql.append(")");
		}
		if(param.getSource()!=null){
			sql.append(" and h1.sourceid in (");
			sql.append(param.getSource());
			sql.append(")");
		}
		if(param.getIndus()!=null){
			sql.append(" and h1.industryid in (");
			sql.append(param.getIndus());
			sql.append(")");
		}
		if(param.getFrequency()!=null){
			sql.append(" and h1.frequencyid in (");
			sql.append(param.getFrequency());
			sql.append(")");
		}
		if(!param.getRegion().equals("")){
			sql.append(" and h1.regionid in (");
			if(!param.getSubregion().equals("")&&param.getSubregion()!=null){
				sql.append(param.getSubregion());
			}else{
				sql.append(param.getRegion());
			}
			sql.append(")");
		}
		if(!param.getKeywords().equals("")){
			sql.append(" and h1.keywords like '%");
			sql.append(param.getKeywords());
			sql.append("%'");
		}
		if(!param.getItemname().equals("")){
			sql.append(" and h1.itemname like '%");
			sql.append(param.getItemname());
			sql.append("%'");
		}
		return jdbcTemplate.queryForInt(sql.toString());
	}
	/**
	 * 查询数据
	 * @param item
	 * @return
	 */
	public String getData(Item item){
//		String sql ="select * from ("
//				+ "select 0 as flag,itemid,to_char(value) as value,sdate from highfd_data "
//				+ "union select 1 as flag,to_number(collect_item_id) as itemid,"
//				+ "to_char(CACULATEVALUE) as value,to_date(collect_date,'YYYY-MM-DD') as sdate "
//				+ "from bd_caculatevalue@dblink where index_id=1 union "
//				+ "select 2 as flag,collect_item_id as itemid,to_char(COLLECT_DATA) as value,"
//				+ "COLLECT_DATE as sdate from bd_collect@dblink) "
//				+ "where itemid=? and flag=? order by sdate ";
		String sql = "";
		int flag = item.getFlag();
		if(flag == 0 ){
			sql = "select to_char(value) as value,sdate from highfd_data where itemid = ? order by sdate";
		}else if(flag == 1){
			sql = "select to_char(COLLECT_DATA) as value,COLLECT_DATE as sdate from bd_collect@dblink "
					+ "where collect_item_id = ? order by sdate";
		}else if(flag == 2){
			sql = "select to_char(CACULATEVALUE) as value,to_date(collect_date,'YYYY-MM-DD') as sdate "
					+ "from bd_caculatevalue@dblink where index_id=1 and collect_item_id = ? order by sdate";
		}else if(flag == 3){
			sql = "select to_char(COLLECT_DATA) as value,COLLECT_DATE as sdate from bd_collect_mainproduct@dblink"
					+ " where collect_item_id = ? order by sdate";
		}else if(flag == 4){
			sql = "select to_char(COLLECT_DATA) as value,COLLECT_DATE as sdate from bd_collect@dblink "
					+ "where collect_item_id = ? and data_type_id = 5 order by sdate";
		}
//		Integer[] code = {Integer.valueOf(item.getItemId()),Integer.valueOf(item.getFlag())};
		Integer[] code = {Integer.valueOf(item.getItemId())};
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,code);
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			for(int i=0; i<list.size(); i++) {
				rs = list.get(i);
				valuebuf.append("{");
				valuebuf.append("\"sdate\":\"");
				valuebuf.append(rs.get("sdate"));
				valuebuf.append("\",\"value\":");
				if(rs.get("value") != null){
					valuebuf.append(Double.parseDouble(rs.get("value").toString()));
				}else{
					valuebuf.append(rs.get("value"));
				}
				valuebuf.append("},");
			}
			if(valuebuf.length()>10){
				valuebuf.deleteCharAt(valuebuf.length()-1);
			}
			valuebuf.append("]}");
		}
		return valuebuf.toString();
	}
	/**
	 * 查询指标
	 */
	public String getAllIndex(){
		String sql = "SELECT indexid,indexname FROM highfd_dim_index where indexid in ("
				+ "select indexid from highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1)"
				+ "union select indexid from highfd_item_jjmx )order by indexid";
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			for(int i=0; i<list.size(); i++) {
				rs = list.get(i);
				valuebuf.append("{");
				valuebuf.append("\"indexid\":");
				valuebuf.append(rs.get("indexid"));
				valuebuf.append(",\"indexname\":\"");
				valuebuf.append(rs.get("indexname"));
				valuebuf.append("\"");
				valuebuf.append("},");
			}
			if(valuebuf.length()>10){
				valuebuf.deleteCharAt(valuebuf.length()-1);
			}
			valuebuf.append("]}");
		}
		return valuebuf.toString();
	}
	/**
	 * 查询单位
	 */
	public String getAllUnit() {
		String sql = "select unitid,unitname from highfd_dim_unit where unitname is not null order by unitname";
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			for(int i=0; i<list.size(); i++) {
				rs = list.get(i);
				valuebuf.append("{");
				valuebuf.append("\"unitid\":");
				valuebuf.append(rs.get("unitid"));
				valuebuf.append(",\"unitname\":\"");
				valuebuf.append(rs.get("unitname"));
				valuebuf.append("\"");
				valuebuf.append("},");
			}
			if(valuebuf.length()>10){
				valuebuf.deleteCharAt(valuebuf.length()-1);
			}
			valuebuf.append("]}");
		}
		return valuebuf.toString();
	}
	/**
	 * 查询来源
	*/
	public String getAllSource() {
		String sql = "select sourceid,sourcename from highfd_dim_source where sourcename is not null order by sourceid";
		StringBuffer valuebuf = new StringBuffer();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> rs = new HashMap<String, Object>();
		if(list!=null) {
			valuebuf.append("{\"data\":[");
			for(int i=0; i<list.size(); i++) {
				rs = list.get(i);
				valuebuf.append("{");
				valuebuf.append("\"sourceid\":");
				valuebuf.append(rs.get("sourceid"));
				valuebuf.append(",\"sourcename\":\"");
				valuebuf.append(rs.get("sourcename"));
				valuebuf.append("\"");
				valuebuf.append("},");
			}
			if(valuebuf.length()>10){
				valuebuf.deleteCharAt(valuebuf.length()-1);
			}
			valuebuf.append("]}");
		}
		return valuebuf.toString();
	}
	/**
	 * 查询行业
	 * 
	 */
	public String getIndustry(){
		String sql = "select industryid as id,industryname as name from highfd_dim_industry where industryid in "
				+ "(select industryid from highfd_item where itemid in (select distinct t1.itemid from highfd_DATA t1) "
				+ "union select industryid from highfd_item_jjmx) order by industryid";
				StringBuffer valuebuf = new StringBuffer();
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
				Map<String, Object> rs = new HashMap<String, Object>();
				if(list!=null) {
					valuebuf.append("{\"data\":[");
					for(int i=0; i<list.size(); i++) {
						rs = list.get(i);
						valuebuf.append("{");
						valuebuf.append("\"id\":");
						valuebuf.append(rs.get("id"));
						valuebuf.append(",\"name\":\"");
						valuebuf.append(rs.get("name"));
						valuebuf.append("\"");
						valuebuf.append("},");
					}
					if(valuebuf.length()>10){
						valuebuf.deleteCharAt(valuebuf.length()-1);
					}
					valuebuf.append("]}");
				}
		return valuebuf.toString();
	}
	
	/**
	 * 导出数据
	 * @param indexid
	 * @param indusid
	 * @param type
	 * @return
	 */
	public ArrayList<Map> getExcelData(Item item){
		String sql ="SELECT sdate,value FROM highfd_data where itemid=? order by sdate";
		Integer[] code = {Integer.valueOf(item.getItemId())};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<Map> highFDList = (ArrayList<Map>) jdbcTemplate.query(sql, code, new RowMapper(){
			public Map mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				HashMap<String,String> highFDmap = new HashMap<String,String>();
				highFDmap.put("sdate", rs.getString(1));
				highFDmap.put("value", rs.getString(2));
				
				return highFDmap;
			}
			
		});
		return highFDList;
	}


}