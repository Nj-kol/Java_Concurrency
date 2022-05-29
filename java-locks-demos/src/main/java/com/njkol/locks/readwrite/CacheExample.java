package com.njkol.locks.readwrite;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheExample {

	public static void main(String args[]) throws Exception {

		ExecutorService es = Executors.newFixedThreadPool(15);

		// cache
		HashMap<String, Object> cache = new HashMap<>();
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

		// put some data in the cache
		cache.put("key", -1);

		Runnable writerTask = new Runnable() {
			@Override
			public void run() {
				writerThread(cache, lock);
			}
		};

		Runnable readerTask = new Runnable() {
			@Override
			public void run() {
				readerThread(cache, lock);
			}
		};

		try {
			// submit tasks for execution
			Future future1 = es.submit(writerTask);
			Future future2 = es.submit(readerTask);
			Future future3 = es.submit(readerTask);
			Future future4 = es.submit(readerTask);
			Future future5 = es.submit(readerTask);

			// wait for tasks to finish
			future1.get();
			future2.get();
			future3.get();
			future4.get();
			future5.get();
		} finally {
			es.shutdown();
		}

	}

	static void writerThread(HashMap<String, Object> cache, ReadWriteLock lock) {

		for (int i = 0; i < 9; i++) {
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(50));
			} catch (InterruptedException ie) {
				// ignore
			}

			lock.writeLock().lock();
			try {
				System.out.println("Acquired write lock");
				cache.put("key", ThreadLocalRandom.current().nextInt(1000));
			} finally {
				lock.writeLock().unlock();
			}
		}
	}

	static void readerThread(HashMap<String, Object> cache, ReadWriteLock lock) {

		for (int i = 0; i < 3; i++) {
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(100));
			} catch (InterruptedException ie) {
				// ignore
			}

			lock.readLock().lock();
			try {
				System.out.println("Acquire read lock and reading key = " + cache.get("key"));
			} finally {
				lock.readLock().unlock();
			}
		}
	}
}
