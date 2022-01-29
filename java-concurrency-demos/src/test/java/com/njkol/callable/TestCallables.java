package com.njkol.callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

public class TestCallables {

	private ExecutorService executorService = Executors.newFixedThreadPool(1);

	@Test
	public void whenTaskSubmitted_ThenFutureResultObtained() throws InterruptedException, ExecutionException {
		FactorialTask task = new FactorialTask(5);
		Future<Integer> future = executorService.submit(task);
		int expected = future.get().intValue();
		System.out.println(expected);
		assertEquals(120, expected);
	}

	@Test
	public void whenException_ThenCallableDoesntThrowsItIfGetIsNotCalled() {
		FactorialTask task = new FactorialTask(-5);
		Future<Integer> future = executorService.submit(task);
		assertEquals(false, future.isDone());
	}

	@Test
	public void whenException_ThenCallableThrowsIt() throws InterruptedException, ExecutionException {
		FactorialTask task = new FactorialTask(-5);
		Future<Integer> future = executorService.submit(task);
		assertThrows(ExecutionException.class, () -> {
			future.get().intValue();
		});
	}

	@Test
	public void testCallable() throws InterruptedException, ExecutionException {

		// Using Anonymous class
		Callable<Integer> task1 = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return 2 * 3;
			}
		};
		
		Future<Integer> res1 = executorService.submit(task1);
		
		// Using Lambda
		Callable<Integer> task2 = () -> {
			return 2 * 3;
		};

		Future<Integer> res2 = executorService.submit(task2);
		
		final List<Integer> integers =  Arrays.asList(1,2,3,4,5);
		Callable<Integer> task3 = () -> {
			int result = integers.stream().mapToInt(i -> i.intValue()).sum();
			return result;
		};
		
		Future<Integer> res3 = executorService.submit(task3);
		
		System.out.println(res1.get());
		System.out.println(res2.get());
		System.out.println(res3.get());
	}

}
