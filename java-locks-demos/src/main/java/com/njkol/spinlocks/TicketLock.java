package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock implements SpinLock {
	
    private AtomicInteger serviceNum = new AtomicInteger(0);
    private AtomicInteger ticketNum = new AtomicInteger(0);
    
    private final ThreadLocal<Integer> myNum = new ThreadLocal<>();

    public void lock() {
        myNum.set(ticketNum.getAndIncrement());
        while (serviceNum.get() != myNum.get()) {
        }
    }

    public void unlock() {
        serviceNum.compareAndSet(myNum.get(), myNum.get() + 1);
        myNum.remove();
    }
}