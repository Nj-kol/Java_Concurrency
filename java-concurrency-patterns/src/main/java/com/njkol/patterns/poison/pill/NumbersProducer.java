package com.njkol.patterns.poison.pill;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersProducer implements Runnable {

	private final BlockingQueue<Integer> numbersQueue;
	private final int poisonPill;

	NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill) {
		this.numbersQueue = numbersQueue;
		this.poisonPill = poisonPill;
	}

	public void run() {
		try {
			generateNumbers();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void generateNumbers() throws InterruptedException {

		for (int i = 0; i < 10000; i++) {
			numbersQueue.put(ThreadLocalRandom.current().nextInt(100));
		}
		numbersQueue.put(poisonPill);
	}
}