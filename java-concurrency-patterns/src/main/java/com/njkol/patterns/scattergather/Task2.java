package com.njkol.patterns.scattergather;

import java.util.Map;

public class Task2 implements Runnable {

	private Map<String, Integer> prices;
	private String source;
	private int productId;

	public Task2(Map<String, Integer> price,int productId, String source) {
		this.prices = price;
		this.source = source;
		this.productId = productId;
	}

	public void run() {
		int productPrice = (int) (Math.random() * 100D);
		prices.put(source, productPrice);
		System.out.println("Got price "+ productPrice +" for product Id: " + productId + " from " + source);
	}
}
