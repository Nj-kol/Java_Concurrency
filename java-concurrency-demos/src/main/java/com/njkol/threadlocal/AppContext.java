package com.njkol.threadlocal;

public class AppContext {
    /**
     * The unique transaction id per http request
     */
    private String transactionId = null;

    private String userId = null;

    public AppContext(String transactionId, String userId) {
        this.transactionId = transactionId;
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }
}