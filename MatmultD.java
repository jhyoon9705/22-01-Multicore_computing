package project1_2;

import java.util.*;

// import java.lang.*;
public class MatmultD
{
  private static Scanner sc = new Scanner(System.in);
  public static void main(String [] args) throws InterruptedException
  {
    int thread_no=0;
    if (args.length==1) thread_no = Integer.valueOf(args[0]);
    else thread_no = 1;
    int startRow, endRow;
    
    int a[][]=readMatrix();
    int b[][]=readMatrix();
        
    int rowNum = a.length;
    int colNum = b[0].length;
    int[][] resultMatrix = new int[rowNum][colNum];
    
    mat_mult_thread[] thread = new mat_mult_thread[thread_no];
    
    for (int t = 0; t < thread_no; t++) {
		startRow = t * (a.length / thread_no);
		if (t == thread_no - 1) {
			endRow = a.length - 1;
		}
		else {
			endRow = (t + 1) * (a.length / thread_no) - 1;
		}
		thread[t] = new mat_mult_thread(startRow, endRow, a, b, resultMatrix, rowNum, colNum); 
	}
    
    long startTime = System.currentTimeMillis();
    
    for (int i = 0; i < thread_no; i++) {
    	thread[i].start();
    }
    for (int i = 0; i < thread_no; i++) {
    	thread[i].join();
    }
    long endTime = System.currentTimeMillis();

    for (int i = 0; i < thread_no; i++) {
		System.out.println("Thread " + (i+1) + " Execution Time: " + thread[i].timeDiff + " ms" );
	}
    
    printSum(resultMatrix);
    
    System.out.printf("Program Execution Time: %d ms\n" , endTime-startTime);

  }
  
  static class mat_mult_thread extends Thread {
	  int startRow, endRow, rowNum, colNum;
	  long startTime, endTime, timeDiff;
	  static int[][] resultMatrix;
	  int[][] a, b;
	  
	  
	  public mat_mult_thread(int startRow, int endRow, int[][] a, int[][] b, int[][] resultMatrix, int rowNum, int colNum) {
		  this.startRow = startRow;
		  this.endRow = endRow;
		  this.a = a;
		  this.b = b;
		  mat_mult_thread.resultMatrix = resultMatrix;
		  this.rowNum = rowNum;
		  this.colNum = colNum;
	  }
	  
	  public void run() {
		  startTime = System.currentTimeMillis();
		  for(int i = startRow; i <= endRow ; i++){
		      for(int j = 0; j < colNum; j++){
		        for(int k = 0; k < rowNum; k++){
		          resultMatrix[i][j] += a[i][k] * b[k][j];
		        }
		      }
		    }
		  endTime = System.currentTimeMillis();
		  timeDiff = endTime - startTime;
	  }
	  
  }

   public static int[][] readMatrix() {
       int rows = sc.nextInt();
       int cols = sc.nextInt();
       int[][] result = new int[rows][cols];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {
              result[i][j] = sc.nextInt();
           }
       }
       return result;
   }

  public static void printSum(int[][] mat) {
    int rows = mat.length;
    int columns = mat[0].length;
    int sum = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        sum+=mat[i][j];
      }
    }
    System.out.println("Matrix Sum = " + sum + "\n");
  }

  
 }