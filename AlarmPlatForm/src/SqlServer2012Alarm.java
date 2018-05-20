

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlServer2012Alarm {
    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://172.128.8.82:1433;DatabaseName=TPPDB";
    
    //exec sp_addlinkedserver   'testDB1', ' ', 'SQLOLEDB', '172.128.8.82' 
    //exec sp_addlinkedsrvlogin  'testDB1', 'false ',null, 'test', 'Trimble123456'
    //exec sp_dropserver  'testDB ', 'droplogins '   //删除跨域连接
    
    public static void main(String[] args) {
    	getAlarmStation();
	}
    public static List<String> getAlarmStation() {
    	List<String> alarmStationList = new ArrayList<String>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        try {
            Class.forName(cfn);
            con = DriverManager.getConnection(url,"test","Trimble123456");
            String sql = "SELECT  s.m_StationName,h.logT FROM testDB1.TPPDB.dbo.StationList s left join  " +
                "(SELECT Configuration,max(LogTime) as logT FROM testDB1.TPPDB.dbo.MeteorologicSensorHistory  where " +
                "datediff(ss,LogTime,getdate()) <= (60*60*8+150) group by Configuration ) h  " +
             "on s.m_StationName=h.Configuration";
            System.out.println(sql);
            statement = con.prepareStatement(sql);
            res = statement.executeQuery();
            while(res.next()){
                String siteNumber = res.getString("m_StationName");//获取test_name列的元素         
                String logTime = res.getString("logT");//获取test_name列的元素      ;
                if(null!=logTime && !"".equals(logTime) && !"null".equalsIgnoreCase(logTime) && logTime.length()>=19){
                	logTime = logTime.substring(0, 19);
                	long distanceTime = getDistanceTime(logTime);
                	if(distanceTime>=60*60*24){
                		//System.out.println(logTime);
                	}
                }else{
                	logTime="";
                	System.out.println("有问题的台站:"+siteNumber);
                	alarmStationList.add(siteNumber);
                }
                //System.out.println("更新时间："+logTime);
            }
            return alarmStationList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(res != null) res.close();
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return alarmStationList;
    }
    
    public static long getDistanceTime(String str1) {  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        try {  
            Date one = sdf.parse(str1);  
            Date two = new Date();  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1; 
                return diff/1000;
            } else {  
                return -1;
            }  
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return -1;
    }  
    
    
    /*public static int callmonitor(){
        String monitorServer = "tcp://localhost:8181/ServerInfo.rem".Replace("localhost", getIp);
        try
        {
            WellKnownClientTypeEntry remoteType = new WellKnownClientTypeEntry(typeof(ServerInfo), monitorServer);
            if (!ConnectRemoteStatus)
            {
                RemotingConfiguration.RegisterWellKnownClientType(remoteType);
                ConnectRemoteStatus = true;
            }
            ServerInfo si = new ServerInfo();
            mre = new ManualResetEvent(false);
            AsyncCallback RemoteCallback = new AsyncCallback(EITuxedo.RemoteSetAsyncCallBack);
            IAsyncResult RemAr = new RemoteSetAsyncDelegate(si.Set).BeginInvoke(currServiceName, 0, 1, "test", RemoteCallback, null);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
        }
        return 0;
    }*/
}