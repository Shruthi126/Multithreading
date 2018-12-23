package udemy.classes;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReEntrantlockDemo{
	public static void main(String args[]){
	Runner6 r= new Runner6();
	Thread t1= new Thread(new Runnable() {
		
		@Override
		public void run() {
				try {
					r.firstThread();
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
				r.secondThread();
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
		r.finished();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
class  Runner6 {
	
	private Lock lock= new ReentrantLock();
	private Condition condition = lock.newCondition();

	private int count=0;
	
	public void increment(){
		for(int i=0; i<10000;i++)
			count++;
	}
	
	public void firstThread() throws InterruptedException{
		lock.lock();
		System.out.println("Waiting await() is called");
		condition.await();
		
		System.out.println("Woken up");
		try{
			increment();
			
		}
		finally{
			//unlocking should always happen in finally coz if exceptions are there then it won tbe unlocked at all
			lock.unlock();
		}
	
		
	}
	
	public void secondThread() throws InterruptedException{
		Thread.sleep(1000);
		lock.lock();
		try{
			increment();
			System.out.println("Press enter key to notify");
			new Scanner(System.in).nextLine();
			condition.signal();
		}
		finally{
			//unlocking should always happen in finally coz if exceptions are there then it wont be unlocked at all
			lock.unlock();
		}
	}
	
	public void finished(){
		System.out.println("The count value is :" + count);
	}
}



