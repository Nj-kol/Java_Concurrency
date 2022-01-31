package com.njkol.stoppingthread;

public class NonBlockingThread implements Runnable {

	@Override
	public void run() {
		while (true) {
			System.out.println("Server is running.....");
			if (Thread.interrupted()) {
				throw new RuntimeException(new InterruptedException("Shutdown request was received"));
			}
		}
	}
}
