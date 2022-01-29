package com.njkol;

import org.junit.jupiter.api.Test;

public class TestBadSynchorization {

	@Test
	public void demo1() throws InterruptedException {
		Object dummyObject = new Object();

		// Attempting to call wait() on the object
		// outside of a synchronized block.
		dummyObject.wait();
	}

	@Test
	public void demo2() {
		
		Object dummyObject = new Object();
		Object lock = new Object();

		synchronized (lock) {
			lock.notify();

			// Attempting to call notify() on the object
			// in synchronized block of another object
			dummyObject.notify();
		}

	}
}
