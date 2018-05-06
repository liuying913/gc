package com.highfd.common.param;

public class CheckParam {

	public static String checkStartTime(String startTime){
		if(null!=startTime){
			if("undefined".equals(startTime) || "".equals(startTime)){
				startTime=null;
			}
		}
		return startTime;
	}
}
