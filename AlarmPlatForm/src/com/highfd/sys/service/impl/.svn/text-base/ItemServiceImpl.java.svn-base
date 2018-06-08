package com.highfd.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highfd.sys.dao.ItemDAO;
import com.highfd.sys.service.ItemService;
import com.highfd.vo.DimIndex;
import com.highfd.vo.DimSource;
import com.highfd.vo.DimUnit;
import com.highfd.vo.Item;
import com.highfd.vo.ItemVO;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemDAO dao;

	@Transactional
	public List<ItemVO> getAllItems() {
		// TODO Auto-generated method stub
		return dao.searchAll();
	}

	@Transactional
	public void addItem(ItemVO item) {
		// TODO Auto-generated method stub
		dao.save(item);
	}
	
	public int saveItem(Item item){
		//logger.error("error");
		//logger.warn("warn");
		//logger.info("info");
		//logger.debug("debug");
		if(item.getItemId()==-999){//添加item
			int saveItem = dao.saveItem(item);
			//logger.error("操作人：XXX insert item Id:"+saveItem);
			return saveItem;
		}else{//修改item
			int updateItem = dao.updateItem(item);
			//logger.error("操作人：XXX update item Id:"+updateItem);
			return updateItem;
		}
		
	}

	public int deleteItem(String itemid) {
		// TODO Auto-generated method stub
		int deleteItem = dao.deleteItem(itemid);
		//logger.error("操作人：XXX delete item Id:"+itemid+" 包含itemData:"+deleteItemData+"条");
		return deleteItem;
	}
	
	public int updateItem(String itemid) {
		// TODO Auto-generated method stub
		return dao.updateItem(itemid);
	}

	public int changeItemStatus(String itemid, String status) {
		// TODO Auto-generated method stub
		return dao.changeItemStatus(itemid, status);
	}

	public Item queryItem(String itemid) {
		// TODO Auto-generated method stub
		return dao.queryItem(itemid);
	}
	
	
	public String checkIndex(String index,String name) throws Exception {
		if(null!=index && !"".equals(index)){
			if(index.equals("0")){
				DimIndex selectIndex = dao.selectIndex(name);//根据name，判断数据库中是否存在
				if(null==selectIndex){//如果不存在，添加
					return dao.insertIndex(new DimIndex(index,name));
				}else{//如果存在
					return selectIndex.getIndexId()+"";
				}
			}else{
				return index;
			}
		}
		return "";
	}
	
	public String checkUnit(String index,String name) throws Exception {
		if(null!=index && !"".equals(index)){
			if(index.equals("0")){
				DimUnit selectUnit = dao.selectUnit(name);//根据name，判断数据库中是否存在
				if(null==selectUnit){//如果不存在，添加
					return dao.insertUnit(new DimUnit(index,name));
				}else{//如果存在
					return selectUnit.getUnitId()+"";
				}
			}else{
				return index;
			}
		}
		return "";
	}
	
	public String checkSource(String index,String name) throws Exception {
		if(null!=index && !"".equals(index)){
			if(index.equals("0")){
				DimSource selectSource = dao.selectSource(name);//根据name，判断数据库中是否存在
				if(null==selectSource){//如果不存在，添加
					return dao.insertSource(new DimSource(index,name));
				}else{//如果存在
					return selectSource.getSourceid()+"";
				}
			}else{
				return index;
			}
		}
		return "";
	}
}