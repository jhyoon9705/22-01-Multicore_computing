package project1_1_3;

public class pc_dynamic {
	private static int NUM_END = 200000;
	private static int NUM_THREADS = 32; // the number of threads
	
	public static void main(String[] args) throws InterruptedException {
		
		dynamic_thread[] thread = new dynamic_thread[NUM_THREADS];
		
		for (int t = 0; t < NUM_THREADS; t++) {
			thread[t] = new dynamic_thread(t); 
		}
		
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < NUM_THREADS; i++) {
			thread[i].start();
		}
		for (int i = 0; i < NUM_THREADS; i++) {
			thread[i].join();
		}
		
		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - startTime;
		
		for (int i = 0; i < NUM_THREADS; i++) {
			System.out.println("Thread " + (i+1) + " Execution Time: " + thread[i].timeDiff + "ms" );
		}
		
		System.out.println("Program Execution Time: " + timeDiff + "ms");
		System.out.println("1..." + (NUM_END-1) + " prime# counter=" + dynamic_thread.counter);

	}
	
	private static boolean isPrime(int x) {
		int i;
		if (x <= 1) return false;
		for (i = 2; i < x; i++) {
			if(x % i == 0) return false;
		}
		return true;
	}
	
	static class dynamic_thread extends Thread {
		int startNumber;
		long startTime, endTime, timeDiff;
		int subCounter = 0;
		static int counter;
		static int checker;
		
		public dynamic_thread(int startNumber) {
			this.startNumber = startNumber;
		}
			
		public void run() {
			startTime = System.currentTimeMillis();
			
			int objectNumber = getNumber();			
			while(objectNumber < NUM_END) {
				if(isPrime(objectNumber)) {
					subCounter++;
				}
				objectNumber = getNumber();
			}
			
			endTime = System.currentTimeMillis();
			counter += subCounter;
			timeDiff = endTime - startTime;
			
		}
		
		static synchronized int getNumber() {
			int number = checker;
			checker++;
			return number;
		}
	}
}
