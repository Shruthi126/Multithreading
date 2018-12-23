package udemy.classes;

import java.util.Calendar;

public class WaitNotify {
	
	
public static void main(String args[]){
		Processor3 p=new Processor3();
		Thread t1= new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					p.producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		Thread t2= new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					p.consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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

class Processor3{
	public void producer() throws InterruptedException  {
		synchronized (this) {
			System.out.println("Started Producer : " +Calendar.getInstance().getTime());
			System.out.println("Calling wait: "+Calendar.getInstance().getTime());
			wait();
			System.out.println("Resuming after wait :"+Calendar.getInstance().getTime());
			
		}
		
	}
	//Put debug points everywhere in both producer and consumer class. You will understand the flow
	public  void consumer() throws InterruptedException{
		synchronized (this) {
			Thread.sleep(1000);//This is to make sure produce is kick started before
			System.out.println("Done the required job :"+Calendar.getInstance().getTime());
			notify();
			System.out.println("Lock is not yet given up. Just notified. Let me complete the method "+Calendar.getInstance().getTime());
		}
		
	}
}