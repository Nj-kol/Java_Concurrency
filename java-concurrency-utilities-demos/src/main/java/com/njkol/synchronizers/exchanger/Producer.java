package com.njkol.synchronizers.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable {

	private Exchanger<List<String>> exchanger;
	private List<String> buffer;
	private boolean isActive=true;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Producer(Exchanger<List<String>> exchanger, List<String> buffer) {
		this.exchanger = exchanger;
		this.buffer = buffer;
	}

	public void run() {

		while (isActive) {
			try {
				// create tasks & fill the queue
				// exchange the full queue for a empty queue with Consumer
				buffer = new ArrayList<String>();
				buffer.add("My ");
				buffer.add("name ");
				buffer.add("is ");
				buffer.add("Nilanjan ");
				buffer.add("Sarkar ");
				buffer = exchanger.exchange(buffer);
				System.out.println(Thread.currentThread().getName() + " now has " + buffer.size() + " elements");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
