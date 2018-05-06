package com.highfd.alarm;

import java.util.Date;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.highfd.common.SiteInfo;

public class EsperTest2 {

    /**
     * 测试
     */
    public static void main(String[] args) {
    	System.out.println(new Date());
    	EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
        String product = SiteInfo.class.getName();  
        EPAdministrator admin = epService.getEPAdministrator();  
        
        //select '"+alarmId+"' as alarmId,   '"+alarmName+"' as name, '"+ varStringId +"' as varIds, A.time as time,
        //String epl = "select avg(siteState) from " + product + "(siteState>0).win:length(2) ";
        
        String epl = "select '_E_Route_error' as alarmId, a.siteNumber as siteNumber ,  '路由器报警' as name from pattern[every a=" + product + "(siteState=1)]   ";
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new AppleStartListener());
        
        //String epl2 = "select '_E_Route_error' as alarmId,A.siteNumber as siteNumber,   '路由器报警' as name from " + product + "(siteState=2).win:length(1) as  A ";
        String epl2 = "select '_E_Route_error' as alarmId, a.siteNumber as siteNumber ,  '路由器报警' as name from pattern[every a=" + product + "(siteState=2)]   ";
        EPStatement state2 = admin.createEPL(epl2);  
        state2.addListener(new AppleEndListener());  
        
        EPRuntime runtime = epService.getEPRuntime();
        SiteInfo apple1 = new SiteInfo();  
        apple1.setSiteState(1);
        apple1.setSiteNumber("AAAA");
        runtime.sendEvent(apple1);  
  
        SiteInfo apple2 = new SiteInfo();  
        apple2.setSiteState(1);  
        apple2.setSiteNumber("BBBB");
        runtime.sendEvent(apple2);  
  
        SiteInfo apple3 = new SiteInfo();  
        apple3.setSiteState(2); 
        apple3.setSiteNumber("AAAA");
        runtime.sendEvent(apple3);  
        
        SiteInfo apple4 = new SiteInfo();  
        apple4.setSiteState(1);  
        apple4.setSiteNumber("AAAA");
        runtime.sendEvent(apple4);  
        
        SiteInfo apple5 = new SiteInfo();  
        apple5.setSiteState(2);  
        apple5.setSiteNumber("AAAA");
        runtime.sendEvent(apple5);  
        
	}
}



class AppleStartListener implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
        	System.out.println(newEvents[0].get("siteNumber")+"   开始报警");
        }
    }  
}
class AppleEndListener implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
        	System.out.println(newEvents[0].get("siteNumber")+"  结束报警");
        }  
    }  
}
