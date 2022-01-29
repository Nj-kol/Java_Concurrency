package com.njkol.atomic.adders;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class TestAddersAndAccumulators {

	ExecutorService executorService = Executors.newFixedThreadPool(8);

	@Test
	public void testLongAdder() throws InterruptedException {

		LongAdder counter = new LongAdder();

		int numberOfThreads = 4;
		int numberOfIncrements = 100;

		Runnable incrementAction = () -> IntStream.range(0, numberOfIncrements).forEach(i -> counter.increment());

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.execute(incrementAction);
		}

		Thread.sleep(1000);

		System.out.println(counter.sum());
	}

	@Test
	public void testLongAccumulator() throws InterruptedException {

		LongAccumulator accumulator = new LongAccumulator(Long::sum, 0L);

		int numberOfThreads = 4;
		int numberOfIncrements = 100;

		Runnable accumulateAction = () -> IntStream.rangeClosed(0, numberOfIncrements).forEach(accumulator::accumulate);

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.execute(accumulateAction);
		}

		Thread.sleep(1000);

		System.out.println(accumulator.longValue());
	}
}
