package com.njkol.locks.stampedlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class SimpleLockGradingDemos {

	public void demoUpgradeFromPessimisticToWriteLock() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// get into optimistic read mode
		long stamp = stampedLock.tryOptimisticRead();

		// upgrade to write lock
		stampedLock.tryConvertToWriteLock(stamp);

		/*
		 * output of program is Read locks held : 0 Write lock held : true
		 */
		System.out.println("Read locks held : " + stampedLock.getReadLockCount() + "\nWrite lock held : "
				+ stampedLock.isWriteLocked());
	}

	public void demoUpgradeFromReadToWriteLock() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// acquire read lock
		long stamp = stampedLock.readLock();

		// upgrade to write lock
		stampedLock.tryConvertToWriteLock(stamp);

		/*
		 * output of program is Read locks held : 0 Write lock held : true
		 */
		System.out.println("Read locks held : " + stampedLock.getReadLockCount() + "\nWrite lock held : "
				+ stampedLock.isWriteLocked());
	}

	public void demoDownGradeFromWriteToReadLock() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// acquire write lock
		long stamp = stampedLock.writeLock();

		// downgrade to read lock
		stampedLock.tryConvertToReadLock(stamp);

		/*
		 * output of program is Read locks held : 1 Write lock held : false
		 */
		System.out.println("Read locks held : " + stampedLock.getReadLockCount() + "\nWrite lock held : "
				+ stampedLock.isWriteLocked());
	}

	public void demoDownGradeFromReadToOptimisticReadLock() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// acquire read lock
		long stamp = stampedLock.readLock();

		// downgrade to optimistic read mode
		stampedLock.tryConvertToOptimisticRead(stamp);

		/*
		 * output of program is Read locks held : 0 Write lock held : false
		 */
		System.out.println("Read locks held : " + stampedLock.getReadLockCount() + "\nWrite lock held : "
				+ stampedLock.isWriteLocked());
	}

	/**
	 *  demonstrates a failed attempt by the main thread to upgrade to the write lock from optimistic read mode.
	 *  
	 *  Another spawned thread holds the write lock and prevents the main thread from upgrading
	 *  
	 * @throws InterruptedException
	 */
	public void demoFailedUpgrade() throws InterruptedException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();
		long stamp = stampedLock.tryOptimisticRead();

		try {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					long stamp = stampedLock.writeLock();

					// simulate work by sleeping
					try {
						Thread.sleep(6000);
						System.out.println("spawned thread exiting.");
					} catch (InterruptedException ie) {
						// ignore
					} finally {
						stampedLock.unlockWrite(stamp);
					}
				}
			});

			// give the spawned thread a chance to acquire the write lock. NEVER use
			// Thread.sleep() for thread synchronization in production code!
			Thread.sleep(3000);

			// attempt to upgrade to write lock
			stamp = stampedLock.tryConvertToWriteLock(stamp);
			System.out.println("Stamp : " + stamp + "\nRead locks held : " + stampedLock.getReadLockCount()
					+ "\nWrite lock held : " + stampedLock.isWriteLocked());

		} finally {
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);
		}
	}
}
