package com.njkol.atomic.doublecas.markablereference;

public class DemonstrationLinkedListSolution {

	public static void main(String args[]) throws Exception {

		Node nodeC = new Node(3, null);
		Node nodeB = new Node(2, nodeC);
		Node nodeA = new Node(1, nodeB);

		// nodeA --> nodeB --> nodeC

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// deleting NodeB
				// mark NodeB as deleted
				nodeB.next.set(nodeB.next.getReference(), true);
				Node expected = nodeA.next.getReference();
				Node next = nodeB.next.getReference();

				// thread goes to sleep
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ie) {
					// ignore
				}

				// thread wakes-up and attempts to compareAndSet.
				nodeA.next.compareAndSet(expected, next, false, false);
			}
		});

		thread1.start();
		Thread.sleep(2000);

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// deleting NodeC
				nodeC.next.set(nodeC.next.getReference(), true);
				Node expected = nodeB.next.getReference();
				Node next = nodeC.next.getReference();
				// The result of deleting nodeC is false.
				nodeB.next.compareAndSet(expected, next, false, false);
			}
		});

		thread2.start();

		thread1.join();
		thread2.join();

		Node start = nodeA;
		while (start != null) {
			boolean[] mark = new boolean[1];
			start.next.get(mark);

			// filter out deleted nodes, which are identified by having mark as true
			if (!start.next.isMarked()) {
				System.out.println(start.value + " (mark : " + mark[0] + ") ");
			}
			start = start.next.getReference();
		}
	}
}