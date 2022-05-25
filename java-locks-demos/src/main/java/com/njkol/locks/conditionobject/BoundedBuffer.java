package com.njkol.locks.conditionobject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

	private final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	private final Object[] items;
	private int putptr, takeptr, count;

	public BoundedBuffer(int capacity) {
		items = new Object[capacity];
	}

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				System.out.println(Thread.currentThread().getName() + " : Buffer is full, waiting");
				notFull.await();
			}
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;
			System.out.printf("%s added %d into queue %n", Thread .currentThread().getName(), x); 
			// signal consumer thread that, buffer has element now 
			System.out.println(Thread.currentThread().getName() + " : Signalling that buffer is no more empty now");
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				System.out.println(Thread.currentThread().getName() + " : Buffer is empty, waiting");
				notEmpty.await();
			}
			Object value = items[takeptr];
			System.out.printf("%s consumed %d from queue %n", Thread.currentThread().getName(), value);

			if (++takeptr == items.length)
				takeptr = 0;
			--count;
			// signal producer thread that, buffer may be empty now 
			System.out.println(Thread.currentThread().getName() + " : Signalling that buffer may be empty now");
			notFull.signal();
			return value;
		} finally {
			lock.unlock();
		}
	}
}
