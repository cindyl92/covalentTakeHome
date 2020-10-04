package com.covalenthq;

import java.util.Map;

import javax.swing.JFrame;

/**
 * JFreeChart
 * http://www.jfree.org/jfreechart/api.html 
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraph extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @brief Creates bar graph with given data
	 * @param title
	 * @param chartTitle
	 * @param categoryAxis
	 * @param valueAxis
	 * @param data
	 */
	public BarGraph(String title, String chartTitle, String categoryAxis, String valueAxis, Map<String, Integer> data) {  
	    /* JFrame */
		super(title);  
	  
	    /* Create data set for the table */
	    CategoryDataset dataset = createDataset(data);  
	      
	    if (dataset == null) return;
	    
	    /* Create bar chart */  
	    JFreeChart chart=ChartFactory.createBarChart (  
	    		chartTitle,		// Chart Title  
	    		categoryAxis,		// Category axis  
	    		valueAxis,		// Value axis  
	        dataset,			// Data
	        PlotOrientation.HORIZONTAL,  
	    		false,			// Include legend
	        true,
	        false  
	    );  
	  
	    ChartPanel panel = new ChartPanel(chart);  
	    setContentPane(panel);  
	}
	
	/**
	 * createDataset
	 * 
	 * @brief Convert dataset from Map<String, Integer> to CategoryDataset format
	 * @param data
	 * @param size
	 * @return CategoryDataset
	 */
	public static CategoryDataset createDataset(Map<String, Integer> data) { 
		if (data == null || data.size() == 0) return null;
		
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    String type = "default";
	    
	    for (Map.Entry<String,Integer> entry : data.entrySet()) { 
	    		String key  = entry.getKey(); 
	    		Integer value = entry.getValue();
            dataset.addValue(value, type, key);
            //System.out.println(key + " " + value);
	    } 
	    
	    return dataset;  
	}  
}
