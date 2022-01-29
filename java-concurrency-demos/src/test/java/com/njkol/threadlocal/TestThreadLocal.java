package com.njkol.threadlocal;

import org.junit.jupiter.api.Test;

public class TestThreadLocal {

	@Test
	public void testSimple() throws InterruptedException {
		
		MyRunnable sharedRunnableInstance = new MyRunnable();

		Thread thread1 = new Thread(sharedRunnableInstance);
		Thread thread2 = new Thread(sharedRunnableInstance);

		thread1.start();
		thread2.start();

		thread1.join(); // wait for thread 1 to terminate
		thread2.join(); // wait for thread 2 to terminate
	}
	
	@Test
	public void testServerDemo() throws InterruptedException {
		
		Request req = new Request();
		
		Thread thread1 = new Thread(req);
		Thread thread2 = new Thread(req);
		
		thread1.start();
		thread2.start();

		thread1.join(); // wait for thread 1 to terminate
		thread2.join(); // wait for thread 2 to terminate
	}
	
}
