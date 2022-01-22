package com.njkol.threadlocal;

public class MyRunnable implements Runnable {

	private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {
			threadLocal.set(threadLocal.get() + 1);
			System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
		}
	}
}