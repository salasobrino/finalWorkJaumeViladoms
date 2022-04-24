package com.example.demo4;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
//@Slf4j
public class RequestController {
	
	@Autowired
	AVRequestService aVRequestService;
	
	@Autowired
	StockPriceRpsyService stockPriceRpsyService;
	
	@Autowired
	StockResultsService stockResults;
	
	
	//Example--> http://localhost:8086/api/prices?symbol=IBM
	@GetMapping(value = "/prices",
			produces = MediaType.APPLICATION_JSON_VALUE)
	//We want to take in two parameters - the ticker and the number of days data to display
	public Iterable<StockPrice> getPrices(
			@RequestParam(value = "symbol", required = true) String symbol )
			
			throws IOException, ParseException {
		
	//	return aVRequestService.getStockData(symbol); --> change return of String to Iterable<StockPrice>
		
		aVRequestService.getStockData(symbol); //initialized StockPrice and save
		
		return stockPriceRpsyService.getStockPrice();  //return Json after request API
		
	}
	
	
	@GetMapping(value="/stockResults")
	public Iterable<StockResults> getStockResults(){
		
		return stockResults.getStockResults();
		
	} 
	
	
}

