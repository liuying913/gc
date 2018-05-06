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

public class TianMaoTest {
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
		String sql = "select t.itemid from ABD_COLLECT_HB t where t.type=6 group by t.itemid order by t.itemid";
		
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
			String t = "select n.collect_date from ABD_COLLECT_HB n where n.itemid="+rs.getInt(1)+" group by n.collect_date intersect";
			fw.write(t+"\n");
			System.out.println(t);
		}
		stmt.close();
		conn.close();
	}
}
