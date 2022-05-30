package com.njkol.locks.stampedlock;

import java.util.concurrent.locks.StampedLock;

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/StampedLock.html

public class Point {
	
	private double x, y;
	private final StampedLock sl = new StampedLock();

	void move(double deltaX, double deltaY) { // an exclusively locked method
		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	double distanceFromOrigin() { // A read-only method
		long stamp = sl.tryOptimisticRead();
		double currentX = x, currentY = y;
		if (!sl.validate(stamp)) {
			stamp = sl.readLock();
			try {
				currentX = x;
				currentY = y;
			} finally {
				sl.unlockRead(stamp);
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}

	/**
	 * Lock upgrading from pessimistic to a writelock
	 *  
	 * This is a classic Check-then-Act pattern. 
	 * 
	 * This technique outperforms in low-contention scenarios, i.e. when there are more readers than writers
	 * 
	 * @param newX
	 * @param newY
	 */
	void moveIfAtOrigin(double newX, double newY) { // upgrade
		// Could instead start with optimistic, not read mode
		long stamp = sl.readLock();
		try {
			while (x == 0.0 && y == 0.0) {
				long ws = sl.tryConvertToWriteLock(stamp);
				if (ws != 0L) {
					stamp = ws;
					x = newX;
					y = newY;
					break;
				} else {
					sl.unlockRead(stamp);
					stamp = sl.writeLock();
				}
			}
		} finally {
			sl.unlock(stamp);
		}
	}
}