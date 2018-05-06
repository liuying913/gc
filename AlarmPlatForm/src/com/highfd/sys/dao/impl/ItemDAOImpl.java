package com.highfd.sys.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.highfd.sys.dao.ItemDAO;
import com.highfd.vo.DimIndex;
import com.highfd.vo.DimSource;
import com.highfd.vo.DimUnit;
import com.highfd.vo.Item;
import com.highfd.vo.ItemVO;

@Resource
public class ItemDAOImpl implements ItemDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(ItemVO item) {
		// TODO Auto-generated method stub
		final ItemVO fitem = item;
		String sql = "insert into HIGHFD_DIM_UNIT(unitid, unitname) values(?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {  
			public void setValues(PreparedStatement pstmt) throws SQLException {
				// TODO Auto-generated method stub
				pstmt.setInt(1, fitem.getId());  
				pstmt.setString(2, fitem.getName());
			}});  
	}

	public List<ItemVO> searchAll() {
		// TODO Auto-generated method stub
		List<ItemVO> list = new ArrayList<ItemVO>();
		String sql = "select t.unitid, t.unitname from HIGHFD_DIM_UNIT t";
		List<Map<String, Object>> itemlist = jdbcTemplate.queryForList(sql);
		Map<String, Object> itemmap = new HashMap<String, Object>();
		for(int i=0; i<itemlist.size(); i++){
			itemmap = itemlist.get(i);
			if(itemmap.get("unitname") != null){
				ItemVO item = new ItemVO();
				item.setId(Integer.parseInt(itemmap.get("unitid").toString()));
				item.setName(itemmap.get("unitname").toString());
				list.add(item);
			}
		}
		
		return list;
	}
	
	
	
	
	
	//******************************方法示例*********************************************************
	//添加数据
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertEventInfo(final ItemVO itemVO) throws Exception {

		String sql = "INSERT INTO event_info (id,sitenumber,deviceid,eventtype,starttime,endtime,description)"
				+ "VALUES(?,?,?,?,?,?,?)";
		String sql2 = "select event_info_seq.nextval from dual";
		itemVO.setId(getNextID(jdbcTemplate, sql2));
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, itemVO.getId());
				pstmt.setString(2, itemVO.getName());

				pstmt.execute();
				return null;
			}
		});
	}

	
	
	
	
	
	
	//数据修改  示例
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateCubeoneDevice(final ItemVO itemVO) throws Exception {
		String sql = "update site_info s set s.cubeone_memory =?,s.cubeone_model=?,s.cubeone_system=?,s.cubeone_cpu=?,s.cubeone_rigid=? where s.site_number=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, itemVO.getId());
				pstmt.setString(2, itemVO.getName());
				pstmt.execute();
				return null;
			}
		});
	}
	
	//删除数据  示例
	public void delete(String string) {
		String sql = "delete from site_great_event  where  id='"+string+"'";
		jdbcTemplate.execute(sql, new PreparedStatementCallback<Object>() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.execute();
				return null;
			}
		});
	}
	

	//单参数  获得单个对象   示例
    public ItemVO queryNoteInfo(int id) throws Exception{
		String sql = "select * from note_info where note_id = ?";
		Integer[] code = { Integer.valueOf(id) };
		@SuppressWarnings("unchecked")
		ItemVO obj = (ItemVO)this.jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				  if (rs.next()){
					  ItemVO note = new ItemVO();
					  note.setId(rs.getInt("note_id"));
					  note.setName(rs.getString("site_number"));
					  return note;
				 }
				 return null;
		    }
		});
		return obj;
    }
	//多参数查询  示例
    public ItemVO queryInfo(String siteNumber, String noteTitle)throws Exception{
		String sql = "select * from note_info where site_number = ? and note_title=?";
		String[] code = { siteNumber, noteTitle };
		@SuppressWarnings("unchecked")
		ItemVO obj = (ItemVO)jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					  ItemVO note = new ItemVO();
					  note.setId(rs.getInt("note_id"));
					  note.setName(rs.getString("site_number"));
					 
					  return note;
				}
				return null;
		   }
		});
		return obj;
    }
    
    //查询集合  示例
    @SuppressWarnings("unchecked")
	public List<ItemVO> queryCurrentAlarms()throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.id,s.site_number,s.site_name,e.eventtype,e.starttime,e.endtime,e.description,di.dic_cn_name ");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List eventInfoList = jdbcTemplate.query(sql.toString(), new RowMapper(){
		  public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ItemVO ei = new ItemVO();
			ei.setId(rs.getInt("id"));
			ei.setName(rs.getString("site_number"));
			return ei;
		  }
		});
		return eventInfoList;
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
	
	@SuppressWarnings("unchecked")
	public int saveItem(final Item item) {
		// TODO Auto-generated method stub
		String maxidsql = "select max(itemid) from highfd_item";
		int itemid = getNextID(jdbcTemplate, maxidsql);
		item.setItemId(itemid);
		String sql = "insert into highfd_item(itemid,itemname,url,indexid,industryid,frequencyid,regionid,datatypeid,"
				+ "sourceid,unitid,keywords,load_date) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		itemid = jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setInt(1, item.getItemId());
				pstmt.setString(2, item.getItemName());
				pstmt.setString(3, item.getUrl());
				pstmt.setString(4, item.getIndexId());
				pstmt.setString(5, item.getIndustryId());
				pstmt.setString(6, item.getFrequencyId());
				pstmt.setString(7, item.getRegionId());
				pstmt.setString(8, item.getDatatypeId());
				pstmt.setString(9, item.getSourceId());
				pstmt.setString(10, item.getUnitId());
				pstmt.setString(11, item.getKeywords());
				pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
				int result = pstmt.executeUpdate();
				if(result > 0){
					return item.getItemId();
				}else{
					return -1;
				}
			}
		});
		return itemid;
	}

	//修改item
	@SuppressWarnings("unchecked")
	public int updateItem(final Item item) {
		String sql = "update HIGHFD_ITEM set itemname=?,url=?,indexid=?,industryid=?,frequencyid=?,regionid=?,datatypeid=?,sourceid=?,unitid=?,keywords=?,load_date=? where itemid=?";
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt)
					throws SQLException, DataAccessException {
				pstmt.setString(1, item.getItemName());
				pstmt.setString(2, item.getUrl());
				pstmt.setString(3, item.getIndexId());
				pstmt.setString(4, item.getIndustryId());
				pstmt.setString(5, item.getFrequencyId());
				pstmt.setString(6, item.getRegionId());
				pstmt.setString(7, item.getDatatypeId());
				pstmt.setString(8, item.getSourceId());
				pstmt.setString(9, item.getUnitId());
				pstmt.setString(10, item.getKeywords());
				pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
				pstmt.setInt(12, item.getItemId());
				pstmt.execute();
				return null;
			}
		});
		return item.getItemId();
	}
	
	public int deleteItem(String itemid) {
		// TODO Auto-generated method stub
		String sql = "delete from highfd_item where itemid=" + itemid;
		int count = jdbcTemplate.update(sql);
		return count;
	}
	
	//删除ItemData里面的数据
	public int deleteItemData(String itemid) {
		// TODO Auto-generated method stub
		String sql = "delete from HIGHFD_DATA where itemid=" + itemid;
		int count = jdbcTemplate.update(sql);
		return count;
	}

	public int changeItemStatus(String itemid, String status) {
		// TODO Auto-generated method stub
		String sql = "update highfd_item set status=" + status + " where itemid=" + itemid;
		int count = jdbcTemplate.update(sql);
		return count;
	}

	public Item queryItem(String itemid) {
		// TODO Auto-generated method stub
		String sql = "select itemId,itemName,url,indexId,industryId,frequencyId,regionId,datatypeId,"
				+ "sourceId,unitId,keywords,status from highfd_item where itemid=" + itemid;

		return null;
	}
	
	//指标
	@SuppressWarnings("unchecked")
	public String insertIndex(final DimIndex dIndex) throws Exception {
		String sql = "INSERT INTO HIGHFD_DIM_INDEX (indexid,indexname,baseindexid,remark) VALUES(?,?,?,?)";
		String sql2 = "select HIGHFD_DIM_INDEX_SEQ.nextval from dual";
		dIndex.setIndexId(getNextID(sql2)+"");
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, dIndex.getIndexId());
				pstmt.setString(2, dIndex.getIndexName());
				pstmt.setString(3, dIndex.getBaseIndexId());
				pstmt.setString(4, dIndex.getRemark());
				pstmt.execute();
				return null;
			}
		});
		return dIndex.getIndexId();
	}
	
	//单位
	@SuppressWarnings("unchecked")
	public String insertUnit(final DimUnit dUnit) throws Exception {
		String sql = "INSERT INTO HIGHFD_DIM_UNIT (unitid,unitname,remark) VALUES(?,?,?)";
		String sql2 = "select HIGHFD_DIM_UNIT_SEQ.nextval from dual";
		dUnit.setUnitId(getNextID(sql2)+"");
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, dUnit.getUnitId());
				pstmt.setString(2, dUnit.getUnitName());
				pstmt.setString(3, dUnit.getRemark());
				pstmt.execute();
				return null;
			}
		});
		return dUnit.getUnitId();
	}
	
	//来源
	@SuppressWarnings("unchecked")
	public String insertSource(final DimSource dSource) throws Exception {
		String sql = "INSERT INTO HIGHFD_DIM_SOURCE (sourceid,sourcename,url,remark) VALUES(?,?,?,?)";
		String sql2 = "select HIGHFD_DIM_SOURCE_SEQ.nextval from dual";
		dSource.setSourceid(getNextID(sql2)+"");
		jdbcTemplate.execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				pstmt.setString(1, dSource.getSourceid());
				pstmt.setString(2, dSource.getSourcename());
				pstmt.setString(3, dSource.getUrl());
				pstmt.setString(4, dSource.getRemark());
				pstmt.execute();
				return null;
			}
		});
		return dSource.getSourceid();
	}
	
	@SuppressWarnings("unchecked")
	public int getNextID(String sql)throws Exception {
		Integer obj = (Integer) jdbcTemplate.query(sql,new ResultSetExtractor() {
			public Object extractData(ResultSet rs)throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return null;
			}
		});
		return obj;
	}
	
	//指标
    public DimIndex selectIndex(String name) throws Exception{
		String sql = "select * from HIGHFD_DIM_INDEX t where t.indexname = ?";
		String[] code = {name};
		@SuppressWarnings("unchecked")
		DimIndex obj = (DimIndex)this.jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				  if (rs.next()){
					  DimIndex dIndex = new DimIndex();
					  dIndex.setIndexId(rs.getString("indexid"));
					  dIndex.setIndexName(rs.getString("indexname"));
					  dIndex.setBaseIndexId(rs.getString("baseindexid"));
					  dIndex.setRemark(rs.getString("remark"));
					  return dIndex;
				 }
				 return null;
		    }
		});
		return obj;
    }
    
    //单位
    public DimUnit selectUnit(String name) throws Exception{
		String sql = "select * from HIGHFD_DIM_UNIT t where t.unitname = ?";
		String[] code = {name};
		@SuppressWarnings("unchecked")
		DimUnit obj = (DimUnit)this.jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				  if (rs.next()){
					  DimUnit dimUnit = new DimUnit();
					  dimUnit.setUnitId(rs.getString("unitid"));
					  dimUnit.setUnitName(rs.getString("unitname"));
					  dimUnit.setRemark(rs.getString("remark"));
					  return dimUnit;
				 }
				 return null;
		    }
		});
		return obj;
    }
    
    //来源
    public DimSource selectSource(String name) throws Exception{
		String sql = "select * from HIGHFD_DIM_SOURCE t where sourcename = ?";
		String[] code = {name};
		@SuppressWarnings("unchecked")
		DimSource obj = (DimSource)this.jdbcTemplate.query(sql, code, new ResultSetExtractor(){
		    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				  if (rs.next()){
					  DimSource dimSource = new DimSource();
					  dimSource.setSourceid(rs.getString("sourceid"));
					  dimSource.setSourcename(rs.getString("sourcename"));
					  dimSource.setUrl(rs.getString("url"));
					  dimSource.setRemark(rs.getString("remark"));
					  return dimSource;
				 }
				 return null;
		    }
		});
		return obj;
    }
    
    //修改方法
	public int updateItem(String itemid) {
		// TODO Auto-generated method stub
		String sql = "update highfd_item where itemid=" + itemid;
		int count = jdbcTemplate.update(sql);
		return count;
	}
}