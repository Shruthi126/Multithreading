package udemy.classes.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TheDeadLock {
	 static ReentrantLock acc1Lock= new ReentrantLock();
	static  ReentrantLock acc2Lock= new ReentrantLock();
	
	public static void main(String args[]){
		BankAccount acc1= new BankAccount();
		BankAccount acc2= new BankAccount();
		
		Thread t1= new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					firstTransfer(acc1, acc2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		Thread t2= new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					secondTransfer(acc1, acc2);
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
		finish(acc1, acc2);
	}
	
	private static void acquireLocks(Lock lock1, Lock lock2) throws InterruptedException{
		//we want it to aquire lock and then return hence while true
		while(true){
		boolean getlock1=false,getlock2=false;
		try{
			getlock1=lock1.tryLock();
			getlock2=lock2.tryLock();
		}
		finally{
			if(getlock1&&getlock2)
				return;
			else if(getlock1)	
				lock1.unlock();
			else if(getlock2)
				lock2.unlock();
		}
		Thread.sleep(10);
		}
	}
	public static void secondTransfer(BankAccount acc1, BankAccount acc2) throws InterruptedException{
		for(int i=0;i<1000;i++){
			/*acc1Lock.lock();
			acc2Lock.lock();*/
		//This thread has acc1Lock and it is waiting for acc1Lock and other thread has acc2Lock and is waiting for acc1Lock. DEADLOCK!!!
			acquireLocks(acc1Lock,acc2Lock);
			BankAccount.transfer(acc1, acc2, new Random().nextInt(60));
			if(acc2Lock.isHeldByCurrentThread())
			acc2Lock.unlock();
			if(acc1Lock.isHeldByCurrentThread())
			acc1Lock.unlock();
		}
	}
	public static void firstTransfer(BankAccount acc1, BankAccount acc2) throws InterruptedException{
		for(int i=0;i<1000;i++){
			/*acc2Lock.lock();
			acc1Lock.lock();*/
			acquireLocks(acc2Lock,acc1Lock);			
			BankAccount.transfer(acc1, acc2, new Random().nextInt(40));
			if(acc2Lock.isHeldByCurrentThread())
				acc2Lock.unlock();
				if(acc1Lock.isHeldByCurrentThread())
				acc1Lock.unlock();
		}
	}
	
	public static void finish(BankAccount acc1, BankAccount acc2){
	System.out.println("Total balance is :"+(acc1.getBalance()+acc2.getBalance()));
	}
}


