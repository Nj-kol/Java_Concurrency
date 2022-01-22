package com.njkol.patterns.scattergather;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ScatterGather using CompleteableFuture
 * 
 * @author Nilanjan Sarkar
 *
 */
public class ScatterGatherTechnique2 {

	private Map<String, Integer> prices = new ConcurrentSkipListMap<>();

	public Map<String, Integer> getPrices(int productId)
			throws InterruptedException, ExecutionException, TimeoutException {

		CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task2(prices, productId, "Amazon"));
		CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task2(prices, productId, "Flipkart"));
		CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task2(prices, productId, "JioMart"));

		CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
		allTasks.get(3, TimeUnit.SECONDS);

		return prices;
	}
}
