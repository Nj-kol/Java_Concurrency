package com.njkol.stoppingthread;

public class Server implements Runnable {
	
	// However, reads & writes to a volatile variable “spin” rather than “block” threads
	private volatile boolean exit = false;

	public void run() {
		
		while (!exit) {
			System.out.println("Server is running.....");
		}
		System.out.println("Server is stopped....");
	}

	public void shutdown() {
		exit = true;
	}
}
