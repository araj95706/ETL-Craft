package org.example.intuitetllapplication.model;

import jakarta.persistence.Entity;

@Entity
public class MarketEvent extends BaseModel{
	
    private String stock;
    private String time;
    private String price;
    private String shares;


	public String getStock() {
		return stock;
	}
	
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getShares() {
		return shares;
	}
	
	public void setShares(String shares) {
		this.shares = shares;
	}

	@Override
	public String toString() {
		return "MarketEvent [stock=" + stock + ", time=" + time + ", price=" + price + ", shares=" + shares + "]";
	}
}
