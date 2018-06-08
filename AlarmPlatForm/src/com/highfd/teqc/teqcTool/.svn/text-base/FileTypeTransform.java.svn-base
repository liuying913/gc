package com.highfd.teqc.teqcTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.highfd.sys.sysTool.CopyFileUtil;
import com.highfd.sys.sysTool.FileTool;
import com.highfd.teqc.model.FileInfo;
import com.highfd.teqc.teqcTool.linux.Z_TEQC_LINUX;

public class FileTypeTransform {

	//程序超时时间秒数
	private static int timeoutSeconds = 20;//add 原来60秒
	
	public FileTypeTransform(){
		
	}
	
	
	//根据o文件路径，返回文件对象
	public static FileInfo getFileInfoBy_o_Path(File oFile){
		FileInfo fileInfo = FileTool.getFileInfo(oFile.getName());
		String s_file_Ftp_WorkPath = oFile.getPath().substring(0, oFile.getPath().length()-1)+"S";
		String result = o_To_S(oFile.getPath());
		if(result==null){//o转s文件失败！  启动程序  根据正则表达式计算历元数量
			return fileInfo;
		}else{//转o文件成功
			fileInfo = s_setFileInfo(fileInfo, s_file_Ftp_WorkPath, false);
		}
		return fileInfo;
	}
	
	public static String o_To_S(String o_file_WorkPath){
		String command_O_S = "chmod 777 "+o_file_WorkPath+";teqc +qc "+o_file_WorkPath+";exit";
		System.out.println("  o-s: "+command_O_S);
		String result = to2ToTgd(command_O_S);
		String s_file_WorkPath = o_file_WorkPath.substring(0, o_file_WorkPath.length()-1)+"S";
		try {
			System.out.println("o转s命令： "+command_O_S);
			if(CopyFileUtil.fileExist(s_file_WorkPath)){
				System.out.println( "o转s成功！！！     返回值: "+result);
				if(null==result){
					System.out.println("o转s成功！！！     重点研究下！！！！！！");
				}
				return "1";
			}else{
				System.out.println("o转s失败： "+o_file_WorkPath);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String to2ToTgd(String command1){
		//超时执行的话 结果还是null
		String result = null;
		result = callMethod(new Z_TEQC_LINUX(), "executeFileForLinux" , new Class<?>[]{String.class}, new Object[]{ command1 } );
		if(result==null){
			System.out.println("*****executeFileForLinux 方法执行超时*****【"+command1+"】");
			return null;
		}
		return result;
	}
	
	/**
	 * 获得o文件里面的数值
	 */
	public static FileInfo s_setFileInfo(FileInfo fileInfo,String s_file_Ftp_WorkPath,boolean flag){
		FileInfo sFile2 = jiexiSfile(s_file_Ftp_WorkPath, "", "");//解析o文件，并将结果入库
		if(null==sFile2){
			fileInfo.setEphem_number("-1");
			fileInfo.setMp1("-1");
			fileInfo.setMp2("-1");
			fileInfo.setO_slps("-1");
			fileInfo.setFileFlag(6);
		}else{
			fileInfo.setEphem_number(sFile2.getEphem_number());
			fileInfo.setMp1(sFile2.getMp1());
			fileInfo.setMp2(sFile2.getMp2());
			fileInfo.setO_slps(sFile2.getO_slps());
			if(flag){//从临时NAS盘获得的o文件
				fileInfo.setFileFlag(Integer.valueOf(sFile2.getEphem_number())>=600?1:3);
			}else{//从T02文件转换过来的o文件
				fileInfo.setFileFlag(Integer.valueOf(sFile2.getEphem_number())>=600?2:3);
			}
		}
		return fileInfo;
	}
	
	
	/**
	 * 解析TEQC最终生成的文件，进行入库操作
	 * 
	 * @param SfilePath
	 * @param siteNumber
	 * @param beforreDayStr 
	 */
	@SuppressWarnings("resource")
	public static FileInfo jiexiSfile(String SfilePath, String siteNumber,String beforreDayStr) {
		
		FileInfo info = new FileInfo();
		info.setEphem_number("0");
		File f = new File(SfilePath);
		if (f.exists()) {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String lineTXT = null;
				while ((lineTXT = br.readLine()) != null) {
					lineTXT = lineTXT.toString().trim();
					// 发现你这个标石后立即跳出循环，不再解析文件
					if (lineTXT.contains("Processing parameters are:")) {
						break;
					}
					// 找到目标数据的那一行
					if (lineTXT.contains("SUM")) {
						String ss[] = lineTXT.split(" ");
						Map<Integer, String> map = new HashMap<Integer, String>();
						int allLength = 0;
						for (int d = 0; d < ss.length; d++) {
							if(!"".equals(ss[d].trim())) {
								map.put(allLength, ss[d]);
								allLength += 1;
							}
						}
						info.setMp1(map.get(allLength - 3));
						info.setMp2(map.get(allLength - 2));
						info.setO_slps(map.get(allLength - 1));
						System.out.println("历元分析：  "+SfilePath+"分析结果："+" "+ map.get(allLength-4)+" "+ map.get(allLength - 3)+" "+ map.get(allLength - 2)+" "+ map.get(allLength - 1));
					}
					
					if (lineTXT.contains("Epochs w/ observations")) {
						if(lineTXT.contains(":")){
							String ss[] = lineTXT.split(":");
							if(ss.length==2){
								info.setEphem_number(ss[1].trim());
								System.out.println("历元数量："+info.getEphem_number());
							}else{
								System.out.println("历元数量解析有问题A："+lineTXT);
							}
						}else{
							System.out.println("历元数量解析有问题B："+lineTXT);
						}
					}
				}
				return info;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("jiexiSfile() siteNumber:[" + siteNumber + "],time:[" + beforreDayStr + "] YYS result file not exist!");
		}
		return null;
	}
	
	
	/***
	 * 方法参数说明
	 * @param target 调用方法的当前对象
	 * @param methodName 方法名称
	 * @param parameterTypes 调用方法的参数类型
	 * @param params 参数  可以传递多个参数
	 * 
	 * */
	public static String callMethod(final Object target , final String methodName ,final Class<?>[] parameterTypes,final Object[]params){
		ExecutorService executorService = Executors.newSingleThreadExecutor();  
        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
            	String value = null  ; 
            	try {
					Method method = null ; 
					method = target.getClass().getDeclaredMethod(methodName , parameterTypes ) ;  
					
					Object returnValue = method.invoke(target, params) ;  
					value = returnValue != null ? returnValue.toString() : null ;
				} catch (Exception e) {
					e.printStackTrace() ;
					throw e ; 
				}
                return value ;
            }  
        });  
          
        executorService.execute(future);  
        String result = null;  
        try{
        	/**获取方法返回值 并设定方法执行的时间为60秒*/
            result = future.get(timeoutSeconds , TimeUnit.SECONDS );  
            
        }catch (InterruptedException e) {  
            future.cancel(true);  
            System.out.println("方法执行中断"); 
        }catch (ExecutionException e) {  
            future.cancel(true);  
            System.out.println("Excuti on异常");  
        }catch (TimeoutException e) {  
            future.cancel(true);  
            System.out.println("TimeoutException异常");
        }
        executorService.shutdownNow(); 
        return result ;
	}
	
	

	
	//file_info表里的fileFlag，1，完整，2，补回，3，文件不完整，4，ftp连上没有文件，5，ftp连不上，6，文件转换过程出错
	public static FileInfo setFileInfo(FileInfo fileInfo,int fileFlag){
		fileInfo.setEphem_number("0");
		fileInfo.setMp1("-1");
		fileInfo.setMp2("-1");
		fileInfo.setO_slps("-1");
		fileInfo.setFileFlag(fileFlag);
		return fileInfo;
	}
	
