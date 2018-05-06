package com.highfd.sys.sysTool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	
	public static String get_FTP_UserName(){
		  Date d = new Date();  
	      System.out.println(d);  
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	      String dateNowStr = sdf.format(d).replaceAll("-", "");  
	      System.out.println("格式化后的日期：" + dateNowStr);
	      return dateNowStr;
	}
	
	
	public static String get_FTP_EarthQuake_UserName(){
		  Date d = new Date();  
	      System.out.println(d);  
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	      String dateNowStr = sdf.format(d).replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
	      System.out.println("格式化后的日期：" + dateNowStr);
	      return dateNowStr;
	}
	
	
	public static Timestamp getTempTime(String dateStr) throws ParseException{
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
	    Date date=simpleDateFormat.parse(dateStr);
	    Timestamp timestamp = new java.sql.Timestamp(date.getTime());
	    return timestamp;
	}
	
	public static Timestamp getTempByAllTime(String dateStr) throws ParseException{
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
	    Date date=simpleDateFormat.parse(dateStr);
	    Timestamp timestamp = new java.sql.Timestamp(date.getTime());
	    return timestamp;
	}
	
	public static String TimestampToString(Timestamp ts){
        String tsStr = "";
        try {   
            //方法一   
        	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");   
            tsStr = sdf.format(ts);   
            //方法二   
            tsStr = ts.toString(); 
            
            if(tsStr.indexOf(".")>=-1){
            	tsStr = tsStr.substring(0, tsStr.indexOf("."));
            }
            return tsStr;
        } catch (Exception e) {   
            e.printStackTrace(); 
            return null;
        }  
	}
	
	public static String TimestampToString2(Timestamp ts){
        String tsStr = "";
        try {   
            //方法一   
        	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");   
            tsStr = sdf.format(ts);   
            
            return tsStr;
        } catch (Exception e) {   
            e.printStackTrace(); 
            return null;
        }  
	}
	
	
	public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
         
        return lastDayOfMonth;
    }
 
	
	
	public static int getBetweenTwoDayNumbers(String timeStr1,String timeStr2){
		
        try {
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
            Calendar cal = Calendar.getInstance();    
			cal.setTime(sdf.parse(timeStr1));
			long time1 = cal.getTimeInMillis();                 
	        cal.setTime(sdf.parse(timeStr2));    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	        System.out.println(Integer.parseInt(String.valueOf(between_days))+1);
	        return Integer.parseInt(String.valueOf(between_days))+1;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}    
       
	}
	

	public static String getDayNumbers(String t){
		t = t.substring(t.indexOf("-")+1, t.length());
		t = t.substring(t.indexOf("-")+1, t.length());
		if(t.startsWith("0")){
			t = t.substring(1, t.length());
		}
		return t;
	}
	
	
	public static String getTimeCha(String startTime,String endTime){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try{
		    Date d1 = df.parse(startTime);
		    Date d2 = df.parse(endTime);
		    long diff = d2.getTime() - d1.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    days+=1;
		    System.out.println(days);
		    return days+"";
		}catch (Exception e){
			e.printStackTrace();
			return 0+"";
		}
	}

	
	public static String getLastNumberOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(lastDay);
       
        return lastDay+"";
    }
	
	public static int getNowYear(){
		Calendar now = Calendar.getInstance();  
	    return now.get(Calendar.YEAR);  
	}
	
	public static String getYearByTime(String time){
		if(time.indexOf("-")>-1){
			return time.substring(0,time.indexOf("-"));
		}else{
			return time;
		}
		
	}
	
	
	
	/**
	 * 方法描述：得到当前日期向前推beforeDays天后的日期
	 * @param beforeDays 向前推的天数
	 * @return 向前推beforeDays天后的日期
	 */
	public static String getStrYesterday(int beforeDays) {
		int days = 0;
		days = 0-beforeDays;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		Date dateStrings = calendar.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dFormat.format(dateStrings);
	}
	
	
	public static String thisMonthFirstDaate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    //获取当前月第一天：
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        return first;
	}
	
	public static String thisMonthLastDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        return last;
	        
	}
	

	
	//应急数据有效期  加上7天后的结果
	public static String getValidityTime(String strDate, int num){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
 		Date date = new Date();
		try {
			date = f.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.add(Calendar.DAY_OF_MONTH, num);// 今天+1天  
   
        Date tomorrow = c.getTime();  
        System.out.println("截止日期是:" + f.format(tomorrow)); 
        return f.format(tomorrow);
	}

	
	public static String checkDateBig(String dateStr){
		java.util.Date nowdate=new java.util.Date(); 

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean flag = d.before(nowdate);
		if(!flag){
			return dateStr;
		}else{
			return "已过期";
		}
	}
	
	/**
	 * 得到年
	 */
	public static String getSysYear(){
		 Calendar yc=Calendar.getInstance();
		 System.out.println(yc.get(Calendar.YEAR));
		 return yc.get(Calendar.YEAR)+"";
	}
	
	/**
	 * 得到年月日
	 */
	public static String getSysYearMonthDay(){
		Calendar calendar = Calendar.getInstance();
		Date dateStrings = calendar.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		return dFormat.format(dateStrings);
	}
	
	/**
	 * 得到年月日
	 */
	public static String getSysYearMonthDay2(){
		Calendar calendar = Calendar.getInstance();
		Date dateStrings = calendar.getTime();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dFormat.format(dateStrings);
	}
	
	
	public static String getDayNumberOfOneYear(String dataStr) throws ParseException{
		String numStr = "";
		Integer dayInt = Integer.valueOf(getDayNumberOfOneYear(TimeUtils.getTempTime(dataStr)));
		if(dayInt<=9){
			numStr = "00"+dayInt;
		}else if(dayInt<=99){
			numStr = "0"+dayInt;
		}else{
			numStr = ""+dayInt;
		}
		return numStr;
	}

	public static String getDayNumberOfOneYear(Timestamp dateStr){
		Calendar ca = Calendar.getInstance();//创建一个日期实例
	    Date myDate1;
		myDate1 = dateStr;
		ca.setTime(myDate1);//实例化一个日期
		return ca.get(Calendar.DAY_OF_YEAR)+"";
	}
	
	public static String hoursChange_8(String startTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
		date = sdf.parse(startTime);
		} catch (ParseException e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
		}
		Calendar ca=Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY, -8);
		System.out.println(sdf.format(ca.getTime()));
		return sdf.format(ca.getTime());
	}
	
	public static void main(String[] args) {
		hoursChange_8("2012-07-25 4:00:00");
	}
}
