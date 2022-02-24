package com.njkol.executorcompletionservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestExecutorCompletionService {

   private ExecutorService executorService;

    @BeforeEach
    public void setUp () {
        executorService   = Executors.newFixedThreadPool(4);
    }

    @Test
    public void testMultiplyTask () {

        CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);

        MultiplyingTask multiplyingTask1= new MultiplyingTask("Task 1",10,20,2000l);
        MultiplyingTask multiplyingTask2= new MultiplyingTask("Task 2",30,40,4000l);
        MultiplyingTask multiplyingTask3= new MultiplyingTask("Task 3",40,50,3000l);
        MultiplyingTask multiplyingTask4= new MultiplyingTask("Task 4",50,60,1000l);

        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        futures.add(executorCompletionService.submit(multiplyingTask1));
        futures.add(executorCompletionService.submit(multiplyingTask2));
        futures.add(executorCompletionService.submit(multiplyingTask3));
        futures.add(executorCompletionService.submit(multiplyingTask4));

        for (int i=0; i<futures.size(); i++) {
            try {
                Integer result = executorCompletionService.take().get();
                System.out.println("Result: " +result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    @Test
    public void testFoodPlateServiceCase1() throws InterruptedException {

        CompletionService<FoodPlate> compService = new ExecutorCompletionService<>(executorService);

        /*
         * Scenario1: Canteen Staff (Producers) preparing food plates and no students yet at counter
         */

        //Create a few Canteen Staffs as producers.
        CanteenStaffProducer prod1 = new CanteenStaffProducer("staff1");
        CanteenStaffProducer prod2 = new CanteenStaffProducer("staff2");

        compService.submit(prod1);
        compService.submit(prod2);

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testFoodPlateServiceCase2() throws InterruptedException {

        /*
         * Scenario 2: Students (Consumers) at the canteen counter but no food plates yet available.
         */

        //  Note that the following thread would block since we have used CompletionService.take
        //  If you need an unblocking retrieval of completed tasks (retrieval of food plates), use poll method.

        CompletionService<FoodPlate> compService = new ExecutorCompletionService<>(executorService);

        var t1 = new Thread(new StudentConsumer("student1",compService));
        t1.start();
        var t2 = new Thread(new StudentConsumer("student2",compService));
        t2.start();

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void testFoodPlateServiceCase3() throws InterruptedException {
       // Scenario3: For random Producers and Consumers
        CompletionService<FoodPlate> compService = new ExecutorCompletionService<>(executorService);

        //Create a few Canteen Staffs as producers.
        CanteenStaffProducer prod1 = new CanteenStaffProducer("staff1");
        CanteenStaffProducer prod2 = new CanteenStaffProducer("staff2");

        compService.submit(prod1);
        compService.submit(prod2);

        var t1 = new Thread(new StudentConsumer("student1",compService));
        t1.start();
        var t2 = new Thread(new StudentConsumer("student2",compService));
        t2.start();

        TimeUnit.SECONDS.sleep(10);
    }
}
