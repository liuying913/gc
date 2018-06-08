package com.highfd.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCommon{  
	
	
	
	//通过字符串截取里面的数值
	public static String getNumberByString(String str){
		 Pattern p=Pattern.compile("(\\d+)");   
		 Matcher m=p.matcher(str);       
		 if(m.find()){return m.group(1);  
		 }else{return null; }
	}
	
	//获得季度
	public static String getQuarterByString(String str){
		if(null==getNumberByString(str)){
			if(Pattern.compile("一").matcher(str).find()){ return "01-01";  
			}else if(Pattern.compile("二").matcher(str).find()){return "04-01";  
			}else if(Pattern.compile("三").matcher(str).find()){return "07-01";  
			}else if(Pattern.compile("四").matcher(str).find()){return "10-01";  
			}else{return null;}
		}else{
			String quarter = getNumberByString(str);
			if("1".equals(quarter)){quarter = "01-01";
			}else if("2".equals(quarter)){quarter = "04-01";
			}else if("3".equals(quarter)){quarter = "07-01";
			}else if("4".equals(quarter)){quarter = "10-01";}
			return quarter;
		}
	}
	//Sting--->sql date
    public static java.sql.Date getDate(String dates){

    	//周的形式
    	if(dates.indexOf("周")>-1){
    		String year = getNumberByString(dates.substring(0, dates.indexOf("年")));
    		String week = getNumberByString(dates.substring(dates.indexOf("年"), dates.indexOf("周")));
    		dates = ConvertDateTest.getStartDayOfWeekNo(Integer.parseInt(year),Integer.parseInt(week));
    	}else if(dates.indexOf("季")>-1){
    		String year = getNumberByString(dates.substring(0, dates.indexOf("年")));
    		String quarter = getQuarterByString(dates.substring(dates.indexOf("年"), dates.indexOf("季")));
    		dates = year+"-"+quarter;
    	}else{//其他形式
    		dates = dates.replaceAll(" ", "").replaceAll("上旬", "01").replaceAll("中旬", "11").replaceAll("下旬", "21").replaceAll("年", "-").replaceAll("月份", "-").replaceAll("月", "-").replaceAll("日", "").replaceAll("/", "-").replaceAll("_", "-").trim();
        	if(dates.endsWith("-")){
        		dates=dates.substring(0, dates.length()-1);
        	}
    	}
    	
    	//System.out.print(dates);
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	if(getYearMonth(dates)){                    //年月形式
    		sdf = new SimpleDateFormat("yyyy-MM");
    	}else if(getYearMonthCom(dates)){           //年月形式
    		sdf = new SimpleDateFormat("yyyy.MM");
    	}else if(getMonthYear(dates)){              //月年形式
    		sdf = new SimpleDateFormat("MM-yyyy");
    	}else if(getYearMonthDay(dates)){           //年月日形式
    		sdf = new SimpleDateFormat("yyyy-MM-dd");
    	}else{                                      //月日年形式
    		sdf = new SimpleDateFormat("MM-dd-yyyy");
    	}
    	
    	java.util.Date parse = new java.util.Date();
		try {
			parse = sdf.parse(dates);
			//System.out.println("   "+parse);
		} catch (ParseException e) {
			//System.err.println(dates +" 时间转换出现错误！！！");
			//e.printStackTrace();
			return null;
		}
		//System.out.println(parse.getTime());
    	return new java.sql.Date(parse.getTime());
    }
    
    
    //格式2015-12
	public static boolean getYearMonth(String date){
		String regex = "^\\d{4}\\-\\d{1,2}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式2015.12
	public static boolean getYearMonthCom(String date){
		String regex = "^\\d{4}\\.\\d{1,2}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式12-2015
	public static boolean getMonthYear(String date){
		String regex = "^\\d{1,2}\\D+\\d{4}";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}
	//格式2015-12-02
	public static boolean getYearMonthDay(String date){
		String regex = "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}[^>]*";  
		 Pattern pattern = Pattern.compile(regex);  
		 Matcher matcher = pattern.matcher(date);  
		 if(matcher.matches()){return true;
        }else{return false;}  
	}

	
	
	/*	//格式12-02-2015
		public static boolean getDateRegex1(String date){
			String regex = "[0-9]{2}-[0-9]{2}-[0-9]{4}";  
			 Pattern pattern = Pattern.compile(regex);  
			 Matcher matcher = pattern.matcher("12-02-2015");  
			 if(matcher.matches()){  
				 return true;
	        }else{  
	        	return false;
	        }  
			
		}*/
	
	
	//日期  当天时间
	public static String getNowDate(){
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	} 
	//日期 string——>Date
	public static Date getDates(String str){
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");         
		Date date = null;                    
		try {    
		    date = format1.parse(str);   
		} catch (ParseException e) {    
		    e.printStackTrace();    
		}  
		return date;
	}
	//日期 string 转换成long型
	public static Long getDataTimeLong(String dates) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.parse(dates).getTime();
	}
	//日期 long型转换成 date型
	public static Date getTimeLongToDate(Long times) throws ParseException{
		Date dt = new Date(times); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String str = format.format(dt);
		Date date = format.parse(str);
		return date;
	}
	//根据周期算秒
	public static long getCycleTimeMinutes(String fre){
		long longTime = 0;
		if("6".equals(fre)){
			longTime = 1;
		}else if("5".equals(fre)){
			longTime =  7;
		}else if("4".equals(fre)){
			longTime =  10;
		}else if("3".equals(fre)){
			longTime =  30;
		}else if("2".equals(fre)){
			longTime =  3*30;
		}else if("1".equals(fre)){
			longTime =  365;
		}else{
			longTime =  0;
		}
		return longTime*86400*1000;
	}
	
	//获得当天日期
	public static String getSysMonthDay(){
		Calendar cal=Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日
		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	//获得昨天日期
	public static String getYesMonthDay(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日

		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	//获得前天日期
	public static String getBeforeMonthDay(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		int month = cal.get(Calendar.MONTH)+1;//月
		String monthStr="";  String dayStr="";
		if(month<10){
			monthStr="0"+month;
		}else{
			monthStr=month+"";
		}
		int day   = cal.get(Calendar.DATE);//日
		if(day<10){
			dayStr = "0"+day;
		}else{
			dayStr = day+"";
		}
		return monthStr+"-"+dayStr;
	}
	
	public static String getAllStrTime(String str){
		Calendar cal=Calendar.getInstance();
		return cal.get(Calendar.YEAR)+"-"+str;
	}
	
	public static boolean checkLastThreeDay(String todayTime){
		boolean flag=false;
		if (TimeCommon.getSysMonthDay().equals(todayTime)) {//当天
			flag=true;
		}/*else if (TimeCommon.getYesMonthDay().equals(todayTime)) {
			flag=true;
		}else if (TimeCommon.getBeforeMonthDay().equals(todayTime)) {
			flag=true;
		}*/
		return flag;
	}
	
	public static void main(String []abc) throws ParseException, InterruptedException{
		System.out.println(checkLastThreeDay("05-06"));
		//System.out.println(getSysMonthDay());
		//System.out.println("现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒"); 
		//System.out.println(getNowDate());
		/*
		 * 第一种：2015-12-02 12-12-12  
		 * 第二种：2015-12-02 12:12:12   
		 * 第三种：2015年12月02日
		 * 第四种：2015/12/02
		 * 第五种：2015/12/02dfdff
		 * 第六种：2015_12_02
		 * 第七种：12_02_2015
		 * 第八种：2_2_2015ewewe
		 */
		//System.out.println(getMonthYear("1-1-2015"));
		//getDate("2015年12月");//1448985600000//年月、月年的形式
		//getDate("2016年第1周");//1448985600000
		//getDate("2016年2月上旬大方的说法");//1448985600000
		//getDate("2016年第1季度");//1448985600000
		//getDate("2016年第三季度");//1448985600000
		
		//getDate("2016.01");//1448985600000
		//getDate("2016-01-29 00:00:00.0");//1448985600000
		
		
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));// new Date()为获取当前系统时间
		
		
		/*SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
		System.out.println("2016-01-29 00:00:00.0");
		Date dateStr = sdf.parse("2016-01-29 00:00:00.0");
		System.out.println(dateStr);
		java.util.Date parse = new java.util.Date();*/
		/*String url="http://sou.zhaopin.com/jobs/searchresult.ashx?in=160200&jl=%E4%B8%8A%E6%B5%B7&sm=0&sf=0&st=99999&el=7&isadv=1&sg=d90536b8a7504110a4bb9ae738df7e10&p=";
		for(int degree=0;degree<degreeList.size();degree++){
			String middle = url.substring(0, url.indexOf("&el="));
			String end    = url.substring(url.indexOf("&isadv"), url.length());
			url = middle+"&el="+degreeList.get(degree)+end;
		}*/
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		Thread.sleep(1000);
		System.out.println(System.currentTimeMillis());
		 
	}
	
	public static List<String> degreeList = new ArrayList<String>();
	static{
		degreeList.add("1");degreeList.add("3");degreeList.add("4");degreeList.add("5");degreeList.add("6");
	}
}