package com.njkol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class TestExchanger {

	@Test
	public void demo() throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		Exchanger<String> exchanger = new Exchanger<>();

		Runnable taskA = () -> {
			try {
				String message = exchanger.exchange("from A");
				assertEquals("from B", message);
				System.out.println("In task A, got message : " +message);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		};

		Runnable taskB = () -> {
			try {
				String message = exchanger.exchange("from B");
				assertEquals("from A", message);
				System.out.println("In task B, got message : " +message);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		};
		
		executor.execute(taskA);
		executor.execute(taskB);
	}
}
