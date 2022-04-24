package com.example.demo4;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockResults {
	
	@Id
	private int id;
	
	private double median;
	
	public StockResults() {
		super();
	}
	
	public StockResults(int id, double median) {
		this.id= id;
		this.median = median;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMedian() {
		return median;
	}

	public void setMedian(double median) {
		this.median = median;
	}


	@Override
	public String toString() {
		return "StockResults [id=" + id + ", median=" + median + "]";
	}


}
