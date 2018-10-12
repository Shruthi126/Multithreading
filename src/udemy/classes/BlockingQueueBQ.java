package udemy.classes;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueBQ {
	
	private static BlockingQueue<Integer> bq= new ArrayBlockingQueue<>(6);
	
	private static void producer(){
		Random random = new Random();
		try {
			while(true){
			Integer value= random.nextInt(100);
			Thread.sleep(10);
					bq.put(value);
			System.out.println("Produced value is"+value);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void consumer(){
		try {
			while(true){
			Thread.sleep(1000);
			Integer value=bq.take();
			System.out.println("Taken Value is"+ value);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		
		Thread t1= new Thread(new Runnable() {
			
			@Override
			public void run() {
				producer();
				
			}
		});
		Thread t2= new Thread(new Runnable() {
			
			@Override
			public void run() {
				consumer();
				
			}
		});
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
