package com.njkol.futuretask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import java.util.concurrent.TimeUnit;

public class TestFutureTask {

    private ExecutorService executor ;

    @BeforeEach
    private void doSetup(){
        executor = Executors.newFixedThreadPool(8);
    }

    @Test
    public void testFactorial() throws ExecutionException, InterruptedException, TimeoutException {

        var callable1 = new FactorialCalculator<Long>(4);
        var callable2 = new FactorialCalculator<Long>(5);

        var futureTask1 = new FutureTask<Long>(callable1);
        var futureTask2 = new MyFutureTask<Long>(callable2);

        var f1 = executor.submit(futureTask1);
        var f2 = executor.submit(futureTask2);

        // wait indefinitely for future
        // task to complete
        if (!futureTask1.isDone()) {
            System.out.println("FutureTask1 output = " + futureTask1.get());
        }

        // Wait if necessary for the computation to complete, (Timed-Blocking technique)
        // and then retrieves its result
        Long s = futureTask2.get(3, TimeUnit.SECONDS);
        System.out.println("FutureTask2 output = " + s);
    }

    @AfterEach
    private void doTearDown(){
        executor.shutdown();
    }
}
