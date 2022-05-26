package com.njkol.locks.stampedlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class SimpleReadLockDemo {

	public void demoReadLock(ExecutorService executorService) throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// main thread attempts to acquire the lock twice, which is granted
		long readStamp1 = stampedLock.readLock();
		long readStamp2 = stampedLock.readLock();
		
		System.out.println("readStamp1="+readStamp1);
		System.out.println("readStamp1="+readStamp2);
		
		try {

			// create 3 threads
			for (int i = 0; i < 3; i++) {
				executorService.submit(new Runnable() {
					@Override
					public void run() {
						try {
							long readStamp = stampedLock.readLock();
							System.out.println(Thread.currentThread().getName()+" readStamp="+readStamp);
							System.out.println("Read lock count in spawned thread " + stampedLock.getReadLockCount());

							// simulate thread performing read of shared state
							try {
								Thread.sleep(2000);
							} catch (InterruptedException ie) {
								// ignore
							} finally {
								stampedLock.unlockRead(readStamp);
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				});
			}
			// let the main thread simulate work for 5 seconds
			Thread.sleep(5000);
		} finally {
			// wait for spawned threads to finish
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);

			// remember to unlock
			stampedLock.unlock(readStamp1);
			stampedLock.unlock(readStamp2);
		}

		System.out.println("Read lock count in main thread " + stampedLock.getReadLockCount());
		System.out.println("stampedLock.isReadLocked() " + stampedLock.isReadLocked());
	}
}