	public static boolean isNumeric(String str){
	   for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		      return false;
		   }
	   }
	   return true;
	}
	
	@SuppressWarnings("unused")
	private String fileCompress(String unzipPath,String zipPath){
		String dPath = unzipPath;
		if(unzipPath.toLowerCase().endsWith(".z") || unzipPath.toLowerCase().endsWith(".gz") || unzipPath.toLowerCase().endsWith(".zip")){
			return dPath;
		}
		if(unzipPath.toLowerCase().endsWith("o") && unzipPath.toLowerCase().contains(".")){
			dPath = dPath.substring(0,dPath.toLowerCase().lastIndexOf("o"))+"d";
			String cmd = "chmod 777 "+unzipPath +";rnx2crx < \"" + unzipPath + "\" > \"" +dPath+"\";exit";
			System.out.println("执行压缩操作: "+cmd);
			int dReturnCode =  execute(cmd);
			if(dReturnCode == 0){
				File f = new File(dPath);
				f.delete();
				System.out.println("创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
				return null;
			}
		}
		
		boolean createFileDictory = CopyFileUtil.createFileDictory(zipPath);
		if(!createFileDictory){
			System.out.println("创建d.Z文件时  目录结构失败！"+zipPath);
		}
		String cmd2 = "chmod 777 "+dPath +";compress -c -f   \"" + dPath + "\" > \"" +zipPath+"\";exit";
		System.out.println("执行生成d.Z操作: "+cmd2);
		int zipReturnCode =  execute(cmd2);
		if(zipReturnCode == 0){
			File f = new File(zipPath);
			f.delete();
			System.out.println("创建d.Z文件时  解压文件出错 "+zipPath+"  failed!");
			return null;
		} 
		return zipPath;
	}
	
	private int execute(String command){
		
		File wd = new File("/bin");
		Process proc = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
			proc.getErrorStream().close();
			if (proc != null) {
				in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
				out.println(command);
				proc.waitFor();
				Thread.sleep(100);
				return 1;
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
			if(proc!=null){
				proc.destroy();
			}
		}
		return 0;
	}
	
}
