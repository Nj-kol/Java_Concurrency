package com.njkol.locks.stampedlock;

import java.util.concurrent.*;

public class Demonstration {

	public static void main(String args[]) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		SimpleReadLockDemo readLck = new SimpleReadLockDemo();
		// readLck.demoReadLock(executorService);

		SimpleWriteLockDemo wrtLck = new SimpleWriteLockDemo();
		// wrtLck.demoWriteLock(executorService);
		// wrtLck.demoDeadLock();
		
		SimpleOptimisticRead opRead = new SimpleOptimisticRead();
		// opRead.demoOpRead()
		// opRead.demoOpReadInvalidation();
		
		SimpleLockGradingDemos grad = new SimpleLockGradingDemos();
		//grad.demoUpgradeFromPessimisticToWriteLock();
		//grad.demoUpgradeFromReadToWriteLock();
		// grad.demoDownGradeFromWriteToReadLock();
		grad.demoFailedUpgrade();
	}
}