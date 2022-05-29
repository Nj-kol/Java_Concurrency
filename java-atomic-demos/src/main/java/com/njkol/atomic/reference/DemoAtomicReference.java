package com.njkol.atomic.reference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DemoAtomicReference {

	static Thread firstThread = null;
	static AtomicReference<Thread> atomicReference = new AtomicReference<>();

	public static void main(String args[]) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(50);
		//demoContention(executor);
		demoContentionCorrected(executor);
	}

	/**
	 * If you run the above widget enough times, you’ll see some runs show multiple
	 * threads entering the if-clause
	 * 
	 * @param executor
	 * @throws InterruptedException
	 */
	public static void demoContention(ExecutorService executor) throws InterruptedException {

		try {

			for (int i = 0; i < 25; i++) {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						synchronized (this) {
							if (firstThread == null) {
								firstThread = Thread.currentThread();
								System.out.println(Thread.currentThread().getName() + " takes first turn");
							}
						}
					}
				});
			}
		} finally {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.HOURS);
		}
	}

	/**
	 * With AtomicReference only one thread enters the if-clause
	 * 
	 * @param executor
	 * @throws InterruptedException
	 */
	public static void demoContentionCorrected(ExecutorService executor) throws InterruptedException {
		try {
			for (int i = 0; i < 25; i++) {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						if (atomicReference.get() == null) {
							if (atomicReference.compareAndSet(null, Thread.currentThread())) {
								System.out.println(Thread.currentThread().getName() + " takes first turn");
							}
						}
					}
				});
			}
		} finally {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.HOURS);
		}
	}
}
