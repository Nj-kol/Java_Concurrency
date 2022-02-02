package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple Non-reentrant spin lock 
 *
 */
class SimpleSpinLock implements SpinLock {
	
	private AtomicReference<Thread> cas;

	SimpleSpinLock(AtomicReference<Thread> cas) {
		this.cas = cas;
	}

	public void lock() {
		Thread current = Thread.currentThread();
		while (!cas.compareAndSet(null, current)) {
			// DO nothing
			System.out.println("I am spinning");
		}
	}

	public void unlock() {
		Thread current = Thread.currentThread();
		cas.compareAndSet(current, null);
	}
}