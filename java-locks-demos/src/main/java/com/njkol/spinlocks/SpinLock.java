package com.njkol.spinlocks;

public interface SpinLock {

	public void lock();

	public void unlock();
}
