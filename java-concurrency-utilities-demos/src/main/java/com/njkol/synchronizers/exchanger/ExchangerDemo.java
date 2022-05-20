package com.njkol.synchronizers.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {

	public static void main(String[] args) throws InterruptedException {

		Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
		List<String> sample = new ArrayList<String>();
		Producer p = new Producer(exchanger,sample);
		Consumer c = new Consumer(exchanger,sample);
		
		Thread pt = new Thread(p,"Producer");
		Thread ct = new Thread(c,"Consumer");
		
		pt.start();
		ct.start();
		
		Thread.sleep(500L);
		
		p.setActive(false);
		c.setActive(false);
		
        System.exit(0);

	}

}
