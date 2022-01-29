package com.njkol.threadfactory;

import java.util.concurrent.ThreadFactory;

/**
 * A custom thread factory implementation
 * 
 * @author Nilanjan Sarkar
 *
 */
public class CustomThreadFactory implements ThreadFactory {

	// stores the thread count
	private int count = 0;

	// returns the thread count
	public int getCount() {
		return count;
	}

	// Factory method
	@Override
	public Thread newThread(Runnable command) {
		count++;
		return new Thread(command);
	}
}