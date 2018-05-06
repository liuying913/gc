package com.highfd.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileCommon {
	
	
    /**
     * 写文本文件
     */
    public static void writeTxtFile(String content) {
    	
    	//判断写入的与 新添加的是否相同
    	String oldTxt = readTxtFile();
    	if(null!=oldTxt && oldTxt.length()>0){
    		if(oldTxt.indexOf(content)>-1){
    			System.out.println("与已存的标记位相同，不写磁盘！！！  标记位："+content);
    			return;
    		}else{
    			System.out.println("写入标记位："+content);
    		}
    	}
    	
    	String path = "E:\\NoTouchSMS\\smsController.property";
    	FileWriter fw = null;
    	try {
	    	//如果文件存在，则追加内容；如果文件不存在，则创建文件
	    	File f=new File(path);
	    	fw = new FileWriter(f, false);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	PrintWriter pw = new PrintWriter(fw);
    	pw.println(content);
    	pw.flush();
    	try {
	    	fw.flush();
	    	pw.close();
	    	fw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * 读取文本文件
     */
    @SuppressWarnings("resource")
	public static String readTxtFile(){
    	String path = "E:\\NoTouchSMS\\smsController.property";
        String read;
        String readStr = "";
        FileReader fileread;
        try {
            fileread = new FileReader(path);
            BufferedReader bufread = new BufferedReader(fileread);
            try {
                while ((read = bufread.readLine()) != null) {
                    readStr = readStr + read+ "\r\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readStr;
    }
    
    public static boolean trueFlag(){
    	String readTxtFile = readTxtFile();
    	if(null!=readTxtFile && !"".equals(readTxtFile) && readTxtFile.indexOf("true")>-1){
    		return true;
    	}
    	return false;
    }
    public static void main(String[] args) {
    	writeTxtFile("false");
    	writeTxtFile("false");
    	writeTxtFile("false");
    	System.out.println(trueFlag());
	}

}
