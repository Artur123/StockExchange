package at.jku.ce.brokerplatform;

import at.jku.ce.stockexchange.service.Stock;

public class StockDepotElement extends Stock {

	private String mic;
	private int quantity;
	
	public String getMic() {
		return mic;
	}

	public void setMic(String mic) {
		this.mic = mic;
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
