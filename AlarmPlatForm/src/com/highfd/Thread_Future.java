package com.highfd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Thread_Future {

    public static void main(String args[]) throws InterruptedException, ExecutionException {
    	// 1.先实例化任务对象
        // 2.实例化Executor框架中的线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 3.使用submit方法将任务提交（返回的是一个Future)
        
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();
        for(int i=0;i<10;i++){
        	Future<Integer> result = executor.submit(new ServiceTask(i));
        	list.add(result);
        }
        
        // 4.记得关闭线程池
        //executor.shutdown();
        
        System.out.println("正在执行任务");
		

		for(int i=0;i<list.size();i++){
			Future<Integer> future = list.get(i);
			/*if(future.isDone()){
				continue;
			}else{
				//System.out.println("  pand");
				//future.cancel(true);
			}*/
			Integer taskResult = null;  
	        String failReason = "原因明细";  
	        try {  
	            // 等待计算结果，最长等待timeout秒，timeout秒后中止任务  
	            taskResult = future.get(1, TimeUnit.SECONDS); 
	            System.out.println(taskResult+"  没有报错");
	        } catch (InterruptedException e) {  
	            failReason = "主线程在等待计算结果时被中断！";  
	        } catch (ExecutionException e) {  
	            failReason = "主线程等待计算结果，但计算抛出异常！";  
	        } catch (TimeoutException e) {  
	            failReason = "主线程等待计算结果超时，因此中断任务线程！";  
	        }  finally{
	        	
	        }
	  
	        System.out.println("taskResult : " + taskResult);  
	        System.out.println("failReason : " + failReason);  
		}
        System.out.println("等待结束后");
        /*System.out.println(result.isDone());
        result.cancel(true);
        System.out.println(result.isDone());*/
        // 5.打印最后的结果
       /* try {
			System.out.println("主方法最终运行结果为:" + result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        System.out.println("***************************");
        System.out.println(13);
    }
}

/**
 * Callable的实现类
 */
class ServiceTask implements Callable<Integer>{
	private int time;
	public ServiceTask(int time){
		this.time=time;
	}
    @Override
    public Integer call() throws Exception {
        Thread.sleep(time*1000);
        int result = 0;
        // 假设一个很庞大的计算
        for(int i=1;i<100;i++){
            for (int j=0;j<i;j++){
                result +=j;
            }
        }
        return time;
    }
}