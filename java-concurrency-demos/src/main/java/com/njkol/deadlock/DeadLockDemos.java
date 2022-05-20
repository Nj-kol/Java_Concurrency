package com.njkol.deadlock;

public class DeadLockDemos {

	public static void main(String args[]) {
		DeadlockDemo1 deadlock = new DeadlockDemo1();
		try {
			deadlock.runTest();
		} catch (InterruptedException ie) {
		}
	}
}
