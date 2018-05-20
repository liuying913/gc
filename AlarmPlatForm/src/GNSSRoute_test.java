import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.highfd.vo.JsonBean;

public class GNSSRoute_test {
	public static void main(String[] args) {
		
		String ip = "172.128.16.49";
		String url="http://"+ip+"/CACHEDIR4074957650/xml/dynamic/spot_subscription.xml";
		
		Map<Integer,JsonBean> map = new HashMap<Integer,JsonBean>();
		map.put(0, new JsonBean("data","",""));
        map.put(1, new JsonBean("SpotHpVersionValue","",""));//HP/XP固件版本
        getValue(url,map);
        map.put(1, new JsonBean("SpotVbsVersionValue","",""));//VBS固件版本
        getValue(url,map);
		
		url="http://"+ip+"/xml/dynamic/merge.xml?sysData=&options=";
		map.put(0, new JsonBean("data","",""));
        map.put(1, new JsonBean("sysData","",""));
        map.put(2, new JsonBean("mDnsName","",""));//接收机类型
        getValue(url,map);
        
        map.put(0, new JsonBean("data","",""));
        map.put(1, new JsonBean("sysData","",""));
        map.put(2, new JsonBean("serial","",""));//接收机序列号
        getValue(url,map);
	}
	
	public static void getValue(String url, Map<Integer,JsonBean> map){
		StringBuffer sendGet = GNSSRoute_test.sendGet(url);
		url = sendGet.toString();
		if(!isXML(url)){
			System.out.println("不是xml格式");
			return;
		}
		
		List<String> analysisXML = analysisXML(sendGet.toString(),map);
		for(int i=0;i<analysisXML.size();i++){
			System.out.println(analysisXML.get(i));
		}
	}
    
	public static List<String> analysisXML(String xmlstr , Map<Integer,JsonBean> map){
    	StringReader reader=new StringReader(xmlstr);  
	    InputSource source=new InputSource(reader);  
	    SAXBuilder sax=new SAXBuilder(); 
	    try {
	       Document doc=sax.build(source);  
	       List<Element> list = new ArrayList<Element>();
	       list.add(doc.getRootElement());
	       
	       for(int i=0;i<map.size();i++){
	    	   JsonBean jsonBean = map.get(i);
	    	   List<Element> stepList = new ArrayList<Element>();
	    	   for(int p=0;p<list.size();p++){
	    		   Element root = list.get(p);
	    		   if(jsonBean.getName().equals(root.getName())){//判断xml的name是否相同
	    			   if(!"".equals(jsonBean.getId())){//有Id的情况
	        				Attribute attribute = root.getAttribute(jsonBean.getId());
	        				if(null!=attribute){//判断是否存在Id  
	        					if(!attribute.getValue().equals(jsonBean.getNameCount())){
	        						continue;
	        					}
	        				}else{
	        					continue;
	        				}
	    			   }
	    		   }else{//name不相同的时候，直接跳出
	    			   continue;
	        	   }
	    		   stepList.add(root);
	    	   }
	    	   if(i==map.size()-1){
	    		   return getLastRootList(stepList);
	    	   }else{
	    		   list = getNextRootList(stepList);
	    	   }
	       }
	    } catch (Exception e) {  
	    	e.printStackTrace();  
	    }
	    return null;
    }

	//获得下一层的RootList集合
	public static List<Element> getNextRootList(List<Element> list){
		List<Element> resultList = new ArrayList<Element>();
		for(int p=0;p<list.size();p++){
 		   Element root = list.get(p);
 		   List<Element> rootChild = root.getChildren();
    	   if(rootChild.size() == 0){//最后一层
	    	   //String text = root.getValue();
	    	   //System.out.println("KK "+text);
	       }else{//不是最后一层
	    	   for(int j=0;j<rootChild.size();j++){
	    		    Element rootStep = rootChild.get(j);
					resultList.add(rootStep);
				}
	       }
 	   }
	   return resultList;
	}
	
	//获得最后一层的RootList集合
	public static List<String> getLastRootList(List<Element> list){
		List<String> resultList = new ArrayList<String>();
		for(int p=0;p<list.size();p++){
 		   Element root = list.get(p);
 		   Attribute prn = root.getAttribute("PRN");
 		   //System.out.println(prn.getValue()+"  ");
    	   String text = root.getValue();
    	   
    	   resultList.add(text);
 	   }
	   return resultList;
	}
	
	//根据url获得xml文件
    public static StringBuffer sendGet(String url) {
		StringBuffer result = new StringBuffer("");
		try {
		String urlName = url;
		URL U = new URL(urlName);
		URLConnection connection = U.openConnection();
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result.append(line);
			result.append("\n");
		}
		line = null;
		in.close();
		} catch (Exception e) {
			System.out.println("没有结果！" + e);
		}
		return result;
	}
   
    /** 
    * 判断是否是xml结构 
    */ 
    public static boolean isXML(String value) {
	    try { 
	    	DocumentHelper.parseText(value); 
	    } catch (DocumentException e) { 
	    	return false; 
	    } 
	    return true; 
    } 
}
