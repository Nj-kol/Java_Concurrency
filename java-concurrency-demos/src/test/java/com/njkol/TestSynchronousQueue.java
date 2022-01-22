package com.njkol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class TestSynchronousQueue {

	@Test
	public void demo() throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();
		
		Runnable producer = () -> {
		    Integer producedElement = ThreadLocalRandom.current().nextInt();
		    try {
		        queue.put(producedElement);
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		Runnable consumer = () -> {
		    try {
		        Integer consumedElement = queue.take();
		        System.out.println(consumedElement);
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		executor.execute(producer);
		executor.execute(consumer);

		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		executor.shutdown();
		assertEquals(queue.size(), 0);
	}
}
