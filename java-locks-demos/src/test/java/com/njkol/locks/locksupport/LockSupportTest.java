package com.njkol.locks.locksupport;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.LockSupport;

import org.junit.jupiter.api.Test;

public class LockSupportTest {

	// In the code widget below, the main thread instantiates a child thread and
	// runs it.
	// The child thread then parks itself by invoking the park() method of the
	// LockSupport class.
	// The main thread then unparks the child thread using the unpark() method and
	// both threads exit.
	@Test
	void testBlocker() throws InterruptedException, BrokenBarrierException {

		CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

		Thread childThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " about to park.");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// ignore
				}
				LockSupport.park();
				System.out.println(Thread.currentThread().getName() + " unparked.");
				try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// ignore
				}
			}
		});

		childThread.start();

		// wait for the childThread to start
		cyclicBarrier.await();
		System.out.println("Main thread about to unpark " + childThread.getName());
		LockSupport.unpark(childThread);

	}
}
