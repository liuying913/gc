import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AllTitleUp {
	public static void main(String[] args) throws Exception {
		update(args[0]);
	}
	
	public static void update(String itemId) throws Exception {
		

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.1.200:1521:jjmx";
		String user = "jjmx";
		String password = "jjmx";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
        try {
            String sql = "select to_char(b.id),to_char(item.id), to_char(b.collect_data) as 价格,to_char(b.col3) as 评价数 ,to_char(b.col4) as 销量,to_char(item.collect_desc) as 描述,b.collect_date as 日期,to_char(re.region) as 产地, " +
            		"to_char(b.col1),to_char(item.industry_id) as 类别 " +
            		"from BD_COLLECT b,BD_COLLECT_ITEM item, ITEMREGION re where " +
            		"b.col2= re.code and b.collect_item_id=item.id and item.industry_id="+itemId+" and item.site_id=102 " +
            		"and (     b.indexcity=1  or b.col1 like '%湖北%'       or  b.col1 like '%武汉%' or  b.col1 like '%黄石%' or  b.col1 like '%十堰%' or  b.col1 like '%宜昌%' or  b.col1 like '%襄阳%'    or  b.col1 like '%鄂州%'     or  b.col1 like '%荆门%' or  b.col1 like '%孝感%' or  b.col1 like '%荆州%' or  b.col1 like '%黄冈%'    or  b.col1 like '%咸宁%'     or  b.col1 like '%随州%' or  b.col1 like '%恩施%'   or  b.col1 like '%武穴%'    or  b.col1 like '%天门%'     or  b.col1 like '%仙桃%' or  b.col1 like '%潜江%' or  b.col1 like '%宜城%'  or  b.col1 like '%神农架%'  or  b.col1 like '%东西湖区%')";
            rs = stmt.executeQuery(sql);
            stmt = rs.getStatement();
            int i=0;
            while(rs.next()){
            	i++;
            	/*if(i==790){
            		System.out.println("111");
            	}*/
            	System.out.print(i+"   "  );
            	String sdate=rs.getString(7);  if(sdate.length()>10){sdate=sdate.substring(0, 10);}
            	String p = rs.getString(1)+",'"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"',to_date('"+sdate+"','yyyy-mm-dd'),'"+rs.getString(8)+"','"+rs.getString(9)+"','"+rs.getString(10)+"'";
            	//if(i>790){
            		insertInto(p);
            	//}
            	
            }
            
            stmt.executeBatch();
            conn.commit();
           
        } catch (Exception ex) {
        	ex.printStackTrace();
        }finally {
        	stmt.close();
        	conn.close();
        	//System.out.println("111OK!");
        }
	}

	
	public static void insertInto(String p) throws ClassNotFoundException, SQLException{
		

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.1.200:1521:jjmx";
		String user = "jjmx";
		String password = "jjmx";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
        try {
        	String upsql = "insert into ABD_COLLECT_HB b (id,itemid,collect_data,col3,col4,collect_desc,collect_date,region,col1,type) " +"values("+p+")";
        	System.out.println(" sql:"+upsql);
        	stmt.addBatch(upsql);
    		stmt.executeBatch();
            conn.commit();
           
        } catch (Exception ex) {
        	ex.printStackTrace();
        }finally {
        	stmt.close();
        	conn.close();
        }
	}

}
