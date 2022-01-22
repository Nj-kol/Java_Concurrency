package com.njkol.patterns.scattergather;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Task implements Runnable {

	private Map<String, Integer> prices;
	private String source;
	private CountDownLatch latch;
	private int productId;

	public Task(Map<String, Integer> price, CountDownLatch latch, int productId, String source) {
		this.prices = price;
		this.source = source;
		this.latch = latch;
		this.productId = productId;
	}

	public void run() {
		int productPrice = (int) (Math.random() * 100D);
		prices.put(source, productPrice);
		System.out.println("Got price "+ productPrice +" for product Id: " + productId + " from " + source);
		latch.countDown();
	}
}
