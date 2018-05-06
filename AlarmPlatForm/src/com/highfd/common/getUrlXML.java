package com.highfd.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.highfd.vo.JsonBean;

public class getUrlXML {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url="http://172.128.16.49/CACHEDIR2957015411/xml/dynamic/merge.xml?svData=&agcData=&sv=&glonass=&galileo=&options=";
		StringBuffer sendGet = getUrlXML.sendGet(url);
		url = sendGet.toString();
		System.out.println(url);
		Map<Integer,JsonBean> map = new HashMap<Integer,JsonBean>();
		map.put(0, new JsonBean("gps","",""));
		 	
		analysisXML(sendGet.toString(),map);
	}
	
	public static void analysisXML(String xmlstr,Map<Integer,JsonBean> map){
    	StringReader reader=new StringReader(xmlstr);  
	    InputSource source=new InputSource(reader);  
	    SAXBuilder sax=new SAXBuilder(); 
	    try {
	       Document doc=sax.build(source);  
	       Element root=doc.getRootElement();
	       getElementList(root,map);
	    } catch (Exception e) {  
	    	e.printStackTrace();  
	    }  
    }
    
    //递归获取 信息
    public static void getElementList(Element element,Map<Integer,JsonBean> map) {
    	
    	List<Element> childrenList = element.getChildren();
        if(childrenList.size() == 0){
        	String text = element.getValue();
        	boolean flag = true;
        	for(int i=map.size()-1;i>=0;i--){
        		JsonBean jsonBean = map.get(i);
        		System.out.println(element.getName()+"  OOO");
        		if(jsonBean.getName().equals(element.getName())){//判断xml的name是否相同
        			if(!"".equals(jsonBean.getId())){//有Id的情况
        				Attribute attribute = element.getAttribute(jsonBean.getId());
        				if(null!=attribute){//判断是否存在Id  
        					if(!attribute.getValue().equals(jsonBean.getNameCount())){
        						flag = false;
        						break;
        					}
        				}else{
        					flag = false;
        					break;
        				}
        			}
        		}else{//name不相同的时候，直接跳出
        			flag = false;
        			break;
        		}
        		element=element.getParentElement();
        		if(element==null){//已经到了最顶层的时候
        			break;
        		}
        	}
            if(flag){
            	System.out.println(text);
	       	}
        }else{
        	for(int j=0;j<childrenList.size();j++){
				Element itemStep = childrenList.get(j);
				getElementList(itemStep,map);
			}
        }
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
		System.out.println(result);
		return result;
	}
}
