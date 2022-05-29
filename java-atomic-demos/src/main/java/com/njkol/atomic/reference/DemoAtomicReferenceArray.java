package com.njkol.atomic.reference;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

// https://www.educative.io/courses/java-multithreading-for-senior-engineering-interviews/g7VkwkwY93Y
public class DemoAtomicReferenceArray {

	public static void main(String args[]) throws Exception {

		int arrayLength = 10;
		AtomicReferenceArray<Long> atomicReferenceArray = new AtomicReferenceArray<>(arrayLength);
		ExecutorService executor = Executors.newFixedThreadPool(15);

		try {
			for (int i = 0; i < arrayLength; i++) {
				executor.submit(new Runnable() {
					@Override
					public void run() {
						for (int index = 0; index < arrayLength; index++) {
							Long currentVal = atomicReferenceArray.get(index);
							// attempt to initialize
							if (currentVal == null && atomicReferenceArray.compareAndSet(index, currentVal, new Long(index))) {
								System.out.println(Thread.currentThread().getName() + " initialized index " + index);
							}
						}
					}
				});
			}
		} finally {
			executor.shutdown();
			executor.awaitTermination(1L, TimeUnit.HOURS);
		}
	}
}