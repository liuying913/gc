import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class T {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			int a = 30;int b=10;
			int c =a/b;
			if(a%b==0){
				
			}else{
				c+=1;
			}
			System.out.println(c);
		
		try{ 
			
			
			String names = 
				"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","
			+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","
			+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\","+"\""+111+"\"";
			System.out.println(names);
			System.out.println(names);
			System.out.println(1111+"\n"+"dfdf");
			String processedStr = "xxx";
			String fullFilename = "D:/tttt.txt";
			 
			try {
			    File newTextFile = new File(fullFilename);
			    FileWriter fw;
			    fw = new FileWriter(newTextFile);
			    fw.write(processedStr);
			    fw.close();
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			String url="http://list.jd.com//list.html?cat=1713,11745,11759&page=2&go=0&JL=6_0_0";
			WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8); 
			//设置webClient的相关参数 
			webClient.setJavaScriptEnabled(true); 
			webClient.setCssEnabled(true); 
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
			//webClient.getOptions().setTimeout(50000); 
			webClient.setThrowExceptionOnScriptError(false); 
			//模拟浏览器打开一个目标网址 
			HtmlPage rootPage= webClient.getPage(url); 
			System.out.println("为了获取js执行的数据 线程开始沉睡等待"); 
			Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的
			System.out.println("线程结束沉睡"); 
			String html = rootPage.asText(); 
			System.out.println(html); 
			}catch(Exception e){ 
			}
	}
}
