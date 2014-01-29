package at.jku.ce.stockexchange.database;

import java.util.List;

import at.jku.ce.stockexchange.service.Exchange;
import at.jku.ce.stockexchange.service.ExchangeServiceImpl;

public class TestDatabase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Exchange> l;
		
		ExchangeServiceImpl exc = new ExchangeServiceImpl();
		exc.reset();
		
		Exchange e = exc.buyStock("AT123456789", 200);
		System.out.println(e.getStock().getIsin() + ";" + e.getOrder() + "|" + e.getExecution());
		
		exc.sellStock("AT123456789", 100);
		
		l = exc.getExchanges();
		
		for(Exchange ex : l){
			System.out.println("execution: " + ex.getExecution());
		}
		
	}

}
