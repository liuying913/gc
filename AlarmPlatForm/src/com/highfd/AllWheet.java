package com.highfd;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.highfd.sys.controller.OnlyOneController;

public class AllWheet {
	
	 public static void main(String[] args) throws Exception {
	        //定义线程数
	        int subThreadNum = 5;
	        //取得一个倒计时器，从5开始
	        CountDownLatch countDownLatch = new CountDownLatch(subThreadNum);
	        //依次创建5个线程，并启动
	        for (int i = 0; i < subThreadNum; i++) {
	            new SubThread(6000*(i+1), countDownLatch).start();
	        }
	        //主线程工作
	        mainWork();
	        //等待所有的子线程结束
	        boolean await = countDownLatch.await(3, TimeUnit.SECONDS);
	        if(!await){
	        	System.out.println("AUPS超时了！！");OnlyOneController.logger.error("AUPS超时了！！");
	        }else{
	        	 System.out.println("AUPS全部结束");OnlyOneController.logger.error("AUPS全部结束");
	        }
	        System.out.println("全部结束");
	    }
	    private static void mainWork(){
	        System.out.println("主线程开始工作");
	        try {
	            Thread.sleep(2000L);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        System.out.println("主线程结束！！！");
	    }
	    /**
	     * 子线程类
	     * @author fuhg
	     */
	    private static class SubThread extends Thread{
	         
	        private CountDownLatch countDownLatch;
	        private long workTime;
	         
	        public SubThread(long workTime,CountDownLatch countDownLatch){
	            this.workTime = workTime;
	            this.countDownLatch = countDownLatch;
	        }
	         
	        public void run() {
	            // TODO Auto-generated method stub
	            try {
	                System.out.println("子线程 开始工作");
	                Thread.sleep(workTime);
	                System.out.println("子线程结束工作！！！");
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } finally {
	                //线程结束时，将计时器减一
	                countDownLatch.countDown();
	            }
	        }
	    }
	}

