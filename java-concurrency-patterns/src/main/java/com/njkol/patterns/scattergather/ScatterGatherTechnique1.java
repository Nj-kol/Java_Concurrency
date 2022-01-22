package com.njkol.patterns.scattergather;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ScatterGather using Countdownlatch
 * 
 * @author Nilanjan Sarkar
 *
 */
public class ScatterGatherTechnique1 {

	private Map<String, Integer> prices = new ConcurrentSkipListMap<>();
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private CountDownLatch latch =  new CountDownLatch(3);
    
	public Map<String, Integer> getPrices(int productId) throws InterruptedException {
		
		executor.execute(new Task(prices,latch,productId,"Amazon"));
		executor.execute(new Task(prices,latch,productId,"Flipkart"));
		executor.execute(new Task(prices,latch,productId,"JioMart"));
		
		latch.await(3,TimeUnit.SECONDS);
		
		return prices;
	}
}
