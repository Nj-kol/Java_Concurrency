package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

public class TestSpinLocks {

	//@Test
	public void testSimpleSpinLock() throws InterruptedException {
		AtomicReference<Thread> cas = new AtomicReference<Thread>();
		SpinLock lock = new SimpleSpinLock(cas);
		Thread thread1 = new Thread(new Task(lock));
		Thread thread2 = new Thread(new Task(lock));
		thread1.start();
		thread2.start();
	}

	//@Test
	public void testTicketLock() throws InterruptedException {
		SpinLock lock = new TicketLock();
		Thread thread1 = new Thread(new Task(lock));
		Thread thread2 = new Thread(new Task(lock));

		thread1.start();
		thread2.start();
	}
	
	//@Test
	public void testCLHLock() throws InterruptedException {
		SpinLock lock = new CLHLock();
		Thread thread1 = new Thread(new Task(lock));
		Thread thread2 = new Thread(new Task(lock));

		thread1.start();
		thread2.start();
	}

	@Test
	public void testMCHLock() throws InterruptedException {
		SpinLock lock = new MCSLock();
		Thread thread1 = new Thread(new Task(lock));
		Thread thread2 = new Thread(new Task(lock));

		thread1.start();
		thread2.start();
	}

}
