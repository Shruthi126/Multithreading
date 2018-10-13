package udemy.classes;

import java.util.Random;

public class InterruptingThreads {

	public static void main(String[] args) throws InterruptedException{
		Thread t1= new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					if(!Thread.currentThread().isInterrupted())
						System.out.println(new Random().nextInt(10));
					else{
							try {
								throw new InterruptedException();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								break;
							}	
					}
				}
			}
		});
		
		t1.start();
		Thread.sleep(1000);
		t1.interrupt();
	}
}
