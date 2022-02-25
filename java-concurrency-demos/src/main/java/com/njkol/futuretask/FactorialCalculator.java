package com.njkol.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FactorialCalculator<Long> implements Callable<java.lang.Long> {
    private final int number;
    public FactorialCalculator(int number) {
        this.number = number;
    }
    @Override
    public java.lang.Long call() {
        long output = 0;
        try {
            output = factorial(number);
        } catch (InterruptedException ex) {
           System.out.println(ex);
        }
        return output;
    }
    private long factorial(int number) throws InterruptedException {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be greater than zero");
        }
        long result = 1;
        while (number > 0) {
            TimeUnit.SECONDS.sleep(3); // adding delay for example
            result = result * number;
            number--;
        }
        return result;
    }
}
