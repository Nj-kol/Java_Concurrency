package com.njkol.barrier.synchronization.countdownlatch;

/**
 * The master thread that has to wait for the worker to complete its operations
 * first
 */
public class Master implements Runnable {

	public Master(String name) {

	}

	@Override
	public void run() {
		System.out.println("Master executed " + Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}
}