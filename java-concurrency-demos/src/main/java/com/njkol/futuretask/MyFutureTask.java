package com.njkol.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * Custom tasks to showcase the done callback capability
 *
 * @author Nilanjan Sarkar
 * @param <V>
 */
public class MyFutureTask<V> extends FutureTask<V> {

    public MyFutureTask(Callable<V> callable) {
        super(callable);
    }

    @Override
    protected void done() {
        super.done();
        System.out.println("Callback invoked!!");
    }
}
