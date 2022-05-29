package com.njkol.threadlocalrandom;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class DemoThreadLocalRandom {

	public static void main(String args[]) throws Exception {
		performanceUsingThreadLocalRandom();
		performanceUsingRandom();
	}

	static void demoThreadLocal() {
		// generate a random boolean value
		System.out.println(ThreadLocalRandom.current().nextBoolean());

		// generate a random int value
		System.out.println(ThreadLocalRandom.current().nextInt());

		// generate a random int between 0 (inclusive) and 500 (exclusive)
		System.out.println(ThreadLocalRandom.current().nextInt(500));

		// generate a random int between 700 (inclusive) and 1900 (exclusive)
		System.out.println(ThreadLocalRandom.current().nextInt(700, 1900));

		// generate a random double between 0 (inclusive) and 1 (exclusive)
		System.out.println(ThreadLocalRandom.current().nextDouble());

		// generate a random float value between 0 (inclusive) and 1 (exclusive)
		System.out.println(ThreadLocalRandom.current().nextFloat());

		// generate a Gaussian ("normally") distributed double value with mean 0.0 and
		// standard deviation 1.0 from this random number generator's sequence
		System.out.println(ThreadLocalRandom.current().nextGaussian());
	}

	static void performanceUsingThreadLocalRandom() throws Exception {

		ExecutorService es = Executors.newFixedThreadPool(15);

		Runnable task = new Runnable() {
			@Override
			public void run() {

				for (int i = 0; i < 50000; i++) {
					ThreadLocalRandom.current().nextInt();
				}

			}
		};

		int numThreads = 4;
		Future[] futures = new Future[numThreads];
		long start = System.currentTimeMillis();

		try {
			for (int i = 0; i < numThreads; i++)
				futures[i] = es.submit(task);

			for (int i = 0; i < numThreads; i++)
				futures[i].get();

			long executionTime = System.currentTimeMillis() - start;
			System.out.println("Execution time using ThreadLocalRandom : " + executionTime + " milliseconds");

		} finally {
			es.shutdown();
		}
	}

	static void performanceUsingRandom() throws Exception {

		Random random = new Random();
		ExecutorService es = Executors.newFixedThreadPool(15);

		Runnable task = new Runnable() {
			@Override
			public void run() {

				for (int i = 0; i < 50000; i++) {
					random.nextInt();
				}
			}
		};

		int numThreads = 4;
		Future[] futures = new Future[numThreads];
		long start = System.currentTimeMillis();

		try {
			for (int i = 0; i < numThreads; i++)
				futures[i] = es.submit(task);

			for (int i = 0; i < numThreads; i++)
				futures[i].get();

			long executionTime = System.currentTimeMillis() - start;
			System.out.println("Execution time using Random : " + executionTime + " milliseconds");

		} finally {
			es.shutdown();
		}
	}
}