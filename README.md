# 22-01-Multicore_computing

### An environment that the experimentation was performed
- Processor: AMD Ryzen 5 6-core & 6-thread
- RAM: 16GB
- OS: Microsoft Windows 10 Education
- The source code was compiled(executed) in Eclipse IDE for Enterprise Java and Web Developers – 2021-06
___

## 1-1. Prime number counter with multi-threaded
### a. Code
- [pc_static_block.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/pc_static_block.java) (static load balancing with block decomposition)
- [pc_static_cyclic.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/pc_static_cyclic.java) (static load balancing with cyclic decomposition)
- [pc_dynamic.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/pc_dynamic.java) (dynamic load balancing)

### b. Execution result
![image](https://user-images.githubusercontent.com/79308015/165342712-f9b8ebec-084a-4d92-a211-095981726cfd.png)
![image](https://user-images.githubusercontent.com/79308015/165345763-bdbf9716-e95c-4fe9-9128-dc5de643ee38.png)


### c. Analysis on the results
  First of all, codes that used in this project was executed in laptop with 6-core & 6-threads processor. Therefore, it can be expected that the best performace will be achieved when using 6 threads.
  
  First, let’s see the case of using **dynamic load balancing**. Since the dynamic load balancing assigns tasks to threads at execution time(not while programming time), the end time of each thread is alomost the same. When looking at the graph, it can be seen that the overall execution time decreases. Characteristically, it decreases rapidly until six threads are used, but there is little change thereafter. This is consistent with what we expected earlier.   
 Next, let’s see the case of using **static load balancing with block decomposition**. In static load balancing with block decomposition, assuming that the task is from 1 to 20000 and 4 threads are used, 1 to 5000 are assigned to 1st thread, and 5001 to 10000 are assigned to the 2nd thread and so on. This also shows a similar pattern to using dynamic load balancing, but the reduction in execution time is slower than dynamic. This is because the number of tasks was divided equally, not based on the execution time. In other words, the thread that finishes the task early becomes idle.
 
 Finally, this is the case using **static load balancing with cyclic decomposition**. In the case of static load balancing with cyclic decomposition, assuming that the conditions are the same as block decomposition, {4k} task is assigned to the first thread amd {4k+1} task is assigned to the second thread and so on. This case also show a decreasing trend, but slightly different. It shows a stepwise decreasing trend. It assumed that this is because it does not perform continuous tasks, but rather computations such as {4k+1} and some overhead such as context switching occurs.
 
 Overall, it is important to know the number of threads that a program can use rather than increasing the number of threads used in program. In addition, the key point is that tasks are similarly distributed to each thread based on the execution time. 
 
 <br />
 
 ## 1-2. Matrix multiplication with multi-threaded
 ### a. Code
 - [MatmultD.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/MatmultD.java)

### b. Execution result
![image](https://user-images.githubusercontent.com/79308015/165345561-70d16274-35d5-4b32-8cb4-b4c6e60c534f.png)
![image](https://user-images.githubusercontent.com/79308015/165345903-645040c9-f825-417b-ab6e-5e6f0524e84a.png)

### c. Analysis on the results
 First of all, codes that used in this project was executed in laptop with 6-core & 6-threads processor. Therefore, it can be expected that the best performace will be achieved when using 6 threads. This is the same as described in problem 1.
 
 Let’s say that two matrices that have to be multiplid are A and B. In order to calculate A*B using a multi-thread, the number of rows of A maix was divided by the number of threads. And then, each thread is only responsible for calculating the corresponding range. For ease of understanding, suppose two matrices, 20 rows and 20 columns, are multiplied by each other and we will use 4 thread to calculate. Then, the multiplied matrix is also 20 rows and 20 columns. At this time, the first thread is in charge of calculating lines 1 to 4 of the result matrix, and the second thread is in charge of calculating lines 5 to 8 and so on. Each thread will be performed in parallel. 
 
 Looking at the execution time graph, it may be seen that the execution time decreases as the number of threads increases. However, the execution time decreases rapidly until six threads are used, but there is no significant change after that. This is because the PC running this program is a 6-core processor.
 

___

## 2-1. Parking garage operation with ArrayBlockingQueue()
### a. Problem
Threads mean cars, and shared resources mean parking lots. It is a program that manages the access of cars that want to use a limited parking lot.

### b. Code
- [ParkingBlockingQueue.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ParkingBlockingQueue.java)

<br />

## 2-2. Parking garage operation with Semaphore()
### a. Problem
Same as 2-1.

### b. Code
- [ParkingSemaphore.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ParkingSemaphore.java)

<br />

## 2-3-1. ArrayBlockingQueue and BlockingQueue
### a. Descriptions
 ArrayBlockingQueue is a BlockingQueue that implemented with an array. The programmer sets the size when setting the queue, so it is fixed and cannot be changed. The key functions of ArrayBlockingQueue are ‘put()’ and ‘take()’. If you want to get items from the queue, you should use ‘take()’. If the queue is empty when getting a item from queue, it will wait until item is added instead of returning ‘null’. On the other side, when want to put an item in a queue, we can use ‘put()’. If the queue is full in that case, it will wait until space is available. That is, wait for some items to be removed from the queue. Also, it can be used without ‘synchronized’ in multiple thread because it guarantees concurrency safety.
 
### b. Example Code
- [ex1.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ex1.java)

<br />

## 2-3-2. ReadWriteLock
### a. Descriptions
 Mutex uses only ‘lock’ and ‘unlock’. However, this can be said to be a slightly inefficient method. For example, if one thread accesses the data protected by the lock to read, the other thread cannot read that data. Since the simple reading does not impair the consistency of the data, synchronization problems don’t occur even if multiple threads read the same data at the same time. On the other hand, ReadWriteLock has ‘readLock’ and ‘writeLock’ additionally. If one thread read some data, that data becomes read-lock state. In this case, the other threads can read that data simultaneously but cannot write. However, if one thread is writing some data, all requests(read or write) are blocked.
 
### b. Example Code
- [ex2.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ex2.java)

<br />

## 2-3-3. AtomicInteger
### a. Descriptions
 ‘AtomicInteger’ means integer that atomicity. It guarantees concurrency safety without using ‘synchronized’ keyword or ‘lock’ when modifying(or reading) the same integer variable in multithread environment. That is, it is safe from threads accssing the variable at the same time. If you want to modify or read integer value, you should use ‘set(int)’ or ‘get()’ respectively. There are many methods that can modify(or read) such as ‘getAndAdd(n)’, ‘addAndGet(n)’, ‘incrementAndGet()’, ‘getAndIncrement()’, and so on. For example, ‘getAndAdd(n)’ returns the current integer value and adds newValue(n) to the current value. 

 
### b. Example Code
- [ex3.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ex3.java)

<br />

## 2-3-3. CyclicBarrier
### a. Descriptions
 ‘CyclicBarrier’ makes it wait at a desired point inside the runnig thread. And if the program call ‘await()’ as much as the value that passes as a parameter to the constructor, the program can release the waiting of all waiting threads.

 
### b. Example Code
- [ex4.java](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ex4.java)


___

## 3-1. Prime number counter using OpenMP
### a. Code
- [ompPrimeCounter.c](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ompPrimeCounter.c)

### b. Execution result
![image](https://user-images.githubusercontent.com/79308015/169676184-2fa90250-17f9-41d1-a224-db0918d2a605.png)

<br />

![image](https://user-images.githubusercontent.com/79308015/169676187-b41b3ca5-2d5a-467e-ae34-c999ffa654f9.png)

### c. Descriptions
 Looking at the graph and the table above, it can be seen that in all 4 cases(static_default, dynamic_default, static_10, dynamic_10), it decreases rapidly until 6 threads are used, but after that, gently or there is no change. The reason is that the CPU of the computer running the program has 6 cores and 6 threads. <br />
 Let’s look at the case where the number of threads is the same in 4 cases. In order of best performance, they are dynamic_10 >= dynamic_default > static_10 > static_default. First of all, dynamic load balancing is more faster than static load balancing. This is because dynamic load balancing has less idel time than static because dynamic receives another task immediately after completing the given task. Second, performance is generally good when chunk size is 10, and the difference by chunk size is large in static. To understand this, we have to know the role of the chunk size. When we use chunk size in openMP pragma, it distributes tasks to threads by chunk size. Also, default chunk size is the number of tasks divided by the number of threads. So when calculate the number of prime number, small chunk size in static load balancing makes faster. This is because prime numbers are not evenly distributed over the entire range(1~200000), and the larger the number, the longer it takes to determine whether it is a prime number or not.

<br />

## 3-2. PI calculation using OpenMP
### a. Code
- [ompPiCalculator.c](https://github.com/jhyoon9705/22-01-Multicore_computing/blob/main/ompPiCalculator.c)

### b. Execution result
![image](https://user-images.githubusercontent.com/79308015/169676345-84a65bc6-595f-45db-a7d4-3e47ae84dba1.png)

<br/>

- **Static load balancing**

![image](https://user-images.githubusercontent.com/79308015/169676357-dc729b5c-5e98-44d4-ac36-1a10265f1bd6.png)
![image](https://user-images.githubusercontent.com/79308015/169676361-dc25a0bc-a334-4a09-a51c-8e63e94883c6.png)

<br />

- **Dynamic load balancing**

![image](https://user-images.githubusercontent.com/79308015/169676398-1ccab515-bb28-415d-bbbb-5a04ddb79db5.png)
![image](https://user-images.githubusercontent.com/79308015/169676400-00fda334-977b-4bf3-a55b-61ae2ba75acd.png)

<br />

- **Guided load balancing**

![image](https://user-images.githubusercontent.com/79308015/169676404-02ce6b69-2d0a-4ad0-abae-f96915f55d55.png)
![image](https://user-images.githubusercontent.com/79308015/169676405-d2ac8b16-306d-477c-a809-6c677543f16b.png)

<br />

### c. Descriptions
 Static load balancing is the way that the number of iterations of the loop is divided by the number of threads, and the number of chunks is sequentially executed. Dynamic load balancing is a method of queueing tasks divided by chunk size so that whenever threads finish a task, it takes the next task. Guided load balancing is similar to dynamic, but it starts with a large chunk size and gradually decreases the chunk size. <br/>
 First of all, when we see the static and guided load balancing graph above, it can be seen that chunk size does not have a large effect. Of course, since the running computer uses 6 cores, the execution time decreases until 6 threads are used, but there is little change after that. In case of static load balancing, even if the chunk size is different, the amount of work perfomed by each thread in the end is almost the same, so there is little performace(execution time) difference. Guided load balancing is also similar to the reason for static, and since the performance of each thread will be similar, there is little performance difference due to chunk size. However, in the case of dynamic, it shows a different aspect. When chunk size is 1, the performace improvement of parallel execution is not obtained. Predicting the reason, since tasks are not assigned to each thread at once, context switching between threads occurs very frequently, resulting in performance loss. When the graph is redrawn except when the chunk size is 1, it is as follows.
 
![image](https://user-images.githubusercontent.com/79308015/169676425-65491045-daa5-41b9-8a85-7506b6b59c34.png)

As the chunk size is increases, the overhead caused by context switching decreases, and it can be seen that the performance is improved by the number of threads. <br />
 Finally, let’s look at the difference in execution time for each scheduling method(static, dynamic, guided) when the chunk size is 100. A graph to verify that is below.
 
![image](https://user-images.githubusercontent.com/79308015/169676435-94a7aff6-4dc1-4f36-8ee2-ffacdb844560.png)

We can see a decreasing trend until 6 threads are used. As mentioned above, this is because the running computer is using 6 cores and 6 threads CPU. Guided load balancing has the best performance because it is a method that combines the advantages of static and dynamic load balancing. Comparing static and dynamic, dynamic uses more resources because it has to manage tasks and distribute them to threads even during execution. Therefore, in PI calculation where the difference in the amount of work of each task is not large and there is little difference in performance per thread, it shows poor performance compared to static load balancing.
