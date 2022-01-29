package com.njkol.threadfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.jupiter.api.Test;

class Command implements Runnable {

	@Override
	public void run() {
		// Run some code
	}
}

public class TestThreadFactory {

	@Test
	public void testDefaultTf() {

		// Default ThreadFactory
		ThreadFactory threadFactory = Executors.defaultThreadFactory();

		for (int i = 1; i < 10; i++) {

			// Creating new threads with the default
			// ThreadFactory
			Thread thread = threadFactory.newThread(new Command());

			// print the thread names
			System.out.println("Name given by threadFactory = " + thread.getName());

			// run the thread
			thread.start();
		}
	}

	//@Test
	public void testCustomTf() {

		// Creating a CustomThreadFactory object
		CustomThreadFactory threadFactory = new CustomThreadFactory();

		// Creating Runnable objects using the lambda
		// expression
		Runnable command1 = () -> System.out.println("Command 1 executed");
		Runnable command2 = () -> System.out.println("Command 2 executed");
		Runnable command3 = () -> System.out.println("Command 3 executed");
		Runnable command4 = () -> System.out.println("Command 4 executed");
		Runnable command5 = () -> System.out.println("Command 5 executed");

		// Putting the commands in an ArrayList
		List<Runnable> array = new ArrayList<>(5);
		array.add(command1);
		array.add(command2);
		array.add(command3);
		array.add(command4);
		array.add(command5);

		// creating threads and running them
		for (Runnable command : array) {
			threadFactory.newThread(command).start();
		}

		// print the thread count
		System.out.println("Total number of threads created using CustomThreadFactory = " + threadFactory.getCount());
	}
}
