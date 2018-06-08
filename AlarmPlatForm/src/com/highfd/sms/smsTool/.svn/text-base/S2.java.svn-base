package com.highfd.sms.smsTool;
import java.io.IOException;
import java.util.Date;

import org.smslib.GatewayException;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.TimeoutException;
import org.smslib.Message.MessageEncodings;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

import com.highfd.common.FileCommon;
import com.highfd.sms.controller.SmsSendController;
import com.highfd.sys.sysTool.GetXMLPath;

public class S2 {
	
	     public static Service srv;
	 
	     public static boolean restartService(){
	    	 S2 s2 = new S2();
    		 boolean startService = s2.findCom();
    		 /*while(!startService){
    			 FileCommon.writeTxtFile("false");
    			 //System.out.println("重连");
    			 //AlarmEmailAnnotation.smsLog.error("重连");
    			 startService = s2.findCom();
    		 }
    		 FileCommon.writeTxtFile("true");*/
    		 
    		 if(startService){
    			 FileCommon.writeTxtFile("true");
    		 }else{
    			 FileCommon.writeTxtFile("false");
    		 }
    		 
    		 try {
				Thread.sleep(1000*20);
			 } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
    		 return startService;
	     }
	     
	     
		//启动服务
		 public boolean findCom() {
			 SerialModemGateway gateway = null;
			 try {
				  String comNum = GetXMLPath.getProperties(GetXMLPath.getXMLPath(getClass()),"SMSPoint");
				  srv = new Service();
				  gateway = new SerialModemGateway("modem.com3", "COM"+comNum, 9600, "wavecom", ""); //设置端口与波特率
				  gateway.setInbound(true);
				  gateway.setOutbound(true);
				  gateway.setSimPin("");
				  OutboundNotification outboundNotification = new OutboundNotification();
				  gateway.setOutboundNotification(outboundNotification);
				  srv.addGateway(gateway);
				  //AlarmEmailAnnotation.smsLog.error("程序初始化，准备开启服务       端口号： Com"+comNum);
				  //System.out.println("程序初始化，准备开启服务       端口号： Com"+comNum);
				  srv.startService();
				  System.out.println("开启短信猫服务成功！！！");
				  SmsSendController.smsLog.error("开启短信猫服务成功！！！");
				  return true;
				   //srv.stopService();
			  } catch (Exception e) {
				  try {
					 srv=null;
					 System.out.println("服务启动出现问题！！");
					 SmsSendController.smsLog.error("服务启动出现问题！！");
					 e.printStackTrace();
				 } catch (Exception e1) {
					 srv=null;
					 System.out.println("服务启动出现故障！！！");
					 SmsSendController.smsLog.error("服务启动出现故障！！！");
					 //e1.printStackTrace();
				 }
				 return false;
			  }
			  
		 }
		 
