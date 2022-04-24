package com.example.demo4;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockPriceRpsyService {
	
	private static final Logger log = LoggerFactory.getLogger(StockPriceRpsyService.class);
	
	@Autowired
	private StockPriceRepository stockPriceRepository;
	
	@Autowired
	private StockResultsService stockResults;
	
	
	private final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public Iterable<StockPrice> findAll(){
		
		return stockPriceRepository.findAll();
		
	}
	
	  public Optional<StockPrice> findbyId(Date date){ 
		  return  stockPriceRepository.findById(date); 
		  
	  }
	 
	 //Url copy for deserialized Json--> 
	 //https://github.com/jenn14108/Stock-Price-Checker-REST-service/blob/develop/complete/src/main/java/price/checker/service/AVRequestService.java
	  
	/**
	 * This method is used to parse the returned JSON data from Alpha Vantage to only display
	 * the stock prices for the number of days specified by the user
	 * REMEMBER: stock market opens at 9:30am so you will not get stock data from previous day until
	 * it's 9:30am...
	 * @param initialRes
	 * @return
	 * @throws IOException
	 */
	 
	public String saveAVPrices(String symbol, ResponseEntity<String> initialRes)throws IOException, ParseException {
		
	//create a List to store all Stock Price objects for batch save
	List<StockPrice> prices = new ArrayList<>();
	
	//create a JsonNode as the root
	JsonNode rootNode = new ObjectMapper().readTree(initialRes.getBody());
	
	//this is the chunk of JSON with all the stock info
	JsonNode timeSeriesStart = rootNode.get("Time Series (Daily)");
	Iterator<String> dates = timeSeriesStart.fieldNames();
	
	//we want to skip today, because prices and trading volume is still changing
	dates.next();
	
	while(dates.hasNext()) {
	
	String dateString = dates.next();
	
	JsonNode dateNode = timeSeriesStart.get(dateString);		
	StockPrice spObj = new StockPrice(symbol, DF. parse(dateString),
			Double.parseDouble(dateNode.findValue("1. open").asText()),
			Double.parseDouble(dateNode.findValue("2. high").asText()),
			Double.parseDouble(dateNode.findValue("3. low").asText()),
			Double.parseDouble(dateNode.findValue("4. close").asText()),
			Integer.parseInt(dateNode.findValue("5. volume").asText()));
	prices.add(spObj);
	
	log.info("We have successfully saved new objects {} of {} into db", spObj.getSymbol(), spObj.getDate());
		
	}
	
	stockResults.saveStockCalcMedian(prices.size(), prices);  //Initialize StockResults and save in table STOCK_RESULTS the id and median.
	
	stockPriceRepository.saveAll(prices);  //Receive data from API "https://www.alphavantage.co/" and save in table STOCK_PRICE
	
	return "Save Stock in Table Stock_Prices";
	
	}
	
	public Iterable<StockPrice> getStockPrice(){
		return stockPriceRepository.findAll();
	}
				
}
