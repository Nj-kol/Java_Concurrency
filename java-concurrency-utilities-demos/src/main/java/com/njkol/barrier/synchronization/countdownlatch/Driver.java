package com.njkol.barrier.synchronization.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Driver {

	public static void main(String[] args) throws InterruptedException   {
		
        //Created CountDownLatch for 2 threads
        CountDownLatch countDownLatch = new CountDownLatch(2);

        //Created and started two threads
        Thread A = new Thread(new Worker(countDownLatch), "A");
        Thread B = new Thread(new Worker(countDownLatch), "B");

        A.start();
        B.start();

        //When two threads(A and B)complete their tasks, they are returned (counter reached 0).
        countDownLatch.await();

        //Now execution of master thread has started
        Thread D = new Thread(new Master("Master executed"), "D"); ;
        D.start();
    }
}
