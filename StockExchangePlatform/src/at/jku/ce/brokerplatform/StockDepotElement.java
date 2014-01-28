package at.jku.ce.brokerplatform;

import at.jku.ce.stockexchange.service.Stock;

public class StockDepotElement extends Stock {

	private String stockExchange;
	private int quantity;
	
	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
	
	public void increaseQuantity(int quantity){
		this.quantity += quantity;
	}
	
	public void decreaseQuantity(int quantity){
		this.quantity -= quantity;
	}
}
