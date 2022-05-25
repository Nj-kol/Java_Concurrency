package com.njkol.locks.conditionobject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Producer implements Runnable {

	private BoundedBuffer<Integer> buffer;

	public Producer(BoundedBuffer<Integer> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			try {
				buffer.put(i);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {

	private BoundedBuffer<Integer> buffer;

	public Consumer(BoundedBuffer<Integer> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		try {
			while (true) {
				buffer.take();
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class BoundedBufferTest {

	BoundedBuffer<Integer> buffer;

	@BeforeEach
	void setUp() throws Exception {
		buffer = new BoundedBuffer<Integer>(10);
	}

	@Test
	void test() throws InterruptedException {

		Thread producer = new Thread(new Producer(buffer));
		Thread consumer = new Thread(new Consumer(buffer));

		consumer.start();
		producer.start();

		consumer.join();
		producer.join();
	}

}
