package com.highfd.sys.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.highfd.common.PageInfo;
import com.highfd.vo.Item;
import com.highfd.vo.ItemListParam;

/**
 * DAO层
 * @author Douban73
 */
public interface HighFDDao {

	/**
	 * 查询项目
	 * @param item
	 * @return
	 */
	public String getItem(Item item);
	
	public List<String> getItemAll(ItemListParam param,PageInfo info);
	
	public Integer getItemCount(ItemListParam param);
	
	public List<String> getItemListByPage(Item item,PageInfo info);
	
	public int getItemCountByPage(Item item);
	/**
	 * 查询数据
	 * @param item
	 * @return
	 */
	public String getData(Item item);
	
	/**
	 * 查询指标
	 */
	
	public String getAllIndex();
	/**
	 * 查询单位
	 */
	
	public String getAllUnit();
	/**
	 * 查询来源
	*/
	public String getAllSource();
	
	/**
	 * 查询行业
	 * @return
	 */
	public String getIndustry();
	
	/**
	 * 导出数据
	 * @param indexid
	 * @param indusid
	 * @param type
	 * @return
	 */
	public ArrayList<Map> getExcelData(Item item);
	
}
