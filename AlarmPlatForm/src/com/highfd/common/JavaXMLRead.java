package com.highfd.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import com.highfd.vo.CrawlStepsBean;

public class JavaXMLRead {
    //添加
	public static String writeXMLDetails(String path,int itemid, ArrayList<CrawlStepsBean> stepslist) throws FileNotFoundException, JDOMException, IOException, InterruptedException{
		Document doc = getXMLDom(null,path,"read");
		Element root = doc.getRootElement();//获得根节点
		Element crawlURL = new Element("crawlURL");//创建子节点crawURL
		
		crawlURL.setAttribute("id", itemid+"");//添加属性 id;
		crawlURL.setAttribute("name", "");
		crawlURL.setAttribute("enable", "true");
		
		Element resultFormat = new Element("resultFormat");
		
		for(int j=0;j<stepslist.size(); j++){
			CrawlStepsBean csb = stepslist.get(j);
			Element step = new Element("step");//创建子节点step
			step.setAttribute("grade", ""+j);//设置grade属性

			step.addContent(new Element("charset").setText(getUtf(csb.getCharset())));
			step.addContent(new Element("crawMethod").setText(csb.getCrawMethod()));
			
			step.addContent(new Element("baseUrl").setText(getUtf(csb.getBaseUrlDom())));
			step.addContent(new Element("urlRule").setText(getUtf(csb.getUrlDom())));
			step.addContent(new Element("urlFilter").setText(getUtf(csb.getFilter())));
			
			//if(saveXMLParam.getJadgepage().indexOf("1")>-1){
				step.addContent(new Element("pageUrlRule").setText(getUtf(csb.getPagedom())));
				step.addContent(new Element("pageUrlFilter").setText(getUtf(csb.getPagefilter())));
			//}else{
			//	step.addContent(new Element("pageUrlRule").setText(saveXMLParam.getPagedom()));
			//	step.addContent(new Element("pageUrlFilter").setText(saveXMLParam.getPagefilter()));
			//}
			
			step.addContent(new Element("dateRule").setText(getUtf(csb.getDateDom())));
			step.addContent(new Element("dateRegex").setText(getUtf(csb.getDateRegex())));
			step.addContent(new Element("valueRule").setText(getUtf(csb.getValueDom())));
			step.addContent(new Element("valueRegex").setText(getUtf(csb.getValueRegex())));
			
			crawlURL.addContent(step);
			
			if(j==0){
				crawlURL.setAttribute("url", getUtf(csb.getUrl()));
				resultFormat.addContent(new Element("dateFormat").setText(getUtf(csb.getDateCheckFormat())));
				resultFormat.addContent(new Element("dateLiminal").setText(getUtf(csb.getDateCheckThr())));
				resultFormat.addContent(new Element("valueFormat").setText(getUtf(csb.getValueCheckFormat())));
				resultFormat.addContent(new Element("valueLiminal").setText(getUtf(csb.getValueCheckThr())));
				
			}
		}
		crawlURL.addContent(resultFormat);
		root.addContent(crawlURL);//将crawURL交给root
		getXMLDom(doc,path,"save");
		return getLogXMLdetails(crawlURL);//日志信息
	}
	
	//删除  根据Id进行删除
	public static String deleteXMLdetails(String path, String deleteId) throws FileNotFoundException, JDOMException, IOException, InterruptedException{
		
		String deleteXML = "";
		Document doc = getXMLDom(null,path,"read");
		Element root = doc.getRootElement();
		List<Element> rootList = root.getChildren();
		for (int i = 0; i < rootList.size(); i++) {
			Element rootItem = (Element) rootList.get(i);
			String id = rootItem.getAttributeValue("id");
			if(id.equals(deleteId)){
				deleteXML = getLogXMLdetails(rootItem);
				root.removeContent(rootItem);
			}
		}
		getXMLDom(doc,path,"save");
		return deleteXML;
	}
	
	//获得XML根目录
	public static List<Element> getRootList(String path) throws FileNotFoundException, JDOMException, IOException{
		Document doc = getXMLDom(null,path,"read");
		Element root = doc.getRootElement();//获得根节点
		return root.getChildren();//将根节点下的所有子节点放入List中
	}
	
	
	
	//定时器中  从内存中获得xml信息
	
