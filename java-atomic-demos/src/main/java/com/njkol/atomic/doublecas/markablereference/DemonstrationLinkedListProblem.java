package com.njkol.atomic.doublecas.markablereference;

public class DemonstrationLinkedListProblem {

	public static void main(String args[]) throws Exception {
		
		SimpleNode nodeC = new SimpleNode(3, null);
		SimpleNode nodeB = new SimpleNode(2, nodeC);
		SimpleNode nodeA = new SimpleNode(1, nodeB);

		// NodeA --> NodeB --> NodeC

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				/**
				 * deleting NodeB
				 */
				SimpleNode expected = nodeA.next.get();
				// next holds a reference to Node C which is being deleted by thread2
				SimpleNode next = nodeB.next.get();

				// thread goes to sleep
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ie) {
					// ignore
				}

				// thread wakes-up and successfully updates reference
				nodeA.next.compareAndSet(expected, next);
			}
		});

		thread1.start();
		Thread.sleep(2000);

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// deleting NodeC
				SimpleNode expected = nodeB.next.get();
				SimpleNode next = nodeC.next.get();
				nodeB.next.compareAndSet(expected, next);
				// next of thread A still holds a reference to Node C
			}
		});

		thread2.start();

		thread1.join();
		thread2.join();

		SimpleNode start = nodeA;
		
		while (start != null) {
			System.out.println(start.value + " ");
			start = start.next.get();
		}
	}
}