		 private static int errorNum = 0;
		 public static boolean sendSMS(String mobilePhones, String content) throws TimeoutException, GatewayException, IOException, InterruptedException{
			 
			 
			 if(!FileCommon.trueFlag()){
				 System.out.println("    短信猫运行异常，暂不发送报警");
				 SmsSendController.smsLog.error("    短信猫运行异常，暂不发送报警");
				 return false;
			 }
			 Thread.sleep(1000*20);
			 OutboundMessage msg = new OutboundMessage(mobilePhones, content);
			 msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
			 boolean sendFlag = srv.sendMessage(msg);
			 if(!sendFlag){
				 System.out.println("  发送失败！"+mobilePhones+"   "+content);
				 SmsSendController.smsLog.error("  发送失败！"+mobilePhones+"   "+content);
				 errorNum++;
				 if(errorNum>=3){
					 FileCommon.writeTxtFile("false");
				 }
				 //S2.restartService();
			 }else{
				 System.out.println("  发送成功！"+mobilePhones+"   "+content);
				 errorNum=0;
				 FileCommon.writeTxtFile("true");
			 }
			 return sendFlag;
			 //srv.stopService();
			 
		 }
		 @SuppressWarnings({ "deprecation", "static-access" })
		public static void main(String[] args) throws TimeoutException, GatewayException, IOException, InterruptedException{
			S2 s2 = new S2();
			s2.restartService();
			sendSMS("15811019428","___"+new Date().toGMTString()+"");
			for(int i=0;i<1000000;i++){
				Thread.sleep(30*1000);
				sendSMS("10086","___"+new Date().toGMTString()+"");
				int minutes = new Date().getMinutes();
				if(minutes==0){
					Thread.sleep(30*1000);
					sendSMS("15811019428","___"+new Date().toGMTString()+"");
				}
			}
			sendSMS("15811019428","___"+new Date().toGMTString()+"");
			sendSMS("15811019428","___"+new Date().toGMTString()+"");
			sendSMS("15811019428","___"+new Date().toGMTString()+"");
			sendSMS("1581101942811","___"+new Date().toGMTString()+"");
			
			sendSMS("15811019428","___"+new Date().toGMTString()+"");
			sendSMS("1581101942811","___"+new Date().toGMTString()+"");
			sendSMS("1581101942811","___"+new Date().toGMTString()+"");
			sendSMS("1581101942811","___"+new Date().toGMTString()+"");
			sendSMS("1581101942811","___"+new Date().toGMTString()+"");
			//for(int i=0;i<88;i++){
				//sendSMS("1581101942811",i+"___"+new Date().toGMTString()+"");
			//}
			
			/* boolean sendFlag;
			 try {
				 sendFlag = sendSMS("15811019428",new Date().toGMTString()+"");
				 if(!sendFlag){
					 restartService();
				 }
				 Thread.sleep(1000*20);
				 System.out.println("准备！！");
				 
				 sendFlag = sendSMS("15811019428",new Date().toGMTString()+"");
				 if(!sendFlag){
					 restartService();
				 }
				 Thread.sleep(1000*20);
				 System.out.println("准备222");
				 
				 
				 sendFlag = sendSMS("15811019428",new Date().toGMTString()+"");
				 if(!sendFlag){
					 restartService();
				 }
				 Thread.sleep(1000*20);
				 System.out.println("准备33");
				 
				 sendFlag = sendSMS("15811019428",new Date().toGMTString()+"");
				 if(!sendFlag){
					 restartService();
				 }
				 Thread.sleep(1000*20);
				 System.out.println("准备444");
				 
				 sendFlag = sendSMS("15811019428",new Date().toGMTString()+"");
				 if(!sendFlag){
					 restartService();
				 }
				 Thread.sleep(1000*20);
				 System.out.println("准备555");
				 
			 } catch (Exception e) {
				 e.printStackTrace();
			 }*/
		 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public class OutboundNotification implements IOutboundMessageNotification {
		  public void process(String gatewayId, OutboundMessage msg) {
		     System.out.println("Outbound handler called from Gateway: "+ gatewayId);
		     //System.out.println(msg);
		  }
	 }
	 
	/* //重连
	 public static boolean nextStart(){
		 boolean startService = startService();
		 while(!startService){
			 System.out.println("重连");
			 startService = startService();
		 }
		 return startService;
	 }
	 
	 //寻找 合适的端口号
	 public static boolean startService(){
		 for(int i=3;i<8;i++){
			 S2 s2 = new S2();
			 boolean serverFlag = s2.findCom(i);
			 System.out.println(serverFlag+"   "+i);
			 if(serverFlag){
				 System.out.println("短信猫启动成功！");
				 return serverFlag;
			 }
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 return false;
	 }
	 
	 //启动服务
	 public boolean findCom(int comNum) {
		 SerialModemGateway gateway = null;
		 try {
			  srv = new Service();
			  gateway = new SerialModemGateway("modem.com3", "COM"+comNum, 9600, "wavecom", ""); //设置端口与波特率
			  gateway.setInbound(true);
			  gateway.setOutbound(true);
			  gateway.setSimPin("0000");
			  OutboundNotification outboundNotification = new OutboundNotification();
			  gateway.setOutboundNotification(outboundNotification);
			  srv.addGateway(gateway);
			  System.out.println("程序初始化，准备开启服务       端口号： Com"+comNum);
			  srv.startService();
			   
			   //srv.stopService();
		  } catch (Exception e) {
			  try {
				 srv.stopService();
			 } catch (Exception e1) {
				 System.out.println("sever 故障");
				 e1.printStackTrace();
				 return false;
			 }
			 return false;
		  }
		  return true;
	 }*/
	 
	 
	 
	
	 
} 