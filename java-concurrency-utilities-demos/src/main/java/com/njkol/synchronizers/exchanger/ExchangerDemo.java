package com.njkol.synchronizers.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerDemo {

	public static void main(String[] args) throws InterruptedException {

		 demo1();
		//demo2();

		System.exit(0);
	}

	public static void demo1() throws InterruptedException {
		
		Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
		List<String> sample = new ArrayList<String>();
		
		Producer p = new Producer(exchanger, sample);
		Consumer c = new Consumer(exchanger, sample);

		Thread pt = new Thread(p, "Producer");
		Thread ct = new Thread(c, "Consumer");

		pt.start();
		ct.start();

		Thread.sleep(500L);

		p.setActive(false);
		c.setActive(false);
	}

	public static void demo2() throws InterruptedException {

		// create an exchanger object
		Exchanger<String> exchanger = new Exchanger<>();

		// create an executor service
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		try {
			// submit the first task
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					String name = Thread.currentThread().getName();
					String received = "";
					try {
						received = exchanger.exchange(name);
					} catch (InterruptedException ie) {

					}

					System.out.println("I am thread " + name + " and I received the string " + received);
				}
			});

			// submit the second task
			executorService.submit(new Runnable() {
				@Override
				public void run() {

					String name = Thread.currentThread().getName();
					String received = "";
					try {
						received = exchanger.exchange(name);
					} catch (InterruptedException ie) {

					}
					System.out.println("I am thread " + name + " and I received the string " + received);
				}
			});
			
		} finally {
			// remember to shutdown the executor service
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.HOURS);
		}
	}

}
