package com.njkol.atomic.doublecas.markablereference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class DemoMarkableReference {

	public static void main(String args[]) {

		Long myLong = new Long(5);
		Long anotherLong = new Long(7);
		AtomicMarkableReference<Long> atomicMarkableReference = new AtomicMarkableReference<>(myLong, false);

		// attempt to change the long value with the wrong expected mark
		boolean wasSuccess = atomicMarkableReference.compareAndSet(myLong, anotherLong, true, true);
		System.out.println("compare and set succeeded " + wasSuccess + " current value " + atomicMarkableReference.getReference());

		// attempt to change the long value with the right expected mark
		wasSuccess = atomicMarkableReference.compareAndSet(myLong, anotherLong, false, true);
		System.out.println("compare and set succeeded " + wasSuccess + " current value " + atomicMarkableReference.getReference());
	}
}
