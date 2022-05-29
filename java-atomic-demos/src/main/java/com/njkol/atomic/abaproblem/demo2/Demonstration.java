package com.njkol.atomic.abaproblem.demo2;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

class Demonstration {

	private static ConcurrentLinkedQueue<Node> availableNodes = new ConcurrentLinkedQueue<>();
	private static AtomicReference<Node> headProblem = new AtomicReference<>(null);
	private static AtomicStampedReference<Node> headSolution = new AtomicStampedReference<>(null, 1);

	public static void main(String args[]) throws Exception {

		//problem();
		solution();
	}

	public static void problem() throws InterruptedException {

		Node currHead = null;
		Node node = null;

		// nodes are inserted by the main thread with values
		// ranging from 0 to 9
		for (int i = 0; i < 10; i++) {
			node = new Node(i);
			node.next = currHead;
			headProblem.compareAndSet(currHead, node);
			currHead = node;
		}

		System.out.println("Initial list : ");
		printNodesProblem();

		// creating Thread1
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// Thread1 reads-in the current head and its next node
				Node currentHead = headProblem.get();
				Node nextHead = currentHead.next;

				System.out.println("Thread 1 sees head = " + currentHead.val + " and head.next = " + nextHead.val);

				// sleep Thread1
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					// ignore
				}

				System.out.println("Thread1 about to compare and set");

				if (headProblem.compareAndSet(currentHead, nextHead)) {
					System.out.println("Thread1 successfully updated head. List looks as follows: ");
					printNodesProblem();
				} else {
					System.out.println("CAS failed in Thread1");
				}
			}
		});

		thread1.start();

		// set-up Thread2
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {

				// wait for Thread 1 to reach its sleep statement
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					// ignore
				}

				// dequeue first five nodes from the list and place them
				// in the free nodes list
				Node currHead = null;
				for (int i = 0; i < 5; i++) {
					currHead = headProblem.get();
					headProblem.compareAndSet(currHead, currHead.next);
					currHead.val = -1; // set to -1 to denote the node is in recycle list
					currHead.next = null;
					availableNodes.add(currHead);
				}

				currHead = headProblem.get();
				Node newHead = availableNodes.remove();
				newHead.val = 99; // set a new value
				newHead.next = currHead;
				if (headProblem.compareAndSet(currHead, newHead)) {
					System.out.println("Thread 2 successfully updates. List is as follows : ");
					printNodesProblem();
				}
			}
		});

		thread2.start();

		// wait for threads to exit
		thread1.join();
		thread2.join();
	}

	public static void solution() throws InterruptedException {

		Node currHead = null;
		Node node = null;

		// nodes are inserted by the main thread with values
		// ranging from 0 to 9
		for (int i = 0; i < 10; i++) {
			node = new Node(i);
			node.next = currHead;
			int currStamp = headSolution.getStamp();
			headSolution.compareAndSet(currHead, node, currStamp, currStamp + 1);
			currHead = node;
		}

		System.out.println("Initial list : ");
		printNodesSolution();

		// creating Thread1
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// Thread1 reads-in the current head and its next node
				Node currentHead = headSolution.getReference();
				int currHeadStamp = headSolution.getStamp();
				Node nextHead = currentHead.next;

				System.out.println("Thread 1 sees head (with stamp " + currHeadStamp + ") = " + currentHead.val
						+ " and head.next = " + nextHead.val);

				// sleep Thread1
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ie) {
					// ignore
				}

				System.out.println("Thread1 about to compare and set");

				if (headSolution.compareAndSet(currentHead, nextHead, currHeadStamp, currHeadStamp + 1)) {
					System.out.println("Thread1 successfully updated head. List looks as follows: ");
					printNodesSolution();
				} else {
					System.out.println("CAS failed in Thread1");
				}
			}
		});

		thread1.start();

		// set-up Thread2
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {

				// wait for Thread 1 to reach its sleep statement
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					// ignore
				}

				// dequeue first five nodes from the list and place them
				// in the free nodes list
				Node currHead = null;
				int currStamp;
				for (int i = 0; i < 5; i++) {
					currHead = headSolution.getReference();
					currStamp = headSolution.getStamp();
					headSolution.compareAndSet(currHead, currHead.next, currStamp, currStamp + 1);
					currHead.val = -1; // set to -1 to denote the node is in recycle list
					currHead.next = null;
					availableNodes.add(currHead);
				}

				currHead = headSolution.getReference();
				currStamp = headSolution.getStamp();
				Node newHead = availableNodes.remove();
				newHead.val = 99; // set a new value
				newHead.next = currHead;
				if (headSolution.compareAndSet(currHead, newHead, currStamp, currStamp + 1)) {
					System.out.println("Thread 2 successfully updates. List is as follows : ");
					printNodesSolution();
				}
			}
		});

		thread2.start();

		// wait for threads to exit
		thread1.join();
		thread2.join();

		System.out.println("List when main program exits is as follows ");
		printNodesSolution();

	}

	// helper method to print the list
	static void printNodesProblem() {
		Node currHead = headProblem.get();
		boolean start = true;
		while (currHead != null) {
			if (start) {
				start = false;
				System.out.print(currHead.val + " (head) -> ");
			} else {
				System.out.print(currHead.val + " -> ");
			}
			currHead = currHead.next;
		}
		System.out.println();
	}

	// helper method to print the list
	static void printNodesSolution() {
		Node currHead = headSolution.getReference();
		int currStamp = headSolution.getStamp();
		boolean start = true;
		while (currHead != null) {
			if (start) {
				start = false;
				System.out.print(currHead.val + " (head with stamp " + currStamp + ") -> ");
			} else {
				System.out.print(currHead.val + " -> ");
			}
			currHead = currHead.next;
		}
		System.out.println();
	}
}
