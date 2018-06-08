package com.highfd.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtils implements ApplicationContextAware{
	
   public static final Logger itemCRUD = LoggerFactory.getLogger("itemCRUD"); 
   public static ApplicationContext context;  
  
    public void setApplicationContext(ApplicationContext ctx)  throws BeansException {  
       System.out.println("容器初始化是否成功：" + (ctx!=null));  
       context = ctx;  
       /*SiteStationDAO userDao=(SiteStationDAO)context.getBean("SiteStationDAO");*/
	}  

}
