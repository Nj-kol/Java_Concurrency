package com.njkol.patterns.scattergather;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class TestScattergather {

	@Test
	public void testTech1() throws InterruptedException {
		ScatterGatherTechnique1 t = new ScatterGatherTechnique1();
		Map<String, Integer> prices = t.getPrices(1);
		prices.forEach((k, v) -> System.out.println((k + ":" + v)));
	}
	
	@Test
	public void testTech2() throws Exception {
		ScatterGatherTechnique2 t = new ScatterGatherTechnique2();
		Map<String, Integer> prices = t.getPrices(1);
		prices.forEach((k, v) -> System.out.println((k + ":" + v)));
	}

}
