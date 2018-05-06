package com.highfd.sys.sysTool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetXMLPath {

	
	public static String getXMLPath(Class classs){
		String path = classs.getResource("/").getFile().toString();
		//if(path.indexOf("WEB-INF") > 0){path = path.substring(0, path.indexOf("WEB-INF/classes")); }
		//if(path.indexOf("classes") > 0){path = path.substring(0, path.indexOf("classes")); }
		path += "jdbc.properties";
		return path;
	}
	/*
	//判断是什么系统
	public static String getWindowOrLinux(String path) throws IOException{
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty("winOrLinux").trim();
		return xmlPath;
	}
	
	//文件系统路径
	public static String getFileSystemPath(String path) throws IOException{
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty("fileSystemPath").trim();
		return xmlPath;
	}*/
	
	//获得配置参数
	public static String getProperties(String path,String pName) throws IOException{
		InputStream in = new BufferedInputStream(new FileInputStream(path));   
		Properties p = new Properties();   
		p.load(in);  
		String xmlPath = p.getProperty(pName).trim();
		return xmlPath;
	}

}
