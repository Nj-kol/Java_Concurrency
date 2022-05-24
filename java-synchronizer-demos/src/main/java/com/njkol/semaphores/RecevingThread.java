package com.njkol.semaphores;

public class RecevingThread extends Thread {
	
	private MySemaphore semaphore = null;

	public RecevingThread(MySemaphore semaphore) {
		this.semaphore=semaphore;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				this.semaphore.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// receive signal, then do something...
		}
	}
}