package com.example.demo4;

import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//

@RequiredArgsConstructor
@Service
public class AVRequestService {
	
	private final String API_KEY = "T9MZ8RGN5IY26WT0";
	
	private static final Logger log = LoggerFactory.getLogger(AVRequestService.class);
	
	@Autowired
	StockPriceRpsyService stockPriceRpsyService;
	
	@Autowired
	StockPriceRepository stockPriceRepository;
		
	/*
	 * This method is used when have confirmed that the price data the user
	 * wants does not exist in our database. This method queries Alpha Vantage and
	 * passes a ResponseEntity object to StrockPriceRpsyService to save as StockPrice Objects
	 * @param symbol
	 * @param days
	 * @return
	 * @throws IOException
	 */
	
	public String getStockData (String symbol) throws IOException, ParseException {
		
		//Only One Share in Repository in H2 Table (Joan). This form cannot repead symbol
		stockPriceRepository.deleteAll();
		
		//the alpha vantage querying url ticker is a variable
		
		final String AlphaVantageUri = "https://www.alphavantage.co/query?" + 
				"function=TIME_SERIES_DAILY&" +
				"symbol={stock}&" +
				"outputsize=compact&" +
				"apikey=" + API_KEY;
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<String> initialRes = restTemplate.getForEntity(AlphaVantageUri, String.class, symbol);
		
		
		log.info("connect success with API", initialRes.toString());
		
		stockPriceRpsyService.saveAVPrices(symbol, initialRes);
		
		return initialRes.getBody();
		
		
	}
	
	

}
