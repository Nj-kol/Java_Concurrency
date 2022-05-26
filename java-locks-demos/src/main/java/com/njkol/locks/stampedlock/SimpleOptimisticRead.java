package com.njkol.locks.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class SimpleOptimisticRead {

	public void demoOpRead() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// optimistic read
		stampedLock.tryOptimisticRead();

		// outputs "read lock count 0 is read locked false"
		System.out.println(
				"read lock count " + stampedLock.getReadLockCount() + " is read locked " + stampedLock.isReadLocked());
	}

	public void demoOpReadInvalidation() throws InterruptedException {

		// create an instance of StampedLock
		StampedLock stampedLock = new StampedLock();

		// try optimistic read
		long stamp = stampedLock.tryOptimisticRead();

		// check for validation, prints true
		System.out.println(stampedLock.validate(stamp));

		// acquire the write lock which will invalidate the previous stamp
		stampedLock.writeLock();

		// check for validation, prints false
		System.out.println(stampedLock.validate(stamp));
	}

	public void demoOpReadIdiom() throws InterruptedException {
		StampedLock stampedLock = new StampedLock();

		int[] array = new int[3];
		array[0] = 3;
		array[1] = 5;
		array[2] = 7;
		productOfThree(array,stampedLock);
	}

	public int productOfThree(int[] array, StampedLock stampedLock) {
		
		// get a stamp from optimistic read
		long stamp = stampedLock.tryOptimisticRead();

		// read the three elements of the array in local variables
		int num1 = array[0];
		int num2 = array[1];
		int num3 = array[2];

		// if stamp isn't valid anymore i.e. a write lock was acquired then
		// get the read lock

		if (!stampedLock.validate(stamp)) {

			// this call may block
			stamp = stampedLock.readLock();

			try {
				return array[0] * array[1] * array[3];
			} catch (RuntimeException re) {
				// log exception
				throw re;
			} finally {
				// remember to unlock in finally block
				stampedLock.unlockRead(stamp);
			}
		}

		// assuming the multiplication doesn't result in an overflow exception
		return num1 * num2 * num3;
	}

}
