package com.njkol.spinlocks;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements SpinLock {

	/**
	 * The end of the lock wait queue
	 */
	private AtomicReference<QNode> tail;
	private ThreadLocal<QNode> preNode;
	private ThreadLocal<QNode> myNode;

	public CLHLock() {
		tail = new AtomicReference<>(null);
		myNode = ThreadLocal.withInitial(QNode::new);
		preNode = ThreadLocal.withInitial(() -> null);
	}

	@Override
	public void lock() {
		QNode qnode = myNode.get();
		// Setting your own state to locked=true indicates that you need to obtain a
		// lock
		qnode.locked = true;
		// The tail of the linked list is set to the qNode of the thread, and the
		// previous tail is set to the preNode of the current thread
		QNode pre = tail.getAndSet(qnode);
		preNode.set(pre);
		if (pre != null) {
			// The current thread rotates on the locked field of the precursor node until
			// the precursor node releases the lock resource
			while (pre.locked) {
			}
		}
	}

	@Override
	public void unlock() {
		QNode qnode = myNode.get();
		// When the lock is released, its own locked is set to false, so that its
		// successor node can end the spin
		qnode.locked = false;
		// Recycle the node and delete it from the virtual queue
		// If the current node reference is set as its own preNode, the next node's
		// preNode will become the current node's preNode, thus removing the current
		// node from the queue
		myNode.set(preNode.get());
	}

	private class QNode {
		/**
		 * true Indicates that the thread needs to acquire the lock and does not release
		 * the lock. false indicates that the thread has released the lock and does not
		 * need the lock
		 */
		private volatile boolean locked = false;
	}
}