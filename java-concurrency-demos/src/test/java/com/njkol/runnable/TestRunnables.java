package com.njkol.runnable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("Hello World!");
	}
}

public class TestRunnables {

	private ExecutorService executorService = Executors.newFixedThreadPool(4);
	
	// @Test
	public void testExtendingThread() {
		Thread t = new MyThread();
		t.start();
	}

	// @Test
	public void testRunnable() {

		Runnable r = new MyRunnable();
		Thread t = new Thread(r);
		t.start();

		// Using anonymous class syntax
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				System.out.println("Hello World!");
			}
		});

		t1.start();

		// Using lambda class syntax
		Thread t2 = new Thread(() -> {
			System.out.println("Hello World!");
		});

		t2.start();

		// Declare Runnable using a lambda
		Runnable r2 = () -> {
			System.out.println("Hello World!");
		};

		Thread t3 = new Thread(r2);
		t3.start();
	}

	@Test
	public void testUsingParameters() {

		final List<Book> list = Arrays.asList(new Book(1, "Ramayan"), new Book(2, "Mahabharat"));
	
		Runnable r = () -> {
			Consumer<Book> style = (Book b) -> System.out
					.println("Book Id:" + b.getId() + ", Book Name:" + b.getName());
			list.forEach(style);
		};

		executorService.submit(r);
	}

	@Test
	public void testSubmitRunnableTest() {

		Runnable r = () -> {
			System.out.println("Hello World!");
		};

		for (int i = 0; i < 10; i++) {
			executorService.submit(r);
		}
	}
}
