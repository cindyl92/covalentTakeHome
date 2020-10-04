package com.covlaenthq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.junit.Test;

import com.covalenthq.BarGraph;

public class BarGraphTest {
	
	/** private CategoryDataset createDataset(Map<String, Integer> data) */ 
	
	// INPUT:	Valid input
	// TEST:		Check if result is null
	@Test
	public void evaluateValidDataInput() {
		Map<String, Integer> currencyMap = new HashMap<String, Integer>();
		currencyMap.put("BTC", 10);
		currencyMap.put("ETH", 20);
		currencyMap.put("ABC", 30);
		
		/*
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String type = "default";
		
		dataset.addValue(10, type, "BTC");
		dataset.addValue(20, type, "ETH");
		dataset.addValue(30, type, "ABC");
		*/
		CategoryDataset result = BarGraph.createDataset(currencyMap);
		int rows = result.getColumnCount();
		assertEquals(3, rows);
		
	}
	
	// INPUT:	Empty data
	// TEST:		Check if result is null
	@Test
	public void evaluateEmptyDataInput() {
		Map<String, Integer> currencyMap = new HashMap<String, Integer>();
		
		CategoryDataset result = BarGraph.createDataset(currencyMap);
		System.out.println(result);
		
		assertNull(result);
	
	}
	
	// INPUT:	Null
	// TEST:		Check if result is null
	@Test
	public void evaluateNullDataInput() {
		Map<String, Integer> currencyMap = null;
		
		CategoryDataset result = BarGraph.createDataset(currencyMap);
		assertNull(result);
		
	}
	
	
}
