package com.highfd.alarm;

import com.espertech.esper.client.EPAdministrator;  
import com.espertech.esper.client.EPRuntime;  
import com.espertech.esper.client.EPServiceProvider;  
import com.espertech.esper.client.EPServiceProviderManager;  
import com.espertech.esper.client.EPStatement;  
import com.espertech.esper.client.EventBean;  
import com.espertech.esper.client.UpdateListener;  
  
/** 
 *  
 * @author liuying
 * 
 */  
class Apple  
{  
    private int id;  
    private int price;  
  
    public int getId()  
    {  
        return id;  
    }  
  
    public void setId(int id)  
    {  
        this.id = id;  
    }  
  
    public int getPrice()  
    {  
        return price;  
    }  
  
    public void setPrice(int price)  
    {  
        this.price = price;  
    }  
}  
  
class AppleListener implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
            //Double avg = (Double) newEvents[0].get("avg(price)");  
            //System.out.println("Apple's average price is " + avg); 
        	//System.out.println(newEvents[0].get("price"));
        	//System.out.println(newEvents[1].get("price"));
        	System.out.println(newEvents[0].get("avg(price)")+"   "+111);
        }  
    }  
  
}  
class AppleListener2 implements UpdateListener  {  
    public void update(EventBean[] newEvents, EventBean[] oldEvents)  {  
        if (newEvents != null){  
            //Double avg = (Double) newEvents[0].get("avg(price)");  
            //System.out.println("Apple's average price is " + avg); 
        	//System.out.println(newEvents[0].get("price"));
        	//System.out.println(newEvents[1].get("price"));
        	System.out.println(newEvents[0].get("avg(price)")+"  "+222);
        }  
    }  
  
}  
public class EsperTest {  
  
    public static void main(String[] args) throws InterruptedException {  
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
  
        
  
        String product = Apple.class.getName();  
        EPAdministrator admin = epService.getEPAdministrator();  
        
        String epl = "select avg(price) from " + product + "(price>0).win:length(2) ";
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new AppleListener());
        
        String epl2 = "select avg(price) from " + product + "(price>0).win:length(1) ";
        EPStatement state2 = admin.createEPL(epl2);  
        state2.addListener(new AppleListener2());  
  
        EPRuntime runtime = epService.getEPRuntime();  
        
       /* for(int i=1;i<=6;i++){
        	Apple apple = new Apple(); 
        	apple.setId(i);  
            apple.setPrice(i);  
            runtime.sendEvent(apple);  
        }*/
        Apple apple1 = new Apple();  
        apple1.setId(1);  
        apple1.setPrice(1);  
        runtime.sendEvent(apple1);  
  
        Apple apple2 = new Apple();  
        apple2.setId(2);  
        apple2.setPrice(2);  
        runtime.sendEvent(apple2);  
  
        Apple apple3 = new Apple();  
        apple3.setId(3);  
        apple3.setPrice(3);  
        runtime.sendEvent(apple3);  
        
        Apple apple4 = new Apple();  
        apple4.setId(4);  
        apple4.setPrice(4);  
        runtime.sendEvent(apple4);  
        
        Apple apple5 = new Apple();  
        apple5.setId(5);  
        apple5.setPrice(5);  
        runtime.sendEvent(apple5);  
        
 
    }  
}  