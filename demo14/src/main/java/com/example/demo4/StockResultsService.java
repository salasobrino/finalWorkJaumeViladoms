package com.example.demo4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockResultsService {
	
	private static final Logger log = LoggerFactory.getLogger(StockPriceRpsyService.class);
	
	@Autowired
	StockPriceRepository repo;
	
	@Autowired
	StockResultsRepository repoRes;

	public Iterable<StockResults> saveStockCalcMedian(int id, List<StockPrice> sp) throws IOException{
			
		//List<Double> st1 = new ArrayList<Double>();
		
		List<StockResults> st = new ArrayList<StockResults>();
		
		Iterator<StockPrice> sp2 = sp.iterator();
		
		while(sp2.hasNext()) {
		
		for(int i=0; i<sp.size(); i++) {
			
			StockPrice s = sp2.next();
			
			int j= i+1;
			
			//System.out.println("Opcio 1 " + sp.get(i).getClouse());
			
			//double x = sp.get(i).getClouse();
			
			double x = s.getClouse();
			
			if(j<(sp.size()-1)) {
			
			//System.out.println("Opcio 2 " + sp.get(j).getClouse());
			
			double y = sp.get(j).getClouse();
			
			
			double rend = (x-y)/y;

				
			StockResults results = new StockResults (j, rend);
			
			log.info("We have successfully saved MEDIAN, objects {} of {} into db", i, results.getMedian());
				
			st.add(results);
					
				}
			}
		}
		
		return repoRes.saveAll(st);
	}
	
	public Iterable<StockResults> getStockResults(){
		return repoRes.findAll();
	}
	
}
	
	


