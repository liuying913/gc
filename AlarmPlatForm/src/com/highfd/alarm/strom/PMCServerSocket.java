package com.highfd.alarm.strom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

public class PMCServerSocket extends ServerSocket implements  ServletResponseAware{
	 protected HttpServletResponse response;
	 private static  PMCServerSocket ss = null;
	 public static   PMCServerSocket getSS( int portnum){
    	 if(ss == null){
    		 try{
    			 new PMCServerSocket(portnum);
			 }catch(IOException e){			
			     e.printStackTrace();
			 }
    	 }
		 return ss;
	}
	 
	 public static  String str2 = "";
	 //public static  Map<String,String> map = (Map<String,String>)new HashMap();
	 public static StreamBean sbStream = new StreamBean();
	
	 
	 public PMCServerSocket(int serverPort) throws IOException {
		 super(serverPort);  
		 try{
		    while(true){
			    Socket socket = accept();
			    new GnssServerThread(socket);  
		    }
		 } catch (IOException e) {
		     e.printStackTrace();
		 } 
		 finally {
		    close();  //关闭监听端口
		 }
     }
 
     class GnssServerThread extends Thread {
         private Socket socket;
         private BufferedReader in;
         private PrintWriter out;
         public GnssServerThread(Socket s) throws IOException {
             this.socket = s;
             in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             out = new PrintWriter(socket.getOutputStream(), true);
             start();
         }
 
         public void run(){
        	 System.out.println("**************流 开始");
             try {
            	 while (true){
	            	 if(in.read()==-1){
	            		 break;
	            	 }
	                 String line = in.readLine();
	                 System.out.println(line);
	                 //String [] arry = line.split("\\s+");
	                 //map.put(arry[1], line);  
	                 StreamTool.mainStream(line);
              }
 
            }catch(IOException e){
                 e.printStackTrace();
             }finally{
            	    try {
	                	if(out != null)
	                    out.flush();
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	                try {
	                	if(in != null)
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	                try {
	                	if(socket != null)
						socket.close();
					} catch (IOException e){
						e.printStackTrace();
					}
            	 
             }
         }
 
    }
 
     
	public void setServletResponse(HttpServletResponse arg0) {
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
 }
