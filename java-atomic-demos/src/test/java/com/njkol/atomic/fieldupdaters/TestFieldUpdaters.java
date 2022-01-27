package com.njkol.atomic.fieldupdaters;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.junit.jupiter.api.Test;

public class TestFieldUpdaters {

	@Test
	public void test() {
		// create an updater object with Counter as target class
		AtomicIntegerFieldUpdater<Counter> updater = AtomicIntegerFieldUpdater.newUpdater(Counter.class, "count");
		// create a counter object
		Counter myCounter = new Counter();
		// compare and set using updater object
		updater.compareAndSet(myCounter, 0, 1);
		// print counter value
		System.out.println("count = " + updater.get(myCounter));
	}
}
