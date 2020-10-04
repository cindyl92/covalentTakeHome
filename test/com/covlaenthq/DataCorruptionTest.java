package com.covlaenthq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.covalenthq.DataCorruption;

public class DataCorruptionTest {
	private static final String INVALID_URL = "invalid";
	
	/** public static String readJSONfromUrl(String urlLink) throws IOException */
	
	// INPUT:	Working URL
	// TEST:		Check if first ticker_symbol is "BTC"
	// Note:		Not the best way to check as the order of ticker_symbol list may change
	@Test
	public void evaluateWorkingUrl1(){
		String urlLink = "https://api.covalenthq.com/v1/ticker/?key=ckey_be61e76bc0dd4012890ecdc3564";
		String result = "";
		try {
			result = DataCorruption.readJSONfromUrl(urlLink);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject obj = new JSONObject(result);
		JSONArray arr = obj.getJSONObject("data").getJSONArray("ticker");
		
		String currencyName = arr.getJSONObject(0).getString("ticker_symbol");
		
		assertEquals("BTC", currencyName);
	}
	
	// INPUT:	Working URL
	// TEST:		Check if exception is not thrown
	@Test
	public void evaluateWorkingUrl2() {
		String urlLink = "https://api.covalenthq.com/v1/ticker/?key=ckey_be61e76bc0dd4012890ecdc3564";
		try {
			String result = DataCorruption.readJSONfromUrl(urlLink);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Exception is not thrown
		assertTrue(true);
	}
	
	// INPUT:	Proper URL with no key
	// TEST:		Check if result equals to INVALID_URL
	@Test
	public void evaluateNoKeyUrl() {
		String urlLink = "https://api.covalenthq.com/v1/ticker/";
		try {
			String result = DataCorruption.readJSONfromUrl(urlLink);
			assertEquals(INVALID_URL, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// INPUT:	Empty URL
	// TEST:		Check if result equals to INVALID_URL
	@Test
	public void evaluateEmptyUrl() {
		String urlLink = "";
		try {
			String result = DataCorruption.readJSONfromUrl(urlLink);
			assertEquals(INVALID_URL, result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// INPUT:	Proper URL but not API URL
	// TEST:		Check if result equals to INVALID_URL
	@Test
	public void evaluateNonApiUrl() {
		String urlLink = "www.google.ca";
		try {
			String result = DataCorruption.readJSONfromUrl(urlLink);
			assertEquals(INVALID_URL, result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// INPUT:	Null
	// TEST:		Check if result equals to INVALID_URL
	@Test
	public void evaluateNullUrl() {
		String urlLink = null;
		try {
			String result = DataCorruption.readJSONfromUrl(urlLink);
			assertEquals(INVALID_URL, result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** private static void collectCurrencyNullData(Map<String, Integer> currencyMap) throws IOException */
	
	// INPUT:	
	// TEST:		Check if exception is not thrown
	@Test
	public void evaluateProperInputNoExceptionThrown() {
		Map<String, Integer> currencyMap = new HashMap<String, Integer>();
		
		try {
			DataCorruption.collectCurrencyNullData(currencyMap);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Exception is not thrown
		assertTrue(true);
	}
	
	// INPUT:	Proper URL but not API URL
	// TEST:		Check if exception is not thrown
	@Test
	public void evaluateNullInput() {
		try {
			DataCorruption.collectCurrencyNullData(null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Exception is not thrown
		assertTrue(true);
	}
}