	//根据Id 获得xml明细
	public static List<CrawlStepsBean> getXMLDetail(String path,String xmlId) throws FileNotFoundException, JDOMException, IOException{
		List<CrawlStepsBean> resultList = new ArrayList<CrawlStepsBean>();
		try{
			Document doc = JavaXMLRead.getXMLDom(null,path,"read");
			Element root = doc.getRootElement();//获得根节点
			List<Element> rootList = root.getChildren();//将根节点下的所有子节点放入List中
			for (int i = 0; i < rootList.size(); i++) {
				Element rootItem = (Element) rootList.get(i);//取得节点实例
				String id = rootItem.getAttribute("id").getValue();//取得属性值
				if(id.equals(xmlId)){
					String url = rootItem.getAttribute("url").getValue();//取得属性值
					List<Element> stepList = rootItem.getChildren();
					for(int j=0;j<stepList.size();j++){
						Element step = stepList.get(j);
						String stepName = step.getName();
						if("step".indexOf(stepName)>-1) {
							CrawlStepsBean  csb = new CrawlStepsBean();
							csb.setGrade(step.getAttribute("grade").getValue());
							csb.setCharset(step.getChild("charset").getText());
							csb.setCrawMethod(step.getChild("crawMethod").getText());
							csb.setUrlDom(step.getChild("urlRule").getText());
							csb.setBaseUrlDom(step.getChild("baseUrl").getText());
							csb.setFilter(step.getChild("urlFilter").getText());
							if(null!=step.getChild("pageUrlRule").getText() && !"".equals(step.getChild("pageUrlRule").getText())){
								csb.setJadgepage("1");
							}else{
								csb.setJadgepage("0");
							}
							csb.setPagedom(step.getChild("pageUrlRule").getText());
							csb.setPagefilter(step.getChild("pageUrlFilter").getText());
							csb.setDateDom(step.getChild("dateRule").getText());
							csb.setDateRegex(step.getChild("dateRegex").getText());
							csb.setValueDom(step.getChild("valueRule").getText());
							csb.setValueRegex(step.getChild("valueRegex").getText());
							resultList.add(csb);
						}
						if("resultFormat".indexOf(stepName)>-1) {
							Element resultFormat = rootItem.getChild("resultFormat");
							CrawlStepsBean csb = resultList.get(0);
							csb.setUrl(url);
							csb.setDateCheckFormat(resultFormat.getChild("dateFormat").getText());
							csb.setDateCheckThr(resultFormat.getChild("dateLiminal").getText());
							csb.setValueCheckFormat(resultFormat.getChild("valueFormat").getText());
							csb.setValueCheckThr(resultFormat.getChild("valueLiminal").getText());
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	
	//检测是否与已有的url重复
	public static String checkUrlRepeat(String path,String pageUrl) throws FileNotFoundException, JDOMException, IOException{
		Document doc = getXMLDom(null,path,"read");
		Element root = doc.getRootElement();//获得根节点
		List<Element> list = root.getChildren();//将根节点下的所有子节点放入List中
		String returnId= "-1";
		for (int i = 0; i < list.size(); i++) {
			Element item = (Element) list.get(i);//取得节点实例
			String id = item.getAttribute("id").getValue();//取得属性值
			String url = item.getAttribute("url").getValue();//取得属性值
			if(pageUrl.equals(url)){
				System.out.println("该url与id为：" + id+"的item是相同的");
				return id;
			}
		}
		return returnId;
	}
	
	public static String getUtf(String str) throws UnsupportedEncodingException{
		//String resultStr = new String(str.toString().getBytes("ISO-8859-1"), "gb2312");
		return str;
	}
	
	@SuppressWarnings("deprecation")
	public synchronized static Document getXMLDom(Document doc,String path, String method) throws FileNotFoundException, JDOMException, IOException{
		
		if("read".equals(method)){
			SAXBuilder sb = new SAXBuilder(true);
			return doc = sb.build(new FileInputStream(path));
		}else{
			Format format = Format.getCompactFormat();   
		    format.setEncoding("GBK");   //设置xml文件的字符为utf-8            
		    format.setIndent("    ");      //设置xml文件的缩进为4个空格    
		    //***********end*******************************
			XMLOutputter op = new XMLOutputter(format);
			FileWriter writer = null;
			writer = new FileWriter(path);
	        op.output(doc, writer);
	        writer.close();
	        op.clone();
	        return null;
		}
	}
	
	public static String getLogXMLdetails(Element rootItem){
		Format format = Format.getCompactFormat();   
	    format.setEncoding("GBK");   //设置xml文件的字符为utf-8            
	    XMLOutputter op = new XMLOutputter(format);
		String deleteXML = op.outputString(rootItem);
		return deleteXML;
	}
	
	public static void main(String[] args) throws Exception {
		String path="D://read.xml";
		//getXMLDetail(path);
		//writeXMLDetail(path);
		//testtest(path);
		
		  List<String> oracleList = new ArrayList<String>();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.1.20/servergfp";
			String user = "gfpuser";
			String password = "gfpuser";
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = conn.createStatement();
			String sql = "select * from HIGHFD_DATA";
			ResultSet rs = stmt.executeQuery(sql);
			//String eventType= "AUPS";
			while(rs.next()){
				String itemid = rs.getString("itemid").trim();
				System.out.println(itemid);
				oracleList.add(itemid);
			}
	}
	
	
	//根据Id 获得xml明细
	public static List<CrawlStepsBean> testtest(String path) throws FileNotFoundException, JDOMException, IOException{
		List<CrawlStepsBean> resultList = new ArrayList<CrawlStepsBean>();
		try{
			
            List<String> oracleList = new ArrayList<String>();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.1.20/servergfp";
			String user = "gfpuser";
			String password = "gfpuser";
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = conn.createStatement();
			String sql = "select * from HIGHFD_DATA";
			ResultSet rs = stmt.executeQuery(sql);
			//String eventType= "AUPS";
			while(rs.next()){
				String itemid = rs.getString("itemid").trim();
				oracleList.add(itemid);
			}
			
			
			List<String> list = new ArrayList<String>();
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new FileInputStream(path));
			Element root = doc.getRootElement();//获得根节点
			List<Element> rootList = root.getChildren();//将根节点下的所有子节点放入List中
			for (int i = 0; i < rootList.size(); i++) {
				Element rootItem = (Element) rootList.get(i);//取得节点实例
				String id = rootItem.getAttribute("id").getValue();//取得属性值
				if(!oracleList.contains(id)){
					System.out.println(id);
				}
				list.add(id);
			}
			System.out.println(list.size());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
}
