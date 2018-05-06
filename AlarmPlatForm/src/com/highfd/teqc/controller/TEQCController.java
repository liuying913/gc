package com.highfd.teqc.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import com.highfd.sys.sysTool.GetXMLPath;
import com.highfd.sys.sysTool.TimeUtils;
import com.highfd.teqc.service.TEQCService;

/**
 * 基于注解的定时器
 */
@Component
@Controller
public class TEQCController {
	
	/**
	 * o文件的根目录
	 */
	public static String oFileRootPath = "";
	
	@Autowired
	TEQCService teqcService;
	
	/**
	 * 每天早上8:00计算昨天的o文件
	 */
	public void everyStartTask() throws Exception {
		
		//远程下载文件
		//new Ftp().oneLogindownFile(url, username, password,remotePath, localPath);
		
		//获得年积日
	    String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String yesterday = TimeUtils.getValidityTime(today, -1);
		String dayNumberOfYear = TimeUtils.getDayNumberOfOneYear(yesterday);
		
		//o文件路径
		String winOrLinux = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"winOrLinux");//判断是win 还是 linux
		if("win".equals(winOrLinux)){
			oFileRootPath = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"oFileRootPath");//o文件的根目录
		}else{
			oFileRootPath = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"oFileRootPath_Linux");//o文件的根目录
		}
		
		String year = yesterday.substring(0, 4);
		oFileRootPath = oFileRootPath+"/"+year+"/"+dayNumberOfYear+"/";
		
		teqcService.analysisAll_o(new File(oFileRootPath));
	}
	
}