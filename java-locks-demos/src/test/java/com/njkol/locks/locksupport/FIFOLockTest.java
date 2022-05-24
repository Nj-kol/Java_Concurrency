package com.njkol.locks.locksupport;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FIFOLockTest {

	private ExecutorService executorService;

	@BeforeEach
	void setUp() throws Exception {
		executorService = Executors.newFixedThreadPool(10);
	}

	//@Test
	void test() throws InterruptedException {

		FIFOLock fifoLock = new FIFOLock();

		try {
			for (int i = 0; i < 5; i++) {
				executorService.submit(new Runnable() {
					@Override
					public void run() {
						fifoLock.lock();
						System.out.println(Thread.currentThread().getName() + " acquires the lock.");
						// simulate work by sleeping
						try {
							Thread.sleep(ThreadLocalRandom.current().nextInt(20));
						} catch (InterruptedException ie) {
							// ignore for now
						}
						fifoLock.unlock();
					}
				});
			}
		} finally {
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);
		}

		System.out.println("Main thread exiting successfully");
	}

	@Test
	void testBlocker() throws InterruptedException {
		FIFOLock fifoLock = new FIFOLock();

		// main thread locks the FIFLock instance so that the spawned
		// thread parks itself when invoking lock()
		fifoLock.lock();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " about to park itself.");
				fifoLock.lockWithBlocker();
			}
		});

		thread.start();
		
		// wait for the child thread to get blocked
		Thread.sleep(2000);
		
		// now retrieve the blocker object associated with the thread
		Object blocker = LockSupport.getBlocker(thread);
		System.out.println(blocker);
	

		// unlock so spawned thread can make progress
		fifoLock.unlock();
	}
}