

import java.util.Date;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.highfd.siteUser.model.AupsState;
import com.highfd.siteUser.model.DupsState;
import com.highfd.siteUser.model.SiteInfo;
public class EsperTest3 {

    /**
     * 测试
     */
    public static void main(String[] args) {
    	System.out.println(new Date());
    	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
        String product = SiteInfo.class.getName();  
        String dupsProduct = DupsState.class.getName();
        String aupsProduct = AupsState.class.getName();
        EPAdministrator admin = epService.getEPAdministrator();  
        //String epl = "select '_E_Route_error' as alarmId,A.siteNumber as siteNumber,   '路由器报警' as name from " + product + "(siteState<=0).win:length(1) as  A ";
        String epl = "select '_E_ACUPS_WORK_ERROR' as alarmId, A.siteNumber as siteNumber, '交流UPS报警' as name from " + product + ".win:length(1) as  A ," + aupsProduct + ".win:length(1) as B where A.siteNumber=B.siteNumber and A.routeState<=12 and B.aupsState=12 ";
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new AppleStartListener());
        
        String epl2 = "select '_E_Route_error' as alarmId,A.siteNumber as siteNumber,   '路由器报警' as name from " + product + ".win:length(1) as  A ," + aupsProduct + ".win:length(1) as B where A.routeState<=12 and B.aupsState=2 ";
        EPStatement state2 = admin.createEPL(epl2);  
        state2.addListener(new AppleEndListener());  
        
        EPRuntime runtime = epService.getEPRuntime();
        SiteInfo apple1 = new SiteInfo();  
        AupsState aups = new AupsState();
        for(int i=1;i<100;i++){
        	apple1.setRouteState(i);
        	for(int j=1;j<3;j++){
        		aups.setAupsState(j);
        		apple1.setSiteNumber("AAAA"+i+" "+j);
        		aups.setSiteNumber("AAAA"+i+" "+j);
            	runtime.sendEvent(apple1);
            	runtime.sendEvent(aups); 
        	} 
        }
  
        
	}
}



class AppleStartListener implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
        	System.out.println(newEvents[0].get("siteNumber")+"   结束报警");
        }
    }  
}
class AppleEndListener implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
        	System.out.println(newEvents[0].get("siteNumber")+"   开始报警");
        }  
    }  
}
