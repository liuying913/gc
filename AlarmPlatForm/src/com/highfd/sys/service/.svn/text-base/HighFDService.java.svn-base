package com.highfd.sys.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.highfd.common.PageInfo;
import com.highfd.vo.Item;
import com.highfd.vo.ItemListParam;

public interface HighFDService {
	
	/**
	 * 查询指定时间序列详细信息
	 * @param item
	 * @return
	 */
	public String getItem(Item item);
	
	public List<String> getItemAll(ItemListParam param,PageInfo info);
	
	public List<String> getItemListByPage(Item item,PageInfo info);
	
	public int getItemCountByPage(Item item);
	/**
	 * 查询指定时间序列的数据
	 * @param item
	 * @return
	 */
	public String getData(Item item);
	
	/**
	 * 查询指标维度表
	 * @return
	 */
	public String getAllIndex();
	/**
	 * 查询单位维度表
	 * @return
	 */
	public String getAllUnit();
	/**
	 * 查询来源维度表
	 * @return
	 */
	public String getAllSource();
	
	/**
	 * 查询行业
	 */
	public String getIndustry();
	/**
	 * 查询需要导入excel的数据
	 * @param item
	 * @return
	 */
	public Map<Long,Map> getExcelData(List<Item> itemList) throws ParseException;
}
