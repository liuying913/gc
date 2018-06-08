package com.highfd.sys.dao;

import java.util.List;

import com.highfd.vo.DimIndex;
import com.highfd.vo.DimSource;
import com.highfd.vo.DimUnit;
import com.highfd.vo.Item;
import com.highfd.vo.ItemVO;

public interface ItemDAO {
	void save(ItemVO item);
	List<ItemVO> searchAll();
	
	/**
	 * 时间序列保存，返回序列id
	 * @param item
	 * @return
	 */
	public int saveItem(Item item);//添加item
	
	public int updateItem(Item item);//修改item
	
	public int deleteItem(String itemid);
	
	public int deleteItemData(String itemid);//删除itemData中的记录
	
	public int changeItemStatus(String itemid, String status);
	
	public Item queryItem(String itemid);
	
	public String insertIndex(final DimIndex dIndex) throws Exception;
	
	public String insertUnit(final DimUnit dUnit) throws Exception;
	
	public String insertSource(final DimSource dSource) throws Exception;
	
	//指标
    public DimIndex selectIndex(String name) throws Exception;
    
    //单位
    public DimUnit selectUnit(String name) throws Exception;
    
    //来源
    public DimSource selectSource(String name) throws Exception;
	public int updateItem(String itemid);
}