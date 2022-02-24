package com.njkol.executorcompletionservice;

import java.util.concurrent.Callable;

public class MultiplyingTask implements Callable{
    private final int a;
    private final int b;
    private final long sleepTime;
    private final String taskName;

    public MultiplyingTask(String taskName,int a, int b, long sleepTime) {
        this.taskName=taskName;
        this.a=a;
        this.b=b;
        this.sleepTime=sleepTime;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Started taskName: "+taskName);
        int result=a*b;
        Thread.sleep(sleepTime);
        System.out.println("Completed taskName: "+taskName);
        return result;
    }
}