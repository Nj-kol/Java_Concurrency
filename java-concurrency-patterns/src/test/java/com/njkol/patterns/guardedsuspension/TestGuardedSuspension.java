package com.njkol.patterns.guardedsuspension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class TestGuardedSuspension {

	private ExecutorService executorService = Executors.newFixedThreadPool(3);
	private SimpleBlockingBoundedQueue<Integer> guardedQueue = new SimpleBlockingBoundedQueue<>(5);

	@Test
	public void testSample() {

		// here we create first thread which is supposed to take from guardedQueue. This will block
		executorService.execute(() -> {
			try {
				guardedQueue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executorService.execute(() -> {
			try {
				guardedQueue.put(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		executorService.shutdown();

		try {
			executorService.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
