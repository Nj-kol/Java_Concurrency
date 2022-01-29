package com.njkol;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.ThreadPoolExecutor;

import org.junit.jupiter.api.Test;

class Task implements Runnable {

	@Override
	public void run() {
		System.out.println("Hello!");
	}
}

public class TestExecutors {

	ExecutorService executorService;

	public void testExecutor() {
		// get count of available cores
		int noOfCores = Runtime.getRuntime().availableProcessors();
		Executor executor = Executors.newFixedThreadPool(noOfCores);
		executor.execute(new Task());
	}

	public void testFixed() {
		// get count of available cores
		int noOfCores = Runtime.getRuntime().availableProcessors();
		executorService = Executors.newFixedThreadPool(noOfCores);
	}

	public void testCached() {
		executorService = Executors.newCachedThreadPool();
	}

	public void testSingleThread() {
		executorService = Executors.newSingleThreadExecutor();
	}

	public void testThreadPoolExecutor() {

		int corePoolSize = 5;
		int maxPoolSize = 10;
		long keepAliveTime = 5000;

		executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	@Test
	public void testScheduled() throws InterruptedException {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

		// task to run after 10 seconds delay
		executorService.schedule(new Task(), 10, TimeUnit.SECONDS);

		// tasks to run repeatedly every 10 seconds
		executorService.scheduleAtFixedRate(new Task(), 10, 10, TimeUnit.SECONDS);

		// tasks to run repeatedly every 10 seconds after previous task completes
		// executorService.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);

		executorService.awaitTermination(40, TimeUnit.SECONDS);
	}
}
