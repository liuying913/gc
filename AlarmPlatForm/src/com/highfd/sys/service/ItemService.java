package com.highfd.sys.service;

import java.util.List;

import com.highfd.vo.Item;
import com.highfd.vo.ItemVO;

public interface ItemService {
	public List<ItemVO> getAllItems();
	public void addItem(ItemVO item);
	/**
	 * 保存时间序列信息
	 * @param item
	 * @return
	 */
	public int saveItem(Item item);
	
	public int deleteItem(String itemid);
	
	public int changeItemStatus(String itemid, String status);
	
	public Item queryItem(String itemid); 
	
	public String checkIndex(String index,String name) throws Exception;
	
	public String checkUnit(String index,String name) throws Exception;
	
	public String checkSource(String index,String name) throws Exception;
}