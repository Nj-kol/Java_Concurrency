package com.njkol.semaphores;

public class BoundedSemaphore {

	private int signals = 0;
	private int bound = 0;

	public BoundedSemaphore(int upperBound) {
		this.bound = upperBound;
	}

	/*
	 * Notice how the take() method now blocks if the number of signals is equal to the upper bound. 
	 * Not until a thread has called release() will the thread calling take() be allowed to deliver its signal, 
	 * if the BoundedSemaphore has reached its upper signal limit.
	 */
	public synchronized void take() throws InterruptedException {

		while (this.signals == bound) {
			wait();
		}
		this.signals++;
		this.notify();
	}

	public synchronized void release() throws InterruptedException {
		while (this.signals == 0) {
			wait();
		}
		this.signals--;
		this.notify();
	}
}