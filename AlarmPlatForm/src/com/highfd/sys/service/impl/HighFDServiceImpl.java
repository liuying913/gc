package com.highfd.sys.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highfd.common.PageInfo;
import com.highfd.common.TimeCommon;
import com.highfd.sys.dao.HighFDDao;
import com.highfd.sys.service.HighFDService;
import com.highfd.vo.Item;
import com.highfd.vo.ItemListParam;

@Service
public class HighFDServiceImpl implements HighFDService {
	
	@Autowired
	HighFDDao dao;
	
	/**
	 * 查询指定时间序列详细信息
	 */
	@Transactional
	public String getItem(Item item) {
		// TODO Auto-generated method stub
		return dao.getItem(item);
	}
	
	public List<String> getItemAll(ItemListParam param,PageInfo info) {
		// TODO Auto-generated method stub
		return dao.getItemAll(param,info);
	}
	
	public List<String> getItemListByPage(Item item,PageInfo info){
		return dao.getItemListByPage(item, info);
	}
	
	public int getItemCountByPage(Item item){
		return dao.getItemCountByPage(item);
	}
	
	/**
	 * 查询指定时间序列的数据
	 */
	@Transactional
	public String getData(Item item) {
		// TODO Auto-generated method stub
		return dao.getData(item);
	}
	
	/**
	 * 查询指标维度表
	 */
	@Transactional
	public String getAllIndex() {
		return dao.getAllIndex();
	}
	/**
	 * 查询单位维度表
	 */
	@Transactional
	public String getAllUnit() {
		return dao.getAllUnit();
	}
	/**
	 * 查询来源维度表
	 */
	@Transactional
	public String getAllSource() {
		return dao.getAllSource();
	}
	/**
	 * 
	 * 查询行业
	 */
	public String getIndustry() {
		return dao.getIndustry();
	}

	
	/**
	 * 数据下载
	 * @throws ParseException 
	 */
	@Transactional
	public Map<Long,Map> getExcelData(List<Item> itemList) throws ParseException{
		TreeSet allTimeSet = new TreeSet(); //获得所有的时间
		for(int i=0;i<itemList.size();i++){
			Item item = itemList.get(i);
			ArrayList<Map> itemData = dao.getExcelData(item);
			for(int j=0; j<itemData.size(); j++){
				Map map = itemData.get(j);
				allTimeSet.add(  TimeCommon.getDataTimeLong((String) map.get("sdate"))  );
			}
		}
		Map<Long,Map> itemDataMap = new TreeMap<Long,Map>();//将时间排序，并放入map中
		Iterator it = allTimeSet.iterator();
		while(it.hasNext()){
			Map map = new HashMap();
			for(int i=0;i<itemList.size();i++){
				map.put(i, "");
			}
			itemDataMap.put((Long) it.next(), map);
		}
		for(int i=0;i<itemList.size();i++){
			Item item = itemList.get(i);
			ArrayList<Map> itemData = dao.getExcelData(item);
			for(int j=0; j<itemData.size(); j++){
				Map map = itemData.get(j);//某一个item的 时间、值
				Map itemListMap = itemDataMap.get(TimeCommon.getDataTimeLong((String) map.get("sdate")));//通过时间，获得excel的行
				itemListMap.put(i,map.get("value"));//将改行、的改列赋值
				itemDataMap.put(TimeCommon.getDataTimeLong((String) map.get("sdate")), itemListMap);
			}
		}
		return itemDataMap;
	}


}
