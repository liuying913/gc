package com.highfd.alarm.strom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class StreamTool {

    public static void main(String[] args) throws IOException {
   	 mainStream("@@@Acups@@@;BJFS,0;TJJX,1;SDTA,1;  ");
	}
   public static void mainStream(String str) throws IOException {
   	//new ServerSocket1(50000);
   	//getSS(9003);

   	//StreamBean sbStream = new StreamBean();
   	Map<String,String> routerMap = new LinkedHashMap<String,String>();
   	Map<String,String> receiverMap = new LinkedHashMap<String,String>();
   	Map<String,String> acupsMap = new LinkedHashMap<String,String>();
   	Map<String,String> dcupsMap = new LinkedHashMap<String,String>();
   	//String str = "@@@Receiver@@@;BJFS,0;TJJX,1;SDTA,1;  ";
   	//String str = "@@@time@@@;2018-03-28 10:10:10";
   	String[] split = str.split("@@@");
   	if(split.length==3){
       	String typeStream = split[1];
       	String dataStream = split[2];
       	if(null!=typeStream && !"".equals(typeStream) && typeStream.length()>0){
       		if(typeStream.indexOf("ime")>-1){
       			boolean analyzeTime = analyzeTime(dataStream);
       			PMCServerSocket.sbStream.setTimeFlag(analyzeTime);
       		}else if(typeStream.indexOf("Router")>-1){
       			routerMap = analyzeStream(dataStream,routerMap);
       			PMCServerSocket.sbStream.setRouterMap(routerMap);
       		}else if(typeStream.indexOf("Receiver")>-1){
       			receiverMap = analyzeStream(dataStream,receiverMap);
       			PMCServerSocket.sbStream.setReceiverMap(receiverMap);
       		}else if(typeStream.indexOf("Acups")>-1){
       			acupsMap = analyzeStream(dataStream,acupsMap);
       			PMCServerSocket.sbStream.setAcupsMap(acupsMap);
       		}else if(typeStream.indexOf("Dcups")>-1){
       			dcupsMap = analyzeStream(dataStream,dcupsMap);
       			PMCServerSocket.sbStream.setDcupsMap(dcupsMap);
       		}
       	}
   	}
   	
   	
   	for(int i=0;i<4;i++){
   		Set<Entry<String, String>> set = null;
   		if(i==0){
   			set = routerMap.entrySet();
   		}else if(i==1){
   			set = receiverMap.entrySet();
   		}else if(i==2){
   			set = acupsMap.entrySet();
   		}else if(i==3){
   			set = dcupsMap.entrySet();
   		}
   		
           Iterator<Entry<String, String>> it = set.iterator();  
   		while(it.hasNext()){
   	        Entry<String, String> next = it.next(); 
   	        String value = next.getValue();
   	        String key = next.getKey();
   	        System.out.println("开始："+key+"--"+value);
   		}
   	}
   	
    }
   
   //解析流
   public static Map<String,String> analyzeStream(String dataStream,Map<String,String> returnMap){
   	if(null!=dataStream && !"".equals(dataStream) && dataStream.length()>0){
   		String[] siteArray = dataStream.split(";");
   		if(siteArray.length>2){
   			for(int v=1;v<siteArray.length;v++){
					if(null!=siteArray[v] && !"".equals(siteArray[v]) && siteArray[v].length()>0 && siteArray[v].indexOf(",")>-1 ){
						String[] oneValue = siteArray[v].split(",");
						returnMap.put(oneValue[0], oneValue[1]);
					}
   			}
   		}
   	}
   	if(null==returnMap || returnMap.size()==0){
   		return null;
   	}else{
   		return returnMap;
   	}
   	
   }
   
   //解析 时间
   public static boolean analyzeTime(String dataStream){
   	if(null!=dataStream && !"".equals(dataStream) && dataStream.length()>0){
   		String[] siteArray = dataStream.split(";");
   		if(siteArray.length==2){
   			System.out.println(siteArray[1]);
   			return checkTime(siteArray[1]);
   		}
   	}
   	return false;
   }
   
   
   public static boolean checkTime(String dateTimes) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date liuDate;
		try {
			liuDate = dateFormat.parse(dateTimes);
			Date nowDate = new Date();
			if( (nowDate.getTime()-liuDate.getTime())>9*60*60*1000      ){
				return false;
			}else{
				return true;
			}
		} catch (ParseException e) {
			System.out.println("流中时间错误！！！时间："+dateTimes);
			return false;
		}
	}

}
