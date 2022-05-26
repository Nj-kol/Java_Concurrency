package com.njkol.barrier.synchronization.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * The worker thread that has to complete its tasks first
 */
public class Worker implements Runnable {
	
	private CountDownLatch countDownLatch;

	public Worker(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println("Worker " + Thread.currentThread().getName() + " started");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("Worker  " + Thread.currentThread().getName() + " finished");

		// Each thread calls countDown() method on task completion.
		countDownLatch.countDown();
	}
}