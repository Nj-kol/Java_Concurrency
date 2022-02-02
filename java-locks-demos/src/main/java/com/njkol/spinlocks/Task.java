package com.njkol.spinlocks;

public class Task implements Runnable {
	
    private SpinLock slock ;

    public Task(SpinLock slock) {
        this.slock = slock;
    }

    public void run() {
        slock.lock(); 
        for (int i = 0; i < 10; i++) {
            //Thread.yield();
            System.out.println(i);
        }
        slock.unlock();
    }
}