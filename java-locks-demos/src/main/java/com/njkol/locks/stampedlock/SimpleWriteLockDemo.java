package com.njkol.locks.stampedlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class SimpleWriteLockDemo {

	public void demoWriteLock(ExecutorService executorService) throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// main thread acquires the write lock before spawning read thread
		long stamp = stampedLock.writeLock();

		try {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("Attempting to acquire read lock");
					long readStamp = stampedLock.readLock();
					System.out.println("Read lock acquired");
					stampedLock.unlock(readStamp);
				}
			});

			// Having the thread sleep for 3 seconds so that the read thread
			// gets blocked
			Thread.sleep(3000);

		} finally {
			// unlock write
			System.out.println("Write lock being released");
			stampedLock.unlock(stamp);

			// wait for read thread to exit
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);
		}
	}

	public void demoDeadLock() throws InterruptedException {
		
		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		stampedLock.writeLock();

		stampedLock.writeLock();
	}
}
