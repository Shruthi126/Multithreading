package udemy.classes;

import java.util.LinkedList;
import java.util.List;

public class WaitNotify2 {
	

public static void main(String args[]){
		Processor4 p=new Processor4();
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

class Processor4{
	private List<Integer> list= new LinkedList<>();
	int LIMIT=10;
	
	public void producer() throws InterruptedException  {
		int value=0;
		while(true){
			synchronized (this) {
				if(list.size()==LIMIT){
					System.out.println("List was full. Waiting to be consumed. ");
					this.wait();
				}
				list.add(value);
				this.notify();
				System.out.println("Added Item  is :"+ value++);	
				
				
				
			}
		}
		
	}
	
	public  void consumer() throws InterruptedException{
		synchronized (this) {
			while(true){
				synchronized (this) {
					if(list.size()==0){
						System.out.println("List is empty. Waiting");
						this.wait();
					}
					Integer i=((LinkedList<Integer>) list).removeFirst();
					System.out.println("Removed item is :"+ i);
					this.notify();
					
				}
			}
		}
		
	}
}