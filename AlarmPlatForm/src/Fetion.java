import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/** 
 * API2.1调用:f.php?phone=xxxxxx&pwd=xxx&to=xxxx&msg=xxxx&type=0 
 *以上接口参数详细说明 VIP API 
 *1.phone:手机号 
 *2.pwd:飞信密码 
 *3.to:发送给谁(手机号或飞信号) 
 *4.msg:飞信内容 
 *5.type:操作 0(空)发送短信 1检查好友 2添加好友 
 *6.u:备用参数:当发送内容为乱码时 在最后加上&u=1 
 */ 
public class Fetion {
	//自己的手机号
	private static String PHONE = "";  
	//自己的飞信密码
    private static String PWD = "";  
    //对方的手机号
    private static String TO = "";  
    //
    private static String configuration;
    
    public Fetion(){
    	init();
    }
    //初始化配置文件
    public void init(){
    	PHONE="15811019428";
		PWD="liuying";
		TO="15811019428";
    }
	 public static void sendMsg(String _phone,String _pwd,String _to,String _msg) throws HttpException, IOException{  
		         HttpClient client = new HttpClient();  
		         PostMethod post = new PostMethod("http://3.ibtf.sinaapp.com/f.php");  
		         post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码   
		         NameValuePair[] data ={   
		                 new NameValuePair("phone", _phone),  
		                 new NameValuePair("pwd", _pwd),  
		                 new NameValuePair("to",_to),  
		                 new NameValuePair("msg",_msg),  
		                 new NameValuePair("type","0")  
		                 };  
		         post.setRequestBody(data);  
		       
		         client.executeMethod(post);  
		         Header[] headers = post.getResponseHeaders();  
		         int statusCode = post.getStatusCode();  
		         System.out.println("statusCode:"+statusCode);  
		         for(Header h : headers){  
		             System.out.println(h.toString());  
		         }  
		         //String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));   
		         //System.out.println(result);   
		         System.out.println("ok!");  
		         post.releaseConnection();  
		     }  
	 public  void start(){
		 try {
		
				Fetion.sendMsg(PHONE, PWD, TO, "1111");  
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	 public static void main(String[] args) {  
		 Fetion f = new Fetion();
		 f.start();
	    }  

}
