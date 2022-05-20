package com.njkol.synchronizers.exchanger;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable {

	private Exchanger<List<String>> exchanger;
	private List<String> buffer;
	private boolean isActive=true;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Consumer(Exchanger<List<String>> exchanger, List<String> buffer) {
		this.exchanger = exchanger;
		this.buffer = buffer;
	}

	public void run() {

		while (isActive) {
			try {
				// do procesing & empty the queue
				// exchange the empty queue for a full queue with Producer
				ListIterator<String> iter = buffer.listIterator();
				while (iter.hasNext()) {
					System.out.println(iter.next());
					iter.remove();
				}
				buffer = exchanger.exchange(buffer);
				System.out.println(Thread.currentThread().getName() + " now has " + buffer.size() + " elements");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
