package udemy.classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorThread {

	public static void main(String args[]){

		ExecutorService exService=Executors.newFixedThreadPool(3);

		for(int i=0;i<10;i++){
			exService.submit(new Processor(i));
		}
		exService.shutdown();
	}
}

class Processor implements Runnable{
	private int id;

	public Processor (int id){
		this.id=id;
	}
	public Processor (){}

	public void run(){
		System.out.println("Starting proccess "+ id);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		System.out.println("Ending proccess "+id);
	}
}