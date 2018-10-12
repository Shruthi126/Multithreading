package udemy.classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

	public static void main(String args[]){
		ExecutorService service= Executors.newCachedThreadPool();
		for(int i=0;i<200;i++)
		service.submit(new Runnable(){
			public void run(){
				try {
					Connection.getConnection().connect();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		service.shutdown();
	}
}

class Connection{
	
	private static Connection c= new Connection();
	private Semaphore semaphore= new Semaphore(10);// I want only 10 threads to connect to it simultaneously
	private int count=0;
	
	private Connection(){}
	
	public static Connection getConnection(){
		return c;
	}
	
	public void connect() throws InterruptedException{
		/*All the threads here can increment "connections". So that needs to be synchronized. 
		 * After we add the semaphore, a maximum of ten threads can "connect" at the same time. 
		 * So synchronized is still needed because ten threads can potentially try to increment 
		 * "connections" at the same time.
		*/
		semaphore.acquire();
		synchronized (this) {
			count++;
			System.out.println("Connections got incremented:" + count);
		}
		//db connections
		Thread.sleep(1000);//Work is done on db 
		//then release
		synchronized (this) {
			count--;
			System.out.println("Connections got decremented:" + count);
		}
		semaphore.release();
	}
	
}
