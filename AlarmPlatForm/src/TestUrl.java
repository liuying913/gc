import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TestUrl {
	static List<String> names=new ArrayList<String>();
  static void init(){
	  names.add("重庆");

  } 
  public static void main(String[] args) throws UnsupportedEncodingException {
	  init();

	  /*for(String t:names){
		  String te=URLEncoder.encode(t,"UTF-8");
          System.out.println(t+"\t"+te);
	  }
*/
       System.out.println(URLEncoder.encode("http://search.yhd.com/c0-0-0/b/a-s2-v4-p1-price-d0-f0d-m1-rt0-pid-mid0-k%E5%AD%9D%E6%84%9F%E9%BA%BB%E7%B3%96/","UTF-8"));
   }
	
	  
   
	
}
