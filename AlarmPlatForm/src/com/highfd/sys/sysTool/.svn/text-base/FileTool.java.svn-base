package com.highfd.sys.sysTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.highfd.teqc.model.FileInfo;

	public class FileTool {
	
		@SuppressWarnings("resource")
		public static long forChannel(final File f1,final File f2) throws Exception{
			System.out.println(f1.getName());
	        long time=new Date().getTime();
	        int length=2097152;
	        FileInputStream in=new FileInputStream(f1);
	        FileOutputStream out=new FileOutputStream(f2);
	        FileChannel inC=in.getChannel();
	        FileChannel outC=out.getChannel();
	        ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	                return new Date().getTime()-time;
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	   }
	 
	   public static String getYearByFileName(String fileName){
		   if(fileName.endsWith("d.Z")){
		   }else if(fileName.endsWith("D.Z")){
		   }else if(fileName.endsWith("D.z")){
		   }else if(fileName.endsWith("d.z")){
		   }else{
			   return null;
		   }
		   fileName = fileName.substring(9, 11);
		   String year = "20"+fileName;
		   System.out.println(year);
		   return year;
	   }
	 
	 
	   // 判断文件夹是否存在
	   public static boolean judeDirExists(String filePath) {
		   boolean flag = false;
		   File file = new File(filePath);
		   file.setWritable(true, false);
	       if (file.exists()) {
	           if (file.isDirectory()) {
	           } else {
	               System.out.println("the same name file exists, can not create dir");
	           }
	       } else {
	           System.out.println(file.getAbsolutePath());
	           flag = file.mkdir();
	           if(flag){
	        	   System.out.println("创建目录成功！");
	           }else{
	        	   System.out.println("创建目录失败！");
	           }
	       }
	       return flag;
	   }
	    /**   
	     * 递归查找文件   
	     * @param baseDirName  查找的文件夹路径   
	     * @param targetFileName  需要查找的文件名   
	     */    
	    public static File findFiles(String baseDirName, String targetFileName) {     
	  
	        File baseDir = new File(baseDirName);       //创建一个File对象  
	        if (!baseDir.exists() || !baseDir.isDirectory()) {  //判断目录是否存在  
	            //System.out.println("文件查找失败：" + baseDirName + "不是一个目录！"); 
	            return null;
	        }
	        String tempName = null;     
	        //判断目录是否存在     
	        File tempFile;  
	        File[] files = baseDir.listFiles();  
	        for (int i = 0; i < files.length; i++) {
	            tempFile = files[i];  
	            if(tempFile.isDirectory()){  
	                findFiles(tempFile.getAbsolutePath(), targetFileName);  
	            }else if(tempFile.isFile()){  
	                tempName = tempFile.getName();  
	                if(tempName.equals(targetFileName)){  
	                    return tempFile.getAbsoluteFile();  
	                }  
	            }
	        }
	        return null;  
	    }     
	    
	    
	    
	    
	    /**   
	     * 递归查找文件  (气象文件查询，忽略大小写) 
	     * @param baseDirName  查找的文件夹路径   
	     * @param targetFileName  需要查找的文件名   
	     */    
	    public static String findFiles2(String baseDirName, String targetFileName) {     
	  
	        File baseDir = new File(baseDirName);       //创建一个File对象  
	        if (!baseDir.exists() || !baseDir.isDirectory()) {  //判断目录是否存在  
	            //System.out.println("文件查找失败：" + baseDirName + "不是一个目录！"); 
	            return null;
	        }
	        String tempName = null;     
	        //判断目录是否存在     
	        File tempFile;  
	        File[] files = baseDir.listFiles();  
	        for (int i = 0; i < files.length; i++) {
	            tempFile = files[i];  
	            if(tempFile.isDirectory()){  
	                findFiles2(tempFile.getAbsolutePath(), targetFileName);  
	            }else if(tempFile.isFile()){  
	                tempName = tempFile.getName();  
	                if(tempName.equalsIgnoreCase(targetFileName)){  
	                    return tempName;  
	                }  
	            }
	        }
	        return null;  
	    }     
		public static boolean deleteFile(String filePath) {
			File f = new File(filePath);
			if (!f.exists()) {
				 System.out.println("deleteFile() " + filePath + "  file is not exist!");
				return false;
			}
			if (f.isDirectory()) {
				 System.out.println("deleteFile() " + filePath+ " is directory,delete file is failed!");
				return false;
			}
			return f.delete();
		}
		
		public static String getYearByZ(String fileName){
			String name = fileName;
			try{
				name = name.substring(name.indexOf(".")+1, name.indexOf("d.Z"));
				if(name.length()==2){return "20"+name;}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("数据下载入库时，入库失败！"+fileName);
			}
			return null;
		}
		

		public static String getFileNameByPath(String path){
			path = path.substring(path.lastIndexOf("/")+1, path.length());
			System.out.println(path);
			return path;
		}
		
		public static String getYearByPath(String path){  
			 Pattern pt = Pattern.compile("\\s+[0-9]{4}.*"); 
			 Matcher match = pt.matcher(path);
			 while (match.find()) {
			      System.out.println(match.group());
			 }
			return path;
		}
		
		
		public static FileInfo getFileInfo(String path){
			FileInfo fileInfo = new FileInfo();
			
			String year2 = path.substring(path.length()-3, path.length()-1);
			String year4 = "20"+year2;
			String siteNumber = path.substring(0, 4).toUpperCase();
			String yearDay = path.substring(4, 7);
			//入关系库对象
			fileInfo.setFileYear(year4);
			fileInfo.setSiteNumber(siteNumber);
			fileInfo.setFileDayYear(yearDay);
			fileInfo.setFileName(siteNumber.toLowerCase()+yearDay+"0."+year2+"d.Z");
			//fileInfo.setFilePath(Z_DataSupplyController.S30S_ZipPath+"/"+year4+"/"+yearDay);
			//fileInfo.setFileSize(new File(z_file_ResultPath).length()/1024.00);
			try {
				//fileInfo.setSystemStr(DayNumberOfOneYear.yearDayToDate(Integer.valueOf(yearDay), year4));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			fileInfo.setFileFlag(5);
			fileInfo.setType("1");
			return fileInfo;
		}
}