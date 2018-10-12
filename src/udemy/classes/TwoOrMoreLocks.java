package udemy.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoOrMoreLocks {
	
	private  Random random = new Random();
	private  List<Integer> list1= new ArrayList<Integer>();
	private  List<Integer> list2= new ArrayList<Integer>();
	private  Object lock1 = new Object();
	private  Object lock2 = new Object();
	
	public  void addToList1(){
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			list1.add(random.nextInt(100));
			}
		
	}
	/*If we add synchronize in method declaration, it will be obj level lock and list1 cannot access any addToList1 even though there is no dependency
	 * time taken approx 4 secs
	 * With synchronized block it is 2 secs 
	*/
	public  void addToList2(){
		synchronized (lock2) {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			list2.add(random.nextInt(100));
			}
	
}
	
	public  void process(){
		for(int i=0; i<1000;i++){
			addToList1();
			addToList2();
			/*this coud have been broken into two methods like process1 process2 adds to list 1 and 2 respectively in t1 and t2,
			but to show case individual locks we are keeping it.*/
		}
	}
	
	public  void main(){
		
		System.out.println("Starting...");
		long start = System.currentTimeMillis();
		
		Thread t1= new Thread(new Runnable(){
			@Override
			public void run() {
				process();
			}
			
		});
		Thread t2= new Thread(new Runnable(){
			@Override
			public void run() {
				process();
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
		
		long end = System.currentTimeMillis();
		System.out.println("List1 size : "+list1.size()+"; List2 size: "+list2.size());
		System.out.println("Time taken "+ (end-start));
	}
	
	public static void main(String args[]){
		TwoOrMoreLocks t= new TwoOrMoreLocks();
		t.main();
	}
}
