package com.covalenthq;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.lang.RuntimeException;
import javax.swing.WindowConstants;

/**
 * JSON parser
 * https://github.com/stleary/JSON-java 
 */
import org.json.*;  

public class DataCorruption {
	
	private static final String INVALID_URL = "invalid";
	private static final int TIMEOUT = 1;
	
	/**
	 * Main Method
	 * 
	 * @brief Triggers data collection and displays bar graph (histogram)
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Map<String, Integer> currencyMap = new HashMap<String, Integer>();
		
		for (int i=0; i<TIMEOUT; i++) {
			Date startTime = new Date();
			//System.out.println(i+ ": "+startTime);
			
			collectCurrencyNullData(currencyMap);
			
			/* If data is not loaded due to invalid issue stop running */
			if (currencyMap.size() == 0) {
				System.out.println("Cannot create the bar graph");
				return;
			}
			
			Date endTime = new Date();
			
			/* Calculate the run time */
			int runtimeSeconds = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
			//System.out.println("Runtime sec "+runtimeSeconds);
			
			/* Sleep for one minute to wait for API update */
			//Thread.sleep(60000 - (runtimeSeconds * 1000));
		}
		
		BarGraph example = new BarGraph("Bar Chart Window", "Data Corruption", "Currencies", "Values", currencyMap);
		
		if (example == null) return;
		
	    example.setSize(1500, 700);  
	    example.setLocationRelativeTo(null);  
	    example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	    example.setVisible(true);
		
	    return;
	}
	
	/**
	 * collectCurrencyNullData
	 * 
	 * @brief Adds currencies which contain null data to currencyMap 
	 * @param currencyMap
	 * @throws IOException
	 */
	public static void collectCurrencyNullData(Map<String, Integer> currencyMap) throws IOException {
		/* Grab url and key from config file */ 
		FileReader reader=new FileReader("./././resources/config.properties");
		Properties p=new Properties();  
	    p.load(reader);  
	    
		String jsonString = readJSONfromUrl(p.getProperty("url") + "?key=" + p.getProperty("key"));
		
		if (jsonString == INVALID_URL || currencyMap == null) {
			System.out.println("Invalid data");
			return;
		}
		
		JSONObject obj = new JSONObject(jsonString);

		JSONArray arr = obj.getJSONObject("data").getJSONArray("ticker");
		//System.out.println("TOTAL CURRENCY # : " +arr.length());
		
		for (int i = 0; i < arr.length(); i++)
		{
		    String currencyName = arr.getJSONObject(i).getString("ticker_symbol");
		    
		    if (arr.getJSONObject(i).get("ticker_symbol").equals(null) || arr.getJSONObject(i).get("quote_rate").equals(null)
		    		|| arr.getJSONObject(i).get("decimals").equals(null) || arr.getJSONObject(i).get("address").equals(null)) {
		    	
			    	if (currencyMap.keySet().contains(currencyName)) 
			    		currencyMap.put(currencyName, currencyMap.get(currencyName) + 1); 
	            else
	            		currencyMap.put(currencyName, 1); 
		    }
		}
		
		return;
	}
	
	/**
	 * readJSONfromUrl
	 * 
	 * @brief Parse each line of JSON from API link into String
	 * @param urlLink
	 * @return JSON in string
	 * @throws IOException
	 */
	public static String readJSONfromUrl(String urlLink) throws IOException {
		/* check if url is null or empty */
		if (urlLink == null || urlLink == "") return INVALID_URL;
		
		/* Grab url and key from config file */ 
		FileReader reader=new FileReader("./././resources/config.properties");
		Properties p=new Properties();  
	    p.load(reader);  
	    
		/* check if url is valid */
		if (!urlLink.contains(p.getProperty("url")) || !urlLink.contains(p.getProperty("key"))) return INVALID_URL;		
		
		String inline = new String();
		try {
			URL url = new URL(urlLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responsecode = conn.getResponseCode();
			
			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			}
			else {
				/* Start parsing & add lines to string */
				Scanner sc = new Scanner(url.openStream());
				
				while (sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				sc.close();
			}
		
		} catch (MalformedURLException e) {
			System.out.println("URL Failed");
			e.printStackTrace();			
		}
		
		return inline;
	}
}
