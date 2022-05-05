package project2_1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Car extends Thread {
	  private BlockingQueue<String> queue;
	  private String name;
	  public Car(String name, BlockingQueue<String> queue) {
		  this.name = name;
		  this.queue = queue;
		  start();
	  }
	  private void tryingEnter() {
	      System.out.println(name+": trying to enter");
	  }

	  private void justEntered() {
	      System.out.println(name+": just entered");
	  }
	  private void aboutToLeave() {
	      System.out.println(name+":                                     about to leave");
	  }
	  private void Left() {
	      System.out.println(name+":                                     have been left");
	  }

	  public void run() {
	    while (true) {
	      try {
	    	  sleep((int)(Math.random() * 10000)); // drive before parking
	      } catch (InterruptedException e) {}
	      
	      try {
	    	  tryingEnter();
	    	  queue.put(this.name);
	    	  justEntered();
	      } catch (InterruptedException e1) {
	    	  e1.printStackTrace();
	      }
	     
	      try {
	    	  sleep((int)(Math.random() * 20000)); // stay within the parking garage
	      } catch (InterruptedException e) {}
	      
	      try {
	    	  aboutToLeave();
	    	  queue.take();
	    	  Left();
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	      }
	    }
	  }
}

public class ParkingBlockingQueue {

	public static void main(String[] args){
		BlockingQueue queue = new ArrayBlockingQueue<String>(7);
	    for (int i=1; i<= 10; i++) {
	    	Car c = new Car("Car "+ i, queue);
	    }
	  }
}
