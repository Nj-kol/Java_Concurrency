package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements SpinLock {

	private AtomicReference<QNode> tail;
	private ThreadLocal<QNode> myNode;

	public MCSLock() {
		tail = new AtomicReference<>(null);
		myNode = ThreadLocal.withInitial(QNode::new);
	}

	@Override
	public void lock() {
		QNode qnode = myNode.get();
		QNode preNode = tail.getAndSet(qnode);
		if (preNode != null) {
			qnode.locked = false;
			preNode.next = qnode;
			// wait until predecessor gives up the lock
			while (!qnode.locked) {
			}
		}
		qnode.locked = true;
	}

	@Override
	public void unlock() {
		QNode qnode = myNode.get();
		if (qnode.next == null) {
			// There is no waiting thread
			if (tail.compareAndSet(qnode, null)) {
				// If there is no waiting thread, it will return directly without notification
				return;
			}
			// wait until predecessor fills in its next field
			// Suddenly someone is behind him. Maybe he doesn't know who he is. Here are the
			// people waiting for the follow-up
			while (qnode.next == null) {
			}
		}
		// If there is a waiting thread behind, the following thread will be notified
		qnode.next.locked = true;
		qnode.next = null;
	}

	private class QNode {
		/**
		 * Is it locked by the thread of qNode
		 */
		private volatile boolean locked = false;
		/**
		 * Compared with CLHLock, there is a real next
		 */
		private volatile QNode next = null;
	}
}