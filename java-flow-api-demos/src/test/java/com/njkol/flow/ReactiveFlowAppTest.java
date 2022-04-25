/**
 * 
 */
package com.njkol.flow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nilanjan
 *
 */
class ReactiveFlowAppTest {

	private static final int NUMBER_OF_MAGAZINES = 20;
	private static final long MAX_SECONDS_TO_KEEP_IT_WHEN_NO_SPACE = 2;
	private static final Logger log = LoggerFactory.getLogger(ReactiveFlowAppTest.class);

	private ReactiveFlowApp app;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		 app = new ReactiveFlowApp();
	}

	/**
	 *  CASE 1: Subscribers are fast, buffer size is not so important in this cas
	 *  
	 * Test method for {@link com.njkol.flow.ReactiveFlowApp#magazineDeliveryExample(long, long, int)}.
	 * @throws Exception 
	 */
	//@Test
	void testMagazineDeliveryExampleFastSubscribers() throws Exception {

		log.info("\n\n### CASE 1: Subscribers are fast, buffer size is not so " + "important in this case.");
		app.magazineDeliveryExample(100L, 100L, 8);
	}
	
	
	/**
	 *  CASE 2: A slow subscriber, but a good enough buffer size on the publisher's side to keep all items until they're picked up
				
	 * @throws Exception
	 */
	// @Test
	void testMagazineDeliveryExampleSlowSubscribers() throws Exception {

		log.info("\n\n### CASE 2: A slow subscriber, but a good enough buffer "
				+ "size on the publisher's side to keep all items until they're picked up");
		app.magazineDeliveryExample(1000L, 3000L, NUMBER_OF_MAGAZINES);
	}

	
	
	/**
	 *  CASE 3: A slow subscriber, and a very limited buffer size on the publisher's side 
	 *  so it's important to keep the slow subscriber under control
	 *		
	 * @throws Exception
	 */
	@Test
	void testMagazineDeliveryExampleSlowSubscriberAndLimitedBuffer() throws Exception {

		log.info("\n\n### CASE 3: A slow subscriber, and a very limited buffer "
				+ "size on the publisher's side so it's important to keep the slow " + "subscriber under control");
		app.magazineDeliveryExample(1000L, 3000L, 8);
	}
}
