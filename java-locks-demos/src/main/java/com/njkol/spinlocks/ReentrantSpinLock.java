package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicReference;

public class ReentrantSpinLock implements SpinLock {

	/**
	 * use thread itself as synchronization state Using Owner Thread as
	 * synchronization state can carry more information than using a simple boolean
	 * flag
	 */
	private AtomicReference<Thread> owner = new AtomicReference<>();

	/**
	 * reentrant count of a thread, no need to be volatile
	 */
	private int count = 0;

	public void lock() {
		Thread t = Thread.currentThread();
		// if re-enter, increment the count.
		if (t == owner.get()) {
			++count;
			return;
		}
		// spin
		while (owner.compareAndSet(null, t)) {
		}
	}

	public void unlock() {
		Thread t = Thread.currentThread();
		// only the owner could do unlock;
		if (t == owner.get()) {
			if (count > 0) {
				// reentrant count not zero, just decrease the counter.
				--count;
			} else {
				// compareAndSet is not need here, already checked
				owner.set(null);
			}
		}
	}
}