package com.njkol.stoppingthread;

public class BlockingThread implements Runnable {

	@Override
	public void run() {
		
		while (true) {
			try {
				System.out.println("Server is running.....");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Preserve the evidence that the exception may have occurred
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		}
	}
}
