import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class AllUp {
	public static void main(String[] args) throws Exception {
		int iniNum=0;
		int jgN=2;
		for(int i=0;i<440;i++){
			
			ttt(iniNum+i,iniNum+i+jgN);
			iniNum+=jgN-1;
		}
		
	}
	
	public static void ttt(int small,int big) throws Exception {
		

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.1.200:1521:jjmx";
		String user = "jjmx";
		String password = "jjmx";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
        try {
            String sql = "select t.*, t.rowid from ALIBABA t";
            rs = stmt.executeQuery(sql);
            stmt = rs.getStatement();
            
            String upsql = "update BD_COLLECT  set indexcity=1  where collect_item_id in(select i.id from BD_COLLECT_ITEM i where i.site_id=102) and col2 in(select to_char(b.code) from ITEMREGION b where b.region like '%����%' or  b.region like '%����%'     or  b.region like '%����%'      or  b.region like '%����%'  or  b.region like '%Т��%'  or  b.region like '%����%'or  b.region like '%�Ƹ�%'     or  b.region like '%����%'      or  b.region like '%����%'  or  b.region like '%��ʩ%'  or  b.region like '%����%' or  b.region like '%��Ѩ%'     or  b.region like '%����%'      or  b.region like '%����%'  or  b.region like '%Ǳ��%'  or  b.region like '%�˳�%'       or  b.region like '%��ũ��%'   or  b.region like '%��������%') ";
            for(int i=small;i<big;i++){
            	stmt.addBatch(upsql+" and id>="+i+"000000and id<="+(i+1)+"000000");
            	System.out.println(upsql+" and id>="+i+"000000 and id<="+(i+1)+"000000");
            }
            stmt.executeBatch();
            conn.commit();
            Thread.sleep(1000);
        } catch (Exception ex) {
        }finally {
        	stmt.close();
        	conn.close();
        	System.out.println(0);
        }
        
	
		
	}


}
