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

public class OraTest {
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
		String sql = "select co.id, item.collect_desc,item.class1,item.class2,item.class3,item.class4,item.class5,item.class6," +
				"item.class7,item.class8,item.class9,item.class10,co.collect_date,co.collect_data, co.col1,co.col3,co.col4 " +
				"from BD_COLLECT co,BD_COLLECT_ITEM item where co.collect_item_id=item.id and item.site_id=102  " +
				"and item.id >10900 and item.id<=11028   order by item.id ,co.collect_date ";
		
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
			String code1 = rs.getString("co.id").trim();String code2 = rs.getString("item.collect_desc").trim();String code3 = rs.getString("item.class1").trim();
			String code4 = rs.getString("item.class2").trim();String code5 = rs.getString("item.class3").trim();String code6 = rs.getString("item.class4").trim();
			String code7 = rs.getString("item.class5").trim();String code8 = rs.getString("item.class6").trim();String code9 = rs.getString("item.class7").trim();
			String code10 = rs.getString("item.class8").trim();String code11 = rs.getString("item.class9").trim();String code12 = rs.getString("item.class10").trim();
			String code13 = rs.getString("co.collect_date").trim();String code14 = rs.getString("co.collect_data").trim();String code15 = rs.getString("co.col1").trim();
			String code16 = rs.getString("co.col3").trim();String code17 = rs.getString("co.col4").trim();
			String names = 
				"\""+code1+"\","+"\""+code2+"\","+"\""+code3+"\","+"\""+code4+"\","+"\""+code5+"\","
			+"\""+code6+"\","+"\""+code7+"\","+"\""+code8+"\","+"\""+code9+"\","+"\""+code10+"\","+"\""+code11+"\","
			+"\""+code12+"\","+"\""+code13+"\","+"\""+code14+"\","+"\""+code15+"\","+"\""+code16+"\","+"\""+code17+"\"";
			fw.write(names+"\n");
			System.out.println();
		}
		stmt.close();
		conn.close();
	}
}
