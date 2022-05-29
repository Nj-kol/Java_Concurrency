package com.njkol.atomic.doublecas.stampedreference;

import java.util.concurrent.atomic.AtomicStampedReference;

public class DemoStampedReference {

	public static void main(String args[]) {

        Long myLong = new Long(3);
        Long anotherLong = new Long(7);
        
        // set the initial stamp to 1 and reference to myLong
        AtomicStampedReference<Long> atomicStampedReference = new AtomicStampedReference<>(myLong,1);
        
        // we attempt to change the object reference but use the incorrect stamp and the compareAndSet fails
        boolean result = atomicStampedReference.compareAndSet(myLong, anotherLong, 0, 1);
        System.out.println("Was compareAndSet() successful : " + result + " and object value is " + atomicStampedReference.getReference().toString());
        
        // we attempt compareAndSet again with the right expected stamp
        result = atomicStampedReference.compareAndSet(myLong, anotherLong, 1, 2);
        System.out.println("Was compareAndSet() successful : " + result + " and object value is " + atomicStampedReference.getReference().toString());

        // Retrieve the current stamp and reference using the get() method
        int[] currStamp = new int[1];
        Long reference = atomicStampedReference.get(currStamp);
        System.out.println("current stamp " + currStamp[0] + " reference value " + reference.toString());   
	}
}
