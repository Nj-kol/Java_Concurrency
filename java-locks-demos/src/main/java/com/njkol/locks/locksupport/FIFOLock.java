package com.njkol.locks.locksupport;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOLock {

	private ConcurrentLinkedQueue<Thread> q = new ConcurrentLinkedQueue<>();
	private AtomicBoolean locked = new AtomicBoolean(false);

	public void lock() {

		// enqueue the current thread in the list of threads waiting
		// to acquire the lock
		q.add(Thread.currentThread());

		// we'll use compare and set method to change the status of the
		// FIFOLock to locked. Also, if the thread wakes up from the park
		// call we check for the predicate, that is the lock is available
		// for acquiring and also whether the woken-up thread is at the
		// head of the queue. Note, that park() method can experience
		// spurious wake-ups.
		while (q.peek() != Thread.currentThread() || !locked.compareAndSet(false, true)) {
			LockSupport.park();
		}

		// remove the head of the queue
		q.remove();
	}

    public void lockWithBlocker() {

        // enqueue the current thread in the list of threads waiting
        // to acquire the lock
        q.add(Thread.currentThread());

        // we'll use compare and set method to change the status of the
        // FIFOLock to locked. Also, if the thread wakes up from the park
        // call we check for the predicate, that is the lock is available
        // for acquiring and also whether the woken-up thread is at the
        // head of the queue. Note, that park() method can experience
        // spurious wake-ups.
        while (q.peek() != Thread.currentThread()
                || !locked.compareAndSet(false, true)) {
            // we pass the lock object itself to the park() method                                
            LockSupport.park(this);
        }
        // remove the head of the queue
        q.remove();
    }

	public void unlock() {

		// set the lock to false
		locked.set(false);

		// unpark the head of the queue
		LockSupport.unpark(q.peek());
	}
}