package com.njkol;

import org.junit.jupiter.api.Test;

public class TestThreadGroup {

	@Test
	public void testGroup() {
		ThreadGroup tg1 = new ThreadGroup("Parent ThreadGroup");

		Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
		Thread t1 = new Thread(tg1, runnable, "one");
		t1.start();
		Thread t2 = new Thread(tg1, runnable, "two");
		t2.start();
		Thread t3 = new Thread(tg1, runnable, "three");
		t3.start();

		System.out.println("Thread Group Name: " + tg1.getName());
		System.out.println("Active threads: " + tg1.activeCount());
		tg1.list();
	}
}
