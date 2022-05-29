package com.njkol.atomic.doublecas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.njkol.atomic.doublecas.stampedreference.StampedAccount;

public class TestDoubleCAS {

	@Test
	public void givenMultiThread_whenStampedAccount_thenSetBalance() throws InterruptedException {

		StampedAccount account = new StampedAccount();

		Thread t = new Thread(() -> {
			while (!account.withdrawal(100))
				Thread.yield();
		});

		t.start();
		assertTrue(account.deposit(100));
		
		t.join(1_000);

		assertFalse(t.isAlive());

		assertSame(0, account.getBalance());
		System.out.println(account.getBalance());
	}
}
