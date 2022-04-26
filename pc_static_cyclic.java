package project1_1_2;

public class pc_static_cyclic {
	private static int NUM_END = 200000;
	private static int NUM_THREADS = 32; // the number of threads
	
	public static void main(String[] args) throws InterruptedException {
		if(args.length==2) {
			NUM_THREADS = Integer.parseInt(args[0]);
			NUM_END = Integer.parseInt(args[1]);
		}
		
		static_cyclic_thread[] thread = new static_cyclic_thread[NUM_THREADS];
		
		for (int t = 0; t < NUM_THREADS; t++) {
			thread[t] = new static_cyclic_thread(t); 
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
		System.out.println("1..." + (NUM_END-1) + " prime# counter=" + static_cyclic_thread.counter);
	}
	
	private static boolean isPrime(int x) {
		int i;
		if (x <= 1) return false;
		for (i = 2; i < x; i++) {
			if(x % i == 0) return false;
		}
		return true;
	}

	static class static_cyclic_thread extends Thread {
		int startNumber;
		long startTime, endTime, timeDiff;
		int subCounter = 0;
		static int counter;
		int EndNumber;
		
		public static_cyclic_thread(int startNumber) {
			this.startNumber = startNumber;
		}
	
		public void run() {
			
			if (startNumber < NUM_END % NUM_THREADS ) {
				EndNumber = NUM_END / NUM_THREADS + 1;
			}
			else {
				EndNumber = NUM_END / NUM_THREADS;
			}
			
			startTime = System.currentTimeMillis();

			for (int i = 0; i < EndNumber; i++) {
				if(isPrime(NUM_THREADS * i + startNumber)) {
					subCounter++;
				}
			}
			endTime = System.currentTimeMillis();
			counter += subCounter;
			timeDiff = endTime - startTime;
		}
	}

}
