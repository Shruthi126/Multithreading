package udemy.classes;

public class Synchronize {
	
	private static int count;
	
	public static void main(String args[]){
		doWork();
			}
	
	public static synchronized void increment(){
		count++;
	}
	
	public static void doWork(){
		
		Thread t1= new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<10000;i++){
						increment();
						//count++;This step is not atomic you cannot add synchronized key word to runnable run method as it is interface
				}
			}
		});
		
		Thread t2= new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<10000;i++){
					increment();
					// count++;This step is not atomic
				}
				
			}
			
		});
		
		t1.start();
		t2.start();
		
		System.out.println("Count is "+ count);
		
		/*"Count is 0" 
		 * This could be the output because we thread scheduler might have not executed these two threads at all
		 * by the time we output the count.
		*/
		
		try {
			t1.join();//join(0) wait forever till thread is completed
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Count is "+ count);
		
	}
}
