package udemy.classes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Countdownlatch {
	

	public static void main(String args[]){
		CountDownLatch cdl= new CountDownLatch(8);
		ExecutorService exService=Executors.newFixedThreadPool(3);

		for(int i=0;i<10;i++){
			exService.submit(new Processor2(cdl));
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
		}
		exService.shutdown();
		System.out.println("Exeution done");
	}
}

class Processor2 implements Runnable{
	private int id;
	private CountDownLatch latch;
	
	public Processor2 (CountDownLatch latch){
		this.latch=latch;
	}
	public Processor2 (){}

	public void run(){
		System.out.println("Starting proccess " );
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		System.out.println("Ending proccess ");
		latch.countDown();
	}
}