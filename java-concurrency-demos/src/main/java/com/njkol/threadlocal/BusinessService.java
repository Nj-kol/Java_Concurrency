package com.njkol.threadlocal;

public class BusinessService {

    public void businessMethod() {
        System.out.println(Thread.currentThread().getId() + " - Transaction id " + ContextHelper.getContext().getTransactionId());
        System.out.println(Thread.currentThread().getId() + " - User id" + ContextHelper.getContext().getUserId());
    }
}