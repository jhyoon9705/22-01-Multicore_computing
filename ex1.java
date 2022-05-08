package project2_3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Ex1_Thread extends Thread {
	  private BlockingQueue<String> queue;
	  private String name;
	  public Ex1_Thread(String name, BlockingQueue<String> queue) {
		  this.name = name;
		  this.queue = queue;
		  start();
	  }

	  public void run() {
	      try {
	    	  System.out.println(name+": I want to enter");
	    	  queue.put(this.name);
	    	  System.out.println(name+": Hello, I entered!");
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	      }
	    
	      try {
	    	  sleep((int)(Math.random() * 5000));
	      } catch (InterruptedException e) {}
	      
	      try {
	    	  System.out.println(name+": I want to leave");
	    	  queue.take();
	    	  System.out.println(name+": Bye!");
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	      }
	
	  }
}

public class ex1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockingQueue queue = new ArrayBlockingQueue<String>(2);
	    for (int i=1; i<= 5; i++) {
	    	Ex1_Thread t = new Ex1_Thread("Thread "+ i, queue);
	    }
	}
}
