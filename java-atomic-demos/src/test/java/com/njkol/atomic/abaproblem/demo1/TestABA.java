package com.njkol.atomic.abaproblem.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class TestABA {

	private Account account;

	@BeforeEach
	public void setUp() {
		account = new Account();
	}

	@Test
	public void zeroBalanceInitializationTest() {
		assertEquals(0, account.getBalance());
		assertEquals(0, account.getTransactionCount());
		assertEquals(0, account.getCurrentThreadCASFailureCount());
	}

	private static ExecutorService getSingleThreadExecutorService(String threadName) {
		return Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat(threadName).build());
	}

	@Test
	public void abaProblemTest() throws Exception {

		final int defaultBalance = 50;
		final int amountToWithdrawByThread1 = 20;
		final int amountToWithdrawByThread2 = 10;
		final int amountToDepositByThread2 = 10;

		account.deposit(defaultBalance);

		Runnable thread1 = () -> {
			// this will take longer due to the name of the thread
			assertTrue(account.withdraw(amountToWithdrawByThread1));

			// thread 1 fails to capture ABA problem
			assertNotEquals(1, account.getCurrentThreadCASFailureCount());
		};

		Runnable thread2 = () -> {
			assertTrue(account.deposit(amountToDepositByThread2));
			assertEquals(defaultBalance + amountToDepositByThread2, account.getBalance());

			System.out.println("Thread 2 balance after deposit: " + account.getBalance());
			// this will be fast due to the name of the thread
			assertTrue(account.withdraw(amountToWithdrawByThread2));
			System.out.println("Thread 2 balance after withdrawl: " + account.getBalance());

			// thread 1 didn't finish yet, so the original value will be in place for it
			assertEquals(defaultBalance, account.getBalance());

			assertEquals(0, account.getCurrentThreadCASFailureCount());
		};

		Future<?> future1 = getSingleThreadExecutorService("thread1").submit(thread1);
		Future<?> future2 = getSingleThreadExecutorService("thread2").submit(thread2);

		future1.get();
		future2.get();

		// compareAndSet operation succeeds for thread 1
		assertEquals(defaultBalance - amountToWithdrawByThread1, account.getBalance());
		System.out.println("Thread 1 balance after Thread 1's operation: " + account.getBalance());

		// but there are other transactions
		assertNotEquals(2, account.getTransactionCount());

		// thread 2 did two modifications as well
		assertEquals(4, account.getTransactionCount());
	}
}
