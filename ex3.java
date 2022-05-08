package project2_3;

import java.util.concurrent.atomic.AtomicInteger;

public class ex3 {
	 
    private static AtomicInteger myNumber = new AtomicInteger(0);
 
    static class MyRunnable implements Runnable {
 
        private int number;
        private int prevNumber;
        private int parameter;
 
        public MyRunnable(int i) {
			this.parameter = i;
		}

		public void run() {
            number = myNumber.get();
            System.out.println("Thread " + parameter +  " reads " + number);
            prevNumber = myNumber.getAndAdd(10);
            System.out.println("Thread " + parameter + " reads " + prevNumber + " : after getAndAdd(10)"); 
            number = myNumber.addAndGet(100);
            System.out.println("Thread " + parameter + " reads " + number+ " : after addAndGet(100)");
            myNumber.set(1000);     
            System.out.println("Thread " + parameter + " reads " + myNumber+ " : after set(1000)");
          
 
        }
    }
 
    public static void main(String[] args) {
        for (int i=1; i<= 2; i++) {
        	Thread t = new Thread(new MyRunnable(i));
        	t.start();
	    }
    }
}
