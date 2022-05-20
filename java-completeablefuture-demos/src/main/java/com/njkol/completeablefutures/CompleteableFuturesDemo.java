package com.njkol.completeablefutures;

import java.util.concurrent.CompletableFuture;

public class CompleteableFuturesDemo {

	private static final String sF1 = "62675744/15668936";
	private static final String sF2 = "609136/913704";
	private static final String sBI1 = "846122553600669882";
	private static final String sBI2 = "188027234133482196";

	public static void main(String[] args) {

		//demoRunSync2();
		demoSupplyAsync();
	}

	/**
	 * Set the value of a future manually
	 */
	public static void demoRunSync1() {

		// Create an empty completable future.
		CompletableFuture<BigFraction> future = new CompletableFuture<>();

		CompletableFuture
				// Initiate an async task whose supplier multiplies two
				// large fractions.
				.runAsync(() -> {
					BigFraction bf1 = new BigFraction(sF1);
					BigFraction bf2 = new BigFraction(sF2);

					// Complete the future once the computation is
					// finished.
					future.complete(bf1.multiply(bf2));
				});

		// Print the result, blocking until it's ready.
		System.out.println("     supplyAsync() result = " + future.join().toMixedString());
	}

	/**
	 * Show side-effect
	 */
	public static void demoRunSync2() {

		CompletableFuture<Void> future = CompletableFuture
				// Initiate an async task whose supplier multiplies two
				// large fractions.
				.runAsync(() -> {
					BigFraction bf1 = new BigFraction(sF1);
					BigFraction bf2 = new BigFraction(sF2);

					// Complete the future once the computation is
					// finished.
					System.out.println(bf1.multiply(bf2).toMixedString());
				});

		future.join();
	}

	/**
	 * Show side-effect
	 */
	public static void demoSupplyAsync() {

		var future = CompletableFuture
				// Initiate an async task whose supplier multiplies two
				// large fractions.
				.supplyAsync(() -> {
					BigFraction bf1 = new BigFraction(sF1);
					BigFraction bf2 = new BigFraction(sF2);
					// Return the result of multiplying the fractions.
					return bf1.multiply(bf2);
				});

		var result = future.join();
		System.out.println(result.toMixedString());
	}

}
