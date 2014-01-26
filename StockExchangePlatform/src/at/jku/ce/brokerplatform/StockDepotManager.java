package at.jku.ce.brokerplatform;

import java.util.ArrayList;
import java.util.HashMap;

public class StockDepotManager {
	
	private static StockDepotManager instance;
	private HashMap<String, ArrayList<StockDepotElement>> stocks;
	
	private StockDepotManager(){
		stocks = new HashMap<String, ArrayList<StockDepotElement>>();
	}
	
	public static StockDepotManager getInstance(){
		if(instance==null)
			instance = new StockDepotManager();
		return instance;
	}
	
	public void addStock(String user, StockDepotElement stock){
		int i;
		
		//initialize ArrayList if null
		if(!stocks.containsKey(user))
			stocks.put(user, new ArrayList<StockDepotElement>());
		
		//check if stock is already in the depot of the user
		for(i=0;i<stocks.get(user).size();i++){
			if(stocks.get(user).get(i).getIsin().equalsIgnoreCase(stock.getIsin())){
				stocks.get(user).get(i).increaseQuantity(stock.getQuantity());
				break;
			}
		}
		
		//stock is not already in depot
		if(i == stocks.get(user).size())
			stocks.get(user).add(stock);
	}
	
	public void removeStock(String user, StockDepotElement stock){
		//Check if stock exists
		if(stocks.containsKey(user)){
			//select stock
			for(int i=0;i<stocks.get(user).size();i++){
				if(stocks.get(user).get(i).getIsin().equalsIgnoreCase(stock.getIsin())){
					stocks.get(user).get(i).decreaseQuantity(stock.getQuantity());
					//delete if user has sold his last stocks
					if(stocks.get(user).get(i).getQuantity() == 0)
						stocks.get(user).remove(i);
					break;
				}
			}
		}
	}
	
	public ArrayList<StockDepotElement> getStocks(String user){
		return stocks.get(user);
	}
	
	public StockDepotElement getStock(String user, String isin, String mic){
		for(int i=0;i<stocks.get(user).size();i++){
			if(stocks.get(user).get(i).getIsin().equalsIgnoreCase(isin) &&
					stocks.get(user).get(i).getMic().equalsIgnoreCase(mic)){
				return stocks.get(user).get(i);
			}
		}
		return null;
	}
}
