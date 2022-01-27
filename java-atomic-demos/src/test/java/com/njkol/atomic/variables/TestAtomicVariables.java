package com.njkol.atomic.variables;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.jupiter.api.Test;

public class TestAtomicVariables {

	@Test
	public void testCounter() {
		var counter = new SafeCounterWithoutLock();
		for (int i = 0; i < 10; i++) {
			counter.increment();
		}
		System.out.println(counter.getValue());
	}

	@Test
	public void testAtomicArrays() {

		// Initializing an array
		int a[] = { 1, 2, 3, 4, 5 };

		// Initializing an AtomicIntegerArray with array a
		AtomicIntegerArray arr = new AtomicIntegerArray(a);

		// Displaying the AtomicIntegerArray
		System.out.println("The array : " + arr);

		// Index where operation is performed
		int idx = 0;

		// The new value to update at idx
		int val = 10;

		// Updating the value at
		// idx applying set
		arr.set(idx, val);

		// Displaying the AtomicIntegerArray
		System.out.println("The array after update : " + arr);
	}

	@Test
	public void testAtomicReference() {
	
		String initialReference = "initial value referenced";

		AtomicReference<String> atomicStringReference = new AtomicReference<String>(initialReference);

		String newReference = "new value referenced";
		boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);

		exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
		System.out.println("exchanged: " + exchanged);
	}

	@Test
	public void testAtomicReferenceArray() {

		String string1 = "string1";
		String string2 = "string2";

		String[] source = new String[10];
		source[5] = string1;

		AtomicReferenceArray<String> array = new AtomicReferenceArray<String>(source);

		array.compareAndSet(5, string1, string2);

		// get and print the value
		// using getPlain method
		for (int i = 0; i < array.length(); i++) {

			String value = array.getPlain(i);
			System.out.println("value at " + i + " = " + value);
		}
	}
}
