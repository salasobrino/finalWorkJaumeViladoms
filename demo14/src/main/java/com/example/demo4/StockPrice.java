package com.example.demo4;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

//https://www.buggybread.com/2016/12/error-hibernate-orghibernatemappingexce.html  --> Serializable

@Entity
@Table(name="Stock_Price")
@IdClass(StockPrice.class)
public class StockPrice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 5)
	private String symbol;
	
	@Id
	@Column
	private Date date;
	
	//all the price information on the given date
	@Column
	private Double open;
	@Column
	private Double high;
	@Column
	private Double low;
	@Column
	private Double clouse;
	@Column
	private Integer volume;
	
	public StockPrice() {}
	
	

	public StockPrice(String symbol, Date date, 
						Double open, Double high, 
						Double low, Double clouse, Integer volume) {

		super();
		this.symbol = symbol;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.clouse = clouse;
		this.volume = volume;
	}



	public String getSymbol() {
		return symbol;
	}



	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



	public Date getDate() {
		return this.date;
	}
	

	public void setDate(Date date) {
		this.date = date;
	}



	public Double getOpen() {
		return open;
	}



	public void setOpen(Double open) {
		this.open = open;
	}



	public Double getHigh() {
		return high;
	}



	public void setHigh(Double high) {
		this.high = high;
	}



	public Double getLow() {
		return low;
	}



	public void setLow(Double low) {
		this.low = low;
	}



	public Double getClouse() {
		return clouse;
	}



	public void setClouse(Double clouse) {
		this.clouse = clouse;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	
	public String toString(){
        return "\n\tdate: " + this.getDate() + "\n\t\t" +
                "closing price: " + this.getClouse() + "\n\t\t" +
                "highest price: " + this.getHigh() + "\n\t\t" +
                "lowest price: " + this.getLow() + "\n\t\t" +
                "opening price " + this.getOpen() + "\n\t\t" +
                "trading volume: " + this.getVolume();
    }

	@Override
	public int hashCode() {
		return Objects.hash(clouse, date, high, low, open, symbol, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockPrice other = (StockPrice) obj;
		return Objects.equals(clouse, other.clouse) && Objects.equals(date, other.date)
				&& Objects.equals(high, other.high) && Objects.equals(low, other.low)
				&& Objects.equals(open, other.open) && Objects.equals(symbol, other.symbol)
				&& Objects.equals(volume, other.volume);
	}
	
}
