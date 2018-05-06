package com.highfd.common;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class JavaXMLWrite {
	
	/**
	 * 该方法适用于创建新的xml文件时
	 * @param path
	 * @throws Exception
	 */
	public static void buildXML(String path) throws Exception {
		//*********创建包含dtd的xml文件***********************
		SAXBuilder sb = new SAXBuilder(true);
		String iniPath="ini.xml";
		Document Doc = sb.build(new FileInputStream(iniPath));
		Element root = Doc.getRootElement();
		List list = root.getChildren();
		Element iniItem = (Element) list.get(0);
		root.removeContent(iniItem);
		//*********end***********************
		
		
		for (int i = 0; i < 5; i++) {
			Element crawlURL = new Element("crawlURL");//创建子节点crawURL
			crawlURL.setAttribute("id", ""+i);//添加属性 id;
			crawlURL.setAttribute("name", "京东");
			crawlURL.setAttribute("enable", "true");
			for(int j=0;j<2; j++){
				Element step = new Element("step");//创建子节点step
				step.setAttribute("grade", ""+j);//设置grade属性
				
				step.addContent(new Element("charset").setText("charset"));
				step.addContent(new Element("crawMethod").setText("crawMethod"));
				step.addContent(new Element("baseUrl").setText("baseUrl"));
				step.addContent(new Element("urlRule").setText("urlRule"));
				step.addContent(new Element("urlFilter").setText("urlFilter"));
				
				step.addContent(new Element("pageUrlRule").setText("pageUrlRule"));
				step.addContent(new Element("pageUrlFilter").setText("pageUrlFilter"));
				
				step.addContent(new Element("dateRule").setText("dateRule"));
				step.addContent(new Element("dateRegex").setText("dateRegex"));
				step.addContent(new Element("valueRule").setText("valueRule"));
				step.addContent(new Element("valueRegex").setText("valueRegex"));
				crawlURL.addContent(step);
			}
			
			Element resultFormat = new Element("resultFormat");
			resultFormat.addContent(new Element("dateFormat").setText(""));
			resultFormat.addContent(new Element("dateLiminal").setText(""));
			resultFormat.addContent(new Element("valueFormat").setText(""));
			resultFormat.addContent(new Element("valueLiminal").setText(""));
			crawlURL.addContent(resultFormat);
			
			root.addContent(crawlURL);//将crawURL交给root
		}
		
		//***********设置输出格式（可以不用设置）*******************************
		Format format = Format.getCompactFormat();   
	    format.setEncoding("utf-8");   //设置xml文件的字符为utf-8            
	    format.setIndent("    ");      //设置xml文件的缩进为4个空格              
	    //***********end*******************************
	    
		XMLOutputter XMLOut = new XMLOutputter(format);
		FileWriter writer = new FileWriter(path);
		XMLOut.output(Doc, writer);//输出company_list.xml文件；  
		writer.close();
		XMLOut.clone();
	}

	/**
	 * 判断写入的xml是否正确
	 * @param path
	 * @return
	 */
	public static boolean checkXML(String path){
		try {
			JavaXMLRead.getXMLDom(null,path,"read");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return true;
		} catch (JDOMException e) {
			e.printStackTrace();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		String path = "write.xml";
		
		try {
			buildXML(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean checkXML = checkXML(path);
		if(checkXML){
			System.out.println("错误！");
		}else{
			System.out.println("写入正确！");
		}
	}
}


//如果要删除某一个节点的话
//item.removeAttribute("id");//删除某个属性
//if("2".equals(item.getAttribute("id").getValue().trim())){
	//root.removeContent(item);删除节点
//}