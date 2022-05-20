package com.njkol.locks.readwrite;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Writer implements Runnable {

	private SimpleAtomicLong atmLong;

	public Writer(SimpleAtomicLong atmLong) {
		this.atmLong = atmLong;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " writting on count variable..");
		
		for (int i = 0; i <= 10; i++) {
			atmLong.incrementAndGet();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Current value : "+atmLong.incrementAndGet());
	}
}

class Reader implements Runnable {

	private SimpleAtomicLong atmLong;

	public Reader(SimpleAtomicLong atmLong) {
		this.atmLong = atmLong;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " Reading on count variable..");
		System.out.println("Current value : "+atmLong.get());
	}
}


class SimpleAtomicLongTest {

	private SimpleAtomicLong atmLong;

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	@BeforeEach
	void setUp() throws Exception {
		atmLong = new SimpleAtomicLong(1L);
	}

	@Test
	void testIncrementAndGet() {

		executorService.submit(new Writer(atmLong));

		executorService.submit(new Reader(atmLong));
		executorService.submit(new Reader(atmLong));
		executorService.submit(new Reader(atmLong));
		
		executorService.submit(new Writer(atmLong));
		
		executorService.submit(new Reader(atmLong));
		executorService.submit(new Reader(atmLong));
		
		executorService.submit(new Writer(atmLong));
	}

}
