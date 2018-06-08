package com.highfd.sys.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import com.highfd.common.IpCheck;

public class SitePing {

	
	public boolean ping_GC(String server, int timeout) {
		if(null==server || "".equals(server)){//IP不为null
    		return false;
    	}
		if(!server.matches(new MyRegex().pattern)){//正确的IP地址
			return false;
		}
		
		try {
			boolean status=InetAddress.getByName(server).isReachable(timeout);
			return status;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean ping(String server, int timeout) {
		if(!IpCheck.ipCheck(server)){return false;}//如果IP错误
		try {
			boolean status=InetAddress.getByName(server).isReachable(timeout);
			return status;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	public boolean HttpCheck(String url, int timeout) {
	    //url = url.replaceFirst("https", "http"); 
		url="http://"+url;
	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (100 <= responseCode && responseCode <= 599);
	    } catch (IOException exception) {
	        return false;
	    }
	}
	
	
	public boolean pingServer(String url, int timeout) {
		boolean res = false;
		int iter = timeout / 5000;
		while(!res && iter>0)
		{
			res = HttpCheck(url, 5000);
			iter--;
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("Strart！！！");
		Date date = new Date();
		long start_time = date.getTime();
		SitePing ps = new SitePing();
		for(int i=0;i<100;i++){
			boolean flag = ps.ping("192.168.1.223", 2000);
			if(!flag){
				System.out.println("问题："+i);
			}else{
			}
		}
		
		long end_time = new Date().getTime();
		System.out.println("结束:"+(end_time-start_time));
	}
	
	 class MyRegex{
         String pattern = 
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

}
}
