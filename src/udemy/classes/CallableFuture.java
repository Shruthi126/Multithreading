package udemy.classes;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFuture {
	
	public static void main(String args[]) throws InterruptedException, ExecutionException{
		ExecutorService service= Executors.newFixedThreadPool(10);
		//This future returns Integer
		Future<Integer> future=null;
		for(int i=0;i<100;i++){
		 future=service.submit(new Callable<Integer>() {
			public Integer call() throws InterruptedException{
				int value=new Random().nextInt(100);
				
				System.out.println(value);
				return value;
				
			}
		});
		 System.out.println(future.get().intValue());}
		
		service.shutdown();
		System.out.println("Service 1 done");
		
		ExecutorService service2= Executors.newFixedThreadPool(10);
		// future can return nothing like runnable 
		Future<Void> future2=null;
		for(int i=0;i<100;i++){
		 future2=service2.submit(new Callable<Void>() {
			public Void call() throws InterruptedException{
				int value=new Random().nextInt(100);
				if(value%2==0)
					throw new InterruptedException("To showcase error handling");
				System.out.println(value);
				return null;
				
				
			}
		});
		}
		
		service2.shutdown();

}
}
