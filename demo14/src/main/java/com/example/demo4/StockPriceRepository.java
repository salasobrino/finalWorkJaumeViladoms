package com.example.demo4;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

public interface StockPriceRepository extends CrudRepository<StockPrice, Date> {

	

}
