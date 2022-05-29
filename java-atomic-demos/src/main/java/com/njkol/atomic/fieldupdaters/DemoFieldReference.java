package com.njkol.atomic.fieldupdaters;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DemoFieldReference {

	// Node class
	static class Node {
		volatile Node next;
		int val;

		public Node(int val) {
			this.val = val;
		}
	}

	public static void main(String args[]) {
		
		// create an updater object
		AtomicReferenceFieldUpdater<Node, Node> updater = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class,"next");
		
		Node firstNode = new Node(1);
		Node secondNode = new Node(2);
		
		// update the next pointer of the firstNode to point to secondNode
		updater.compareAndSet(firstNode, null, secondNode);

		// print the value of firstNode's next node
		System.out.println(updater.get(firstNode).val);
	}
}
