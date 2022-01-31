package com.njkol.stoppingthread;


import org.junit.jupiter.api.Test;

public class TestStoppingThread {

	//@Test
	public void testStopThreadViaBoolean() throws InterruptedException {
		Server myServer = new Server();
		Thread t1 = new Thread(myServer, "T1");
		t1.start(); 
		// Let the server run for 1 second
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName() + " is stopping Server thread");
		// Shutdown the thread
		myServer.shutdown(); // Let's wait to see server thread stopped
		System.out.println(Thread.currentThread().getName() + " is finished now");
	}
	
	//@Test
	public void testStopThreadViaInterruptBlocking() throws InterruptedException {
		var myServer = new BlockingThread();
		Thread t1 = new Thread(myServer, "T1");
		t1.start(); 
		// Let the server run for 2 second
		Thread.sleep(2000);
		System.out.println(Thread.currentThread().getName() + " is stopping Server thread");
		// Shutdown the thread
		try {
			t1.interrupt();
		}catch (Exception e) {
			System.out.println("Server is stopped....");
		}
	}
	
	@Test
	public void testStopThreadViaInterruptNonBlocking() throws InterruptedException {
		var myServer = new NonBlockingThread();
		Thread t1 = new Thread(myServer, "T1");
		t1.start(); 
		// Let the server run for 500 milliseconds
		Thread.sleep(500);
		System.out.println(Thread.currentThread().getName() + " is stopping Server thread");
		// Shutdown the thread
		try {
			t1.interrupt();
		}catch (Exception e) {
			System.out.println("Server is stopped....");
		}
	}
}
