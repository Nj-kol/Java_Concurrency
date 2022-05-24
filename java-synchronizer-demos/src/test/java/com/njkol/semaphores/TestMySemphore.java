package com.njkol.semaphores;

import org.junit.jupiter.api.Test;

import com.njkol.semaphores.BoundedSemaphore;
import com.njkol.semaphores.MySemaphore;
import com.njkol.semaphores.RecevingThread;
import com.njkol.semaphores.SendingThread;

public class TestMySemphore {

	@Test
	public void testSimpleSemaphore() {
		
		MySemaphore semaphore = new MySemaphore();

		SendingThread sender = new SendingThread(semaphore);
		RecevingThread receiver = new RecevingThread(semaphore);

		receiver.start();
		sender.start();
	}
	
	@Test
	public void testSemaphoreAsLock() throws InterruptedException {
		BoundedSemaphore semaphore = new BoundedSemaphore(1);

		semaphore.take();

		/*
		 * The relase() method is called from inside a finally-block to make sure it is called even if an exception is thrown
		 *  from the critical section.
		 */
		try{
		  //critical section
		} finally {
		  semaphore.release();
		}
	}
}
