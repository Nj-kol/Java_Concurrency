package com.njkol.locks.readwrite;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @class SimpleAtomicLong
 * 
 * @brief This class implements a subset of the
 *        java.util.concurrent.atomic.SimpleAtomicLong class using a
 *        ReentrantReadWriteLock to illustrate how they work.
 *        
 *  References:
 *  - https://github.com/thiagotigaz/solutions_posa/blob/master/Copy%20of%20W2-A1-SimpleAtomicLong/src/edu/vuum/mocca/SimpleAtomicLong.java
 *  - https://dzone.com/articles/longdouble-are-not-atomic-in-java
 */
public class SimpleAtomicLong {
	/**
	 * The value that's manipulated atomically via the methods.
	 */
	private long mValue;

	/**
	 * The ReentrantReadWriteLock used to serialize access to mValue.
	 */
	private final ReentrantReadWriteLock mRWLock;
	private final ReentrantReadWriteLock.WriteLock writeLock;
	private final ReentrantReadWriteLock.ReadLock readLock;

	/**
	 * Creates a new SimpleAtomicLong with the given initial value.
	 */
	public SimpleAtomicLong(long initialValue) {
		mValue = initialValue;
		mRWLock = new ReentrantReadWriteLock();
		writeLock = mRWLock.writeLock();
		readLock = mRWLock.readLock();
	}

	/**
	 * @brief Atomically increments by one the current value
	 * 
	 * @returns the updated value
	 */
	public long incrementAndGet() {
		long value = 0;
		// Atomically acquire the writelock (blocking if necessary) & increment the
		// current mValue
		writeLock.lock();
		try {
			value = ++mValue;
			return value;
		} finally {
			// The “try/finally” idiom ensures the lock is always released
			writeLock.unlock();
		}
	}

	/**
	 * @brief Gets the current value.
	 * 
	 * @returns The current value
	 */
	public long get() {
		long value;
		// Atomically acquire the readlock (blocking if necessary) & return current
		// mValue
		readLock.lock();
		try {
			value = mValue;
			return value;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * @brief Atomically increments by one the current value
	 *
	 *        “Lock downgrading” example
	 * 
	 * @returns the previous value
	 */
	public long getAndIncrement() {
		long value = 0;
		Lock lock = writeLock;
		lock.lock();
		try {
			mValue++;
			// Downgrade a write-lock to read-lock
			final Lock readlck = readLock;
			readlck.lock();
			try {
				// Unlock the write-lock & read the mValue with the read-lock still held
				lock.unlock();
				value = mValue;
			} finally {
				lock = readlck;
			}
		} finally {
			lock.unlock();
		}
		return value;
	}

	/**
	 * @brief Atomically decrements by one the current value
	 *
	 * @returns the updated value
	 */
	public long decrementAndGet() {
		long value = 0;
		writeLock.lock();
		try {
			value = --mValue;
			return value;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * @brief Atomically decrements by one the current value
	 *
	 * @returns the previous value
	 */
	public long getAndDecrement() {
		long value = 0;
		Lock lock = writeLock;
		lock.lock();
		try {
			--mValue;
			// Downgrade a write-lock to read-lock
			final Lock readlck = readLock;
			readlck.lock();
			try {
				// Unlock the write-lock & read the mValue with the read-lock still held
				lock.unlock();
				value = mValue;
			} finally {
				lock = readlck;
			}
		} finally {
			lock.unlock();
		}
		return value;
	}
}