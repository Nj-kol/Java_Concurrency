package com.njkol.threadlocal;

import java.util.UUID;

public class Request implements Runnable {

	@Override
	public void run() {
	     // sample code to simulate transaction id.
        String transactionId = String.valueOf(UUID.randomUUID());
        // Set the AppContext for the current thread.
        // After that all the business methods can access the transaction id from the thread local.
        ContextHelper.setContext(new AppContext(transactionId, "someUserId"));

        // we are not explicitly passing data
        new BusinessService().businessMethod();
        ContextHelper.unsetContext();
	}

}
