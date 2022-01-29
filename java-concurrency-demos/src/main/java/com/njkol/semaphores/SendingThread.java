package com.njkol.semaphores;

public class SendingThread extends Thread {
	MySemaphore semaphore = null;

	public SendingThread(MySemaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while (true) {
			// do something, then signal
			this.semaphore.take();
		}
	}
}