package project2_2;

import java.util.concurrent.Semaphore;

class Car extends Thread {
	  private Semaphore sem;
	  private String name;
	  public Car(String name, Semaphore sem) {
		  this.name = name;
		  this.sem = sem;
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
	    	  sem.acquire();
	    	  justEntered();
	      } catch (InterruptedException e1) {
	    	  e1.printStackTrace();
	      }
	     
	      try {
	    	  sleep((int)(Math.random() * 20000)); // stay within the parking garage
	      } catch (InterruptedException e) {}
      
	      aboutToLeave();
		  sem.release();
		  Left();
	    }
	  }
}

public class ParkingSemaphore {
	public static void main(String[] args){
		Semaphore sem = new Semaphore(7);
	    for (int i=1; i<= 10; i++) {
	    	Car c = new Car("Car "+ i, sem);
	    }
	  }
}
