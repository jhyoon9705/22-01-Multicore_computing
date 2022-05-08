package project2_3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Ex2_Thread extends Thread {
	  Lock rLock;
	  Lock wLock;
	  private String name;
	  public Ex2_Thread(String name, Lock readLock, Lock writeLock) {
		  this.name = name;
		  this.rLock = readLock;
		  this.wLock = writeLock;
		  start();
	  }
	  
	  public void run() {
	      try {
	    	  rLock.lock();
	    	  System.out.println(name+" is reading...");
	    	  sleep((int)(Math.random() * 5000));
	    	  rLock.unlock();
	    	  
	    	  wLock.lock();
	    	  System.out.println(name+" is writing...");
	    	  sleep((int)(Math.random() * 5000));
	    	  System.out.println(name+" has finished writing!");
	    	  wLock.unlock();
	      } catch (InterruptedException e) {
	    	  e.printStackTrace();
	      }
	  }
}

public class ex2 {
	public static void main(String[] args) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		Lock readLock= readWriteLock.readLock();
		Lock writeLock = readWriteLock.writeLock();
				
		for(int i=1; i<=5; i++) {
			Ex2_Thread t = new Ex2_Thread("Thread "+ i , readLock, writeLock);
		}
 	}
}
