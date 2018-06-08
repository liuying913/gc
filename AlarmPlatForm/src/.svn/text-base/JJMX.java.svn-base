import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JJMX {
	 public static void outPutStrim(String processedStr ){  
			
	    }  
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		System.out.println();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.1.200:1521:jjmx";
		String user = "jjmx";
		String password = "jjmx";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		String sql = "select item.id, b.collect_data as 价格,b.col3 as 评价数 ,b.col4 as 销量,item.collect_desc as 描述,b.collect_date as 日期,re.region as 产地,item.industry_id as 类别 " +
				"from BD_COLLECT b,BD_COLLECT_ITEM item, ITEMREGION re " +
				"where b.col2= re.code and b.collect_item_id=item.id and re.region like '%湖北%' and item.site_id=102 " +
				"and item.industry_id =1 " +
				"order by item.id,b.collect_date";
		
		ResultSet rs = stmt.executeQuery(sql);
		String fullFilename = "D:/tttt.txt";
		FileWriter fw = new FileWriter(fullFilename);
		try {
		    File newTextFile = new File(fullFilename);
		    fw = new FileWriter(newTextFile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		while(rs.next()){
			String code1 = rs.getString("item.id").trim();String code2 = rs.getString("b.collect_data").trim();String code3 = rs.getString("b.col3").trim();
			String code4 = rs.getString("b.col4").trim();String code5 = rs.getString("item.collect_desc").trim();String code6 = rs.getString("b.collect_date").trim();
			String code7 = rs.getString("re.region").trim();String code8 = rs.getString("item.industry_id").trim();

			String names = 
				"\""+code1+"\","+"\""+code2+"\","+"\""+code3+"\","+"\""+code4+"\","+"\""+code5+"\","
			+"\""+code6+"\","+"\""+code7+"\","+"\""+code8+"\"";
			fw.write(names+"\n");
			System.out.println();
		}
		stmt.close();
		conn.close();
	}
}
