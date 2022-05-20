package com.njkol.patterns.poison.pill;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PoisonPillDemo {

	public static void main(String[] args) {
		
		int BOUND = 10;
		int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
		int poisonPill = Integer.MAX_VALUE;
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(BOUND);

		Thread producer = new Thread(new NumbersProducer(queue, poisonPill));
		producer.start();

		for (int j = 0; j < N_CONSUMERS; j++) {
			new Thread(new NumbersConsumer(queue, poisonPill)).start();
		}
	}
}